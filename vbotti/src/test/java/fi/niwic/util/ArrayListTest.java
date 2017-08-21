package fi.niwic.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ArrayListTest {

    private ArrayList<Integer> list;
    
    @Before
    public void setUp() {
        list = new ArrayList();
    }
    
    @Test
    public void checkAddAndGet() {
        boolean isAdded = list.add(1);
        assertEquals((Integer) 1, list.get(0));
        assertTrue(isAdded);
    }
    
    @Test
    public void checkSetAndGet() {
        Integer retVal = list.set(0, 1);
        assertEquals((Integer) 1, list.get(0));
        assertEquals((Integer) 1, retVal);
    }
    
    @Test
    public void checkAdd100AndGet() {
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        
        for (int i = 0; i < 100; i++) {
            assertEquals((Integer) i, list.get(i));
        }
    }
    
    @Test
    public void checkIsEmpty() {
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void checkSizeEmpty() {
        assertEquals(0, list.size());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void checkNotImplementedAddIntInt() {
        list.add(0, 0);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationContains() {
        list.contains(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationToArray() {
        list.toArray();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationToArrayArray() {
        list.toArray(new Integer[]{1});
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationRemove() {
        list.remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationContainsAll() {
        list.containsAll(list);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationAddAll() {
        list.addAll(list);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationAddAllIndex() {
        list.addAll(0, list);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationRemoveAll() {
        list.removeAll(list);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationRetainAll() {
        list.retainAll(list);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationClear() {
        list.clear();
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationRemoveObject() {
        list.remove((Integer) 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationIndexOf() {
        list.indexOf(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationLastIndexOf() {
        list.lastIndexOf(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationListIterator() {
        list.listIterator();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationListIteratorIndex() {
        list.listIterator(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkUnsupportedOperationSubList() {
        list.subList(0, 10);
    }
    
}
