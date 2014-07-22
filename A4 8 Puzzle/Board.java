public class Board {
    
    private final int N;    // size of Board
    private int[][] tiles;               // = new int[N][N];
    
    //construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        //blocks = new int[this.N][this.N];
        N = blocks.length;
        tiles = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = (char) blocks[i][j];
                //StdOut.println("tile at i,j is :" + i + j + blocks[i][j]);
            }
        }
    }
    
   // board dimension N
    public int dimension() {
       return this.N; 
    }
        
    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int idx = (i * N) + (j + 1);
                if (tiles[i][j] == idx) {
                    hamming += 0;
                } else if (tiles[i][j] == 0) {
                    continue;
                } else {           
                    hamming += 1;
                }
            }
        }
        return hamming;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int idx = (i * N) + (j + 1);
                if (tiles[i][j] == idx) {
                    manhattan += 0;
                } else if (tiles[i][j] == 0) {
                    continue;
                } else {
                    manhattan += manhattan_helper(tiles[i][j], i, j);
                }
             }
        }
        return manhattan;
    }
    
    private int manhattan_helper(int number, int row, int col) {
        // calculate the manhattan distance of this tile
        int manhattan_dist;
        int dest_row = (int) (Math.ceil((double) number / this.N) - 1);
        int dest_col = (number + (this.N - 1)) % this.N;
        manhattan_dist = Math.abs(dest_row - row) + Math.abs(dest_col - col);
        return manhattan_dist;
    }
                                 
                                 
        
    // is this board the goal board?
    public boolean isGoal() {
        boolean result = false; 
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                int idx = (i * this.N) + (j + 1);
                //StdOut.println("index: , tile: " + idx +", " + tiles[i][j]);
                //int temp = this.N-1;
                //StdOut.println("N-1: " + temp);
                //if (tiles[i][j] != idx) StdOut.println("bad tile"); 
                if (idx !=  N * N - 1 && tiles[i][j] != idx) {
                    //StdOut.println("found misplaced tile, idx, value:" + idx + tiles[i][j]); 
                    return false;
                } 
                if (idx == N * N - 1) {
                    //StdOut.println("reached last tile");
                    if (tiles[N - 1][N - 1] == 0)   return true;
                    return false;
                }
            }
        }
        return result;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        
        // copy board
        int [][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++) 
                 blocks[i][j] = tiles[i][j];
        
        // swapping operation
        int prev = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int idx = (i * this.N) + (j + 1);
                //StdOut.println("index: " + idx);
                // if number is 0 go onto next 
                if (blocks[i][j] == 0) {
                    break;
                } else if (blocks[i][j] != 0) {
                    //StdOut.println("row: " + i);
                    if (j == 0) {
                        // save current number
                        prev = blocks[i][j];
                    } else if (j == 1) {
                        // swap
                        blocks[i][j-1] = blocks[i][j];
                        blocks[i][j] = prev;
                    }
                }
            }
        }
        Board twin = new Board(blocks);
        return twin;
    }
        
    // does this board equal y?
    public boolean equals(Object y) {
        
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        
        boolean board = true;
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    board = false;
                }
            }
        }
        return (this.N == that.N) && board;
    }
        

    // all neighboring boards    
    public Iterable<Board> neighbors() {
        
        // make a queue
        Queue<Board> result = new Queue<Board>();
        
        int i, j = 0;
        outerloop:
        for (i = 0; i < N; i++) 
            for (j = 0; j < N; j++) {
            if (tiles[i][j] == 0) {
                      
                      break outerloop;
            }
        }
        
        if (i > 0) 
            result.enqueue(new Board(swap(this.tiles, i, j, i - 1, j)));
        if (i < this.N - 1)
            result.enqueue(new Board(swap(this.tiles, i, j, i + 1, j)));
        if (j > 0)
            result.enqueue(new Board(swap(this.tiles, i, j, i , j - 1)));
        if (j < this.N -1)
            result.enqueue(new Board(swap(this.tiles, i, j, i , j + 1)));
        
        /*
        // make some board
        int[][] blocks = new int[N][N];
        for (int id = 0; id < N; id++) 
            for (int jd = 0; jd < N; jd++) 
                 blocks[i][j] = 1;
        
        Board test1 = new Board(blocks);
        
        //int[][] blocks = new int[N][N];
        for (int idx = 0; idx < N; idx++) 
            for (int jdx = 0; jdx < N; jdx++) 
                 blocks[i][j] = 2;
        
        Board test2 = new Board(blocks);
        */
        
        //result.enqueue(test1);
        
        //result.enqueue(test2);
        
        return result;
    }

    public int[][] swap(int[][] blocks, int row, int col, int to_row , int to_col) {
        
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++) 
                 copy[i][j] = blocks[i][j];
  
        copy[to_row][to_col] = blocks[row][col];
        copy[row][col] = blocks[to_row][to_col];
        
        return copy;
    }

        
    // string representation of the board (in the output format specified below)    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) { 
       
    }
    
}