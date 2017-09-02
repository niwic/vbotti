package fi.niwic.vbotti.bot;

import fi.niwic.vbotti.lib.Move;

public class MoveAndPOIDistance implements Comparable<MoveAndPOIDistance> {

    private Move move;
    private int distance;

    /**
    * Yhdistää siiroon ja etäsiyyden kiinnostavaan kohteeseen jotta voidaan
    * järjestää siirrot niin, että lähin tulos kokeillaan ensin pelipuussa.
    * 
    * @param move siirto
    * @param distance etäisyys
    */
    public MoveAndPOIDistance(Move move, int distance) {
        this.move = move;
        this.distance = distance;
    }

    @Override
    public int compareTo(MoveAndPOIDistance o) {
        return this.distance - o.distance;
    }
    
    public Move getMove() {
        return move;
    }
    
    public int getDistance() {
        return distance;
    }

}
