import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> tree;
    public PointSET(){
        tree = new TreeSet<Point2D>();
    }                               // construct an empty set of points
    public boolean isEmpty() {
        return tree.size() == 0;
    }                     // is the set empty?
    public int size() {
        return tree.size();
    }                      // number of points in the set
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        tree.add(p);
    }             // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        return tree.contains(p);
    }           // does the set contain point p?
    public void draw() {
        StdDraw.clear();
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        for(Point2D p : tree){
            p.draw();
        }
    }                        // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> rangeTree = new TreeSet<>();
        for(Point2D p : tree){
            if(rect.contains(p)) rangeTree.add(p);
        }
        return rangeTree;
    }            // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        Point2D nearstResult = null;
        if(tree.isEmpty()) return nearstResult;
        for(Point2D p2D : tree){
            if(nearstResult == null || p2D.distanceSquaredTo(p) < nearstResult.distanceSquaredTo(p)){
                nearstResult = p2D;
            }
        }
        return nearstResult;
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
//        In in = new In(args[0]);
        PointSET pointSET = new PointSET();
//        while (!in.isEmpty()){
//            Point2D point = new Point2D(in.readDouble(), in.readDouble());
//            if(!pointSET.contains(point)) pointSET.insert(point);
//        }
        Point2D p1 = new Point2D(0.206107, 0.095492);
        Point2D p2 = new Point2D(0.975528, 0.654508);
        Point2D p3 = new Point2D(0.024472, 0.345492);
        Point2D p4 = new Point2D(0.793893, 0.095492);
        Point2D p5 = new Point2D(0.793893, 0.904508);
        Point2D p6 = new Point2D(0.975528, 0.345492);
        Point2D p7 = new Point2D(0.206107, 0.904508);
        Point2D p8 = new Point2D(0.500000, 0.000000);
        Point2D p9 = new Point2D(0.024472, 0.654508);
        Point2D p10 = new Point2D(0.024472, 0.654508);
//        Point2D p1 = new Point2D(0.7, 0.2);
//        Point2D p2 = new Point2D(0.5, 0.4);
//        Point2D p3 = new Point2D(0.9, 0.6);
//        Point2D p4 = new Point2D(0.2, 0.3);
//        Point2D p5 = new Point2D(0.4, 0.7);
        pointSET.insert(p1);
        pointSET.insert(p2);
        pointSET.insert(p3);
        pointSET.insert(p4);
        pointSET.insert(p5);
        pointSET.insert(p6);
        pointSET.insert(p7);
        pointSET.insert(p8);
        pointSET.insert(p9);
        pointSET.insert(p10);
        System.out.println(pointSET.size());
        Point2D requestPoint = new Point2D(0.1, 0.1);
        RectHV requestRect = new RectHV(0.0,0.095492,0.0,0.095492);
//        pointSET.nearest(requestPoint);
        pointSET.range(requestRect);
        pointSET.draw();
    }                 // unit testing of the methods (optional)
}
