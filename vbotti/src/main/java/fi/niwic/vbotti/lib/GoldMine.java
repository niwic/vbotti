package fi.niwic.vbotti.lib;

public class GoldMine implements Tile {
        
    int owner;

    public GoldMine() {
        this.owner = 0;
    }

    public GoldMine(int owner) {
        this.owner = owner;
    }

    public boolean isFree() {
        return owner == 0;
    }

    public boolean isOwnedBy(int id) {
        return id == owner;
    }

    @Override
    public boolean isMovePossible() {
        return false;
    }

    @Override
    public String toString() {
        return "$" + owner;
    }
    
}
