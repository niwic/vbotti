package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BoardExtTest {

    private static int testBoardOneSize = 18;
    private static String testBoardOneTiles = 
              "##############        ##############"
            + "##############        ##############"
            + "################    ################"
            + "##############$4    $4##############"
            + "##############  @4    ##############"
            + "##########  @1##    ##    ##########"
            + "##########  []        []  ##########"
            + "########        ####        ########"
            + "############  $4####$4  ############"
            + "############  $4####$4  ############"
            + "########        ####        ########"
            + "##########  []        []  ##########"
            + "##########  @2##    ##@3  ##########"
            + "##############        ##############"
            + "##############$-    $-##############"
            + "################    ################"
            + "##############        ##############"
            + "  ############        ##############";
    
    private BoardExt board;
    
    @Before
    public void createBoard() {
        GameState.Board gsb = new GameState.Board(testBoardOneTiles, testBoardOneSize);
        board = new BoardExt(gsb);
    }
    
    @Test
    public void checkIsImpassableWoodAtX0Y0() {
        GameState.Position pos = new GameState.Position(0, 0);
        assertTrue(board.isImpassableWood(pos));
    }
    
    @Test
    public void checkIsImpassableWoodAtX6Y0() {
        GameState.Position pos = new GameState.Position(6, 0);
        assertTrue(board.isImpassableWood(pos));
    }
    
    @Test
    public void checkIsMovePossibleX6Y0() {
        GameState.Position pos = new GameState.Position(6, 0);
        assertFalse(board.isMovePossible(pos));
    }
    
    @Test
    public void checkMovePossibleX7Y0() {
        GameState.Position pos = new GameState.Position(7, 0);
        assertTrue(board.isMovePossible(pos));
    }
    
    @Test
    public void checkIsHeroX8Y5() {
        GameState.Position pos = new GameState.Position(8, 4);
        assertTrue(board.isHero(pos));
    }
    
    @Test
    public void checkIsMovePossibleX8Y4() {
        GameState.Position pos = new GameState.Position(8, 4);
        assertFalse(board.isMovePossible(pos));
    }
    
    @Test
    public void checkIsHeroX6Y5() {
        GameState.Position pos = new GameState.Position(6, 5);
        GameState.Hero hero = new GameState.Hero(1, "test", "test", 0, pos, 0, 0, 0, pos, false);
        assertTrue(board.isHero(pos, hero));
    }
    
    @Test
    public void checkIsTavernX6Y6() {
        GameState.Position pos = new GameState.Position(6, 6);
        assertTrue(board.isTavern(pos));
    }
    
    @Test
    public void checkIsGoldMineX6Y3() {
        GameState.Position pos = new GameState.Position(7, 3);
        assertTrue(board.isGoldMine(pos));
    }
    
    @Test
    public void checkIsFreeGoldMineX7Y14() {
        GameState.Position pos = new GameState.Position(7, 14);
        assertTrue(board.isFreeGoldMine(pos));
    }
    
    @Test
    public void checkIsHeroGoldMineX7Y14() {
        GameState.Position pos = new GameState.Position(10, 3);
        GameState.Hero hero = new GameState.Hero(4, "test", "test", 0, pos, 0, 0, 0, pos, false);
        assertTrue(board.isHeroGoldMine(pos, hero));
    }
   
    @Test
    public void checkIsMovePossibleX0Y17() {
        GameState.Position pos = new GameState.Position(0, 17);
        assertTrue(board.isMovePossible(pos));
    }
    
    @Test
    public void checkIsMovePossibleXNegY0() {
        GameState.Position pos = new GameState.Position(-1, 0);
        assertFalse(board.isMovePossible(pos));
    }
       
    @Test
    public void checkIsMovePossibleX0Yneg() {
        GameState.Position pos = new GameState.Position(0, -1);
        assertFalse(board.isMovePossible(pos));
    }
    
    @Test
    public void checkIsMovePossibleXoverY0() {
        GameState.Position pos = new GameState.Position(testBoardOneSize, 0);
        assertFalse(board.isMovePossible(pos));
    }
    
    @Test
    public void checkIsMovePossibleX0Yover() {
        GameState.Position pos = new GameState.Position(0, testBoardOneSize);
        assertFalse(board.isMovePossible(pos));
    }
    
}
