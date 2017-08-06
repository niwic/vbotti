package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class GoldMineTest {

    private GameState.Position position;
    private GoldMine mine;
    
    @Before
    public void setUp() {
        position = new GameState.Position(4,9);
        mine = new GoldMine(position, 1);
    }
    
    @Test
    public void checkSetOwner() {
        mine.setOwner(10);
        assertEquals(10, mine.getOwner());
    }
    
    @Test
    public void checkIsFree() {
        mine.setOwner(0);
        assertTrue(mine.isFree());
    }
    
    @Test
    public void checkIsNotFree() {
        mine.setOwner(7);
        assertFalse(mine.isFree());
    }
    
    @Test
    public void checkIsOwnedBy() {
        assertTrue(mine.isOwnedBy(1));
    }
    
    @Test
    public void checkGetOwner() {
        assertEquals(1, mine.getOwner());
    }
    
    @Test
    public void checkIsMovePossible() {
        assertFalse(mine.isMovePossible());
    }
    
    @Test
    public void checkOnMoveInto() {
        GameState.Hero me = GameStateGenerator.createHero(1, position);
        GameState.Hero opponent = GameStateGenerator.createHero(2, position);
        List<GameState.Hero> heroes = new ArrayList();
        heroes.add(me);
        heroes.add(opponent);
        
        State state = new State(GameStateGenerator.createGameState(heroes, me));
        Hero hero = state.me;
        Hero owner = state.heroes[2];
        owner.setMineCount(1);
        mine.setOwner(2);
        
        assertEquals(hero.getLife(), 100);
        assertEquals(hero.getMineCount(), 0);
        assertEquals(opponent.getMineCount(), 0);
        
        mine.onMoveInto(state, hero);
        
        assertEquals(hero.getLife(), 80);
        assertEquals(hero.getMineCount(), 1);
        assertEquals(owner.getMineCount(), 0);
    }
    
    @Test
    public void checkToString() {
        assertEquals("$1", mine.toString());
    }
    
}