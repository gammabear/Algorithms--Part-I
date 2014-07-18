import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    
    private int N;                // size of the queue
    private Node first, last;     // front, end of queue
  
  
    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() { 
        first = null;
        last = null;
        N = 0;
        //assert check();

        
    }
    
    // is the deque empty?
    public boolean isEmpty() {
       return N == 0;
    }
    
        
        
    // return the number of items on the deque   
    public int size() {
        return N;
    }
    
        
    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)    throw new NullPointerException("Null item");
        
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;

        if (isEmpty()) {
            last = first;
            first.next = null;
        } else {
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        N++;
        assert check();
    }
       
    // insert the item at the end
    public void addLast(Item item) {
        if (item == null)    throw new NullPointerException("Null item");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
            last.prev = null;
        }
        else {
            oldlast.next = last;
            last.prev = oldlast;
        }
        N++;
    }
       
    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;         // save item to return
        first = first.next;
        N--;
        if (isEmpty()) {
            first = null;
            last = null;
        } else {
            first.prev = null;  
        }
        assert check();
        return item;                    // return the saved item
    }
        
    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = last.item;          // save item to return
        last = last.prev;
        N--;
        if (isEmpty()) {
            first = null;
            last = null;
        } else {                
            last.next = null;
        }
        assert check();
        return item;
   }
        
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
        
     private class ListIterator implements Iterator<Item> {  
        private Node current = first;  
        
        public boolean hasNext() { return current != null; }  
        public void remove()     { throw new UnsupportedOperationException(); }
  
        public Item next() {  
            if (!hasNext()) throw new NoSuchElementException();  
            Item item = current.item;  
            current = current.next;  
            return item;  
        }  
    }  
    
        
    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null) return false;
        }
        else if (N == 1) {
            if (first == null)      return false;
            if (first.next != null) return false;
        }
        else {
            if (first.next == null) return false;
        }

        // check internal consistency of instance variable N
        int numberOfNodes = 0;
        for (Node x = first; x != null; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != N) return false;

        return true;
    }    
        

        
    public static void main(String[] args) { 
        
        
   }
   
    
    
    
    
}
