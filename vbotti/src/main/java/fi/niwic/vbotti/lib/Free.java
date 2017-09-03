package fi.niwic.vbotti.lib;

/**
 * Vapaa pelikentän ruutu.
 */
public class Free implements Tile {
    
    @Override
    public boolean isMovePossible() {
        return true;
    }

    @Override
    public String toString() {
        return "  ";
    }
    
    @Override
    public void onMoveInto(State state, Hero hero) {
        /* nothing */
    }
    
    
}
