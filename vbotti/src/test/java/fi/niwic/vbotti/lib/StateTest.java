package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class StateTest {
 
    private GameState.Hero me;
    private GameState gs;
    private State state;
    
    @Before
    public void setUp() {
        me = GameStateGenerator.createHero(1, new GameState.Position(0, 7));
        gs = GameStateGenerator.createGameState(createHeroes(me), me);
        state = new State(gs);
    }
    
    private ArrayList<GameState.Hero> createHeroes(GameState.Hero me) {
        ArrayList<GameState.Hero> heroes = new ArrayList();
        
        for (int i = 0; i < 4; i++) {
            if (i+1 != me.getId()) {
                GameState.Position position = new GameState.Position(4+i, 7+i);
                heroes.add(GameStateGenerator.createHero(i+1, position, i));
            }
        }
        
        heroes.add(me);
        
        return heroes;
    }
    
    @Test
    public void checkGetHeroes() {
        Hero[] heroes = state.getHeroes();
        
        assertTrue(heroes.length > 1);
        assertTrue(heroes.length - 1 == gs.getGame().getHeroes().size());
        
        for (GameState.Hero hero : gs.getGame().getHeroes()) {
            assertEquals(hero.getId(), heroes[hero.getId()].getId());
        }
    }
    
    @Test
    public void checkGetMe() {
        assertEquals(gs.getHero().getId(), state.getMe().getId());
    }
    
    @Test
    public void checkIsFinished() {
        State afterMove = state;
        for (int i = 0; i < 100; i++) {
            afterMove = afterMove.move(1, Move.STAY);
        }
        
        assertTrue(afterMove.isFinished());
    }
    
    @Test
    public void checkIsNotFinished() {
        State afterMove = state;
        for (int i = 0; i < 9; i++) {
            afterMove = afterMove.move(1, Move.STAY);
        }
        
        assertFalse(afterMove.isFinished());
    }
    
    @Test
    public void checkMyMoveNotPossible() {
        State afterMove = state.move(me.getId(), Move.LEFT);
        assertEquals(state.getMe().position, afterMove.getMe().position);
        assertEquals(state.getTurn() + 1, afterMove.getTurn());
    }
    
    @Test
    public void checkGetResult() {
        State afterMove = state.move(4, Move.STAY);
        assertEquals(0, afterMove.getResult());
    }
    
    @Test
    public void checkMyMovePossible() {
        State afterMove = state.move(me.getId(), Move.RIGHT);
        assertEquals(Move.RIGHT.from(state.getMe().position), afterMove.getMe().position);
        assertEquals(state.getTurn() + 1, afterMove.getTurn());
    }
    
    @Test
    public void checkMyMoveCapturesMine() {
        State afterMove = state
                .move(me.getId(), Move.RIGHT)
                .move(me.getId(), Move.RIGHT)
                .move(me.getId(), Move.DOWN)
                .move(me.getId(), Move.DOWN)
                .move(me.getId(), Move.DOWN)
                .move(me.getId(), Move.RIGHT);
        
        GameState.Position afterMovePosition = new GameState.Position(me.getPos().getX() + 3, me.getPos().getY() + 2);
        assertEquals(afterMovePosition, afterMove.getMe().position);
        assertEquals(state.getTurn() + 6, afterMove.getTurn());
        
        assertEquals(0, state.getMe().getGold());
        assertEquals(1, afterMove.getMe().getGold());
        
        assertEquals(100, state.getMe().getLife());
        assertEquals(100 - 20 - 6, afterMove.getMe().getLife());
    }
    
    @Test
    public void checkMyMoveFights() {
        State afterMove = state
                .move(me.getId(), Move.RIGHT)
                .move(me.getId(), Move.DOWN)
                .move(me.getId(), Move.DOWN)
                .move(me.getId(), Move.DOWN)
                .move(me.getId(), Move.DOWN);
        
        GameState.Position afterMovePosition = new GameState.Position(me.getPos().getX() + 4, me.getPos().getY() + 1);
        assertEquals(afterMovePosition, afterMove.getMe().position);
        assertEquals(state.getTurn() + 5, afterMove.getTurn());
        
        assertEquals(100, state.getMe().getLife());
        assertEquals(100 - 5, afterMove.getMe().getLife());
        
        assertEquals(100, state.getHeroes()[2].getLife());
        assertEquals(100 - 20, afterMove.getHeroes()[2].getLife());
        
        assertEquals(1, state.getHeroes()[2].getMineCount());
        assertEquals(1, afterMove.getHeroes()[2].getMineCount());
        
        assertEquals(0, state.getMe().getMineCount());
        assertEquals(0, afterMove.getMe().getMineCount());
    }
    
    @Test
    public void checkOpponenMoveHasADrink() {
        Hero opponent = state.getHeroes()[4];
        State afterMove = state
                .move(opponent.getId(), Move.RIGHT)
                .move(opponent.getId(), Move.UP);
        
        GameState.Position afterMovePosition = new GameState.Position(
                opponent.getPosition().getX(),
                opponent.getPosition().getY() + 1);
        assertEquals(afterMovePosition, afterMove.getHeroes()[4].position);
        assertEquals(state.getTurn() + 2, afterMove.getTurn());
        
        assertEquals(100, state.getHeroes()[4].getLife());
        assertEquals(99, afterMove.getHeroes()[4].getLife());
        
        assertEquals(0, state.getHeroes()[4].getGold());
        assertEquals(4, afterMove.getHeroes()[4].getGold());
    }
    
    @Test
    public void checkIDontDieOfThirst() {
        State afterMove = state;
        for (int i = 0; i < 100; i++) {
            afterMove = afterMove.move(me.getId(), Move.STAY);
        }
        
        GameState.Position afterMovePosition = new GameState.Position(me.getPos().getX(), me.getPos().getY());
        assertEquals(afterMovePosition, afterMove.getMe().position);
        assertEquals(state.getTurn() + 100, afterMove.getTurn());
        
        assertEquals(100, state.getMe().getLife());
        assertEquals(1, afterMove.getMe().getLife());
    }
    
    @Test
    public void checkKillOpponentAndGetMines() {
        Hero opponent = state.getHeroes()[3];
        State afterMove = state
                .move(opponent.getId(), Move.RIGHT)
                .move(opponent.getId(), Move.STAY)
                .move(opponent.getId(), Move.STAY)
                .move(opponent.getId(), Move.STAY)
                .move(opponent.getId(), Move.STAY);
        
        assertEquals(100, state.getHeroes()[3].getLife());
        assertEquals(100 - 5, afterMove.getHeroes()[3].getLife());
        
        assertEquals(100, state.getHeroes()[4].getLife());
        assertEquals(100, afterMove.getHeroes()[4].getLife());
        
        assertEquals(3, state.getHeroes()[4].getMineCount());
        assertEquals(0, afterMove.getHeroes()[4].getMineCount());
        
        assertEquals(2, state.getHeroes()[3].getMineCount());
        assertEquals(5, afterMove.getHeroes()[3].getMineCount());
        
        int heroMines = 0;
        int opponentMines = 0;
        for (GoldMine mine : afterMove.getMines()) {
            if (mine.isOwnedBy(3)) {
                heroMines++;
            }
            if (mine.isOwnedBy(4)) {
                opponentMines++;
            }
        }
        
        assertEquals(6, heroMines);
        assertEquals(0, opponentMines);
    }
    
    @Test
    public void checkKillOpponentRespawnsAndKills() {
        State afterMove = state
                .move(4, Move.RIGHT)
                .move(3, Move.RIGHT)
                .move(3, Move.DOWN)
                .move(3, Move.STAY)
                .move(3, Move.STAY)
                .move(3, Move.STAY)
                .move(3, Move.STAY);
        
        assertEquals(100, state.getHeroes()[4].getLife());
        assertEquals(100, afterMove.getHeroes()[4].getLife());
        
        assertEquals(100, state.getHeroes()[3].getLife());
        assertEquals(100, afterMove.getHeroes()[3].getLife());
        
        assertEquals(state.getHeroes()[4].getPosition(), afterMove.getHeroes()[4].getPosition());
        assertEquals(state.getHeroes()[3].getPosition(), afterMove.getHeroes()[3].getPosition());
    }
    
    @Test
    public void checkToString() {
        String expected = "Turn: 0/100 result: 0" + System.lineSeparator()
                + "1@(0/7) life: 100 gold: 0 mines: 0" + System.lineSeparator()
                + "2@(5/8) life: 100 gold: 0 mines: 1" + System.lineSeparator()
                + "3@(6/9) life: 100 gold: 0 mines: 2" + System.lineSeparator()
                + "4@(7/10) life: 100 gold: 0 mines: 3";
        
        assertEquals(expected, state.toString());
    }
    
}
