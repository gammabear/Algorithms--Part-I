import java.util.Comparator;

public class Solver {
    
    private final MinPQ<Node> pq;
    private final MinPQ<Node> pq_twin;
    
    private int moves = 0;
    
    // helper Node class
    private class Node implements Comparable<Node>{
        private Board board;
        private int moves;
        private Node prev;
        
        // create the Node
        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        
        }
        
        //private class ByManhattan implements Comparator<Node>
        //{
            //public int compare(Node v, Node w) {
            //    int mv = v.manhattan();
            //    int mw = w.manhattan();
            //    return Integer.compare(mv, mw);
            //}
        //}
        
        public int compareTo(Node that) {
            if (that == null)     throw new NullPointerException("Null item");
            if (this.board.equals(that.board))        return 0;
            if (this.board.manhattan() < that.board.manhattan())  return -1;
            return +1;
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
       
       pq = new MinPQ<Node>();
       
       // insert the initial search node
       Node init = new Node(initial, 0, null);
       pq.insert(init);
       
      // delete node with minimum priority
       Node curr_node = pq.delMin();
       
       do {
    
           
           this.moves ++;
           Queue<Board> neighbors = (Queue<Board>) curr_node.board.neighbors();
           while (!neighbors.isEmpty()) {

               Node neighbor = new Node(neighbors.dequeue(), moves, curr_node);
               pq.insert(neighbor);
           }
           curr_node = pq.delMin();
           
       } while (!curr_node.board.isGoal());
       

       
    }
    
    /*
    // is the initial board solvable?
    public boolean isSolvable() {
        
    } */
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return 1;
    }
        
    
    /*
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        
    } */
        
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++) 
                 blocks[i][j] = in.readInt();
        
        
        Board initial = new Board(blocks);
        StdOut.println(initial);
        
        int hamming = 0;
        hamming = initial.hamming();
        //StdOut.println("hamming distance: " + hamming);
        int manhattan = initial.manhattan();
        //StdOut.println("manhattan distance: " + manhattan);
        
        //boolean solved = initial.isGoal();
        //StdOut.println("solved or not? " + solved);
        
        //Board twin;
        //twin = initial.twin();
        //StdOut.println("twin board: " + twin);
        
        
        //int[][] xblocks = new int[N][N];
        //for (int i = 0; i < N; i++) 
         //   for (int j = 0; j < N; j++) 
          //       xblocks[i][j] = 1;
        
        
        
        //Board copy = new Board(blocks);
        //StdOut.println("3x3 1's: " + copy);
        
        //int[][] yblocks;
        //yblocks = initial.swap(blocks,0,0,2,2);
        //Board swapped = new Board(yblocks);
        //StdOut.println("swapped:" + swapped);
        
       //boolean equal;
       //equal = initial.equals(copy);
       //StdOut.println("are boards equal?: " + equal);
        
        
        //Queue<Board> neighbors;
        //neighbors = (Queue<Board>) initial.neighbors();
        //while (!neighbors.isEmpty()) {
        //    StdOut.println("neighbor: " + neighbors.dequeue());
        //}

        //Board one = neighbors.dequeue();
        //Board two = neighbors.dequeue();
        //StdOut.println("first neigbbor: " + one);
        //StdOut.println("second:" + two);
        
        
        
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        /*
        //if (!solver.isSolvable())
        //    StdOut.println("No solution possible");
        //else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        //}
        */
    }
    
  
    
}
