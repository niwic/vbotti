package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class PathFinderTest extends PathFinder {

    private Board board;
    
    @Before
    public void setUp() {
        GameState.Board gsb = TestBoardTwo.getBoard();
        board = new Board(gsb);
        calculateDistancesToPOIS(board);
    }
    
    @Test
    public void checkDistance() {
        GameState.Position from = new GameState.Position(0,0);
        assertEquals(5, board.distanceToClosestGoldMineFrom(from, 5));
    }
    
    @Test
    public void checkDistanceFromSelf() {
        GameState.Position from = new GameState.Position(3,2);
        assertEquals(0, board.distanceToClosestGoldMineFrom(from, 5));
    }
    
}
