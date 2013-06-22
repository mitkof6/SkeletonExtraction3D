package org.jeom3d.core;

/**
 * A 4 dimensional tuple {x, y, z, w}.
 * @author Siddharth
 * @version 26/05/2009
 */
public class Tuple4 {

    /**
     * The X component.
     */
    public double x;
    /**
     * The Y component.
     */
    public double y;
    /**
     * The Z component.
     */
    public double z;
    /**
     * The W component.
     */
    public double w;

    /**
     * Constructs the Tuple4 {0, 0, 0, 0}.
     */
    public Tuple4() {
        x = y = z = w = 0;
    }

    /**
     * Constructs the Tuple {x, y, z, w}.
     * @param x the X component.
     * @param y the Y component.
     * @param z the Z component.
     * @param w the W component.
     */
    public Tuple4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Constructs the tuple {x, y, z} = {t[0], t[1], t[2], t[3]}.
     * @param t the array {x, y, z, w}
     */
    public Tuple4(double[] t) {
        this.x = t[X];
        this.y = t[Y];
        this.z = t[Z];
        this.w = t[W];
    }

    /**
     * Populates this tuple with the data of the given.
     * @param t tuple to copy.
     */
    public Tuple4(Tuple4 t) {
        this.x = t.x;
        this.y = t.y;
        this.z = t.z;
        this.w = t.w;
    }

    /**
     * Sets this tuple to {x, y, z, w}.
     * @param x the X component.
     * @param y the Y component.
     * @param z the Z component.
     * @param w the W component.
     */
    public void set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Sets this tuple with the given vector.
     * @param t the tuple to copy.
     */
    public void set(double[] t) {
        set(t[X], t[Y], t[Z], t[W]);
    }

    /**
     * Sets this tuple with the tuple to copy from.
     * @param t the tuple to copy from.
     */
    public void set(Tuple4 t) {
        set(t.x, t.y, t.z, t.w);
    }

    /**
     * Gets the array {x, y, z, w} for this tuple.
     * @return the array {x, y, z, w}.
     */
    public double[] get() {
        return get(null);
    }

    /**
     * Get the array {x, y, z, w} for this tuple.
     * @param t the array into which to set {x, y, z, w}
     * @return the array {x, y, z, w}.
     */
    public double[] get(double[] t) {
        if (t == null) {
            t = new double[4];
        }

        t[X] = x;
        t[Y] = y;
        t[Z] = z;
        t[W] = w;

        return t;
    }

    /**
     * Gets the array {x, y, z} for this tuple as unsigned bytes. The tuple
     * components are scaled to be in between 0.0 to 1.0. If any component
     * is less than 0.0 or greater than 1.0, they are clamped to be in the
     * range [0:1].
     * @return the byte array {bx, by, bz, bw}
     */
    public byte[] getBytes() {
        return getBytes(null);
    }

    /**
     * Gets the array {x, y, z} for this tuple as unsigned bytes. The tuple
     * components are scaled to be in between 0.0 to 1.0. If any component
     * is less than 0.0 or greater than 1.0, they are clamped to be in the
     * range [0:1].
     * @param b the array into which {bx, by, bz, bw} will be set.
     * @return the byte array {bx, by, bz, bw}
     */
    public byte[] getBytes(byte[] b) {
        if (b == null) {
            b = new byte[4];
        }

        double bx = x;
        double by = y;
        double bz = z;
        double bw = w;

        if (bx < 0.0) {
            bx = 0.0;
        }
        if (by < 0.0) {
            by = 0.0;
        }
        if (bz < 0.0) {
            bz = 0.0;
        }
        if (bw < 0.0) {
            bw = 0.0;
        }
        if (bx > 1.0) {
            bx = 1.0;
        }
        if (by > 1.0) {
            by = 1.0;
        }
        if (bz > 1.0) {
            bz = 1.0;
        }
        if (bw > 1.0) {
            bw = 1.0;
        }

        b[X] = (byte) (bx * 255.0 + 0.5);
        b[Y] = (byte) (by * 255.0 + 0.5);
        b[Z] = (byte) (bz * 255.0 + 0.5);
        b[W] = (byte) (bw * 255.0 + 0.5);

        return b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object != null && object.getClass() == this.getClass()) {
            Tuple4 t = (Tuple4) object;
            return x == t.x && y == t.y && z == t.z;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.w) ^ (Double.doubleToLongBits(this.w) >>> 32));
        return hash;
    }
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;
    private static final int W = 3;
}
