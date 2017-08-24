package fi.niwic.vbotti.bot;

import com.brianstempin.vindiniumclient.bot.BotMove;
import com.brianstempin.vindiniumclient.bot.simple.SimpleBot;
import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.vbotti.lib.Move;
import fi.niwic.vbotti.lib.State;
import fi.niwic.util.ArrayList;
import fi.niwic.util.InsertionSort;

public class AlphaBetaBot implements SimpleBot {

    private final int maxDepth = 11;
    
    @Override
    public BotMove move(GameState gs) {
        State state = new State(gs);
        return move(state).toBotMove();
    }
    
    public Move move(State state) {
        
        System.out.println(state);
        
        ArrayList<MoveAndGoldmineDistance> possibleMoves = getPossibleMoves(state, state.getMe().getId(), true);
        int best = Integer.MIN_VALUE;
        MoveAndGoldmineDistance bestMove = possibleMoves.get(0);
        for (MoveAndGoldmineDistance move : possibleMoves) {
            State mutatedState = state.move(state.getMe().getId(), move.getMove());
            int result = turn(mutatedState, nextHeroId(mutatedState, state.getMe().getId()), 0, best, Integer.MAX_VALUE);
            System.out.println("Alternative move: " + move.getMove() + " result: " + result + " gold mine distance: " + move.getDistance());
            if (result > best) {
                best = result;
                bestMove = move;
            }
        }
        
        System.out.println("Best move for turn " + state.getTurn() + " is " + bestMove.getMove());
        System.out.println();
        
        return bestMove.getMove();
    }

    @Override
    public void setup() {
        
    }

    @Override
    public void shutdown() {
        
    }

    public int turn(State state, int heroId) {
        return turn(state, heroId, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public int turn(State state, int heroId, int depth, int alpha, int beta) {
        
        if (state.isFinished() || depth > maxDepth) {
            return state.getResult();
        } else {
            ArrayList<MoveAndGoldmineDistance> possibleMoves = getPossibleMoves(state, heroId, true);
            if (heroId == state.getMe().getId()) {
                for (MoveAndGoldmineDistance move : possibleMoves) {
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
                for (MoveAndGoldmineDistance move : possibleMoves) {
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
    
    private ArrayList<MoveAndGoldmineDistance> getPossibleMoves(State state, int heroId, boolean sortByDistance) {
        ArrayList<MoveAndGoldmineDistance> possibleMoves = new ArrayList();
        
        GameState.Position currentPosition = state.getHeroes()[heroId].getPosition();
        int closestGoldMine = 0;
        if (sortByDistance) {
            closestGoldMine = state.getBoard().distanceToClosestGoldMineFrom(currentPosition, heroId);
        }
        possibleMoves.add(
                new MoveAndGoldmineDistance(
                        Move.STAY,
                        closestGoldMine
                )
        );
        
        for (Move move : Move.values()) {
            if (move != Move.STAY) {
                GameState.Position destination = move.from(currentPosition);
                if (state.getBoard().isInsideBoard(destination)
                        && !state.getBoard().isImpassableWood(destination)
                        && !state.getBoard().isHeroGoldMine(destination, heroId)) {
                    closestGoldMine = 0;
                    if (sortByDistance) {
                        closestGoldMine = state.getBoard().distanceToClosestGoldMineFrom(destination, heroId);
                    }
                    possibleMoves.add(new MoveAndGoldmineDistance(move, closestGoldMine));
                }
            }
        }
        
        if (sortByDistance) {
            InsertionSort.sort(possibleMoves);
        }
        
        return possibleMoves;
    }
        
}
