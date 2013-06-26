package animation;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;


import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import skeleton.Bone;
import skeleton.BoneFunction;
import skeleton.Node;


import main.Main;
import math.Triangle;
import math.geom3d.Point3D;
import math.geom3d.Vector3D;

import com.jogamp.opengl.util.FPSAnimator;

/**
 * The class for viewing the animation and interaction between
 * bone system and skin
 * 
 * @author Jim Stanev
 */
public class Animator extends Frame implements GLEventListener, KeyListener, 
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
	 * mesh containing the skeleton and the skin
	 */
	private Mesh mesh;
	/**
	 * the initial faces used for updating the skin faces in mesh object
	 */
	private Vector<Triangle> faces = new Vector<>();
	
	/**
	 * used to mark the selected bone
	 */
	private int currentBone = 1;

	/**
	 * used to add offset to an angle of the selected bone
	 */
	private static final double angleOffset = 0.1;


	/**
	 * animation parameters
	 * kayFrameIndex: indicates the current animation frame used for recording a key frame
	 * FPS: frames per second
	 * time: used to indicate the animation frame during animation
	 */
    private int keyFrameIndex = 0, FPS =20, time;
    private boolean animationFlag = false;
    
    /**
     * boolean flag which indicates if bones-skin initialized
     */
    private boolean weightInit = false;

    /**
     * camera movement parameters
     */
    private float CAMERA_ZOOM = -50;
    private float CAMERA_X = 10;
    private float CAMERA_Y = 10;
    private int lastX = 0;
    private int lastY = 0;
    
    /**
     * the grid size
     */
    private static final int GRID_SIZE = 100;
    
    /**
     * Constructor
     */
	public Animator(){

		super("Animator");
		this.setBounds(200, 20, Main.WIDTH, Main.HEIGHT);
		
		//dispose
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				canvas.destroy();
				animator.stop();
				setVisible(false);
			}
		});

		//get data
		faces = Main.faces;
		mesh = AnimationFunction.getInitialPose();
		

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
		canvas.addKeyListener(this);
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
		if(weightInit==false){//init bones-skin bindings
			AnimationFunction.initializeSkinBoneRelation(mesh, Main.SKIN_DEPENDENCIES);
			weightInit = true;
		}
		if(animationFlag){//animation
			AnimationFunction.interpolate(mesh.getRoot(), time);
			time++;
			if(time==mesh.getRoot().getKeyFrameSize()*FPS){
				time = 0;
				mesh = AnimationFunction.getInitialPose();
				weightInit = false;
			}
		}
		updateSkinPosition();
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
      	drawBone(mesh.getRoot(), gl);

      	//draw skin
      	drawSkin(gl);
      		
      	//draw axis and grid
        drawAxis(gl);
        drawGrid(GRID_SIZE, gl);
        
		gl.glFlush();
	}
	
	/**
	 * Calculates and updates the new skin position using
	 * linear blend skinning
	 */
	private void updateSkinPosition(){

		//calculate new position
		for(Node v: mesh.getVertices()){
			ArrayList<BoneSkinBinding> bindings = v.getBoneSkinBindings();
			double x = 0, y = 0, z = 0;
	
			for(BoneSkinBinding b: bindings){

				Vector3D pos = new Vector3D(
								b.getBind().get(0, 3)+b.getBone().getAbsoluteMatrix().get(0, 3),
								b.getBind().get(1, 3)+b.getBone().getAbsoluteMatrix().get(1, 3),
								b.getBind().get(2, 3)+b.getBone().getAbsoluteMatrix().get(2, 3));
				x += pos.getX()*b.getWeight();
				y += pos.getY()*b.getWeight();
				z += pos.getZ()*b.getWeight();
				
				
			}

			//update position
			for(Integer index: v.getAttachedFaces()){
				Triangle t = faces.get(index);
				if(t.getA().equals(v.getInitialPositioln())){
					mesh.getFaces().get(index).setA(new Point3D(x, y, z));
				}else if(t.getB().equals(v.getInitialPositioln())){
					mesh.getFaces().get(index).setB(new Point3D(x, y, z));
				}else if(t.getC().equals(v.getInitialPositioln())){
					mesh.getFaces().get(index).setC(new Point3D(x, y, z));
				}
			}			
		}
	}
	
	/**
	 * Draws a bone
	 * 
	 * @param bone the bone to be drawn
	 * @param gl the gl object
	 */
	private void drawBone(Bone bone, GL2 gl){
		
		gl.glPushMatrix();
		
		double[] m = new double[16];
		
		if(bone.getParent()==null){
			gl.glTranslated(bone.getInitPosition().getX(),
				bone.getInitPosition().getY(),
				bone.getInitPosition().getZ());
		}
		
		
		Vector3D rotXYZ = bone.getRotationAxis();
		gl.glRotated(Math.toDegrees(bone.getAngle()), rotXYZ.getX(), rotXYZ.getY(), rotXYZ.getZ());	
		
		if(currentBone==bone.getName()){
			gl.glColor3d(0, 0, 1);
		}else{
			gl.glColor3d(0, 1, 0);
		}
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(bone.getLength(), 0, 0);
				
		gl.glEnd();
		
		gl.glTranslated(bone.getLength(), 0, 0);	
		
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, m, 0);
		bone.setAbsoluteMatrix(m);

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
	
		for(Triangle t: mesh.getFaces()){
			gl.glBegin(GL2.GL_LINE_STRIP);
			gl.glVertex3d(t.getA().getX(), t.getA().getY(), t.getA().getZ());
			gl.glVertex3d(t.getB().getX(), t.getB().getY(), t.getB().getZ());
			gl.glVertex3d(t.getC().getX(), t.getC().getY(), t.getC().getZ());
			gl.glEnd();
		}
	}
	
	/**
	 * Draws some coordinates axis
	 * 
	 * @param gl the gl object
	 */
	private void drawAxis(GL2 gl) {

        gl.glLineWidth(4.f);
        gl.glBegin(GL2.GL_LINES);
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

	/**
	 * Draws grid
	 * 
	 * @param size of the grid
	 * @param gl the gl object
	 */
    private void drawGrid(int size, GL2 gl) {
    	
        gl.glColor3f(0.5f, 0.5f, 0.5f);
        gl.glLineWidth(1.f);
        gl.glBegin(GL2.GL_LINES);
        for (int i = -size; i < size; i++) {
            gl.glVertex3f(i, 0, -size);
            gl.glVertex3f(i, 0, size);

            gl.glVertex3f(size, 0, i);
            gl.glVertex3f(-size, 0, i);
        }
        gl.glEnd();
        
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
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_N){
			currentBone++;
			if(currentBone==mesh.getRoot().getBonesCount()){
				currentBone = 1;
			}
		}else if(e.getKeyCode()==KeyEvent.VK_UP){
			Bone result = BoneFunction.findByName(mesh.getRoot(), currentBone);
			if(result!=null){
				result.setAngle(result.getAngle()+angleOffset);
			}
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN){
			Bone result = BoneFunction.findByName(mesh.getRoot(), currentBone);
			if(result!=null){
				result.setAngle(result.getAngle()-angleOffset);
			}
		}else if(e.getKeyCode()==KeyEvent.VK_R){
			AnimationFunction.recordKeyFrame(mesh.getRoot(), keyFrameIndex);
			keyFrameIndex += FPS;
		}else if(e.getKeyCode()==KeyEvent.VK_A){
			time = 0;
			animationFlag = !animationFlag;
		}
		
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
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
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
