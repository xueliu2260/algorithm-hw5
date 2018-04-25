import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private int count = 0;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        private Node(Point2D p, double rectLBX, double rectLBY, double rectRTX, double rectRTY) {
            this.p = p;
            this.rect = new RectHV(rectLBX, rectLBY, rectRTX, rectRTY);
            this.lb = null;
            this.rt = null;
        }
    }

    private Node root = null;

    public boolean isEmpty() {
        return count == 0;
    }

    private void tranversalDraw(Node root, int level) {
        if (root.lb != null) tranversalDraw(root.lb, level + 1);
        double x = root.p.x();
        double y = root.p.y();
        if (level % 2 == 0) {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(root.rect.xmin(), y, root.rect.xmax(), y);
        } else {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x, root.rect.ymin(), x, root.rect.ymax());
        }
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(x, y);
        if (root.rt != null) tranversalDraw(root.rt, level + 1);
    }

    public void draw() {
        tranversalDraw(root, 1);
    }

    public int size() {
        return count;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Node curNode = root;
        int level = 1;
        double x = p.x();
        double y = p.y();
        while (curNode != null) {
            if (curNode.p.x() == x && curNode.p.y() == y) return true;
            if (level % 2 == 0) {
                if (y >= curNode.p.y()) curNode = curNode.rt;
                else curNode = curNode.lb;
            } else {
                if (x >= curNode.p.x()) curNode = curNode.rt;
                else curNode = curNode.lb;
            }
            level += 1;
        }

        return false;
    }

    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        if (contains(point)) return;
        root = insert(root, point, 0, 0, 1, 1, 1);
    }

    private Node insert(Node node, Point2D point, double xmin, double ymin, double xmax, double ymax, int level) {
        if (node == null) {
            count += 1;
            return new Node(point, xmin, ymin, xmax, ymax);
        }

        int cmp = cmp(node.p, point, level);
        if (cmp < 0) {
            if (level % 2 == 0) {
                node.lb = insert(node.lb, point, xmin, ymin, xmax, node.p.y(), level + 1);
            } else {
                node.lb = insert(node.lb, point, xmin, ymin, node.p.x(), ymax, level + 1);
            }
        } else {
            if (level % 2 == 0) {
                node.rt = insert(node.rt, point, xmin, node.p.y(), xmax, ymax, level + 1);
            } else {
                node.rt = insert(node.rt, point, node.p.x(), ymin, xmax, ymax, level + 1);
            }
        }
        return node;
    }

    private int cmp(Point2D node, Point2D point, int level) {
        int result;
        if (level % 2 == 0) {
            result = new Double(point.y()).compareTo(new Double(node.y()));
        } else {
            result = new Double(point.x()).compareTo(new Double(node.x()));
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Node curNode = root;
//        nearestResult = null;
//        distance = Double.MAX_VALUE;
        Point2D result = null;
        if (curNode == null || !curNode.rect.contains(p)) return result;
        result = findNearest(root, result, p);
//        System.out.println("nearest point : " + result);
        return result;
    }
//    private Point2D nearestResult;
//    private double distance;
    private Point2D findNearest(Node root, Point2D result, Point2D p){
        if(root != null){
            if(result == null){
                result = root.p;
            }
            if(result.distanceSquaredTo(p) >= root.rect.distanceSquaredTo(p)){
                if(root.p.distanceSquaredTo(p) < result.distanceSquaredTo(p)){
                    result = root.p;
                }
                if(root.rt != null && root.rt.rect.contains(p)){
                    result = findNearest(root.rt, result, p);
                    result = findNearest(root.lb, result, p);
                }else {
                    result = findNearest(root.lb, result, p);
                    result = findNearest(root.rt, result, p);
                }
            }
        }
        return result;
//        if(root.lb != null) findNearest(root.lb, p);
//        if(root.p.distanceSquaredTo(p) < distance){
//            nearestResult = root.p;
//            distance = root.p.distanceSquaredTo(p);
//        }
//        if(root.rt != null) findNearest(root.rt, p);
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> result = new ArrayList<>();
        List<Point2D> res = findRange(rect, root, result);
//        for(Point2D p : res){
//            System.out.println("rect contains : " + p.x() + "   " + p.y());
//        }
//        System.out.println(res.size());
        return res;
    }

    private List<Point2D> findRange(RectHV rect, Node curNode, List<Point2D> result) {
        if (curNode == null || !curNode.rect.intersects(rect)) return result;
        if (rect.contains(curNode.p)) result.add(curNode.p);
        if (curNode.lb != null && curNode.lb.rect.intersects(rect)) findRange(rect, curNode.lb, result);
        findRange(rect, curNode.rt, result);
        return result;
    }

    public static void main(String[] args) {
//        In in = new In(args[0]);
        KdTree kdTree = new KdTree();
//        while (!in.isEmpty()) {
//            Point2D point = new Point2D(in.readDouble(), in.readDouble());
//            kdTree.insert(point);
//        }

        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
//        Point2D p3 = new Point2D(0.9, 0.6);
        Point2D p4 = new Point2D(0.2, 0.3);
        Point2D p5 = new Point2D(0.4, 0.7);
        Point2D p6 = new Point2D(0.9, 0.6);
//        Point2D p1 = new Point2D(1.0, 0.0);
//        Point2D p2 = new Point2D(0.375, 0.125);
//        Point2D p3 = new Point2D(0.125, 0.25);
//        Point2D p4 = new Point2D(0.5, 0.875);
//        Point2D p5 = new Point2D(0.0, 0.1);
        kdTree.insert(p1);
        kdTree.insert(p2);
//        kdTree.insert(p3);
        kdTree.insert(p4);
        kdTree.insert(p5);
        kdTree.insert(p6);
//        kdTree.insert(p7);
//        kdTree.insert(p8);
//        kdTree.insert(p9);
//        kdTree.draw();
        kdTree.nearest(new Point2D(0.702, 0.96));
        System.out.println(kdTree.size());
//        kdTree.range(new RectHV(0.951904296775, 0.001220703025, 0.951904296975, 0.001220703225));
    }
}