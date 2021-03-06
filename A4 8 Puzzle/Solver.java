public class Solver {
    private Node lastNode;
    private final MinPQ<Node> PQ;
    private final MinPQ<Node> twinPQ;
    private boolean solvable = false;
    
    // helper Node class
    private class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final Node prev;
        private final int priority;
        
        // create the Node
        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.priority = board.manhattan() + moves;
        }
        
        public int compareTo(Node that) {
            if (that == null)     throw new NullPointerException("Null item");
            if (this.board.equals(that.board))        return 0;
            if (this.priority < that.priority)  return -1;
            if (this.priority == that.priority && this.board.manhattan() < that.board.manhattan()) return -1;
            return +1;
        }
        
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("priority  = " + this.priority + "\n");
            s.append("moves     = " + this.moves + "\n");
            s.append("manhattan = " + board.manhattan() + "\n");
            s.append(board.toString());
            return s.toString();
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        
        PQ = new MinPQ<Node>();
        twinPQ = new MinPQ<Node>();
        PQ.insert(new Node(initial, 0, null));
        twinPQ.insert(new Node(initial.twin(), 0, null));
        do {
            Node currNode = processNode(PQ);
            Node currTwinNode = processNode(twinPQ);
            if (currNode.board.isGoal()) {
                this.solvable = true;
                lastNode = currNode;
                break;
            } 
            else if (currTwinNode.board.isGoal()) {
                this.solvable = false;
                lastNode = null;
                break;
            }
        } while(true);
    }
    
    // returns least node from priority queue
    private Node processNode(MinPQ<Node> pq) {
        // delete Node with minimum priority
        Node currNode = pq.delMin();
        
        for (Board neighborBoard: currNode.board.neighbors()) {
            if (currNode.prev == null || !neighborBoard.equals(currNode.prev.board)) {
                Node neighborNode = new Node(neighborBoard, currNode.moves + 1, currNode);
                pq.insert(neighborNode);
           }
        }
        return currNode;
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return this.solvable;
    } 
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolvable()) return lastNode.moves;
        else              return -1;
    }
   
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        } else {
            Stack<Board> solution = new Stack<Board>();
            for (Node i = lastNode; i != null; i = i.prev) {
                solution.push(i.board);
            }
            return solution;
        }
    } 

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
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
