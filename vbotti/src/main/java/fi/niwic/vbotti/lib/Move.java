package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public enum Move {
    STAY(0), LEFT(1), RIGHT(2), UP(3), DOWN(4);
    
    private int move;
    Move(int move) {
        this.move = move;
    }
    
    public GameState.Position from(GameState.Position from) {
        switch(move) {
            case 1: return new GameState.Position(from.getX() - 1, from.getY());
            case 2: return new GameState.Position(from.getX() + 1, from.getY());
            case 3: return new GameState.Position(from.getX(), from.getY() - 1);
            case 4: return new GameState.Position(from.getX(), from.getY() + 1);
            default: return from;
        }
    }
    
    public static boolean isOneMoveAway(GameState.Position pos1, GameState.Position pos2) {
        int xDiff = Math.abs(pos1.getX() - pos2.getX());
        int yDiff = Math.abs(pos1.getY() - pos2.getY());
        
        int moveDiff = xDiff + yDiff;
        
        return moveDiff == 1;
    }
}
