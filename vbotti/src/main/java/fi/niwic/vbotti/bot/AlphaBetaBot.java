package fi.niwic.vbotti.bot;

import com.brianstempin.vindiniumclient.bot.BotMove;
import com.brianstempin.vindiniumclient.bot.simple.SimpleBot;
import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.vbotti.lib.Move;
import fi.niwic.vbotti.lib.State;
import fi.niwic.util.ArrayList;

public class AlphaBetaBot implements SimpleBot {

    private final int maxDepth = 11;
    
    @Override
    public BotMove move(GameState gs) {
        State state = new State(gs);
        return move(state).toBotMove();
    }
    
    public Move move(State state) {
        
        System.out.println(state);
        
        ArrayList<Move> possibleMoves = getPossibleMoves(state, state.getMe().getId());
        int best = Integer.MIN_VALUE;
        Move bestMove = possibleMoves.get(0);
        for (Move move : possibleMoves) {
            State mutatedState = state.move(state.getMe().getId(), move);
            int result = turn(mutatedState, nextHeroId(mutatedState, state.getMe().getId()), 0, best, Integer.MAX_VALUE);
            System.out.println("Alternative move " + move + " result " + result);
            if (result >= best) {
                best = result;
                bestMove = move;
            }
        }
        
        System.out.println("Best move for turn " + state.getTurn() + " is " + bestMove);
        System.out.println();
        
        return bestMove;
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
            ArrayList<Move> possibleMoves = getPossibleMoves(state, heroId);
            if (heroId == state.getMe().getId()) {
                for (Move move : possibleMoves) {
                    State mutatedState = state.move(heroId, move);
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
                for (Move move : possibleMoves) {
                    State mutatedState = state.move(heroId, move);
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
    
    private ArrayList<Move> getPossibleMoves(State state, int heroId) {
        ArrayList<Move> possibleMoves = new ArrayList();
        possibleMoves.add(Move.STAY);
        for (Move move : Move.values()) {
            if (move != Move.STAY) {
                GameState.Position position = state.getHeroes()[heroId].getPosition();
                GameState.Position destination = move.from(position);
                if (state.getBoard().isInsideBoard(destination)
                        && !state.getBoard().isImpassableWood(destination)) {
                    possibleMoves.add(move);
                }
            }
        }
        
        return possibleMoves;
    }
    
    class MoveAndState {
        Move move;
        State state;
        
        public MoveAndState(Move move, State state) {
            this.move = move;
            this.state = state;
        }
    }
    
}
