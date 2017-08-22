package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public class GoldMine extends POI implements Tile {

    private int owner;

    public GoldMine(int boardSize, GameState.Position position) {
        this(boardSize, position, 0);
    }

    public GoldMine(int boardSize, GameState.Position position, int owner) {
        this.owner = owner;
        this.position = position;
        this.distances = new Integer[boardSize][boardSize];
    }
    
    public GoldMine(GameState.Position position, int owner, Integer[][] distances) {
        this.owner = owner;
        this.position = position;
        this.distances = distances;
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
    
    public int getOwner() {
        return owner;
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
                if (owner > 0 && state.getHeroes().length > owner) {
                    state.getHeroes()[owner].setMineCount(state.getHeroes()[owner].getMineCount()-1);
                }
                owner = hero.getId();
                hero.setMineCount(hero.getMineCount() + 1);
            } else {
                hero.die(state.getMines());
            }
        }
    }

    @Override
    public String toString() {
        return "$" + owner;
    }
    
}
