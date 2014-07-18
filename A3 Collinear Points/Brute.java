import java.util.Arrays;

public class Brute {
    
    
    public static void main(String[] args) { 
        
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.02);  // make the points a bit larger
       
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();

        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            p.draw();
        }
  
       Arrays.sort(points);
       collinears(points);
      
       // display to screen all at once
       StdDraw.show(0);
       
       // reset the pen radius
       StdDraw.setPenRadius();
       
    }
    
     private static void collinears(Point[] points) {
            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    for (int k = j + 1; k < points.length; k++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
                            for (int l = k + 1; l < points.length; l++) {
                                if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                                    StdOut.println(points[i].toString() + " -> " + points[j].toString() 
                                                       + " -> " + points[k].toString() + " -> " + points[l].toString());
                                    // draw line segment
                                    StdDraw.setPenRadius();
                                    points[i].drawTo(points[l]);
                                }
                            }
                        }
                    }
                }
            }
       } 
}
                                                                                     