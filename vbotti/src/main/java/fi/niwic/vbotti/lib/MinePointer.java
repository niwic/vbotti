package fi.niwic.vbotti.lib;

/**
 * Osoitin kultakaivos listaan.
 * 
 * Käytetään pelikentän kopioinnin helpottamiseksi.
 */
class MinePointer implements Tile {

    int pos;

    public MinePointer(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean isMovePossible() {
        return false;
    }

    @Override
    public void onMoveInto(State state, Hero hero) {
        state.getMines().get(pos).onMoveInto(state, hero);
    }

}
