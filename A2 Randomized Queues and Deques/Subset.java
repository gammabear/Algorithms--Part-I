public class Subset {  
    public static void main(String[] args) {  
        RandomizedQueue<String> rq = new RandomizedQueue<String>();  
        int n = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {  
            String item = StdIn.readString();  
            rq.enqueue(item);  
        }  
        while (n > 0) {  
            StdOut.println(rq.dequeue());  
            n--;  
        }  
    }  
}  

