package org.jeom3d.core;

/**
 * A 3 dimensional tuple.
 * @author Siddharth
 * @version 25/05/2009
 */
public class Tuple3 {

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

    public Tuple3() {
        x = y = z = 0 ;
    }

    /**
     * Constructor for a Tuple {x, y, z}.
     * @param x the X component.
     * @param y the Y component.
     * @param z the Z component.
     */
    public Tuple3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a Tuple3 from a array of doubles.
     * @param t the vector to copy.
     */
    public Tuple3(double[] t) {
        this.x = t[X];
        this.y = t[Y];
        this.z = t[Z];
    }

    /**
     * Constructs a copy of the specified tuple.
     * @param t the vector to copy.
     */
    public Tuple3(Tuple3 t) {
        this.x = t.x ;
        this.y = t.y ;
        this.z = t.z ;
    }

    /**
     * Sets this Tuple3 to {x, y, z}
     * @param x the X component.
     * @param y the Y component.
     * @param z the Z component.
     */
    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Sets this tuple to t[0], t[1], t[2]
     * @param t the vector to copy
     */
    public void set(double[] t) {
        set(t[X], t[Y], t[Z]);
    }

    /**
     * Sets this tuple to a copy of the specified tuple.
     * @param t the tuple to copy.
     */
    public void set(Tuple3 t) {
        set(t.x, t.y, t.z);
    }

    /**
     * Gets the array {x, y, z} from this tuple.
     * @param t the array into which to set {x, y, z}.
     *          if t is null, a new array will be created.
     * @return the array {x, y, z}
     */
    public double[] get(double[] t) {
        if (t == null) {
            t = new double[3];
        }

        t[X] = x;
        t[Y] = y;
        t[Z] = z;

        return t;
    }

    /**
     * Gets the array {x, y, z} for this tuple.
     * @return the array {x, y, z}
     */
    public double[] get() {
        return get(null);
    }

    /**
     * Gets the array {x, y, z} for this tuple as unsigned bytes. The tuple
     * components are scaled to be in between 0.0 to 1.0. If any component
     * is less than 0.0 or greater than 1.0, they are clamped to be in the
     * range [0:1].
     * @return the byte array {bx, by, bz}
     */
    public byte[] getBytes() {
        return getBytes(null);
    }

    /**
     * Gets the array {x, y, z} for this tuple as unsigned bytes. The tuple
     * components are scaled to be in between 0.0 to 1.0. If any component
     * is less than 0.0 or greater than 1.0, they are clamped to be in the
     * range [0:1].
     *
     * @param b the array into which {bx, by, bz} will be set.
     * @return the byte array {bx, by, bz}
     */
    public byte[] getBytes(byte[] b) {
        if (b == null) {
            b = new byte[3];
        }

        double bx = x;
        double by = y;
        double bz = z;

        if (bx < 0.0) {
            bx = 0.0;
        }
        if (by < 0.0) {
            by = 0.0;
        }
        if (bz < 0.0) {
            bz = 0.0;
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

        b[X] = (byte) (bx * 255.0 + 0.5);
        b[Y] = (byte) (by * 255.0 + 0.5);
        b[Z] = (byte) (bz * 255.0 + 0.5);

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
            Tuple3 t = (Tuple3) object;
            return x == t.x && y == t.y && z == t.z;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;
}
