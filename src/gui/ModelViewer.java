package gui;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;


import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import skeleton.Bone;

import main.Main;
import math.Triangle;

import com.jogamp.opengl.util.FPSAnimator;

/**
 * The class for viewing the animation and interaction between
 * bone system and skin
 * 
 * @author Jim Stanev
 */
public class ModelViewer extends Frame implements GLEventListener, 
	MouseListener, MouseMotionListener, MouseWheelListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GLProfile glp;
	private GLCapabilities caps;
	private GLCanvas canvas;
	private FPSAnimator animator;
	private GLU glu = new GLU();
	private GL2 gl;

	/**
	 * bone system root
	 */
	private Bone root;
	/**
	 * the initial faces of the model
	 */
	private Vector<Triangle> faces;


	/**
	 * FPS: frames per second
	 */
    private int FPS = 30;

    /**
     * camera movement parameters
     */
    private float CAMERA_ZOOM = -50;
    private float CAMERA_X = 10;
    private float CAMERA_Y = 10;
    private int lastX = 0;
    private int lastY = 0;

    
    /**
     * Constructor
     */
	public ModelViewer(){

		super("Model Viewer");
		this.setBounds(400, 20, Main.WIDTH, Main.HEIGHT);
		
		//dispose
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				canvas.destroy();
				animator.stop();
				setVisible(false);
			}
		});

		//get data
		root = Main.root;
		faces = Main.faces;
		

		//profile init
		glp =  GLProfile.getDefault();
		GLProfile.initSingleton();
		caps = new GLCapabilities(glp);

		//canvas
		canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		canvas.setFocusable(true);

		//frame
		this.add(canvas);

		//animator
		animator = new FPSAnimator(canvas, FPS);
		animator.add(canvas);
		animator.start();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();

		//enable z- (depth) buffer for hidden surface removal. 
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);

		//enable smooth shading.
		gl.glShadeModel(GL2.GL_SMOOTH);

		//define "clear" color
		gl.glClearColor(0f, 0f, 0f, 0f);

		//nice perspective.
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	@Override
	public void display(GLAutoDrawable drawable) {
		render(drawable);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);

	}

	/**
	 * Render method
	 * 
	 * @param drawable the drawable object
	 */
	private void render(GLAutoDrawable drawable) {

		gl = drawable.getGL().getGL2();
		
		// clear screen.
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
		//set camera
		setCamera(gl, CAMERA_X, CAMERA_Y, CAMERA_ZOOM);
        
		//draw bone system
		gl.glColor3d(0, 0, 1);
      	drawBone(root, gl);

      	//draw skin
      	drawSkin(gl);
        
		gl.glFlush();
	}
	
	/**
	 * Draws a bone
	 * 
	 * @param bone the bone to be drawn
	 * @param gl the gl object
	 */
	private void drawBone(Bone bone, GL2 gl){
		
		gl.glPushMatrix();
		
		
		if(bone.getParent()!=null){
			gl.glBegin(GL2.GL_LINE_LOOP);
			gl.glVertex3d(bone.getInitPosition().getX(), 
					bone.getInitPosition().getY(), 
					bone.getInitPosition().getZ());
			gl.glVertex3d(bone.getParent().getInitPosition().getX(), 
					bone.getParent().getInitPosition().getY(), 
					bone.getParent().getInitPosition().getZ());
			gl.glEnd();
		}
		

		for(Bone child : bone.getChild()){
			drawBone(child, gl);
		}
		
		gl.glPopMatrix();

	}

	/**
	 * Draws the skin
	 * 
	 * @param gl the gl object
	 */
	private void drawSkin(GL2 gl){
		gl.glColor3d(1, 0, 0);
	
		for(Triangle t: faces){
			gl.glBegin(GL2.GL_LINE_STRIP);
			gl.glVertex3d(t.getA().getX(), t.getA().getY(), t.getA().getZ());
			gl.glVertex3d(t.getB().getX(), t.getB().getY(), t.getB().getZ());
			gl.glVertex3d(t.getC().getX(), t.getC().getY(), t.getC().getZ());
			gl.glEnd();
		}
	}
	
    /**
     * Camera position setter
     * 
     * @param gl the gl object
     * @param cX x coordinate of the camera
     * @param cY y coordinate of the camera
     * @param cZ z coordinate of the camera
     */
	private void setCamera(GL2 gl, double cX, double cY, double cZ) {
		// Change to projection matrix.
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		// Perspective.
		float widthHeightRatio = (float) getWidth() / (float) getHeight();
		glu.gluPerspective(45, widthHeightRatio, 1, 1000);
		glu.gluLookAt(cX, cY, cZ, 0, 1, 0, 0, 1, 0);
		
		// Change back to model view matrix.
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void mouseDragged(MouseEvent e) {

         int diffx = e.getX() - lastX;
         int diffy = e.getY() - lastY;

         lastX = e.getX();
         lastY = e.getY();

         if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK) {
             CAMERA_X += 0.05f * diffx;
             CAMERA_Y -= 0.05f * diffy;
         }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
	}
	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()>0){
			CAMERA_ZOOM += 10;
		}else if(e.getWheelRotation()<0){
			CAMERA_ZOOM -= 10;
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
