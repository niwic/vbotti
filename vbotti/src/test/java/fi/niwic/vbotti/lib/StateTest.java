package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class StateTest {
 
    private GameState gs;
    private State state;
    
    @Before
    public void setUp() {
        gs = GameStateGenerator.createGameState();
        state = new State(gs);
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
    
}
