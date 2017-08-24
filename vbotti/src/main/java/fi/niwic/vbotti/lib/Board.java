package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.util.ArrayList;

/**
 * Pelikenttää ja tilannetta kuvaava luokka.
 * 
 * @author nic
 */
public class Board {
    
    final private int size;
    final private Tile[][] board;
    final private ArrayList<GoldMine> mines;
    
    /**
     * Parsii peliklientin luokasta kenttä ja pelitilanne.
     * 
     * @param board Klientin pelikenttä
     */
    public Board(GameState.Board board) {
        
        this.size = board.getSize();
        this.board = new Tile[size][size];
        this.mines = new ArrayList<>();
        
        int max = size * size * 2;
        for (int i = 0; i < max; i+=2) {
            int y = (i % (size * 2)) / 2;
            int x = i / (size * 2);
            char definition = board.getTiles().charAt(i);
            switch (definition) {
                case '#':
                    this.board[x][y] = new ImpassableWood();
                    break;
                case '[':
                    this.board[x][y] = new Tavern();
                    break;
                case '$':
                    char owner = board.getTiles().charAt(i + 1);
                    GoldMine mine;
                    if (owner == '-') {
                        mine = new GoldMine(size, new GameState.Position(x, y));
                    } else {
                        mine = new GoldMine(size, new GameState.Position(x, y), Character.getNumericValue(owner));
                    }
                    this.board[x][y] = new MinePointer(this.mines.size());
                    this.mines.add(mine);
                    break;
                default:
                    this.board[x][y] = new Free();
                    break;
            }
        }
        
        PathFinder.calculateDistancesToGoldMines(this);
    }
    
    
    private Board(int size, Tile[][] board, ArrayList<GoldMine> mines) {
        this.size = size;
        this.board = board;
        this.mines = mines;
    }
    
    public Board copy() {
        ArrayList<GoldMine> newMines = new ArrayList();
        for (GoldMine oldMine : mines) {
            newMines.add(new GoldMine(oldMine.getPosition(), oldMine.getOwner(), oldMine.distances));
        }
        
        return new Board(this.size, this.board, newMines);
    }
    
    public Tile getTile(GameState.Position position) {
        if (!isInsideBoard(position)) {
            return new ImpassableWood();
        }
        
        if (board[position.getX()][position.getY()] instanceof MinePointer) {
            MinePointer pointer = (MinePointer) board[position.getX()][position.getY()];
            return mines.get(pointer.pos);
        }
        
        return board[position.getX()][position.getY()];
    }
    
    /**
     * Palauttaa kentän koon.
     * 
     * @return kentän koko
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Listaa kaikki kaivokset.
     * 
     * @return kaivokset
     */
    public ArrayList<GoldMine> getMines() {
        return this.mines;
    }
    
    /**
     * Keroo voidaanko siirtyä tähän paikkaan.
     * 
     * @param position Paikka johon voisi siirtyä
     * @return kyllä/ei
     */
    public boolean isMovePossible(GameState.Position position) {
        if (!isInsideBoard(position)) return false;

        return getTile(position).isMovePossible();
    }
    
    /**
     * Onko positio pelikentällä?
     * @param position tarkistettava positio
     * @return onko kentällä?
     */
    public boolean isInsideBoard(GameState.Position position) {
        if (position.getX() < 0 || position.getX() == size) return false;
        if (position.getY() < 0 || position.getY() == size) return false;
        
        return true;
    }
    
    /**
     * Onko tässä paikassa metsää?
     * 
     * @param position Paikka
     * @return kyllä/ei
     */
    public boolean isImpassableWood(GameState.Position position) {
        return (getTile(position) instanceof ImpassableWood);
    }
    
    /**
     * Onko tässä paikassa taverna?
     * 
     * @param position Paikka
     * @return kyllä/ei
     */
    public boolean isTavern(GameState.Position position) {
        return (getTile(position) instanceof Tavern);
    }
    
    /**
     * Onko tässä paikassa kultakaivos?
     * 
     * @param position Paikka
     * @return kyllä/ei
     */
    public boolean isGoldMine(GameState.Position position) {
        return (getTile(position) instanceof GoldMine);
    }
    
    /**
     * Onko tässä paikassa vapaa kultakaivos?
     * 
     * @param position Paikka
     * @return kyllä/ei
     */
    public boolean isFreeGoldMine(GameState.Position position) {
        if (!isGoldMine(position)) return false;
        GoldMine goldMine = (GoldMine) getTile(position);
        return goldMine.isFree();
    }
    
    /**
     * Onko tässä paikassa pelaajan omistama kultakaivos?
     * 
     * @param position Paikka paikka mitä tarkistaa
     * @param hero Hero kyseessä oleva pelaaja
     * @return kyllä/ei
     */
    public boolean isHeroGoldMine(GameState.Position position, int heroId) {
        if (!isGoldMine(position)) return false;
        GoldMine goldMine = (GoldMine) getTile(position);
        return goldMine.isOwnedBy(heroId);
    }
    
    /**
     * Onko pelikentässä tyhjä ruutu?
     * 
     * @param position missä
     * @return onko tyhjä?
     */
    public boolean isFree(GameState.Position position) {
        return (getTile(position) instanceof Free);
    }
    
    /**
     * Onko kyseessä kiinnostava paikka?
     * 
     * @param position missä
     * @return onko kiinnostava?
     */
    public boolean isPOI(GameState.Position position) {
        return (getTile(position) instanceof POI);
    }
    
    /**
     * Palautta reitin pituuden lähimpään kultakaivokseen joka ei ole pelaajan.
     * 
     * Metodin aikavaativuus on O(G) missä G on kultakaivosten lukumäärä.
     * 
     * @param from mistä lähdetään etsimään
     * @param heroId minkä pelaajan omistuksessa kaivos ei saa olla
     * @return pituus lähimpään kultakaivokseen
     */
    public int distanceToClosestGoldMineFrom(GameState.Position from, int heroId) {
        int minDistance = Integer.MAX_VALUE;
        for (GoldMine mine : mines) {
            if (!mine.isOwnedBy(heroId)) {
                if (mine.getDistance(from) < minDistance) {
                    minDistance = mine.getDistance(from);
                }
            }
        }
        
        return minDistance;
    }
    
}
