package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

/**
 * Pelikentän ruutu jossa kultakaivos.
 */
public class GoldMine extends POI implements Tile {

    private int owner;

    /**
     * Luo uuden kultakaivoksen jolla ei ole omistajaa.
     * 
     * Vaatii myös pelikentän koon, jotta voidaan luoda taulukko etäisyyksien
     * tallentamiseen.
     * 
     * @param boardSize pelikentän koko
     * @param position missä
     */
    public GoldMine(int boardSize, GameState.Position position) {
        this(boardSize, position, 0);
    }

    /**
     * Luo uuden kultakaivoksen jolla on omistaja.
     * 
     * @param boardSize pelikentän koko
     * @param position missä
     * @param owner omistaja
     */
    public GoldMine(int boardSize, GameState.Position position, int owner) {
        this.owner = owner;
        this.position = position;
        this.distances = new Integer[boardSize][boardSize];
    }
    
    /**
     * Luo uuden kultakaivoksen omistajalla, ja annetulla etäisyystaulukolla.
     * 
     * @param position missä
     * @param owner omistaja
     * @param distances etäisyydet
     */
    public GoldMine(GameState.Position position, int owner, Integer[][] distances) {
        this.owner = owner;
        this.position = position;
        this.distances = distances;
    }
    
    /**
     * Asettaa kultakaivoksen omistajan.
     * 
     * Id 0 takoittaa, että kaivoksella ei ole omistajaa.
     * 
     * @param id omistaja
     */
    public void setOwner(int id) {
        this.owner = id;
    }
    
    /**
     * Onko kaivos jonkun pelaajan omistuksessa?
     * 
     * @return onko vai ei
     */
    public boolean isFree() {
        return owner == 0;
    }

    /**
     * Onko kaivos tietyn pelaajan omistuksessa?
     * 
     * @param id omistaja
     * @return onko vai ei
     */
    public boolean isOwnedBy(int id) {
        return id == owner;
    }
    
    /**
     * Palauttaa kaivoksen omistajan.
     * 
     * @return omistaja
     */
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
                    state.getHeroes()[owner].setMineCount(state.getHeroes()[owner].getMineCount() - 1);
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
