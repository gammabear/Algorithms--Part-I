/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();       

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    
    private class BySlope implements Comparator<Point>
    {
        public int compare(Point v, Point w) {
            double sv = slopeTo(v);
            double sw = slopeTo(w);
            return Double.compare(sv, sw);
        
        }
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null)    throw new NullPointerException("Null item");
        if (that.x == this.x && that.y == this.y)  return Double.NEGATIVE_INFINITY;        
        if (that.x == this.x)                      return Double.POSITIVE_INFINITY;
        if (that.y == this.y)                      return 0.0;             
        double slope = (double) (that.y - this.y)/(that.x - this.x);
        return slope; 
    }
    
    
    

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null)    throw new NullPointerException("Null item");
        if (this.y == that.y && this.x == that.x) return 0;
        if (this.y < that.y)                      return -1;
        if (this.y == that.y && this.x < that.x)  return -1;
        return +1;

    }
        
    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point p = new Point(20379, 25585);
        Point q = new Point(20379, 23784);
        double m = p.slopeTo(q);
        StdOut.println("expect slope Infinity" + m);
    }
}
