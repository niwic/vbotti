package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class FreeTest {

    private Free free;
    
    @Before
    public void setUp() {
        free = new Free();
    }
    
    @Test
    public void checkIsMovePossible() {
        assertTrue(free.isMovePossible());
    }
    
    @Test
    public void checkToString() {
        assertEquals("  ", free.toString());
    }
    
    @Test
    public void checkOnMoveInto() {
        State state = new State(GameStateGenerator.createGameState());
        Hero hero = createNewHero(1, new GameState.Position(0, 0));
        free.onMoveInto(state, hero);
        assertTrue(state == state && hero == hero);
    }
    
    public Hero createNewHero(int id, GameState.Position position) {
        return new Hero(id, 100, 0, 0, position, position);
    }
    
}
