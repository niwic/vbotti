package fi.niwic.util;

// Näistä ei ole mitään toiminnallisuutta käytössä. Nämä ovat tässä koska
// implementoidaan java rajapinta List<T>. Implementaatio pitää olla rajapinnan
// mukainen, koska testissä pelikenttän generoinnissa vaaditaan rajapinnan
// mukainen lista.
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ArrayList<T> implements List<T> {

    private int size;
    private Object storage[];
    
    public ArrayList() {
        storage = new Object[10];
        size = 0;
    }
    
    public ArrayList(T[] list) {
        storage = list;
        size = list.length;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T set(int index, T element) {
        storage[index] = element;
        return element;
    }
    
    @Override
    public boolean add(T object) {
        if (size < storage.length) {
            storage[size] = object;
            size++;
        } else {
            resize(storage.length*2);
            add(object);
        }
        
        return true;
    }
    
    private void resize(int newCapacity) {
        Object newStorage[] = new Object[newCapacity];
        for (int i = 0; i < storage.length; i++) {
            newStorage[i] = storage[i];
        }
        storage = newStorage;
    }
    
    @Override
    public T get(int index) {
        return (T) storage[index];
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int pos = 0;
            
            @Override
            public boolean hasNext() {
                return pos < size;
            }

            @Override
            public T next() {
                T nextVal = (T) storage[pos];
                pos++;
                return nextVal;
            }
        };
    }
    
    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
