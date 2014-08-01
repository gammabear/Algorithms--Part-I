public class KdTree {
    
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;
    
    private Node root;         // root of the KdTree
    private int size;          // number of points in the tree
    private Point2D bestPoint; // nearest neighbor
    private double bestDist;   // distance to nearest neighbor
    
    // KdTree helper node data type
    private static class Node {
        private Point2D p;            // the point
        private RectHV rect;          // the axis-aligned rectangle corresponding to this node
        private Node lb;              // the left/bottom subtree
        private Node rt;              // the right/top subtree
        private boolean orient;       // oreintation of node
    
        
        //public Node(Point2D point, boolean orient, RectHV rect) {
        public Node(Point2D point, boolean orient, RectHV rect) {
            this.p = point;
            this.orient = orient;
            this.rect = rect;
        }
        
        // is this point smaller than that one?
        public int compareTo(Point2D that) {
            if (that == null)    throw new NullPointerException("Null item");
            // but at the root we use the x-coordinate (if the point to be 
            // inserted has a smaller x-coordinate than the point at the root, 
            //go left; otherwise go right);
            if (this.p.y() == that.y() && this.p.x() == that.x())     return 0;
            if (this.orient == VERTICAL && that.x() < this.p.x())     return -1; 
            if (this.orient == HORIZONTAL && that.y() < this.p.y())   return -1; 
            return +1;    // right
        }
        
        public RectHV getRect(Point2D point) {
            int cmp = this.compareTo(point);
            if         (cmp < 0) {
                 if (this.orient == VERTICAL) return new RectHV(this.rect.xmin(), this.rect.ymin(), this.p.x(), this.rect.ymax());
                 else                         return new RectHV(this.rect.xmin(), this.rect.ymin(), this.rect.xmax(), this.p.y());   
            } else {
                 if (this.orient == VERTICAL) return new RectHV(this.p.x(), this.rect.ymin(), this.rect.xmax(), this.rect.ymax());
                 else                         return new RectHV(this.rect.xmin(), this.p.y(), this.rect.xmax(), this.rect.ymax());
             }
        }
        
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(p.toString() + "\n");
            s.append(rect.toString() + "\n");
            s.append(orient);
            s.append("\n");
            return s.toString();
        }
    }

    // construct an empty tree of points
    public KdTree() { 
        root = null;
        size = 0;
    }
    
    // is the tree empty?
    public boolean isEmpty() {
        return root == null;
    }
    
    // number of points in the tree
    public int size() {
        return this.size;
    }

    // add the point p to the tree (if it is not already in the tree)
    public void insert(Point2D p) {
        //RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
        RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
        root = insert(root, p, VERTICAL, r);
        
    } 
 
    // insert the point in the subtree rooted at x
    //which sets up the RectHV for each Node
    private Node insert(Node node, Point2D p, boolean orient, RectHV r) { 
        if (node == null) {    
            Node result = new Node(p, orient, r);
            //StdOut.println(result);
            this.size++;
            return result;
        }
        int cmp = node.compareTo(p);
        RectHV rect = node.getRect(p);
        if      (cmp < 0) {
            node.lb = insert(node.lb, p, !node.orient, rect);
        }
        else if (cmp > 0) {
            node.rt = insert(node.rt, p, !node.orient, rect);
        }
        else              node.p = p;
        return node;
    }

    public boolean contains(Point2D p) {
        return search(p) != null;
    }
    
    private Point2D search(Point2D p) {
        return search(root, p);
    }
    
    private Point2D search(Node node, Point2D p) {
        if (node == null) return null;
        int cmp = node.compareTo(p);
        if      (cmp < 0) return search(node.lb, p);  
        else if (cmp > 0) return search(node.rt, p);
        else              return node.p;
    }

    public void draw() {
        // draw border
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(1, 0, 1, 1);
        StdDraw.line(1, 1, 0, 1);
        StdDraw.line(0, 1, 0, 0);
        
        // draw points, splitting lines
        draw(root);
       
    }
    
    private void draw(Node node) {
        if (node == null) 
             return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.p.draw();
        StdDraw.setPenRadius();
        if (node.orient == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        draw(node.lb);
        draw(node.rt);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();
        range(root, queue, rect);  
        return queue;
    }
    
    private void range(Node node, Queue<Point2D> queue, RectHV rect) {
        if (node == null) return;
        
        // if splitting line intersects rect, search both subtress
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p))      queue.enqueue(node.p);
            range(node.lb, queue, rect);
            range(node.rt, queue, rect);
        } else {
            int cmp;
            //if (node.orient == VERTICAL)
              //  cmp = node.compareTo(new Point2D(rect.xmin(), rect.ymin()));   
            //else                         
            cmp = node.compareTo(new Point2D(rect.xmin(), rect.ymin()));   // same?            
            if      (cmp < 0)
                range(node.lb, queue, rect);
            else if (cmp > 0) 
                range(node.rt, queue, rect);
        }
    }
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        // Maintain a global best estimate of the nearest neighbor, called 'guess.'
        // Maintain a global value of the distance to that neighbor, called 'bestDist'
        bestPoint = null;
        bestDist = Double.POSITIVE_INFINITY;
        nearest(root, p);
        return bestPoint;
        
    } 
    
    private void nearest(Node node, Point2D p) {
        if (node == null)    return;
        // If the current location is better than the best known location, 
        // update the best known location.
        double distance = node.p.distanceSquaredTo(p);
        if (distance < bestDist) {
            bestDist = distance;
            bestPoint = node.p;
        }
        // Recursively search the half of the tree that contains the test point.
        int cmp = node.compareTo(p);
        
        if      (cmp < 0)    nearest(node.lb, p);  
        else if (cmp > 0)    nearest(node.rt, p);
        // If the candidate hypersphere crosses this splitting plane, look on the
        // other side of the plane by examining the other subtree.
        //StdOut.println("node's properties" + node); 
        
        //if (cmp < 0){
        //    StdOut.println("nearest neighbor is on left");
        //    StdOut.println("node child" + node.toString()); 
        //    StdOut.println("node child" + node.lb.toString());
        //}
            
        
    }

    public static void main(String[] args) { 
        // rescale coordinates and turn on animation mode
        /*
        StdDraw.setXscale(0, 3);
        StdDraw.setYscale(0, 3);
        StdDraw.show(0);

        KdTree tree = new KdTree();
        Point2D p1 = new Point2D(.7000, .2000);
        Point2D p2 = new Point2D(.5000, .4000);
        Point2D p3 = new Point2D(.2000, .3000);
        Point2D p4 = new Point2D(.4000, .7000);
        Point2D p5 = new Point2D(.9000, .6000);  
      
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);
        tree.draw();
        
        
        Point2D nearest = tree.nearest(new Point2D(0.1, 0.1));
        StdOut.println("nearest:" + nearest); */
      
        //for (Point2D point: tree.range(new RectHV(0.2, 0.2, 0.4, 0.4)))
        //        StdOut.println(point);
        

        /*
        boolean result = tree.contains(p1);
        StdOut.println("expect false: " + result);
        boolean result2 = tree.contains(p2);
        StdOut.println("expect true: " + result2);
        boolean result3 = tree.contains(p3);
        StdOut.println("expect true: " + result3);
        boolean result4 = tree.contains(p4);
        StdOut.println("expect true: " + result4);
        

        */
        
         //StdDraw.show(0);
    }
    
}
