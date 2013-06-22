/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeom3d.core;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import com.sun.opengl.util.Animator;

/**
 * A simple JFrame that uses SimpleCanvas.
 * @author Siddharth
 * @version 06/07/2009
 */
public class SimpleFrame extends JFrame {

    private SimpleCanvas _canvas = null;

    public SimpleFrame(String title) {
        super(title);
        _canvas = new SimpleCanvas();
        add(_canvas);
        setSize(500, 500);
        setLocation(100, 100);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent evt) {
                Animator anim = _canvas.getAnimator();
                anim.stop();
                System.exit(0);
            }
        });

        pack();
        setVisible(true);
    }
}
