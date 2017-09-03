package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

/**
 * Pelikentän ruutu jossa taverna.
 */
class Tavern extends POI implements Tile {
    
    public Tavern(int boardSize, GameState.Position position) {
        this.position = position;
        this.distances = new Integer[boardSize][boardSize];
    }
    
    @Override
    public void onMoveInto(State state, Hero hero) {
        hero.setGold(hero.getGold() - 2);
        hero.setLife(hero.getLife() + 50);
    }
    
    @Override
    public boolean isMovePossible() {
        return false;
    }
    
    @Override
    public String toString() {
        return "[]";
    }
    
}
