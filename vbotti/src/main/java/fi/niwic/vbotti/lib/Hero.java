package fi.niwic.vbotti.lib;

public class Hero implements Tile {
        
    private int id;

    public Hero(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean isMovePossible() {
        return false;
    }

    @Override
    public String toString() {
        return "@" + id;
    }
        
}
