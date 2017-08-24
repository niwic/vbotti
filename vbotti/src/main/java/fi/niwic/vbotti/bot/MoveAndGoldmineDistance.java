package fi.niwic.vbotti.bot;

import fi.niwic.vbotti.lib.Move;

/**
 * Yhdistää siiroon ja etäsiyyden kultakaivokseen jotta voidaan järjestää
 * siirrot niin, että lähin tulos kokeillaan ensin pelipuussa.
 */
public class MoveAndGoldmineDistance implements Comparable<MoveAndGoldmineDistance> {

    private Move move;
    private int distance;

    public MoveAndGoldmineDistance(Move move, int distance) {
        this.move = move;
        this.distance = distance;
    }

    @Override
    public int compareTo(MoveAndGoldmineDistance o) {
        return this.distance - o.distance;
    }
    
    public Move getMove() {
        return move;
    }
    
    public int getDistance() {
        return distance;
    }

}
