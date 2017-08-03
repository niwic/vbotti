package fi.niwic.vbotti.lib;

class Tavern implements Tile {
    
    @Override
    public boolean isMovePossible() {
        return false;
    }
    
    @Override
    public String toString() {
        return "[]";
    }
    
}
