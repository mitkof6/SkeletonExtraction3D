package org.jeom3d.core;

/**
 * A 4x4 transformation matrix for homogenious coordinates. This matrix is
 * opengl compinant.ie. it is column ordered.
 * <pre><code>
 * m[ 0] m[ 4] m[ 8] m[12]
 * m[ 1] m[ 5] m[ 9] m[13]
 * m[ 2] m[ 6] m[10] m[14]
 * m[ 3] m[ 7] m[11] m[15]
 * </code></pre>
 * Here m[3], m[7] and m[11] = 0.0 and m[15] = 1.0 for affine transforms.
 * @author Siddharth
 * @version 05/27/2009
 */
public class Matrix4 {

    public double[] m = null;

    /**
     * Constructs a new matrix of zeros and not an identity matrix.
     */
    public Matrix4() {
        m = new double[16];
    }

    /**
     * Constructs a new matrix that reference the specified array.
     * @param m the array. Must have length 16.
     */
    public Matrix4(double[] m) {
        this.m = m;
    }

    /**
     * Copies a matrix from the specified matrix.
     * @param m the given matrix.
     */
    public Matrix4(Matrix4 m) {
        this.m = new double[16];
        set(m.m);
    }

    /**
     * Constructs a matrix given the input with row order elements. This is done
     * primarily for readability, but internally its stored as a column data.
     * @param m00 matrix element m(0, 0) => m[ 0].
     * @param m01 matrix element m(0, 1) => m[ 4].
     * @param m02 matrix element m(0, 2) => m[ 8].
     * @param m03 matrix element m(0, 3) => m[12].
     * @param m10 matrix element m(1, 0) => m[ 1].
     * @param m11 matrix element m(1, 1) => m[ 5].
     * @param m12 matrix element m(1, 2) => m[ 9].
     * @param m13 matrix element m(1, 3) => m[13].
     * @param m20 matrix element m(2, 0) => m[ 2].
     * @param m21 matrix element m(2, 1) => m[ 6].
     * @param m22 matrix element m(2, 2) => m[10].
     * @param m23 matrix element m(2, 3) => m[14].
     * @param m30 matrix element m(3, 0) => m[3].
     * @param m31 matrix element m(3, 1) => m[7].
     * @param m32 matrix element m(3, 2) => m[11].
     * @param m33 matrix element m(3, 3) => m[15].
     */
    public Matrix4(double m00, double m01, double m02, double m03,
            double m10, double m11, double m12, double m13,
            double m20, double m21, double m22, double m23,
            double m30, double m31, double m32, double m33) {
        this.m = new double[16];
        set(m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33);
    }

    /**
     * Sets a matrix given the input with row order elements. This is done
     * primarily for readability, but internally its stored as a column data.
     * @param m00 matrix element m(0, 0) => m[ 0].
     * @param m01 matrix element m(0, 1) => m[ 4].
     * @param m02 matrix element m(0, 2) => m[ 8].
     * @param m03 matrix element m(0, 3) => m[12].
     * @param m10 matrix element m(1, 0) => m[ 1].
     * @param m11 matrix element m(1, 1) => m[ 5].
     * @param m12 matrix element m(1, 2) => m[ 9].
     * @param m13 matrix element m(1, 3) => m[13].
     * @param m20 matrix element m(2, 0) => m[ 2].
     * @param m21 matrix element m(2, 1) => m[ 6].
     * @param m22 matrix element m(2, 2) => m[10].
     * @param m23 matrix element m(2, 3) => m[14].
     * @param m30 matrix element m(3, 0) => m[3].
     * @param m31 matrix element m(3, 1) => m[7].
     * @param m32 matrix element m(3, 2) => m[11].
     * @param m33 matrix element m(3, 3) => m[15].
     */
    public void set(double m00, double m01, double m02, double m03,
            double m10, double m11, double m12, double m13,
            double m20, double m21, double m22, double m23,
            double m30, double m31, double m32, double m33) {
        setRow(0, m00, m01, m02, m03);
        setRow(1, m10, m11, m12, m13);
        setRow(2, m20, m21, m22, m23);
        setRow(3, m30, m31, m32, m33);
    }

    /**
     * Sets this matrix to copy of the specified matrix.
     * @param m the matrix.
     */
    public void set(Matrix4 m) {
        set(m.m);
    }

    /**
     * Sets the values of the specified matrix element.
     * @param i row index,  [0:3]
     * @param j column index [0:3]
     * @param a the value.
     */
    public void set(int i, int j, double a) {
        m[i + j * 4] = a;
    }

    /**
     * Sets this matrix to identity matrix.
     * @return this matrix.
     */
    public Matrix4 setIdentity() {
        setRow(0, 1.0, 0.0, 0.0, 0.0);
        setRow(1, 0.0, 1.0, 0.0, 0.0);
        setRow(2, 0.0, 0.0, 1.0, 0.0);
        setRow(3, 0.0, 0.0, 0.0, 1.0);

        return this;
    }

    /**
     * Sets the matrix to perform perspective projection. The near and far
     * parameters should always be positive.
     * @param left the X coordinates of the left side.
     * @param right the X coordinate of the right side.
     * @param bottom the Y coordinate of the bottom side.
     * @param top the Y coordinate of the top side.
     * @param near the Z coordinate of the near side.
     * @param far the Z coordinate of the far side.
     * @return this matrix.
     */
    public Matrix4 setFrustrum(double left, double right, double bottom, double top, double near, double far) {
        double l = left ;
        double r = right ;
        double b = bottom ;
        double t = top ;
        double n = near ;
        double f = far ;

        setRow(0, 2*n/(r-l), 0,         (r+l)/(r-l),  0);
        setRow(1, 0,         2*n/(t-b), (t+b)/(t-b),  0) ;
        setRow(2, 0,         0,         -(f+n)/(f-n), -2*f*n/(f-n)) ;
        setRow(3, 0,         0,         -1,           0) ;
        return this ;
    }

    /**
     * Sets the matrix to perform orthographic projection.
     * @param left the X coordinate of the left side.
     * @param right the X coordinate of the right side.
     * @param bottom the Y coordinate of the bottom side.
     * @param top the Y coordinate of the top side.
     * @param near the Z coordinate of the near side.
     * @param far the Z coordinate of the far side
     * @return this matrix.
     */
    public Matrix4 setOrthographic(double left, double right, double bottom, double top, double near, double far) {
        double l = left ;
        double r = right ;
        double b = bottom ;
        double t = top ;
        double f = far ;
        double n = near ;
        setRow(0, 2/(r-l), 0,           0,        (r+l)/(r-l)) ;
        setRow(1, 0,       2/(t-b),     0,        (t+b)/(t-b)) ;
        setRow(2, 0,       0,           -2/(f-n), (f+n)/(f-n)) ;
        setRow(3, 0,       0,           0,        1) ;
        return this ;
    }

    /**
     * Sets the matrix to perform perspective projection. The near and far
     * should always be positive.
     * @param fovy the field of view in radians.
     * @param aspect the aspect ratio (width/height) of the frustum.
     * @param near the distance to the near side.
     * @param far the distance to the far side.
     * @param eyeAdjust the eye adjust for stereoscopic projection.
     * @return
     */
    public Matrix4 setPerspective(double fovy, double aspect, double near, double far, double eyeAdjust) {
        double t = Math.tan(0.5*fovy) ;
        double right = t*aspect*near + eyeAdjust ;
        double left = -right+eyeAdjust ;
        double top = t*near ;
        double bottom = -top ;
        return setFrustrum(left, right, bottom, top, near, far) ;
    }

    /**
     * Sets this matrix to perform uniform scaling.
     * @param s scale factor for X, Y, Z coordinates
     * @return this matrix.
     */
    public Matrix4 setScale(double s) {
        setRow(0, s, 0.0, 0.0, 0.0);
        setRow(1, 0.0, s, 0.0, 0.0);
        setRow(2, 0.0, 0.0, s, 0.0);
        setRow(3, 0.0, 0.0, 0.0, 1.0);

        return this;
    }

    /**
     * Sets this matrix to perfrom non uniform scaling. 
     * @param sx scale factor for X coordinate.
     * @param sy scale factor for Y coordinate.
     * @param sz scale factor for Z coordinate.
     * @return this matrix.
     */
    public Matrix4 setScale(double sx, double sy, double sz) {
        setRow(0, sx, 0.0, 0.0, 0.0);
        setRow(1, 0.0, sy, 0.0, 0.0);
        setRow(2, 0.0, 0.0, sz, 0.0);
        setRow(3, 0.0, 0.0, 0.0, 1.0);

        return this;
    }

    /**
     * Sets this matrix for non uniform scaling.
     * @param s tuple {sx, sy, sz}.
     * @return this matrix.
     */
    public Matrix4 setScale(Tuple3 s) {
        return setScale(s.x, s.y, s.z);
    }

    /**
     * Sets this matrix to perform translation.
     * @param tx translation for X coordinate.
     * @param ty translation for Y coordinate.
     * @param tz translation for Z coordinate.
     * @return
     */
    public Matrix4 setTranslate(double tx, double ty, double tz) {
        setRow(0, 1.0, 0.0, 0.0, tx);
        setRow(1, 0.0, 1.0, 0.0, ty);
        setRow(2, 0.0, 0.0, 1.0, tz);
        setRow(3, 0.0, 0.0, 0.0, 1.0);

        return this;
    }

    /**
     * Sets this matrix to perform translation.
     * @param t the translation tuple {tx, ty, tz}.
     * @return this matrix.
     */
    public Matrix4 setTranslate(Tuple3 t) {
        return setTranslate(t.x, t.y, t.z);
    }

    /**
     * Sets this matrix to perform rotation along X axis. Looking towards the
     * origin, the rotation is counter-clockwise.
     * @param angle the angle in radians.
     * @return this matrix.
     */
    public Matrix4 setRotationX(double angle) {
        double ca = Math.cos(angle);
        double sa = Math.sin(angle);
        setRow(0, 1.0, 0.0, 0.0, 0.0);
        setRow(1, 0.0, ca, -sa, 0.0);
        setRow(2, 0.0, sa, ca, 0.0);
        setRow(3, 0.0, 0.0, 0.0, 1.0);

        return this;
    }

    /**
     * Sets this matrix to perfom rotation along Y axis. Looking towards the
     * origin, the rotation is counter-clockwise.
     * @param angle the angle in radians.
     * @return this matrix.
     */
    public Matrix4 setRotationY(double angle) {
        double ca = Math.cos(angle);
        double sa = Math.sin(angle);
        setRow(0, ca, 0.0, sa, 0.0);
        setRow(1, 0.0, 1.0, 0.0, 0.0);
        setRow(2, -sa, 0.0, ca, 0.0);
        setRow(3, 0.0, 0.0, 0.0, 1.0);

        return this;
    }

    /**
     * Sets this matrix to perform rotation along Z axis. Looking towards the
     * origin, the rotation is counter-clockwise.
     * @param angle the angle in radian.
     * @return this matrix.
     */
    public Matrix4 setRotationZ(double angle) {
        double ca = Math.cos(angle);
        double sa = Math.sin(angle);
        setRow(0, ca, -sa, 0.0, 0.0);
        setRow(1, sa, ca, 0.0, 0.0);
        setRow(2, 0.0, 0.0, 1.0, 0.0);
        setRow(3, 0.0, 0.0, 0.0, 1.0);

        return this;
    }

    /**
     * Sets this matrix to perform a rotation around the vector {x, y, z}.
     * Looking towards the origin, the rotation is counter-clockwise.
     * @param angle the rotation angle in radians.
     * @param x the vector X component.
     * @param y the vector Y component.
     * @param z the vector Z component.
     * @return this matrix.
     */
    public Matrix4 setRotate(double angle, double x, double y, double z) {
        double s = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= s;
        y *= s;
        z *= s;
        double ca = Math.cos(angle);
        double sa = Math.sin(angle);
        double da = 1.0 - ca;
        double m00 = x * x * da + ca;
        double m01 = x * y * da - z * sa;
        double m02 = x * z * da + y * sa;
        double m10 = x * y * da + z * sa;
        double m11 = y * y * da + ca;
        double m12 = y * z * da - x * sa;
        double m20 = x * z * da - y * sa;
        double m21 = y * z * da + x * sa;
        double m22 = z * z * da + ca;

        setRow(0, m00, m01, m02, 0.0);
        setRow(1, m10, m11, m12, 0.0);
        setRow(2, m20, m21, m22, 0.0);
        setRow(3, 0.0, 0.0, 0.0, 1.0);

        return this;
    }

    /**
     * Sets this matrix to perform a rotation around the vector {x, y, z}.
     * Looking towards the origin, the rotation is counter-clockwise.
     * @param angle the rotation angle in radians.
     * @param v the vector around which to rotate.
     * @return this matrix.
     */
    public Matrix4 setRotate(double angle, Vector3 v) {
        return setRotate(angle, v.x, v.y, v.z);
    }

    /**
     * Creates a transpose of this matrix.
     * @return this matrix.
     */
    public Matrix4 transpose() {
        return new Matrix4(
                m[ 0], m[ 1], m[ 2], m[ 3],
                m[ 4], m[ 5], m[ 6], m[ 7],
                m[ 8], m[ 9], m[10], m[11],
                m[12], m[13], m[14], m[15]);
    }

    /**
     * Sets this matrix to transpose of this matrix.
     * @return this matrix.
     */
    public Matrix4 transposeEquals() {
        set(
                m[ 0], m[ 1], m[ 2], m[ 3],
                m[ 4], m[ 5], m[ 6], m[ 7],
                m[ 8], m[ 9], m[10], m[11],
                m[12], m[13], m[14], m[15]);

        return this;
    }

    /**
     * Constructs a matrix from the array of data.
     * @param a the matrix elements.
     */
    public void set(double[] a) {
        m = a.clone();
    }

    /**
     * Sets the row data to the index specidifed.
     * @param i the row index
     * @param v0 the zeroth column data in that row.
     * @param v1 the first column data in that row.
     * @param v2 the second column data in that row.
     * @param v3 the third column data in that row.
     */
    public void setRow(int i, double v0, double v1, double v2, double v3) {
        m[i + 0] = v0;
        m[i + 4] = v1;
        m[i + 8] = v2;
        m[i + 12] = v3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "\n["+g(0,0)+", "+g(0,1)+", "+g(0,2)+", "+g(0,3)+"]" +
               "\n["+g(1,0)+", "+g(1,1)+", "+g(1,2)+", "+g(1,3)+"]" +
               "\n["+g(2,0)+", "+g(2,1)+", "+g(2,2)+", "+g(2,3)+"]" +
               "\n["+g(3,0)+", "+g(3,1)+", "+g(3,2)+", "+g(3,3)+"]" ;
    }

    /**
     * Returns the matrix element.
     * @param i ith row.
     * @param j jth column.
     * @return the matrix element.
     */
    private String g(int i, int j) {
        return String.valueOf(m[i+j*4]) ;
    }

    /**
     * Gets a copy of the array representing this matrix.
     * @return array[16] of matrix elements.
     */
    public double[] get() {
        return get(null) ;
    }

    /**
     * Gets the scaling for X, Y, Z components of this matrix.
     * @return the scaling for X, Y and Z.
     */
    public Tuple3 getScaling() {
        double sx = Math.sqrt(m[0]*m[0] + m[1]*m[1] + m[ 2]*m[ 2]) ;
        double sy = Math.sqrt(m[4]*m[4] + m[5]*m[5] + m[ 6]*m[ 6]) ;
        double sz = Math.sqrt(m[8]*m[8] + m[9]*m[9] + m[10]*m[10]) ;

        return new Tuple3(sx, sy, sz) ;
    }

    /**
     * Returns the translation vector part of this matrix.
     * @return the translation vector.
     */
    public Vector3 getTranslation() {
        return new Vector3(m[12], m[13], m[14]) ;
    }

    /**
     * Returns the identity matrix.
     * @return the identity matrix.
     */
    public static Matrix4 identity() {
        Matrix4 matrix = new Matrix4() ;
        return matrix.setIdentity() ;
    }

    /**
     * Returns a matrix to perform orthographic projection.
     * Same as in opengl function glOrtho(). The near and far should always
     * be possible.
     * @param left the X coordinate of the left side.
     * @param right the X coordinate of the right side.
     * @param bottom the Y coordinate of the bottom side.
     * @param top the Y coodinate of the bottom side.
     * @param near the Z coordinates of the near side.
     * @param far the Z coordinates of the far side.
     * @return the orthographic projection matrix.
     */
    public static Matrix4 orthographic(double left, double right, double bottom,
            double top, double near, double far) {
        Matrix4 matrix = new Matrix4() ;
        return matrix.setOrthographic(left, right, bottom, top, near, far) ;
    }

    /**
     * Returns a matrix to perform perspective projection.
     * Same as the opengl function gluPerspective(). The near and far values
     * must always positive.
     * @param fovy the field of view angle in radians.
     * @param aspect the aspect ration (width/height) of the frustum.
     * @param near the distance to near side.
     * @param far the distance to far side.
     * @return the perspective projection matrix.
     */
    public static Matrix4 perspective(double fovy, double aspect,
            double near, double far) {
        Matrix4 matrix = new Matrix4() ;
        return matrix.setPerspective(fovy, aspect, near, far, near) ;
    }

    /**
     * Checks if the matrix is reflective , ie. if it changes from a left hand
     * coordinate system to a right hand coordinate system or vice versa. This
     * method takes the determinant of the upper 3X3 matrix. If its less than
     * zero, the matrix is reflective.
     * @return <code>true</code> the matrix is reflective.
     */
    public boolean isReflective() {
        double det =
                m[0]*(m[5]*m[10] - m[6]*m[9]) -
                m[4]*(m[1]*m[10] - m[2]*m[9]) +
                m[8]*(m[1]*m[ 6] - m[2]*m[5]) ;

        return (det < 0.0) ;
    }

    /**
     * Gets a copy of the array representing this matrix
     * @param a array into which matrix elements are to be copied.
     * @return array[16] of matrix elements.
     */
    public double[] get(double[] a) {
        if(a == null) {
            a = new double[16] ;
        }
        for (int i = 0; i < 16; i++) {
            a[i] = m[i];
        }
        return a ;
    }

    /**
     * Matrix multiplication C=A*B. All three arrays may be aliased.
     * @param a the A matrix.
     * @param b the B matrix.
     * @param c the C matrix.
     */
    private static void matmul(double[] a, double[] b, double[] c) {
        double a00 = a[ 0];
        double a10 = a[ 1];
        double a20 = a[ 2];
        double a30 = a[ 3];
        double a01 = a[ 4];
        double a11 = a[ 5];
        double a21 = a[ 6];
        double a31 = a[ 7];
        double a02 = a[ 8];
        double a12 = a[ 9];
        double a22 = a[10];
        double a32 = a[11];
        double a03 = a[12];
        double a13 = a[13];
        double a23 = a[14];
        double a33 = a[15];

        double b00 = b[ 0];
        double b10 = b[ 1];
        double b20 = b[ 2];
        double b30 = b[ 3];
        double b01 = b[ 4];
        double b11 = b[ 5];
        double b21 = b[ 6];
        double b31 = b[ 7];
        double b02 = b[ 8];
        double b12 = b[ 9];
        double b22 = b[10];
        double b32 = b[11];
        double b03 = b[12];
        double b13 = b[13];
        double b23 = b[14];
        double b33 = b[15];

        c[ 0] = a00 * b00 + a01 * b10 + a02 * b20 + a03 * b30;
        c[ 1] = a10 * b00 + a11 * b10 + a12 * b20 + a13 * b30;
        c[ 2] = a20 * b00 + a21 * b10 + a22 * b20 + a23 * b30;
        c[ 3] = a30 * b00 + a31 * b10 + a32 * b20 + a33 * b30;

        c[ 4] = a00 * b01 + a01 * b11 + a02 * b21 + a03 * b31;
        c[ 5] = a10 * b01 + a11 * b11 + a12 * b21 + a13 * b31;
        c[ 6] = a20 * b01 + a21 * b11 + a22 * b21 + a23 * b31;
        c[ 7] = a30 * b01 + a31 * b11 + a32 * b21 + a33 * b31;

        c[ 8] = a00 * b02 + a01 * b12 + a02 * b22 + a03 * b32;
        c[ 9] = a10 * b02 + a11 * b12 + a12 * b22 + a13 * b32;
        c[10] = a20 * b02 + a21 * b12 + a22 * b22 + a23 * b32;
        c[11] = a30 * b02 + a31 * b12 + a32 * b22 + a33 * b32;

        c[12] = a00 * b03 + a01 * b13 + a02 * b23 + a03 * b33;
        c[13] = a10 * b03 + a11 * b13 + a12 * b23 + a13 * b33;
        c[14] = a20 * b03 + a21 * b13 + a22 * b23 + a23 * b33;
        c[15] = a30 * b03 + a31 * b13 + a32 * b23 + a33 * b33;
    }

    /**
     * Returns the product of this matrix A and the specified matrix B.
     * @param b the matrix B
     * @return the product A*B
     */
    public Matrix4 times(Matrix4 b) {
        double[] am = this.m ;
        double[] bm = b.m ;
        double[] cm = new double[16] ;
        matmul(am, bm, cm);

        return new Matrix4(cm) ;
    }

    /**
     * Returns the product of this matrix A and the specified point p.
     * @param p the point. Here the w coordinate = 1.0
     * @return the product A*p, scaled so that w = 1.
     */
    public Point3 times(Point3 p) {
        double x = p.x ;
        double y = p.y ;
        double z = p.z ;
        double w = x*m[3] +y*m[7]+z*m[11]+m[15] ;

        if(w == 1.0) {
            return new Point3(
                    x*m[0]+y*m[4]+z*m[8]+ m[12],
                    x*m[1]+y*m[5]+z*m[9]+ m[13],
                    y*m[2]+y*m[6]+z*m[10]+m[14]) ;
        }
        else {
            double s = 1.0/w ;
            return new Point3(
                    s*(x*m[0]+y*m[4]+z*m[8]+ m[12]),
                    s*(x*m[1]+y*m[5]+z*m[9]+ m[13]),
                    s*(y*m[2]+y*m[6]+z*m[10]+m[14])) ;
        }
    }

    /**
     * Returns the product of this matrix A and the specified point p.
     * @param p the specified point p.
     * @return the product A*p.
     */
    public Point4 times(Point4 p) {
        double x = p.x ;
        double y = p.y ;
        double z = p.z ;
        double w = p.w ;
        return new Point4(
                m[ 0]*x + m[ 4]*y + m[ 8]*z + m[12]*w,
                m[ 1]*x + m[ 5]*y + m[ 9]*z + m[13]*w,
                m[ 2]*x + m[ 6]*y + m[10]*z + m[14]*w,
                m[ 3]*x + m[ 7]*y + m[11]*z + m[15]*w) ;
    }

    /**
     * Returns this matrix A by the specified matrix B and returns this matrix.
     * Post multiplies this matrix by B.
     * @param b the matrix B.
     * @return this matrix A = A*B.
     */
    public Matrix4 timesEquals(Matrix4 b) {
        double[] am = this.m ;
        double[] bm = b.m ;
        double[] cm = this.m ;
        matmul(am, bm, cm);
        return this ;
    }

    /**
     * Returns the product of the matrix A and this matrix B.
     * @param b the matrix B.
     * @return the product A*B.
     */
    public Matrix4 timesLeft(Matrix4 b) {
        double[] am = this.m ;
        double[] bm = b.m ;
        double[] cm = new double[16] ;
        matmul(am, bm, cm);

        return new Matrix4(cm) ;
    }

    /**
     * Returns the product of the specified matrix A, by this matrix Band returns
     * the result. Pre-multiplies this matrix by A.
     * @param a the matrix a.
     * @return this matrix B=A*B.
     */
    public Matrix4 timesLeftEquals(Matrix4 a) {
        double[] am = a.m ;
        double[] bm = this.m ;
        double[] cm = this.m ;
        matmul(am, bm, cm) ;

        return this ;
    }

    /**
     * Returns the product of the transpose of this matrix A and the specified
     * vector v. This method is useful for transforming normal vectors, when
     * this matrix is the inverse of the specified matrix used to transform
     * points. The w coordinate = 0, because vectors(unlike points) are not
     * translated.
     * @param v the vector. the w coordinate = 0.
     * @return the product transpose(A)*v.
     */
    public Vector3 timesTranspose(Vector3 v) {
       double x = v.x ;
       double y = v.y ;
       double z = v.z ;

       return new Vector3(
               x*m[ 0]+y*m[ 1]+z*m[ 2],
               x*m[ 4]+y*m[ 5]+z*m[ 6],
               x*m[ 8]+y*m[ 9]+z*m[10]) ;
    }

    /**
     * Returns the product of the transpose of this matrix A and the specfied
     * vector v. This method is usefule for transforming normal vectors, when
     * this matrix is the inverse of the matrix used to transform points.
     * @param v the vector v.
     * @return the product transpose(A)*v.
     */
    public Vector4 timesTranspose(Vector4 v) {
        double x = v.x ;
        double y = v.y ;
        double z = v.z ;
        double w = v.w ;

        return new Vector4(
                x*m[ 0]+y*m[ 1]+z*m[ 2]+w*m[ 3],
                x*m[ 4]+y*m[ 5]+z*m[ 6]+w*m[ 7],
                x*m[ 8]+y*m[ 9]+z*m[10]+w*m[11],
                x*m[12]+y*m[13]+z*m[14]+w*m[15]
                ) ;
    }
}
