package fi.niwic.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ArrayList<T> implements List<T> {

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int indexOf = indexOf(o);
        if (indexOf > -1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object[] toArray() {
        return storage;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return a;
    }

    @Override
    public boolean remove(Object o) {
        int indexOf = indexOf(o);
        if (indexOf > -1) {
            storage[indexOf] = null;
            for (int i = indexOf; i < size - 1; i++) {
                storage[i] = storage[i+1];
            }
            size--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (indexOf(object) == -1) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T object : c) {
            add(object);
        }
        
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object obj : c) {
            int indexOf = indexOf(obj);
            if (indexOf > -1) {
                storage[indexOf] = null;
            } else {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        
        size = 0;
    }

    @Override
    public T set(int index, T element) {
        storage[index] = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
        if (index > storage.length) {
            resize(storage.length*2);
        }
        
        storage[index] = element;
    }

    @Override
    public T remove(int index) {
        T obj = (T) storage[index];
        storage[index] = null;
        return obj;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(storage[i])) {
                return i;
            }
        }
        
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        for (int i = 0; i < size; i++) {
            if (o.equals(storage[i])) {
                lastIndex = i;
            }
        }
        
        return lastIndex;
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

    private int size;
    private Object storage[];
    
    public ArrayList() {
        storage = new Object[10];
        size = 0;
    }
    
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
    
    public T get(int index) {
        return (T) storage[index];
    }
    
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

}
