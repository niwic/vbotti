package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Kiinnostava paikka (Point of interest; POI).
 * 
 * Kiinnostavasta paikasta tiedetään miten kaukana se on pelikentän jokaisesta
 * ruudusta. Tiedetään myös missä se on.
 */
public abstract class POI extends HasPosition implements HasDistance {

    private static Logger log = LogManager.getLogger(POI.class);
    
    protected Integer[][] distances;
    
    @Override
    public void setDistance(GameState.Position from, int distance) {
        distances[from.getY()][from.getX()] = distance;
    }

    @Override
    public int getDistance(GameState.Position from) {
        
        Integer distance;
        
        if (from.equals(getPosition())) {
            distance = 0;
        } else {
            distance = distances[from.getY()][from.getX()];

            if (distance == null) {
                distance = Integer.MAX_VALUE;
            }
        }
        
        return distance;
    }

}
