package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.util.ArrayList;

public class GameStateGenerator {

    public static GameState createGameState() {
        GameState.Hero hero = createHero(1, new GameState.Position(0, 0));
        ArrayList<GameState.Hero> heroes = new ArrayList();
        heroes.add(hero);
        return createGameState(heroes, hero);
    }
    
    public static GameState createGameState(ArrayList<GameState.Hero> heroes, GameState.Hero me) {
        GameState.Board board = createBoard();
        return createGameState(heroes, me, board);
    }
    
    public static GameState createGameState(ArrayList<GameState.Hero> heroes, GameState.Hero me, GameState.Board board) {
        GameState.Game game = createGame(heroes, board);
        return new GameState(game, me, "test", "test", "test");
    }
    
    public static GameState.Hero createHero(int id, GameState.Position position) {
        return new GameState.Hero(id, "test", "test", 0, position, 100, 0, 0, position, false);
    }
    
    public static GameState.Hero createHero(int id, GameState.Position position, int mineCount) {
        return new GameState.Hero(id, "test", "test", 0, position, 100, 0, mineCount, position, false);
    }
    
    public static GameState.Board createBoard() {
        return TestBoardOne.getBoard();
    }
    
    public static GameState.Game createGame(ArrayList<GameState.Hero> heroes, GameState.Board board) {
        return new GameState.Game("test", 0, 100, heroes, board, false);
    }
    
}
