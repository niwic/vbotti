package fi.niwic.vbotti.lib;

public interface Tile {
    /**
     * Voidaanko tähän paikkaan siirtyä?
     * 
     * @return kyllä/ei
     */
    public boolean isMovePossible();
}
