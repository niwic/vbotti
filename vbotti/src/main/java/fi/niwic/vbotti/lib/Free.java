package fi.niwic.vbotti.lib;

public class Free implements Tile {
    
    @Override
    public boolean isMovePossible() {
        return true;
    }

    @Override
    public String toString() {
        return "  ";
    }
    
}
