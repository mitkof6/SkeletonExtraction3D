/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jeom3d.core;

/**
 * A 4 dimensional vector {x, y, z, w}.
 * @author Siddharth
 * @version 05/27/2009
 */
public class Vector4 extends Tuple4 {

    /**
     * Constructs the vector {0, 0, 0, 0}.
     */
    public Vector4() {
        super() ;
    }

    /**
     * Constructs the vector {x, y, z, w}.
     * @param x the X component.
     * @param y the Y component.
     * @param z the Z component.
     * @param w the W component.
     */
    public Vector4(double x, double y, double z, double w) {
        super(x, y, z, w) ;
    }

}
