package fi.niwic.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class QueueTest {

    private Queue<Integer> queue;
    
    @Before
    public void setUp() {
        queue = new Queue(10);
    }
    
    @Test
    public void checkQueue() {
        for (int i = 0; i < 10; i++) {
            queue.add(i);
        }
        
        for (int i = 0; i < 10; i++) {
            assertEquals((Integer) i, queue.poll());
        }
    }
    
    @Test
    public void checkQueueEmptyNoneAdded() {
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void checkQueueEmptyOneAddedOnePolled() {
        queue.add(1);
        queue.poll();
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void checkQueueNotEmptyTwoAddedOnePolled() {
        queue.add(1);
        queue.add(2);
        queue.poll();
        assertFalse(queue.isEmpty());
    }
    
}
