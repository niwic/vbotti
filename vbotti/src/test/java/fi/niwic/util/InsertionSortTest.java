package fi.niwic.util;

import fi.niwic.vbotti.bot.MoveAndGoldmineDistance;
import fi.niwic.vbotti.lib.Move;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class InsertionSortTest extends InsertionSort {

    @Test
    public void checkSorting() {
        MoveAndGoldmineDistance m1 = new MoveAndGoldmineDistance(Move.UP, 7);
        MoveAndGoldmineDistance m2 = new MoveAndGoldmineDistance(Move.DOWN, 5);
        MoveAndGoldmineDistance m3 = new MoveAndGoldmineDistance(Move.LEFT, 5);
        MoveAndGoldmineDistance m4 = new MoveAndGoldmineDistance(Move.RIGHT, 1);
        ArrayList<MoveAndGoldmineDistance> list = new ArrayList(new MoveAndGoldmineDistance[]{m1, m2, m3, m4});
        ArrayList<MoveAndGoldmineDistance> retList = sort(list);
        
        assertEquals(m4, list.get(0));
        assertEquals(m2, list.get(1));
        assertEquals(m3, list.get(2));
        assertEquals(m1, list.get(3));
        assertEquals(list, retList);
    }
    
}
