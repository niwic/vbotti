package fi.niwic.vbotti.bot;

import com.brianstempin.vindiniumclient.bot.BotMove;
import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.vbotti.lib.GameStateGenerator;
import fi.niwic.vbotti.lib.Move;
import fi.niwic.vbotti.lib.State;
import fi.niwic.vbotti.lib.TestBoardTwo;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class AlphaBetaBotTest {

    private AlphaBetaBot bot;
    
    @Before
    public void setUp() {
        bot = new AlphaBetaBot();
    }
    
    @Test
    public void checkShouldMoveTowardsGoldMine() {
        GameState.Hero me = GameStateGenerator.createHero(1, new GameState.Position(8,1));
        List<GameState.Hero> heroes = new ArrayList();
        heroes.add(me);
        
        GameState gameState = GameStateGenerator.createGameState(heroes, me);
        Move move = bot.move(new State(gameState));
        
        assertEquals(Move.STAY, move);
    }
    
    @Test
    public void checkShouldMoveTowardsGoldMineTwo() {
        GameState.Hero me = GameStateGenerator.createHero(1, new GameState.Position(7,0));
        GameState.Hero opponent = GameStateGenerator.createHero(2, new GameState.Position(0,0));
        List<GameState.Hero> heroes = new ArrayList();
        heroes.add(me);
        heroes.add(opponent);
        
        GameState gameState = GameStateGenerator.createGameState(heroes, me, TestBoardTwo.getBoard());
        Move move = bot.move(new State(gameState));
        
        assertEquals(Move.DOWN, move);
    }
    
}
