import java.util.Arrays;

public class Fast {

    private static void printLine(Point[] points) {
        int last = points.length - 1;
        if (last > 0) {
            for (int i = 0; i < last; i++) {
                System.out.print(points[i] + " -> ");
            }
        }
        System.out.println(points[last]);
    }
    
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
       //Point first = points[0];
       
       for (int i = 0; i < points.length; i++) { 
           // p is the origin
           Point p = points[i];
           
           
           // find other points q
           Point[] q = Arrays.copyOfRange(points, 0, points.length);
           q[0] = points[i];
           q[i] = points[0];
           
           Arrays.sort(q, p.SLOPE_ORDER);
           int counter = 0;
           
           for (int j = 1; j < q.length; j++) {
               if (p.slopeTo(q[j]) == p.slopeTo(q[j - 1])) {
                   if (j == q.length-1) {
                       if (counter >= 1) {
                           Point[] tempq = Arrays.copyOfRange(q, j-counter-2, j+1);
                           tempq[0] = q[0];
                           Arrays.sort(tempq);
                           if (q[0] == tempq[0]) {
                               printLine(tempq);
                               StdDraw.setPenRadius();
                               p.drawTo(q[j]);
                           }
                           counter = 0;
                       }
                   }
                   counter++;
               // slopes aren't equal    
               } else {
                   if (counter >= 2) {
                       Point[] auxq = Arrays.copyOfRange(q, j-counter-2, j);
                       auxq[0] = q[0];
                       Arrays.sort(auxq);
                       if (q[0] == auxq[0]) { 
                           printLine(auxq);
                           StdDraw.setPenRadius();
                           p.drawTo(q[j-1]);
                       }
                       counter = 0;
                   }
                   counter = 0;
               }
           } 
       }   
       // display to screen all at once
       StdDraw.show(0);
       
       // reset the pen radius
       StdDraw.setPenRadius();
    }
}
