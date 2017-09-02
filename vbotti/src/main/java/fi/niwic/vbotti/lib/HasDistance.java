package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public interface HasDistance {

    /**
     * Aseta etäisyys tähän paikkaan tietystä pelikentän ruudusta.
     * 
     * @param from mistä
     * @param distance etäisyys
     */
    public void setDistance(GameState.Position from, int distance);
    
    /**
     * Palauttaa etäisyyden tähän paikkaan tietystä pelikentän ruudusta.
     * 
     * @param from mistä
     * @return etäisyys
     */
    public int getDistance(GameState.Position from);
    
}
