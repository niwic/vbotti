package fi.niwic.vbotti.lib;

class Tavern implements Tile {
    
    @Override
    public void onMoveInto(State state, Hero hero) {
        hero.setGold(hero.getGold() - 2);
        hero.setLife(hero.getLife() + 50);
    }
    
    @Override
    public boolean isMovePossible() {
        return false;
    }
    
    @Override
    public String toString() {
        return "[]";
    }
    
}
