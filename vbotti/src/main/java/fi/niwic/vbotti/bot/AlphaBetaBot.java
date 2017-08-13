package fi.niwic.vbotti.bot;

import com.brianstempin.vindiniumclient.bot.BotMove;
import com.brianstempin.vindiniumclient.bot.simple.SimpleBot;
import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.vbotti.lib.Move;
import fi.niwic.vbotti.lib.State;
import java.util.ArrayList;
import java.util.List;

public class AlphaBetaBot implements SimpleBot {

    private final int maxDepth = 9;
    
    @Override
    public BotMove move(GameState gs) {
        State state = new State(gs);
        return move(state).toBotMove();
    }
    
    public Move move(State state) {
        
        System.out.println(state);
        
        List<MoveAndState> mutations = getMutations(state, state.getMe().getId());
        int best = Integer.MIN_VALUE;
        MoveAndState bestMove = mutations.get(0);
        for (MoveAndState mutation : mutations) {
            int result = turn(mutation.state, nextHeroId(mutation.state, state.getMe().getId()));
            System.out.println("Alternative move " + mutation.move + " result " + result);
            if (result > best) {
                best = result;
                bestMove = mutation;
            }
        }
        
        System.out.println("Best move for turn " + state.getTurn() + " is " + bestMove.move);
        System.out.println();
        
        return bestMove.move;
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
        
        //System.out.println(heroId + ": " + alpha + "/" + beta);
        
        if (state.isFinished() || depth > maxDepth) {
            //System.out.println(heroId + ": " + alpha + "/" + beta);
            return state.getResult();
        } else if (alpha >= beta) {
            //System.out.println(heroId + " pruning!");
            return state.getResult();
        } else {
            List<MoveAndState> mutations = getMutations(state, heroId);
            if (heroId == state.getMe().getId()) {
                int best = Integer.MIN_VALUE;
                for (MoveAndState mutation : mutations) {
                    int result = turn(mutation.state, nextHeroId(state, heroId), depth + 1, alpha, beta);
                    if (result > best) {
                        best = result;
                        alpha = result;
                    }
                }
                
                return best;
            } else {
                int worst = Integer.MAX_VALUE;
                for (MoveAndState mutation : mutations) {
                    int result = turn(mutation.state, nextHeroId(state, heroId), depth + 1, alpha, beta);
                    if (result < worst) {
                        worst = result;
                        beta = result;
                    }
                }
                
                return worst;
            }
        }
        
    }
    
    private int nextHeroId(State state, int heroId) {
        if (heroId + 1 < state.getHeroes().length) return heroId+1;
        else return 1;
    }
    
    private List<MoveAndState> getMutations(State state, int heroId) {
        List<MoveAndState> newStates = new ArrayList();
        for (Move move : Move.values()) {
            GameState.Position position = state.getHeroes()[heroId].getPosition();
            if (state.getBoard().isInsideBoard(move.from(position))) {
                newStates.add(new MoveAndState(move, state.move(heroId, move)));
                //System.out.println("New State for " + heroId + " moving " + move);
                //System.out.println(state);
            }
        }
        
        return newStates;
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
