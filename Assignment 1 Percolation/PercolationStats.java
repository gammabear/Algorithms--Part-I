/**
 * Auto Generated Java Class.
 */
public class PercolationStats {
    
    private double[] percolationThreshold;
    private int experimentCount;
    
    public PercolationStats(int N, int T) { 
        
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Given N <= 0 || T <= 0");
        }
        
        percolationThreshold = new double[T];
        experimentCount = T;
        
        Percolation perc;
        
        for (int k = 0; k < T; k++) {
            perc = new Percolation(N);
            
            int openCount = 0;
            while (!perc.percolates()) {

                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;
                
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    openCount++;    
                }
            }

            percolationThreshold[k] = (double) openCount/ (N * N);
        }
        

   }
    
    public double mean() {
       
        return StdStats.mean(percolationThreshold);    
        
        
    }
    
    public double stddev() {
        
        return StdStats.stddev(percolationThreshold);
        

        
    }
    
    public double confidenceLo() {


        return mean() - ((1.96 * stddev()) / Math.sqrt(experimentCount));

        
    }
    
    public double confidenceHi() {
        
        return mean() + ((1.96 * stddev()) / Math.sqrt(experimentCount));

       
    }
    
     
    public static void main(String[] args) { 
        int N = 0;
        int T = 0;
        if (args.length > 0) {
            try {
                N = Integer.parseInt(args[0]);
                T = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid Arguement.");
            }
        }
            
        PercolationStats myStats = new PercolationStats(N, T);
        
        StdOut.println("mean                    = " + myStats.mean());
        StdOut.println("stddev                  = " + myStats.stddev());
        StdOut.println("95% confidence interval = " + myStats.confidenceLo() + ", " 
                           + myStats.confidenceHi());
        
    }
    
    
    
}
