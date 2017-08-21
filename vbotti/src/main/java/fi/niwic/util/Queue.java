package fi.niwic.util;

public class Queue<T> {

    private Object[] storage;
    private int head;
    private int tail;
    
    public Queue(int n) {
        storage = new Object[n];
        head = 0;
        tail = 0;
    }
    
    public void add(T obj)  {
        storage[tail] = obj;
        tail++;
    }
    
    public T poll() {
        T value = (T) storage[head];
        storage[head] = null;
        head++;
        return value;
    }
    
    public boolean isEmpty() {
        return head == tail;
    }
    
}
