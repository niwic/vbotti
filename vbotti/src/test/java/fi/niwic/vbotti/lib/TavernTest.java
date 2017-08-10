package fi.niwic.vbotti.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class TavernTest {

    private Tavern tavern;
    
    @Before
    public void setUp() {
        tavern = new Tavern();
    }
    
    @Test
    public void checkIsMovePossible() {
        assertFalse(tavern.isMovePossible());
    }
    
    @Test
    public void checkOnMoveInto() {
        State state = new State(GameStateGenerator.createGameState());
        Hero hero = state.getMe();
        hero.setGold(10);
        hero.setLife(10);
        
        tavern.onMoveInto(state, hero);
        
        assertEquals(8, hero.getGold());
        assertEquals(60, hero.getLife());
    }
    
    @Test
    public void checkToString() {
        assertEquals("[]", tavern.toString());
    }
    
}
