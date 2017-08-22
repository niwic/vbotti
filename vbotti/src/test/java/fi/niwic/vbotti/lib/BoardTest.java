package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.util.ArrayList;
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
            GoldMine copyMine = copyBoard.getMines().get(i);
            assertNotEquals(board.getMines().get(i), copyMine);
            assertEquals(copyBoard.getMines().get(i), copyBoard.getTile(copyMine.getPosition()));
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
        GameState.Position pos = new GameState.Position(0, 7);
        assertTrue(board.isMovePossible(pos));
    }
    
    @Test
    public void checkIsMovePossibleX8Y4() {
        GameState.Position pos = new GameState.Position(4, 8);
        assertTrue(board.isMovePossible(pos));
    }
    
    @Test
    public void checkIsTavernX6Y6() {
        GameState.Position pos = new GameState.Position(6, 6);
        assertTrue(board.isTavern(pos));
    }
    
    @Test
    public void checkIsGoldMineX6Y3() {
        GameState.Position pos = new GameState.Position(3, 7);
        assertTrue(board.isGoldMine(pos));
    }
    
    @Test
    public void checkIsFreeGoldMineX7Y14() {
        GameState.Position pos = new GameState.Position(14, 7);
        assertTrue(board.isFreeGoldMine(pos));
    }
    
    @Test
    public void checkIsFreeGoldMineX8Y14() {
        GameState.Position pos = new GameState.Position(8, 14);
        assertFalse(board.isFreeGoldMine(pos));
    }
    
    @Test
    public void checkIsHeroGoldMineX10Y3() {
        GameState.Position pos = new GameState.Position(3, 10);
        GameState.Hero hero = new GameState.Hero(4, "test", "test", 0, pos, 0, 0, 0, pos, false);
        assertTrue(board.isHeroGoldMine(pos, hero.getId()));
    }
    
    @Test
    public void checkIsHeroGoldMineX11Y3() {
        GameState.Position pos = new GameState.Position(11, 3);
        GameState.Hero hero = new GameState.Hero(4, "test", "test", 0, pos, 0, 0, 0, pos, false);
        assertFalse(board.isHeroGoldMine(pos, hero.getId()));
    }
   
    @Test
    public void checkIsMovePossibleX0Y17() {
        GameState.Position pos = new GameState.Position(17, 0);
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
    public void checkGetTileOutsideBoard() {
        Tile result = board.getTile(new GameState.Position(-1, -1));
        assertTrue(result instanceof ImpassableWood);
    }
    
    @Test
    public void checkSize() {
        assertEquals(board.getSize(), 18);
    }
    
    @Test
    public void checkGetMines() {
        ArrayList<GoldMine> mines = new ArrayList();
        mines.add(new GoldMine(0, new GameState.Position(3,7), 4));
        mines.add(new GoldMine(0, new GameState.Position(3,10), 4));
        mines.add(new GoldMine(0, new GameState.Position(8,7), 4));
        mines.add(new GoldMine(0, new GameState.Position(8,10), 4));
        mines.add(new GoldMine(0, new GameState.Position(9,7), 4));
        mines.add(new GoldMine(0, new GameState.Position(9,10), 4));
        mines.add(new GoldMine(0, new GameState.Position(14,7), 0));
        mines.add(new GoldMine(0, new GameState.Position(14,10), 0));
        
        for (int i = 0; i < board.getMines().size(); i++) {
            GoldMine mine1 = board.getMines().get(i);
            GoldMine mine2 = mines.get(i);
            assertTrue(mine1.isOwnedBy(mine2.getOwner()));
            assertTrue(mine1.atPosition(mine2.getPosition()));
        }
    }
    
    @Test
    public void checkDistanceToClosestGoldMineFromX7Y1() {
        GameState.Position from = new GameState.Position(1, 7);
        assertEquals(4, board.distanceToClosestGoldMineFrom(from, 0));
    }
    
    @Test
    public void checkDistanceToClosestGoldMineFromX8Y1() {
        GameState.Position from = new GameState.Position(1, 8);
        assertEquals(3, board.distanceToClosestGoldMineFrom(from, 0));
    }
    
    @Test
    public void checkDistanceToClosestGoldMineFromX8Y1NotOwnedBy4() {
        GameState.Position from = new GameState.Position(1, 8);
        assertEquals(18, board.distanceToClosestGoldMineFrom(from, 4));
    }
    
    @Test
    public void checkDistanceToClosestGoldMineMaxValue() {
        GameState.Board gsb = TestBoardTwo.getBoard();
        Board boardTwo = new Board(gsb);
        GameState.Position from = new GameState.Position(0, 0);
        assertEquals(Integer.MAX_VALUE, boardTwo.distanceToClosestGoldMineFrom(from, 0));
    }
    
}
