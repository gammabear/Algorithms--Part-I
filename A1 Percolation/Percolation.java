/**
 * Percolation Java Class.
 */


public class Percolation {

    private boolean[][] grid;
    private int dim;
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionUF wqu;
    private WeightedQuickUnionUF wqu2;

   
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N > 0) {
            dim = N;
            virtualTop = 0;
            virtualBottom = N*N+1;
            grid = new boolean[N][N];
            wqu = new WeightedQuickUnionUF(N*N+2);
            wqu2 = new WeightedQuickUnionUF (N*N+1);
        } else {
            throw new IllegalArgumentException();
        }
    };
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        int index;
        
        grid[i-1][j-1] = true;
        
        index = this.getIndex(i, j);
         
        // special case: connect to top cells
        if (i == 1) {
            wqu.union(index, virtualTop);
            wqu2.union(index, virtualTop);
        }
        
        // special case: connect to bottom cells
        if (i == dim) {
            wqu.union(index, virtualBottom);
        }
    
        if (i > 1 && this.isOpen(i-1, j)) {
            wqu.union(index, this.getIndex(i-1, j));
            wqu2.union(index, this.getIndex(i-1, j));
        }
        
        if (i < dim && this.isOpen(i+1, j)) {
            wqu.union(index, this.getIndex(i+1, j));
            wqu2.union(index, this.getIndex(i+1, j));
        }
        if (j > 1 && this.isOpen(i, j-1)) {
            wqu.union(index, this.getIndex(i, j-1));
            wqu2.union(index, this.getIndex(i, j-1));
        }
        if (j < dim && this.isOpen(i, j+1)) {
            wqu.union(index, this.getIndex(i, j+1));
            wqu2.union(index, this.getIndex(i, j+1));
        }
       
    };  
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {

        if (i > 0 && i <= dim && j > 0 && j <= dim) {
            return grid[i-1][j-1];
        } else {
            throw new IndexOutOfBoundsException();
        }

    };
    
    // is site (row i, column j) full? 
    public boolean isFull(int i, int j) {
        int index;
        
        if (i > 0 && i <= dim && j > 0 && j <= dim) {
            index = getIndex(i, j);
            
            return wqu2.connected(index, virtualTop);
            

            
            
            
        } else {
            throw new IndexOutOfBoundsException();
        }
    }; 
    
    // does the system percolate?    
    public boolean percolates() {
        return wqu.connected(virtualTop, virtualBottom);
    };   
    
    private int getIndex(int i, int j) {
        int index;
        index = (i-1)*dim + j; 
        return index;
    }

    
    public static void main(String[] args) { 
        
        Percolation myPercolation = new Percolation(10);
        

        
    }

}
