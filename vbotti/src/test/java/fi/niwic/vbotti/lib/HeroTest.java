package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class HeroTest {

    private GameState.Position position;
    private GameState.Position respawnPosition;
    private Hero hero;
    
    @Before
    public void setUp() {
        position = new GameState.Position(1,2);
        respawnPosition = new GameState.Position(1,2);
        hero = new Hero(1, 100, 0, 0, position, respawnPosition);
    }
    
    @Test
    public void checkGetId() {
        assertEquals(1, hero.getId());
    }
    
    @Test
    public void checkGetRewpanPos() {
        assertEquals(respawnPosition, hero.getRespawnPos());
    }
    
    @Test
    public void checkGetPosition() {
        assertEquals(1, hero.getPosition().getX());
        assertEquals(2, hero.getPosition().getY());
    }
    
    @Test
    public void checkGetMineCount() {
        hero.setMineCount(11);
        assertEquals(11, hero.getMineCount());
    }
    
    @Test
    public void checkGetGold() {
        hero.setGold(123);
        assertEquals(123, hero.getGold());
    }
    
    @Test
    public void checkGetLife() {
        hero.setLife(89);
        assertEquals(89, hero.getLife());
    }
    
    @Test
    public void checkGetLifeOver100() {
        hero.setLife(321);
        assertEquals(100, hero.getLife());
    }
    
    @Test
    public void checkGetLifeOneOver100() {
        hero.setLife(101);
        assertEquals(100, hero.getLife());
    }
    
    @Test
    public void checkGetLifeOneUnder100() {
        hero.setLife(99);
        assertEquals(99, hero.getLife());
    }
    
    @Test
    public void checkGetLifeAt100() {
        hero.setLife(100);
        assertEquals(100, hero.getLife());
    }
    
    @Test
    public void checkIsDead() {
        hero.setLife(0);
        assertTrue(hero.isDead());
    }
    
    @Test
    public void checkIsDeadUnder0() {
        hero.setLife(-1);
        assertTrue(hero.isDead());
    }
    
    @Test
    public void checkIsNotDead() {
        hero.setLife(1);
        assertFalse(hero.isDead());
    }
    
    @Test
    public void checkCopy() {
        Hero heroCopy = hero.copy();
        assertNotEquals(hero, heroCopy);
        assertEquals(hero.getId(), heroCopy.getId());
        assertEquals(hero.getLife(), heroCopy.getLife());
        assertEquals(hero.getGold(), heroCopy.getGold());
        assertEquals(hero.getMineCount(), heroCopy.getMineCount());
        assertEquals(hero.getPosition(), heroCopy.getPosition());
        assertEquals(hero.getRespawnPos(), heroCopy.getRespawnPos());
    }
    
    @Test
    public void checkCopyNewPosition() {
        GameState.Position newPosition = new GameState.Position(3, 5);
        Hero heroCopy = hero.copy(newPosition);
        assertNotEquals(hero, heroCopy);
        assertEquals(hero.getId(), heroCopy.getId());
        assertEquals(hero.getLife(), heroCopy.getLife());
        assertEquals(hero.getGold(), heroCopy.getGold());
        assertEquals(hero.getMineCount(), heroCopy.getMineCount());
        assertEquals(newPosition, heroCopy.getPosition());
        assertEquals(hero.getRespawnPos(), heroCopy.getRespawnPos());
    }
    
    @Test
    public void checkRespawn() {
        GoldMine mine = new GoldMine(position, hero.getId());
        List<GoldMine> mines = new ArrayList();
        mines.add(mine);
        
        hero.respawn(mines);
        assertEquals(respawnPosition, hero.getPosition());
        assertEquals(100, hero.getLife());
        assertEquals(0, hero.getMineCount());
        assertEquals(0, mine.getOwner());
    }
    
    @Test
    public void checkDie() {
        hero.die();
        assertEquals(0, hero.getLife());
    }
    
    @Test
    public void checkOnMoveInto() {
        State state = new State(GameStateGenerator.createGameState());
        hero.onMoveInto(state, hero);
    }
    
    @Test
    public void checkIsMovePossible() {
        assertFalse(hero.isMovePossible());
    }
    
    @Test
    public void checkToString() {
        assertEquals("@1", hero.toString());
    }
    
}
