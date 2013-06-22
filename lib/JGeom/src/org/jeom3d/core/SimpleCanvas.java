/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeom3d.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.GLCapabilities;
import com.sun.opengl.util.Animator;

/**
 * A simple canvas that doesnt support Node. It is temporarily used for testing jogl.
 * @author Siddharth
 * @version 07/06/2009
 */
public class SimpleCanvas extends GLCanvas {

    private double[] _modelViewMatrix = null;
    private double[] _projectionMatrix = null;
    private CanvasEventListener _eventListener = new CanvasEventListener();
    private Animator _animator = null ;
    private final int DEFAULT_SIZE = 500 ;

    public SimpleCanvas() {
        super(new GLCapabilities()) ;
        _animator = new Animator(this) ;
        addGLEventListener(_eventListener);
        addMouseListener(_eventListener);
        addMouseMotionListener(_eventListener);

        setSize(DEFAULT_SIZE, DEFAULT_SIZE) ;
        _animator.start();
    }

    public Animator getAnimator() {
        return _animator ;
    }

    private void setModelViewMatrix(double[] m) {
        _modelViewMatrix = m.clone();
    }

    public void setModelViewMatrix(Matrix4 m) {
        setModelViewMatrix(m.m);
    }

    private void setProjectionMatrix(double[] m) {
        _projectionMatrix = m.clone();
    }

    private void setProjectionMatrix(Matrix4 m) {
        setProjectionMatrix(m.m);
    }

    private class CanvasEventListener implements GLEventListener, MouseListener, MouseMotionListener {

        private float _zoom = 15.f;
        private float _rotx = 45.f;
        private float _roty = 0.001f;
        private float _tx = 0.f;
        private float _ty = 0.f;
        private int _lastX = 0;
        private int _lastY = 0;
        private final GLU _glu = new GLU();

        public void init(GLAutoDrawable glDrawable) {
            final GL gl = glDrawable.getGL();
            gl.setSwapInterval(1);
            gl.glEnable(GL.GL_DEPTH_TEST);
        }

        private void drawAxis(GLAutoDrawable glDrawable) {
            final GL gl = glDrawable.getGL();

            gl.glLineWidth(4.f);
            ;
            gl.glBegin(GL.GL_LINES);
            gl.glColor3f(1.f, 0.f, 0.f);
            gl.glVertex3f(0.f, 0.f, 0.f);
            gl.glVertex3f(1.f, 0.f, 0.f);

            gl.glColor3f(0.f, 1.f, 0.f);
            gl.glVertex3f(0.f, 0.f, 0.f);
            gl.glVertex3f(0.f, 1.f, 0.f);

            gl.glColor3f(0.f, 0.f, 1.f);
            gl.glVertex3f(0.f, 0.f, 0.f);
            gl.glVertex3f(0.f, 0.f, 1.f);
            gl.glEnd();
        }

        private void drawGrid(int size, GLAutoDrawable glDrawable) {
            final GL gl = glDrawable.getGL();
            gl.glColor3f(0.5f, 0.5f, 0.5f);
            gl.glLineWidth(1.f);
            gl.glBegin(GL.GL_LINES);
            for (int i = -size; i < size; i++) {
                gl.glVertex3f(i, 0, -size);
                gl.glVertex3f(i, 0, size);

                gl.glVertex3f(size, 0, i);
                gl.glVertex3f(-size, 0, i);
            }
            gl.glEnd();
        }

        public void display(GLAutoDrawable glDrawable) {
            final GL gl = glDrawable.getGL();
            gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
            gl.glLoadIdentity();

            gl.glTranslatef(0, 0, -_zoom);
            gl.glTranslatef(_tx, _tx, 0);
            gl.glRotatef(_rotx, 1, 0, 0);
            gl.glRotatef(_roty, 0, 1, 0);


            drawAxis(glDrawable);
            drawGrid(10, glDrawable);

            glDrawable.swapBuffers();

        }

        public void reshape(GLAutoDrawable glDrawable, int x, int y, int w, int h) {
            if (w == 0) {
                h = 1;
            }

            final GL gl = glDrawable.getGL();


            gl.glViewport(0, 0, w, h);
            gl.glMatrixMode(GL.GL_PROJECTION);
            gl.glLoadIdentity();
            _glu.gluPerspective(45, (float) w / h, 0.1, 100);
            gl.glMatrixMode(GL.GL_MODELVIEW);
            gl.glLoadIdentity();
            ;
        }

        public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            _lastX = e.getX();
            _lastY = e.getY();
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
            int diffx = e.getX() - _lastX;
            int diffy = e.getY() - _lastY;

            _lastX = e.getX();
            _lastY = e.getY();

            //if its a button1 drag, then rotate.
            //if its a button2 drag, then pan.
            //if its a button3 drag, then zoom.
            if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK) {
                _rotx += 0.5f * diffy;
                _roty += 0.5f * diffx;

            }

            if ((e.getModifiers() & MouseEvent.BUTTON2_MASK) == MouseEvent.BUTTON2_MASK) {
                _tx += 0.05f * diffx;
                _ty -= 0.05f * diffy;

            }

            if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) == MouseEvent.BUTTON3_MASK) {
                _zoom -= 0.05f * diffx;
            }

        }

        public void mouseMoved(MouseEvent e) {
        }
    }
}
