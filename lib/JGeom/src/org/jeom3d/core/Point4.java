/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jeom3d.core;

/**
 * A 4 dimensional point with coordinates {x, y, z, w}
 * @author Siddharth
 * @version 05/27/2009
 */
public class Point4 extends Tuple4 {

    /**
     * Constructs the point {0, 0, 0, 0}.
     */
    public Point4() {
        super() ;
    }

    /**
     * Constructs a point {x, y, z, w}.
     * @param x the X component.
     * @param y the Y component.
     * @param z the Z component.
     * @param w the W component.
     */
    public Point4(double x, double y, double z, double w) {
        super(x, y, z, w) ;
    }

    /**
     * Constructs a point from array {X, Y, Z, W}.
     * @param p
     */
    public Point4(double[] p) {
        super(p) ;
    }

    /**
     * Construct a copy of the specified point.
     * @param p the point to copy.
     */
    public Point4(Point4 p) {
        super(p) ;
    }

    /**
     * Returns the sum p+v of this point p and a vector v.
     * @param v the vector v.
     * @return the point p+v.
     */
    public Point4 plus(Vector4 v) {
        return new Point4(x+v.x, y+v.y, z+v.z, w+v.w) ;
    }

    /**
     * Returns the difference p-v of this point p and a vector v.
     * @param v the vector v.
     * @return the point p-v.
     */
    public Point4 minus(Vector4 v) {
        return new Point4(x-v.x, y-v.y, z-v.z, w-v.w) ;
    }

    /**
     * Returns the difference p-q of this point p and a point q.
     * @param q the point q.
     * @return the vector p-q.
     */
    public Vector4 minus(Point4 q) {
        return new Vector4(x-q.x, y-q.y, z-q.z, w-q.w) ;
    }

    /**
     * Adds a vector v to this point p and returns the result.
     * @param v the vector v.
     * @return the point p += v.
     */
    public Point4 plusEquals(Vector4 v) {
        x += v.x ;
        y += v.y ;
        z += v.z ;
        w += v.w ;

        return this ;
    }

    /**
     * Subtracts the vector v from the point p and returns the result.
     * @param v the vector v.
     * @return p -= v.
     */
    public Point4 minusEquals(Vector4 v) {
         x -= v.x ;
         y -= v.y ;
         z -= v.z ;
         w -= v.w ;

         return this ;
    }

    /**
     * Returns the affine combination of this point p and a point q.
     * @param q the point q.
     * @param alpha the combination factor.
     * @return the point (1 - alpha)*p + alpha*q.
     */
    public Point4 affine(Point4 q, double alpha) {
        double pw = 1.0 - alpha ;
        double qw = alpha ;

        double rx = pw*x + qw*q.x ;
        double ry = pw*y + qw*q.y ;
        double rz = pw*z + qw*q.z ;
        double rw = pw*w + qw*q.w ;

        return new Point4(rx, ry, rz, rw) ;
    }

    /**
     * Returns the distance from this point to a point q.
     * @param q the point q.
     * @return the distance.
     */
    public double distance(Point4 q) {
        return Math.sqrt(distanceSquared(q)) ;
    }

    /**
     * Returns the squared distance from this point to a point q.
     * @param q the point q.
     * @return the distance squared.
     */
    public double distanceSquared(Point4 q) {
        double dx = x - q.x ;
        double dy = y - q.y ;
        double dz = z - q.z ;
        double dw = w - q.w ;

        return dx*dx + dy*dy + dz*dz + dw*dw ;
    }


}
