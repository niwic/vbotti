package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathFinder {

    private static Logger log = LogManager.getLogger(PathFinder.class);
    
    /**
     * Laskee reittien pituudet jokaisesta vapaasta paikasta jokaiseen
     * kultakaivokseen.
     * 
     * Algoritmi on breadth-first-search (BFS) tyyppinen ja käyttää apunaan
     * jonoa. Jonon tilavaativuus on O(N) jossa n on pelikentän kaikki ruudut.
     * Tämä on myös BFS algoritmin tilavaativuus.
     * 
     * BFS algoritmin aikiavaativuus pahimmassa tapauksessa on myös O(R) missä
     * R on kaikki pelikentän ruudut. Se suoritetaan pelikentän vapaille
     * ruuduille, joita on pahimmassa tapauksessa kaikki, eli algoritmin
     * kokonaisaikavaativuus on O(R^2).
     * 
     * @param board pelikenttä joka parsitaan
     */
    public static void calculateDistancesToPOIS(Board board) {
        
        log.info("Starting pathfinding to POIs");
        
        for (int x = 0; x < board.getSize(); x++) {
            
            for (int y = 0; y < board.getSize(); y++) {
        
                GameState.Position from = new GameState.Position(y, x);
                
                if (board.isFree(from)) {
                
                    boolean seen[][] = new boolean[board.getSize()][board.getSize()];
                    int distance[][] = new int[board.getSize()][board.getSize()];

                    Queue<GameState.Position> queue = new Queue(board.getSize() * board.getSize());
                    queue.add(from);

                    while (!queue.isEmpty()) {
                        GameState.Position current = queue.poll();
                        seen[current.getY()][current.getX()] = true;

                        GameState.Position moves[] = new GameState.Position[] {
                            Move.UP.from(current), Move.DOWN.from(current),
                            Move.LEFT.from(current), Move.RIGHT.from(current)
                        };

                        for (GameState.Position position : moves) {
                            if (board.isInsideBoard(position) && !seen[position.getY()][position.getX()]) {
                                seen[position.getY()][position.getX()] = true;
                                distance[position.getY()][position.getX()] = distance[current.getY()][current.getX()] + 1;
                                
                                if (board.isPOI(position)) {
                                    POI poi = (POI) board.getTile(position);
                                    poi.setDistance(from, distance[position.getY()][position.getX()]);
                                } else if (board.isMovePossible(position)) {
                                    queue.add(position);
                                }
                            }
                        }
                    }
                
                }
            
            }
        
        }
        
        log.info("Pathfinding done!");
        
    }
    
}
