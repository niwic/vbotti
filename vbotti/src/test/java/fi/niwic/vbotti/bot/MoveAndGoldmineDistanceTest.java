package fi.niwic.vbotti.bot;

import fi.niwic.vbotti.lib.Move;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MoveAndGoldmineDistanceTest {

    @Test
    public void checkCompareTo() {
        MoveAndGoldmineDistance magd1 = new MoveAndGoldmineDistance(Move.UP, 1);
        MoveAndGoldmineDistance magd2 = new MoveAndGoldmineDistance(Move.DOWN, 2);
        
        assertEquals(-1, magd1.compareTo(magd2));
    }
    
    @Test
    public void checkGetDistance() {
        MoveAndGoldmineDistance magd = new MoveAndGoldmineDistance(Move.UP, 1);
        assertEquals(1, magd.getDistance());
    }
    
    @Test
    public void checkGetMove() {
        MoveAndGoldmineDistance magd = new MoveAndGoldmineDistance(Move.UP, 1);
        assertEquals(Move.UP, magd.getMove());
    }
    
}
