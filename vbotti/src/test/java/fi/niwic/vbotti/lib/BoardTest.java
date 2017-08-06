package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    private Board board;
    
    @Before
    public void createBoard() {
        GameState.Board gsb = TestBoardOne.getBoard();
        GameState.Hero me = new GameState.Hero(1, "test1", "test1", 1, new GameState.Position(6, 4), 1, 1, 1, null, false);
        GameState.Hero opp1 = new GameState.Hero(2, "test2", "test2", 1, new GameState.Position(6, 11), 1, 1, 1, null, false);
        GameState.Hero opp2 = new GameState.Hero(3, "test3", "test3", 1, new GameState.Position(11, 12), 1, 1, 1, null, false);
        GameState.Hero opp3 = new GameState.Hero(4, "test4", "test4", 1, new GameState.Position(8, 4), 1, 1, 1, null, false);
        GameState.Hero[] opponents = new GameState.Hero[]{opp1, opp2, opp3};
        board = new Board(gsb);
    }
    
    @Test
    public void checkCopy() {
        Board copyBoard = board.copy();
        for (int i = 0; i < board.getMines().size(); i++) {
            assertNotEquals(board.getMines().get(i), copyBoard.getMines().get(i));
        }
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
    
    /*
    @Test
    public void checkIsHeroX8Y4() {
        GameState.Position pos = new GameState.Position(8, 4);
        assertTrue(board.isHero(pos));
    }
    */
    
    @Test
    public void checkIsMovePossibleX8Y4() {
        GameState.Position pos = new GameState.Position(8, 4);
        assertTrue(board.isMovePossible(pos));
    }
    
    /*
    @Test
    public void checkIsHeroX6Y5() {
        GameState.Position pos = new GameState.Position(6, 5);
        GameState.Hero hero = new GameState.Hero(1, "test", "test", 0, pos, 0, 0, 0, pos, false);
        assertTrue(board.isHero(pos, hero));
    }
    */
    
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
    public void checkIsFreeGoldMineX8Y14() {
        GameState.Position pos = new GameState.Position(8, 14);
        assertFalse(board.isFreeGoldMine(pos));
    }
    
    @Test
    public void checkIsHeroGoldMineX10Y3() {
        GameState.Position pos = new GameState.Position(10, 3);
        GameState.Hero hero = new GameState.Hero(4, "test", "test", 0, pos, 0, 0, 0, pos, false);
        assertTrue(board.isHeroGoldMine(pos, hero));
    }
    
    @Test
    public void checkIsHeroGoldMineX11Y3() {
        GameState.Position pos = new GameState.Position(11, 3);
        GameState.Hero hero = new GameState.Hero(4, "test", "test", 0, pos, 0, 0, 0, pos, false);
        assertFalse(board.isHeroGoldMine(pos, hero));
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
        GameState.Position pos = new GameState.Position(board.getSize(), 0);
        assertFalse(board.isMovePossible(pos));
    }
    
    @Test
    public void checkIsMovePossibleX0Yover() {
        GameState.Position pos = new GameState.Position(0, board.getSize());
        assertFalse(board.isMovePossible(pos));
    }
    
    @Test
    public void checkSize() {
        assertEquals(board.getSize(), 18);
    }
    
    @Test
    public void checkGetMines() {
        List<GoldMine> mines = new ArrayList();
        mines.add(new GoldMine(new GameState.Position(7,3), 4));
        mines.add(new GoldMine(new GameState.Position(10,3), 4));
        mines.add(new GoldMine(new GameState.Position(7,8), 4));
        mines.add(new GoldMine(new GameState.Position(10,8), 4));
        mines.add(new GoldMine(new GameState.Position(7,9), 4));
        mines.add(new GoldMine(new GameState.Position(10,9), 4));
        mines.add(new GoldMine(new GameState.Position(7,14), 0));
        mines.add(new GoldMine(new GameState.Position(10,14), 0));
        
        for (int i = 0; i < board.getMines().size(); i++) {
            GoldMine mine1 = board.getMines().get(i);
            GoldMine mine2 = mines.get(i);
            assertTrue(mine1.isOwnedBy(mine2.getOwner()));
            assertTrue(mine1.atPosition(mine2.getPosition()));
        }
    }
    
}
