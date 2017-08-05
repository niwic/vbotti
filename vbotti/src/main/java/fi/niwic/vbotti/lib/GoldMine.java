package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public class GoldMine extends HasPosition implements Tile {

    int owner;

    public GoldMine(GameState.Position position) {
        this.owner = 0;
        this.position = position;
    }

    public GoldMine(GameState.Position position, int owner) {
        this.owner = owner;
        this.position = position;
    }
    
    public void setOwner(int id) {
        this.owner = id;
    }
    
    public boolean isFree() {
        return owner == 0;
    }

    public boolean isOwnedBy(int id) {
        return id == owner;
    }

    @Override
    public boolean isMovePossible() {
        return false;
    }
    
    @Override
    public void onMoveInto(State state, Hero hero) {
        if (hero.getId() != owner) {
            hero.setLife(hero.getLife() - 20);
            if (!hero.isDead()) {
                state.heroes[owner].setMineCount(state.heroes[owner].getMineCount()-1);
                owner = hero.getId();
                hero.setMineCount(hero.getMineCount() + 1);
            }
        }
    }

    @Override
    public String toString() {
        return "$" + owner;
    }
    
}
