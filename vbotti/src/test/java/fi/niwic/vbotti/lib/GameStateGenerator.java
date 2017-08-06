package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import java.util.ArrayList;
import java.util.List;

public class GameStateGenerator {

    public static GameState createGameState() {
        GameState.Hero hero = createHero(1, new GameState.Position(0, 0));
        List<GameState.Hero> heroes = new ArrayList();
        heroes.add(hero);
        return createGameState(heroes, hero);
    }
    
    public static GameState createGameState(List<GameState.Hero> heroes, GameState.Hero me) {
        GameState.Board board = createBoard();
        GameState.Game game = createGame(heroes, board);
        return new GameState(game, me, "test", "test", "test");
    }
    
    public static GameState.Hero createHero(int id, GameState.Position position) {
        return new GameState.Hero(id, "test", "test", 0, position, 100, 0, 0, position, false);
    }
    
    public static GameState.Board createBoard() {
        return new GameState.Board();
    }
    
    public static GameState.Game createGame(List<GameState.Hero> heroes, GameState.Board board) {
        return new GameState.Game("test", 0, 100, heroes, board, false);
    }
    
}
