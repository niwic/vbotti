package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public class BoardExt {

    private GameState.Board board;
    
    public BoardExt(GameState.Board board) {
        this.board = board;
    }
    
    public boolean isMovePossible(GameState.Position position) {
        if (position.getX() < 0 || position.getX() == board.getSize()) return false;
        if (position.getY() < 0 || position.getY() == board.getSize()) return false;

        return !isImpassableWood(position) && !isHero(position);
    }
    
    private int startIndex(GameState.Position position) {
        return position.getX() * 2 + position.getY() * board.getSize() * 2;
    }
    
    private String getTile(GameState.Position position) {
        int startIndex = startIndex(position);
        int endIndex = startIndex + 2;
        // TODO: Onko substring ok?
        return board.getTiles().substring(startIndex, endIndex);
    }
    
    public boolean isImpassableWood(GameState.Position position) {
        String tile = getTile(position);
        return "##".equals(tile);
    }
    
    public boolean isHero(GameState.Position position) {
        String tile = getTile(position);
        return "@".charAt(0) == tile.charAt(0);
    }
    
    public boolean isHero(GameState.Position position, GameState.Hero hero) {
        String tile = getTile(position);
        int id = hero.getId();
        return ("@"+id).equals(tile);
    }
    
    public boolean isTavern(GameState.Position position) {
        String tile = getTile(position);
        return "[]".equals(tile);
    }
    
    public boolean isGoldMine(GameState.Position position) {
        String tile = getTile(position);
        return "$".charAt(0) == tile.charAt(0);
    }
    
    public boolean isFreeGoldMine(GameState.Position position) {
        String tile = getTile(position);
        return "$-".equals(tile);
    }
    
    public boolean isHeroGoldMine(GameState.Position position, GameState.Hero hero) {
        String tile = getTile(position);
        int id = hero.getId();
        return ("$"+id).equals(tile);
    }
    
}
