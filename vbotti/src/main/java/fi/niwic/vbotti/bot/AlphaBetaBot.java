package fi.niwic.vbotti.bot;

import com.brianstempin.vindiniumclient.bot.BotMove;
import com.brianstempin.vindiniumclient.bot.simple.SimpleBot;
import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.vbotti.lib.Move;
import fi.niwic.vbotti.lib.State;
import fi.niwic.util.ArrayList;
import fi.niwic.util.InsertionSort;
import fi.niwic.vbotti.lib.Board;
import fi.niwic.vbotti.lib.Hero;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlphaBetaBot implements SimpleBot {

    private Logger log = LogManager.getLogger(AlphaBetaBot.class);
    private final int maxDepth = 11;
    
    @Override
    public BotMove move(GameState gs) {
        State state = new State(gs);
        return move(state).toBotMove();
    }
    
    public Move move(State state) {
        
        log.info(state);
        
        ArrayList<MoveAndPOIDistance> possibleMoves = getPossibleMoves(state, state.getMe().getId());
        int best = Integer.MIN_VALUE;
        MoveAndPOIDistance bestMove = possibleMoves.get(0);
        for (MoveAndPOIDistance move : possibleMoves) {
            State mutatedState = state.move(state.getMe().getId(), move.getMove());
            int result = turn(mutatedState, nextHeroId(mutatedState, state.getMe().getId()), 1, best, Integer.MAX_VALUE);
            log.info("Alternative move: " + move.getMove() + " result: " + result + " POI distance: " + move.getDistance());
            if (result > best) {
                best = result;
                bestMove = move;
            }
        }
        
        log.info("Best move for turn " + state.getTurn() + " is " + bestMove.getMove());
        
        return bestMove.getMove();
    }

    @Override
    public void setup() {

    }

    @Override
    public void shutdown() {
        
    }
    
    private int turn(State state, int heroId, int depth, int alpha, int beta) {
        
        if (state.isFinished() || depth > maxDepth) {
            return state.getResult();
        } else {
            ArrayList<MoveAndPOIDistance> possibleMoves = getPossibleMoves(state, heroId);
            if (heroId == state.getMe().getId()) {
                for (MoveAndPOIDistance move : possibleMoves) {
                    State mutatedState = state.move(heroId, move.getMove());
                    int result = turn(mutatedState, nextHeroId(state, heroId), depth + 1, alpha, beta);
                    if (result >= beta) {
                        return beta;
                    }
                    if (result > alpha) {
                        alpha = result;
                    }
                }
                
                return alpha;
            } else {
                for (MoveAndPOIDistance move : possibleMoves) {
                    State mutatedState = state.move(heroId, move.getMove());
                    int result = turn(mutatedState, nextHeroId(state, heroId), depth + 1, alpha, beta);
                    if (result <= alpha) {
                        return alpha;
                    }
                    if (result < beta) {
                        beta = result;
                    }
                }
                
                return beta;
            }
        }
        
    }
    
    private int nextHeroId(State state, int heroId) {
        if (heroId + 1 < state.getHeroes().length) return heroId+1;
        else return 1;
    }
    
    private ArrayList<MoveAndPOIDistance> getPossibleMoves(State state, int heroId) {
        ArrayList<MoveAndPOIDistance> possibleMoves = new ArrayList();
        
        Board board = state.getBoard();
        Hero hero = state.getHeroes()[heroId];
        GameState.Position currentPosition = hero.getPosition();
        
        possibleMoves.add(
                new MoveAndPOIDistance(
                        Move.STAY,
                        goldMineOrTavernDistance(hero, currentPosition, board)
                )
        );
        
        for (Move move : Move.values()) {
            if (move != Move.STAY) {
                GameState.Position destination = move.from(currentPosition);
                if (isDestinationSensible(board, destination, heroId)) {
                    possibleMoves.add(
                            new MoveAndPOIDistance(
                                    move,
                                    goldMineOrTavernDistance(hero, destination, board)
                            )
                    );
                }
            }
        }
        
        InsertionSort.sort(possibleMoves);
        
        return possibleMoves;
    }
    
    /**
     * Otetaan harkintaan vaan ne siirrot jotka oikeasti muuttavat pelitilanteen.
     * 
     * @param board pelikenttä
     * @param destination kohderuutu
     * @param heroId vuorossa
     * @return otetaanko siirto pelipuuhun?
     */
    private boolean isDestinationSensible(Board board, GameState.Position destination, int heroId) {
        return board.isInsideBoard(destination)
                && !board.isImpassableWood(destination)
                && !board.isHeroGoldMine(destination, heroId);
    }
    
    /**
     * Palauttaa etäisyyden lähimpään kohteeseen.
     * 
     * Jos on niin vähän hpta, että siiron jälkeen ei pysty kaviosta valtaamaan,
     * otetaan etäisyys lähimpään tavernaan.
     * 
     * @param hero vuorossa
     * @param destination kohderuutu
     * @param board pelikenttä
     * @return lyhyin etäysyys kiinnostavaan kohteeseen
     */
    private int goldMineOrTavernDistance(Hero hero, GameState.Position destination, Board board) {
        if (hero.getLife() < 21) {
            return board.distanceToClosestTavernFrom(destination);
        } else {
            return board.distanceToClosestGoldMineFrom(destination, hero.getId());
        }
    }
}
