package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.bot.BotMove;
import com.brianstempin.vindiniumclient.dto.GameState;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MoveTest {

    @Test
    public void checkFrom() {
        GameState.Position position = new GameState.Position(1, 1);
        assertEquals(new GameState.Position(0, 1), Move.LEFT.from(position));
        assertEquals(new GameState.Position(2, 1), Move.RIGHT.from(position));
        assertEquals(new GameState.Position(1, 0), Move.UP.from(position));
        assertEquals(new GameState.Position(1, 2), Move.DOWN.from(position));
        assertEquals(position, Move.STAY.from(position));
    }
    
    @Test
    public void checkIsOneMoveAwayVertical() {
        GameState.Position pos1 = new GameState.Position(1, 0);
        GameState.Position pos2 = new GameState.Position(1, 1);
        assertTrue(Move.isOneMoveAway(pos1, pos2));
    }
    
    @Test
    public void checkIsOneMoveAwayHorizontal() {
        GameState.Position pos1 = new GameState.Position(0, 1);
        GameState.Position pos2 = new GameState.Position(1, 1);
        assertTrue(Move.isOneMoveAway(pos1, pos2));
    }
    
    @Test
    public void checkIsOneMoveAwayDiagonal() {
        GameState.Position pos1 = new GameState.Position(0, 1);
        GameState.Position pos2 = new GameState.Position(1, 0);
        assertFalse(Move.isOneMoveAway(pos1, pos2));
    }
    
    @Test
    public void checkIsOneMoveAwayTooFar() {
        GameState.Position pos1 = new GameState.Position(0, 1);
        GameState.Position pos2 = new GameState.Position(2, 1);
        assertFalse(Move.isOneMoveAway(pos1, pos2));
    }
 
    @Test
    public void checkToBotMove() {
        assertEquals(BotMove.WEST, Move.LEFT.toBotMove());
        assertEquals(BotMove.EAST, Move.RIGHT.toBotMove());
        assertEquals(BotMove.NORTH, Move.UP.toBotMove());
        assertEquals(BotMove.SOUTH, Move.DOWN.toBotMove());
        assertEquals(BotMove.STAY, Move.STAY.toBotMove());
    }
    
}
