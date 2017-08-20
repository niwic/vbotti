package fi.niwic.util;

public class InsertionSort {

    public static <T extends Comparable<T>> ArrayList<T> sort(ArrayList<T> list) {
        
        for (int i = 1; i < list.size(); i++) {
            Comparable<T> x = list.get(i);
            
            int j = i-1;
            while (j > -1 && list.get(j).compareTo((T) x) > 0) {
                list.set(j+1, list.get(j));
                j--;
            }
            
            list.set(j+1, (T) x);
        }
        
        return list;
    }
    
}
