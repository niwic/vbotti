package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public class Hero extends HasPosition implements Tile  {

    private int id;

    public Hero(GameState.Hero hero) {
        this.id = hero.getId();
        this.position = hero.getPos();
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean isMovePossible() {
        return false;
    }

    @Override
    public String toString() {
        return "@" + id;
    }
        
}
