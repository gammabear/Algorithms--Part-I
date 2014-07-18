import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;               // array of items
    private int N;                   // number of elements on stack
    
    // construct an empty randomized queue
    public RandomizedQueue() { 
        a = (Item[]) new Object[2];
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }
   
    // return the number of items on the queue
    public int size() {
        return N;
    }   
    
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)    throw new NullPointerException("Null item");
        
        // double size of array if necessary
        if (N == a.length) resize(2*a.length);      
        a[N++] = item;                              
    }
    
     
    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int randomIndex = StdRandom.uniform(N);
        Item item = a[randomIndex];
        a[randomIndex] = a[N-1];
        a[N-1] = null;                              // to avoid loitering
        N--;
        
        // shrink size of array if necessary
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
    }
            
    // return (but do not delete) a random item       
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int randomIndex = StdRandom.uniform(N);
        return a[randomIndex];
    }
      
  
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }
    
    private class QueueIterator implements Iterator<Item> {
        private int i = 0;
        private int[] shuffledIndexes;
        
        // contstruct a shuffled array from the orignal one
        public QueueIterator() {
            shuffledIndexes = new int[N];
            for (int j = 0; j < N; j++) {
                shuffledIndexes[j] = j;
            }
            StdRandom.shuffle(shuffledIndexes);
        }
        
        public boolean hasNext() {
            return i < N;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = a[shuffledIndexes[i]];
            i++;
            return item;
        }
           
    }
    
    
    // unit testing
    public static void main(String[] args) { 
         
    }
}
