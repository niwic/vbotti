package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import java.util.ArrayList;
import java.util.List;

/**
 * Pelikenttää ja tilannetta kuvaava luokka.
 * 
 * @author nic
 */
public class Board {
    
    private int size;
    private Tile[][] board;
    private List<GoldMine> mines;
    
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
            int x = (i % (size * 2)) / 2;
            int y = i / (size * 2);
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
                    if (owner == '-') {
                        this.mines.add(new GoldMine(new GameState.Position(x, y)));
                    } else {
                        this.mines.add(new GoldMine(new GameState.Position(x, y), Character.getNumericValue(owner)));
                    }
                default:
                    this.board[x][y] = new Free();
                    break;
            }
        }
    }
    
    public Tile getTile(GameState.Position position) {
        if (board[position.getX()][position.getY()] instanceof Free) {
            int mine = getGoldMine(position);
            if (mine > -1) return mines.get(mine);
            
            return board[position.getX()][position.getY()];
        } else {
            return board[position.getX()][position.getY()];
        }
    }
    
    private int getGoldMine(GameState.Position position) {
        for (int i = 0; i < mines.size(); i++) {
            GoldMine mine = mines.get(i);
            if (mine.atPosition(position)) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Keroo voidaanko siirtyä tähän paikkaan.
     * 
     * @param position Paikka johon voisi siirtyä
     * @return kyllä/ei
     */
    public boolean isMovePossible(GameState.Position position) {
        if (position.getX() < 0 || position.getX() == size) return false;
        if (position.getY() < 0 || position.getY() == size) return false;

        return getTile(position).isMovePossible();
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
     * @param position Paikka
     * @return kyllä/ei
     */
    public boolean isHeroGoldMine(GameState.Position position, GameState.Hero hero) {
        if (!isGoldMine(position)) return false;
        GoldMine goldMine = (GoldMine) getTile(position);
        return goldMine.isOwnedBy(hero.getId());
    }
    
}
