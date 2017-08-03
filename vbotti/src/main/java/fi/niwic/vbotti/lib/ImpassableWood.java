package fi.niwic.vbotti.lib;

public class ImpassableWood implements Tile {
    
    @Override
    public boolean isMovePossible() {
        return false;
    }
    
    @Override
    public String toString() {
        return "##";
    }
    
}
