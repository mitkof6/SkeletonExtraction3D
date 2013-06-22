package org.jeom3d.core;

/**
 * A 3 dimensional vector with components {x, y, z}.
 * @author Siddharth
 * @version 05/27/2009
 */
public class Vector3 extends Tuple3 {

    /**
     * Constructs the Vector {0, 0, 0}.
     */
    public Vector3() {
        super();
    }

    /**
     * Constructs a Vector {x, y, z}.
     * @param x the X component.
     * @param y the Y component.
     * @param z the Z component.
     */
    public Vector3(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Constructs the vector {x, y, z} = {v[0], v[1], v[2]}.
     * @param v the array {x, y, z}.
     */
    public Vector3(double[] v) {
        super(v) ;
    }

    /**
     * Constructs a copy of the specified vector.
     * @param v the vector to copy.
     */
    public Vector3(Vector3 v) {
        super(v) ;
    }

    /**
     * Returns the sum u+v of this vector u and a vector v.
     * @param v the vector v.
     * @return the vector u+v.
     */
    public Vector3 plus(Vector3 v) {
        return new Vector3(x+v.x, y+v.y, z+v.z) ;
    }

    /**
     * Retunes the sum u+q of this vector u and a point q.
     * @param q the point q.
     * @return the vector u+q.
     */
    public Point3 plus(Point3 q) {
        return new Point3(x+q.x, y+q.y, z+q.z) ;
    }

    /**
     * Returns the difference u-v of this vector u and a vector v.
     * @param v the vector v.
     * @return the vector u-v.
     */
    public Vector3 minus(Vector3 v) {
        return new Vector3(x-v.x, y-v.y, z-v.z) ;
    }

    /**
     * Returns a vector v-p of this vector v and a point p.
     * @param p the point p.
     * @return the vector v-p.
     */
    public Vector3 minus(Point3 p) {
       return new Vector3(x-p.x, y-p.y, z-p.z)  ;
    }

    /**
     * Returns the product u*a of this vector u and a scalar a.
     * @param a the scalar a.
     * @return the vector u*a.
     */
    public Vector3 times(double a) {
        return new Vector3(x*a, y*a, z*a) ;
    }

    /**
     * Returns the product u*A of this vector and a matrix A.
     * Do not use this for transforming normal vectors , where a specified matrix
     * A is the inverse of the matrix used to transform points. This vector u has
     * coordinate w = 0, because unlike points , vectors are not translated.
     * @param a the matrix A.
     * @return the vector u*A.
     */
    public Vector3 times(Matrix4 a) {
        double[] m = a.m ;
        return new Vector3(x*m[0] + y*m[1] * z*m[2],
                           x*m[4] + y*m[5] * z*m[6],
                           x*m[8] + y*m[9] * z*m[10]) ;
    }

    /**
     * Returns the negative -u of this vector u.
     * @return the vector -u.
     */
    public Vector3 negate() {
        return new Vector3(-x, -y, -z) ;
    }

    /**
     * Returns the normalization of this vector u.
     * @return the vector u/|u|.
     */
    public Vector3 normalize() {
        return times(1.0/length()) ;
    }

    /**
     * Adds a vector v to this vector u and returns the result.
     * @param v the vector v.
     * @return the vector u+v.
     */
    public Vector3 plusEquals(Vector3 v) {
        x += v.x ;
        y += v.y ;
        z += v.z ;

        return this ;
    }

    /**
     * Subtracts a vector v from this vector u and returns the result.
     * @param v the vector v.
     * @return the vector u -= v
     */
    public Vector3 minusEquals(Vector3 v) {
        x -= v.x ;
        y -= v.y ;
        z -= v.z ;

        return this ;
    }

    /**
     * Sets the vector to the difference between two points and returns.
     * @param p the first point.
     * @param q the second point.
     * @return the vector p-q.
     */
    public Vector3 minus(Point3 p, Point3 q) {
        x = p.x - q.x ;
        y = p.y - q.y ;
        z = p.z - q.z ;

        return this ;
    }

    /**
     * Multiplies a scalar a to this vector u and returns the result.
     * @param a the scalar a.
     * @return the vector u*a.
     */
    public Vector3 timesEquals(double a) {
        x *= a ;
        y *= a ;
        z *= a ;

        return this ;
    }

    /**
     * Computes the product u*A of this vector and a matrix A. This method is
     * useful for transforming the normal vectors, where the specified matrix A
     * is the inverse of the matrix used to transform points. This vector u has
     * coordinate w = 0, because vectors(unlike points) are not translated.
     * @param a the matrix A.
     * @return this vector u = u*A.
      */
    public Vector3 timesEquals(Matrix4 a) {
        double[] m = a.m ;
        double ux = x ;
        double uy = y ;
        double uz = z ;

        x = ux*m[0] + uy*m[1] + uz*m[2] ;
        y = ux*m[4] + uy*m[5] + uz*m[6] ;
        z = ux*m[8] + uy*m[9] + uz*m[10] ;

        return this ;
    }

    /**
     * Negates this vector u, in place and returns the result.
     * @return this negative vector  -u
     */
    public Vector3 negateEquals() {
        x = -x ;
        y = -y ;
        z = -z ;

        return this ;
    }

    /**
     * Normalizes the vector u in place and returns the result.
     * @return the vector u /= |u|
     */
    public Vector3 normalizeEquals() {
        return timesEquals(1.0/length()) ;
    }

    /**
     * Returns the length of this vector u.
     * @return the length |u|.
     */
    public double length() {
        return Math.sqrt(lengthSquared()) ;
    }

    /**
     * Returns the length squared of this vector.
     * @return the length squared |u|*|u|.
     */
    public double lengthSquared() {
        return x*x + y*y + z*z ;
    }

    /**
     * Returns the dot product of this vector u and a vector v.
     * @param v the vect v.
     * @return the scalar u dot v.
     */
    public double dot(Vector3 v) {
        return x*v.x + y*v.y + z*v.z ;
    }

    /**
     * Returns the cross product of this vector u and a vector v.
     * @param v the vector v.
     * @return the vector u cross v.
     */
    public Vector3 cross(Vector3 v) {
        return new Vector3(y*v.z - z*v.y,
                           z*v.x - x*v.z,
                           x*v.y - y*v.x) ;
    }

    /**
     * Sets the values if the vector to the cross product of this vector u and a
     * vector v and returns the result.
     * @param v the vector v.
     * @return the vector u cross v.
     */
    public Vector3 crossEquals(Vector3 v) {
        double ux = x ;
        double uy = y ;
        double uz = z ;
        x = uy*v.z - uz*v.y ;
        y = uz*v.x - ux*v.z ;
        z = ux*v.y - uy*v.x ;

        return this ;
    }

    /**
     * Returns the angle in [0, PI] between this vector and the vector v.
     * @param v the vector v.
     * @return the angle between u and v.
     */
    public double angle(Vector3 v) {
        double ux =   x; double uy =   y; double uz =   z;
        double vx = v.x; double vy = v.y; double vz = v.z ;
        double wx = uy*vz - uz*vy ;
        double wy = uz*vx - ux*vz ;
        double wz = ux*vy - uy*vx ;

        double w1 = Math.sqrt(wx*wx + wy*wy + wz*wz) ;
        double uv = ux*vx + uy*vy + uz*vz ;
        return Math.abs(Math.atan2(w1, uv)) ;
    }

    /**
     * Returns a vector orthogonal to this vector u.
     * @return a vector v such that u dot v = 0.
     */
    public Vector3 orthogonal() {
        double ax = Math.abs(x) ;
        double ay = Math.abs(y) ;
        double az = Math.abs(z) ;
        double am = Math.min(ax, Math.min(ay, az)) ;
        if(am == ax) {
            return new Vector3(0.0, -az, ay) ;
        } else if(am == ay) {
            return new Vector3(-az, 0.0, ax) ;
        } else if(am == az) {
            return new Vector3(-ay, ax, 0.0) ;
        }

        return null ;
    }

    /**
     * Returns the affine combination of this vector u and a vector v.
     * @param v the vector v.
     * @param alpha the alpha value.
     * @return the vector (1-alpha)*u + alpha*v.
     */
    public Vector3 affine(Vector3 v, double alpha) {
        double uw = 1.0 - alpha ;
        double vw = alpha ;
        double wx = uw*x + vw*v.x ;
        double wy = uw*y + vw*v.y ;
        double wz = uw*z + vw*v.z ;

        return new Vector3(wx, wy, wz) ;
    }
}
