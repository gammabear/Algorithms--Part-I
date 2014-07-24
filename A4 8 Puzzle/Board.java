public class Board {
    
    private final int N;    // size of Board
    private int[][] tiles;
    private int manhattan = -1;
    private int hamming = -1;
    
    //construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        tiles = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
    }

    // board dimension N
    public int dimension() {
       return this.N; 
    }
        
    // number of blocks out of place
    public int hamming() {
        if (this.hamming >= 0)    return this.hamming;
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int idx = (i * N) + (j + 1);
                if (tiles[i][j] == idx) {
                    result += 0;
                } else if (tiles[i][j] == 0) {
                    continue;
                } else {           
                    result += 1;
                }
            }
        }
        this.hamming = result;
        return result;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (this.manhattan >= 0)    return this.manhattan;
        
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int idx = (i * N) + (j + 1);
                if (tiles[i][j] == idx) {
                    result += 0;
                } else if (tiles[i][j] == 0) {
                    continue;
                } else {
                    result += manhattanHelper(tiles[i][j], i, j);
                }
             }
        }
        this.manhattan = result;
        return result;
    }
    
    private int manhattanHelper(int number, int row, int col) {
        int distance;
        int destRow = (int) (Math.ceil((double) number / this.N) - 1);
        int destCol = (number + (this.N - 1)) % this.N;
        distance = Math.abs(destRow - row) + Math.abs(destCol - col);
        return distance;
    }
        
    // is this board the goal board?
    public boolean isGoal() {
        boolean result = false; 
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                int idx = (i * this.N) + (j + 1);
                if (idx !=  N * N - 1 && tiles[i][j] != idx) {
                    return false;
                } 
                if (idx == N * N - 1) {
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
        outerloop:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (blocks[i][j] == 0) {
                    break;
                } 
                if (blocks[i][j] != 0) {
                    if (j == 0) {
                        prev = blocks[i][j];
                    } 
                    if (j == 1) {
                        // swap
                        blocks[i][j-1] = blocks[i][j];
                        blocks[i][j] = prev;
                        break outerloop;
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
        if (that.dimension() != this.dimension())
            return false;
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
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
        return result;
    }

    private int[][] swap(int[][] blocks, int row, int col, int toRow , int toCol) {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++) 
            copy[i][j] = blocks[i][j];
        copy[toRow][toCol] = blocks[row][col];
        copy[row][col] = blocks[toRow][toCol];
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
