package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public interface HasDistance {

    public void setDistance(GameState.Position from, int distance);
    public int getDistance(GameState.Position from);
    
}
