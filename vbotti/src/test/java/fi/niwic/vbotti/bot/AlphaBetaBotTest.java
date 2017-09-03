package fi.niwic.vbotti.bot;

import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.vbotti.lib.GameStateGenerator;
import fi.niwic.vbotti.lib.Move;
import fi.niwic.vbotti.lib.State;
import fi.niwic.vbotti.lib.TestBoardTwo;
import fi.niwic.util.ArrayList;
import fi.niwic.vbotti.lib.GoldMine;
import fi.niwic.vbotti.lib.Hero;
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
        ArrayList<GameState.Hero> heroes = new ArrayList();
        heroes.add(me);
        
        GameState gameState = GameStateGenerator.createGameState(heroes, me);
        Move move = bot.move(new State(gameState));
        
        assertEquals(12, bot.getStatsForPreviousMove().getNodesChecked());
        assertEquals(Move.STAY, move);
    }
    
    @Test
    public void checkShouldMoveTowardsGoldMineTwo() {
        GameState.Hero me = GameStateGenerator.createHero(1, new GameState.Position(7,0));
        GameState.Hero opponent = GameStateGenerator.createHero(2, new GameState.Position(0,0));
        ArrayList<GameState.Hero> heroes = new ArrayList();
        heroes.add(me);
        heroes.add(opponent);
        
        GameState gameState = GameStateGenerator.createGameState(heroes, me, TestBoardTwo.getBoard());
        Move move = bot.move(new State(gameState));
        
        assertEquals(8043, bot.getStatsForPreviousMove().getNodesChecked());
        assertEquals(Move.DOWN, move);
    }
    
    private State generateGameState() {
        GameState.Hero me = GameStateGenerator.createHero(1, new GameState.Position(10,9));
        GameState.Hero opponent = GameStateGenerator.createHero(2, new GameState.Position(12,9));
        ArrayList<GameState.Hero> heroes = new ArrayList();
        heroes.add(me);
        heroes.add(opponent);
        
        GameState gameState = GameStateGenerator.createGameState(heroes, me, TestBoardTwo.getBoard());
        State state = new State(gameState);
        
        return state;
    }
    
    @Test
    public void checkGuessedMoveRightDirection() {
        State state = generateGameState();
        
        Hero opponent = state.getHeroes()[2];
        opponent.setMineCount(0);
        
        Move move = bot.move(state);
        
        assertEquals(57378, bot.getStatsForPreviousMove().getNodesChecked());
        assertEquals(Move.RIGHT, move);
    }
    
    @Test
    public void checkGuessedMoveAlmostRightDirection() {
        State state = generateGameState();
        
        Hero me = state.getHeroes()[2];
        me.setLife(40);
        
        Hero opponent = state.getHeroes()[2];
        opponent.setMineCount(1);
        
        for (GoldMine mine : state.getMines()) {
            if (mine.atPosition(new GameState.Position(24, 9))) {
                mine.setOwner(2);
            } else {
                mine.setOwner(1);
            }
        }
        
        state.getMines().get(0).setOwner(2);
        
        Move move = bot.move(state);
        
        assertEquals(83193, bot.getStatsForPreviousMove().getNodesChecked());
        assertEquals(Move.DOWN, move);
    }
    
    @Test
    public void checkGuessedMoveWrongDirection() {
        State state = generateGameState();
        
        Hero opponent = state.getHeroes()[2];
        opponent.setMineCount(10);
        opponent.setLife(1);
        
        Move move = bot.move(state);
        
        assertEquals(133696, bot.getStatsForPreviousMove().getNodesChecked());
        assertEquals(Move.DOWN, move);
    }
    
}
