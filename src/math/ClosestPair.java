package math;

import java.util.ArrayList;

import math.geom3d.Point3D;


/**
 * Finds the pair of closest points using different algorithms.
 * 
 */
public class ClosestPair {
    
    /**
     * Finds the pair of closest points using a naive method in O(n^2).
     */
    public static ArrayList<Point3D> Naive(ArrayList<Point3D> points) {
    	Point3D p0 = points.get(0);
    	Point3D p1 = points.get(1);
        
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if(points.get(i).distance(points.get(j)) < p0.distance(p1)) {
                    p0 = points.get(i);
                    p1 = points.get(j);
                }
            } 
        }
        
        ArrayList<Point3D> resultPair = new ArrayList<Point3D>();
        resultPair.add(p0);
        resultPair.add(p1);
        
        return resultPair;
    }
}