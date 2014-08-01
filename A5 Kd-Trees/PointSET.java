public class PointSET {
    
    private SET<Point2D> set; 
    
    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return set.size();
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("called insert() with a null point");
        set.add(p);
    }
    
    // does the set contain the point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("called contains() with a null point");
        return set.contains(p);
    }
    
    // draw all of the points to standard draw
    public void draw() {
        for (Point2D point: set) {
            point.draw();
        }
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        // make a queue
        Queue<Point2D> result = new Queue<Point2D>();
        for (Point2D point: set) {
            if (rect.contains(point)) {
                result.enqueue(point);
            }
        }
        return result;
    }
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (set.isEmpty())       return null;

        Point2D nearest = new Point2D(0, 0); 
        
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point2D point: set) {
           double distance = p.distanceTo(point);
           if (distance < minDistance) {
               minDistance = distance;
               nearest = point;
           }
        }
        return nearest;
    }
    
    public static void main(String[] args) { 
        /*
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
        PointSET set = new PointSET();
        Point2D p1 = new Point2D(2000, 2000);
        Point2D p2 = new Point2D(5000, 5000);
        Point2D p3 = new Point2D(8000, 8000);
        Point2D p4 = new Point2D(1001, 1001);
        //StdDraw.setPenColor(StdDraw.RED);
        set.insert(p2);
        set.insert(p3);
        set.insert(p4);
        
        p1.draw(); 
        p2.draw();
        p3.draw();
        
        Point2D result;
        
        result = set.nearest(p1);
        StdOut.println("nearest: " + result);
        
        // display to screen all at once
       StdDraw.show(0);
       */
    }
    
    
    
}
