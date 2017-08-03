package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public class BoardExt {
    
    private int size;
    private Tile[][] board;
    
    public BoardExt(GameState.Board board) {
        this.size = board.getSize();
        this.board = new Tile[size][size];
        
        int max = size * size * 2;
        for (int i = 0; i < max; i+=2) {
            int x = (i % (size * 2)) / 2;
            int y = i / (size * 2);
            char definition = board.getTiles().charAt(i);
            switch (definition) {
                case '#':
                    this.board[x][y] = new ImpassableWood();
                    break;
                case '@':
                    int id = Character.getNumericValue(board.getTiles().charAt(i + 1));
                    this.board[x][y] = new Hero(id);
                    break;
                case '$':
                    char owner = board.getTiles().charAt(i + 1);
                    if (owner == '-') {
                        this.board[x][y] = new GoldMine();
                    } else {
                        this.board[x][y] = new GoldMine(Character.getNumericValue(owner));
                    }
                    break;
                case '[':
                    this.board[x][y] = new Tavern();
                    break;
                default:
                    this.board[x][y] = new Free();
                    break;
            }
        }
    }
    
    public BoardExt move(GameState.Hero hero, GameState.Position to) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean isMovePossible(GameState.Position position) {
        if (position.getX() < 0 || position.getX() == size) return false;
        if (position.getY() < 0 || position.getY() == size) return false;

        return board[position.getX()][position.getY()].isMovePossible();
    }
    
    public boolean isImpassableWood(GameState.Position position) {
        return (board[position.getX()][position.getY()] instanceof ImpassableWood);
    }
    
    public boolean isHero(GameState.Position position) {
        return (board[position.getX()][position.getY()] instanceof Hero);
    }
    
    public boolean isHero(GameState.Position position, GameState.Hero hero) {
        Tile tile = board[position.getX()][position.getY()];
        if (!(tile instanceof Hero)) return false;
        Hero heroTile = (Hero) tile;
        return heroTile.getId() == hero.getId();
    }
    
    public boolean isTavern(GameState.Position position) {
        return (board[position.getX()][position.getY()] instanceof Tavern);
    }
    
    public boolean isGoldMine(GameState.Position position) {
        return (board[position.getX()][position.getY()] instanceof GoldMine);
    }
    
    public boolean isFreeGoldMine(GameState.Position position) {
        Tile tile = board[position.getX()][position.getY()];
        if (!(tile instanceof GoldMine)) return false;
        GoldMine goldMine = (GoldMine) tile;
        return goldMine.isFree();
    }
    
    public boolean isHeroGoldMine(GameState.Position position, GameState.Hero hero) {
        Tile tile = board[position.getX()][position.getY()];
        if (!(tile instanceof GoldMine)) return false;
        GoldMine goldMine = (GoldMine) tile;
        return goldMine.isOwnedBy(hero.getId());
    }
    
}
