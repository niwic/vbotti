package fi.niwic.vbotti.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class ImpassableWoodTest {

    private ImpassableWood wood;
    
    @Before
    public void setUp() {
        wood = new ImpassableWood();
    }
    
    @Test
    public void checkIsMovePossible() {
        assertFalse(wood.isMovePossible());
    }
    
    @Test
    public void checkOnMoveInto() {
        State state = new State(GameStateGenerator.createGameState());
        Hero hero = state.me;
        wood.onMoveInto(state, hero);
    }
    
    @Test
    public void checkToString() {
        assertEquals("##", wood.toString());
    }
    
}
