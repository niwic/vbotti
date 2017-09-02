package fi.niwic.vbotti.lib;

/**
 * Pelikentän osa.
 * 
 * @author nic
 */
public interface Tile {
    
    /**
     * Voidaanko tähän paikkaan siirtyä?
     * 
     * @return kyllä/ei
     */
    public boolean isMovePossible();
    
    /**
     * Logiikka joka suoritetaan jos siirrytään tähän ruutuun.
     * 
     * @param state pelikentän tilanne
     * @param hero hero joka siirtyy ruutuun
     */
    public void onMoveInto(State state, Hero hero);
    
}
