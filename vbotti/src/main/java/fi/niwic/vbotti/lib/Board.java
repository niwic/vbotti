package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.util.ArrayList;

/**
 * Pelikenttää kuvaava luokka.
 */
public class Board {
    
    final private int size;
    final private Tile[][] board;
    final private ArrayList<GoldMine> mines;
    final private ArrayList<Tavern> taverns;
    
    /**
     * Parsii peliklientin luokasta kenttä ja pelitilanne.
     * 
     * Kultakaivoksen tallennetaan "osoittimina", jotta pelikentän kopioiminen
     * olisi helpompaa. Kultakaivoksessa on tieto kaivoksen omistajasta. Voidaan
     * siis tällä tavalla kopioida vain kultakaivoslista, eikä tavitse kopioida
     * koko pelikenttä kun pelitilanne mutatoituu.
     * 
     * Lopussa lasketaan myös etäisyydet jokaiseen kiinnostavaan kohteeseen,
     * eli käytännössä jokaiseen kultakaivokseen ja tavernaan.
     * 
     * Algoritmin aikavaativuus on O(R + D) missä R on pelikentän ruutujen 
     * lukumäärä, ja D on reitinhakualgoritmin aikavaativuus.
     * 
     * @param board Klientin pelikenttä
     */
    public Board(GameState.Board board) {
        
        this.size = board.getSize();
        this.board = new Tile[size][size];
        this.mines = new ArrayList<>();
        this.taverns = new ArrayList<>();
        
        int max = size * size * 2;
        for (int i = 0; i < max; i += 2) {
            int x = i / (size * 2);
            int y = (i % (size * 2)) / 2;
            char definition = board.getTiles().charAt(i);
            switch (definition) {
                case '#':
                    this.board[y][x] = new ImpassableWood();
                    break;
                case '[':
                    Tavern tavern = new Tavern(size, new GameState.Position(x, y));
                    this.board[y][x] = tavern;
                    this.taverns.add(tavern);
                    break;
                case '$':
                    char owner = board.getTiles().charAt(i + 1);
                    GoldMine mine;
                    if (owner == '-') {
                        mine = new GoldMine(size, new GameState.Position(x, y));
                    } else {
                        mine = new GoldMine(size, new GameState.Position(x, y), Character.getNumericValue(owner));
                    }
                    this.board[y][x] = new MinePointer(this.mines.size());
                    this.mines.add(mine);
                    break;
                default:
                    this.board[y][x] = new Free();
                    break;
            }
        }
        
        PathFinder.calculateDistancesToPOIS(this);
    }
    
    private Board(int size, Tile[][] board, ArrayList<GoldMine> mines, ArrayList<Tavern> taverns) {
        this.size = size;
        this.board = board;
        this.mines = mines;
        this.taverns = taverns;
    }
    
    /**
     * Luodaan kopio pelikentästä.
     * 
     * Tässä kopioidaan kultakavokset, koska niissä on tieto kultakaivoksen
     * omistajasta. Aikavaativuus on O(G) missä G on kultakaivosten lukumäärä.
     * 
     * @return kopio pelikentästä
     */
    public Board copy() {
        ArrayList<GoldMine> newMines = new ArrayList();
        for (GoldMine oldMine : mines) {
            newMines.add(new GoldMine(oldMine.getPosition(), oldMine.getOwner(), oldMine.distances));
        }
        
        return new Board(this.size, this.board, newMines, taverns);
    }
    
    /**
     * Palauttaa tietyn pelikentän ruudun.
     * 
     * Jos ruutu on pelikentän ulkopuolella, palutetaan ImpassableWood. Jos 
     * ruutu osoitin kultakaivokseen, palautetaan osoittimen indikoiva
     * kultakaivos. Muuten palautetaan ruudun sisältö suoraan.
     * 
     * @param position mikä ruutu
     * @return ruudun sisältö
     */
    public Tile getTile(GameState.Position position) {
        if (!isInsideBoard(position)) {
            return new ImpassableWood();
        }
        
        if (board[position.getY()][position.getX()] instanceof MinePointer) {
            MinePointer pointer = (MinePointer) board[position.getY()][position.getX()];
            return mines.get(pointer.pos);
        }
        
        return board[position.getY()][position.getX()];
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
        if (!isInsideBoard(position)) {
            return false;
        }

        return getTile(position).isMovePossible();
    }
    
    /**
     * Onko positio pelikentällä?
     * @param position tarkistettava positio
     * @return onko kentällä?
     */
    public boolean isInsideBoard(GameState.Position position) {
        if (position.getX() < 0 || position.getX() == size) {
            return false;
        }
        
        if (position.getY() < 0 || position.getY() == size) {
            return false;
        }
        
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
        if (!isGoldMine(position)) {
            return false;
        } else {
            GoldMine goldMine = (GoldMine) getTile(position);
            return goldMine.isFree();
        }
    }
    
    /**
     * Onko tässä paikassa pelaajan omistama kultakaivos?
     * 
     * @param position paikka mitä tarkistaa
     * @param heroId kyseessä oleva pelaajan id
     * @return kyllä/ei
     */
    public boolean isHeroGoldMine(GameState.Position position, int heroId) {
        if (!isGoldMine(position)) {
            return false;
        }
        
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
    
    /**
     * Palauttaa reitin pituuden lähimpään tavernaan.
     * 
     * Metodin aikavaativuus on O(T) missä T on tavernojen lukumäärä.
     * 
     * @param from mistä lähdetään etsimään
     * @return pituus lähimpään kultakaivokseen
     */
    public int distanceToClosestTavernFrom(GameState.Position from) {
        int minDistance = Integer.MAX_VALUE;
        for (Tavern tavern : taverns) {
            if (tavern.getDistance(from) < minDistance) {
                minDistance = tavern.getDistance(from);
            }
        }
        
        return minDistance;
    }
    
}
