package fi.niwic.vbotti.bot;

import fi.niwic.vbotti.lib.Move;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MoveAndPOIDistanceTest {

    @Test
    public void checkCompareTo() {
        MoveAndPOIDistance magd1 = new MoveAndPOIDistance(Move.UP, 1);
        MoveAndPOIDistance magd2 = new MoveAndPOIDistance(Move.DOWN, 2);
        
        assertEquals(-1, magd1.compareTo(magd2));
    }
    
    @Test
    public void checkGetDistance() {
        MoveAndPOIDistance magd = new MoveAndPOIDistance(Move.UP, 1);
        assertEquals(1, magd.getDistance());
    }
    
    @Test
    public void checkGetMove() {
        MoveAndPOIDistance magd = new MoveAndPOIDistance(Move.UP, 1);
        assertEquals(Move.UP, magd.getMove());
    }
    
}
