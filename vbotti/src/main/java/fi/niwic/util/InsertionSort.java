package fi.niwic.util;

/**
 * Lisäysjärjestäminen ArrayList oliolle jonka jäsenet ovat verattavissa.
 */
public class InsertionSort {

    /**
     * Järjestää ArrayList olion lisäysjärjestämisalgoritmilla.
     * 
     * Algoritmi soveltuu hyvin pienten listojen järjestämiseen, eli esim.
     * pelikentän siirtojen järjestämiseen.
     * 
     * @param <T> verrattavissa oleva tyyppi
     * @param list lista joka järjestetään
     */
    public static <T extends Comparable<T>> void sort(ArrayList<T> list) {
        
        for (int i = 1; i < list.size(); i++) {
            Comparable<T> x = list.get(i);
            
            int j = i - 1;
            while (j > -1 && list.get(j).compareTo((T) x) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            
            list.set(j + 1, (T) x);
        }
        
    }
    
}
