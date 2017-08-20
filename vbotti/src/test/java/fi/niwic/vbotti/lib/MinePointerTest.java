package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class MinePointerTest {

    private MinePointer mp;
    
    @Before
    public void setUp() {
        mp = new MinePointer(0);
    }
    
    @Test
    public void checkPointerPos() {
        assertEquals(0, mp.pos);
    }
    
    @Test
    public void checkPointerIsMovePossible() {
        assertFalse(mp.isMovePossible());
    }
    
    @Test
    public void checkOnMoveInto() {
        State state = new State(GameStateGenerator.createGameState());
        GoldMine mine = new GoldMine(new GameState.Position(1,1));
        state.getMines().add(mine);
        
        mp.onMoveInto(state, state.getMe());
        assertEquals(80, state.getMe().getLife());
    }
    
}
