package fi.niwic.util;

public class Queue<T> {

    private Object[] storage;
    private int head;
    private int tail;
    
    /**
     * Luo uuden vakiokokoisen jonon.
     * 
     * @param n jonon kapasiteetti
     */
    public Queue(int n) {
        storage = new Object[n];
        head = 0;
        tail = 0;
    }
    
    /**
     * Lisää jonon viimeiseski.
     * 
     * @param obj asia joka lisätään
     */
    public void add(T obj)  {
        storage[tail] = obj;
        tail++;
    }
    
    /**
     * Ottaa jonon alusta.
     * 
     * @return jonon alussa oleva asia.
     */
    public T poll() {
        T value = (T) storage[head];
        storage[head] = null;
        head++;
        return value;
    }
    
    /**
     * Onko jono tyhjä?
     * 
     * @return onko tyhjä vai ei
     */
    public boolean isEmpty() {
        return head == tail;
    }
    
}
