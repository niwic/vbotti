package fi.niwic.util;

import fi.niwic.vbotti.bot.MoveAndPOIDistance;
import fi.niwic.vbotti.lib.Move;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class InsertionSortTest extends InsertionSort {

    @Test
    public void checkSorting() {
        MoveAndPOIDistance m1 = new MoveAndPOIDistance(Move.UP, 7);
        MoveAndPOIDistance m2 = new MoveAndPOIDistance(Move.DOWN, 5);
        MoveAndPOIDistance m3 = new MoveAndPOIDistance(Move.LEFT, 5);
        MoveAndPOIDistance m4 = new MoveAndPOIDistance(Move.RIGHT, 1);
        ArrayList<MoveAndPOIDistance> list = new ArrayList(new MoveAndPOIDistance[]{m1, m2, m3, m4});
        ArrayList<MoveAndPOIDistance> retList = sort(list);
        
        assertEquals(m4, list.get(0));
        assertEquals(m2, list.get(1));
        assertEquals(m3, list.get(2));
        assertEquals(m1, list.get(3));
        assertEquals(list, retList);
    }
    
}
