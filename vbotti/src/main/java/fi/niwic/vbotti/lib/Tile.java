package fi.niwic.vbotti.lib;

/**
 * Pelikentän osa
 * @author nic
 */
public interface Tile {
    /**
     * Voidaanko tähän paikkaan siirtyä?
     * 
     * @return kyllä/ei
     */
    public boolean isMovePossible();
}
