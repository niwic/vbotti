package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public abstract class HasPosition {

    protected GameState.Position position;
    
    public boolean atPosition(GameState.Position at) {
        return at.getX() == position.getX() && at.getY() == position.getY();
    }
    
    public GameState.Position getPosition() {
        return position;
    }
    
}
