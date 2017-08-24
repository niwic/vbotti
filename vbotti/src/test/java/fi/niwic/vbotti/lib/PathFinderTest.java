package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import org.junit.Before;
import org.junit.Test;

public class PathFinderTest extends PathFinder {

    private Board board;
    
    @Before
    public void setUp() {
        GameState.Board gsb = TestBoardTwo.getBoard();
        board = new Board(gsb);
        calculateDistancesToGoldMines(board);
    }
    
    @Test
    public void checkDistance() {
        GameState.Position from = new GameState.Position(0,0);
        board.distanceToClosestGoldMineFrom(from, 5);
    }
    
}
