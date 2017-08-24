package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

/**
 * Kiinnostava paikka (Point of interest; POI).
 * 
 * Kiinnostavasta paikasta tiedetään miten kaukana se on pelikentän jokaisesta
 * ruurusta. Tiedetään myös missä se on.
 */
public abstract class POI extends HasPosition implements HasDistance {

    protected Integer[][] distances;
    
    @Override
    public void setDistance(GameState.Position from, int distance) {
        distances[from.getY()][from.getX()] = distance;
    }

    @Override
    public int getDistance(GameState.Position from) {
        Integer distance = distances[from.getY()][from.getX()];
        
        if (distance == null) {
            return Integer.MAX_VALUE;
        } else {
            return distance;
        }
    }

}
