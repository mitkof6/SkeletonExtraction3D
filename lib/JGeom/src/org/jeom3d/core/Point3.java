/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeom3d.core;

/**
 *A 3 dimensional point with coordinates {x, y, z}.
 * @author Siddharth
 * @version 05/27/2009
 */
public class Point3 extends Tuple3 {

    /**
     * Constructs the point {0, 0, 0}
     */
    public Point3() {
        super();
    }

    /**
     * Constructs a point {x, y, z}.
     * @param x the X component.
     * @param y the Y component.
     * @param z the Z component.
     */
    public Point3(double x, double y, double z) {
        super(x, y, z) ;
    }

    /**
     * Constructs a point from array {X, Y, Z}.
     * @param p
     */
    public Point3(double[] p) {
        super(p) ;
    }

    /**
     * Construct a copy of the specified point.
     * @param p the point to copy.
     */
    public Point3(Point3 p) {
        super(p) ;
    }

    /**
     * Returns the sum p+v of this point p and a vector v.
     * @param v the vector v.
     * @return the point p+v.
     */
    public Point3 plus(Vector3 v) {
        return new Point3(x+v.x, y+v.y, z+v.z) ;
    }

    /**
     * Returns the difference p-v of this point p and a vector v.
     * @param v the vector v.
     * @return the point p-v.
     */
    public Point3 minus(Vector3 v) {
        return new Point3(x-v.x, y-v.y, z-v.z) ;
    }

    /**
     * Returns the difference p-q of this point p and a point q.
     * @param q the point q.
     * @return the vector p-q.
     */
    public Vector3 minus(Point3 q) {
        return new Vector3(x-q.x, y-q.y, z-q.z) ;
    }

    /**
     * Adds a vector v to this point p and returns the result.
     * @param v the vector v.
     * @return the point p += v.
     */
    public Point3 plusEquals(Vector3 v) {
        x += v.x ;
        y += v.y ;
        z += v.z ;

        return this ;
    }

    /**
     * Subtracts the vector v from the point p and returns the result.
     * @param v the vector v.
     * @return p -= v.
     */
    public Point3 minusEquals(Vector3 v) {
         x -= v.x ;
         y -= v.y ;
         z -= v.z ;

         return this ;
    }

    /**
     * Returns the product of the specified matrix and this point.
     * w is as assumed to be 1.0.
     * @param mat the matrix.
     * @return the product A*p, scaled so that w = 1.
     */
    public Point3 timesEquals(Matrix4 mat) {
        double[] m = mat.m ;
        double ux = x ;
        double uy = y ;
        double uz = z ;

        x = m[0]*ux + m[4]*uy + m[8]* uz + m[12] ;
        y = m[1]*ux + m[5]*uy + m[9]* uz + m[13] ;
        z = m[2]*ux + m[6]*uz + m[10]*uz + m[14] ;
        double w = m[3]*ux + m[7]*uy + m[11]*uz + m[15];

        if(w != 1.0) {
            double s = 1.0/w ;
            x *= s ;
            y *= s ;
            z *= s ;
        }
        return this ;
    }

    /**
     * Returns the affine combination of this point p and a point q.
     * @param q the point q.
     * @param alpha the combination factor.
     * @return the point (1 - alpha)*p + alpha*q.
     */
    public Point3 affine(Point3 q, double alpha) {
        double pw = 1.0 - alpha ;
        double qw = alpha ;

        double rx = pw*x + qw*q.x ;
        double ry = pw*y + qw*q.y ;
        double rz = pw*z + qw*q.z ;

        return new Point3(rx, ry, rz) ;
    }

    /**
     * Returns the distance from this point to a point q.
     * @param q the point q.
     * @return the distance.
     */
    public double distance(Point3 q) {
        return Math.sqrt(distanceSquared(q)) ;
    }

    /**
     * Returns the squared distance from this point to a point q.
     * @param q the point q.
     * @return the distance squared.
     */
    public double distanceSquared(Point3 q) {
        double dx = x - q.x ;
        double dy = y - q.y ;
        double dz = z - q.z ;

        return dx*dx + dy*dy + dz*dz ;
    }
}
