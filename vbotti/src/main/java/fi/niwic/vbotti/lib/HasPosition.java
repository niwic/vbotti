package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

/**
 * Rajapinta pelikentän ruudulle jolle tallennetaan lokaatio pelikentällä.
 * @author nic
 */
public abstract class HasPosition {

    protected GameState.Position position;
    
    /**
     * Onko tämä ruutu annetussa paikassa?
     * 
     * @param at missä
     * @return onko vai ei
     */
    public boolean atPosition(GameState.Position at) {
        return at.getX() == position.getX() && at.getY() == position.getY();
    }
    
    /**
     * Palauttaa ruudun paikan.
     * 
     * @return paikka
     */
    public GameState.Position getPosition() {
        return position;
    }
    
}
