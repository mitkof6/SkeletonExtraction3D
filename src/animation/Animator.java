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
	private GLU glu;
	private GL2 gl;

	//data
	private Mesh mesh;
	private Vector<Triangle> faces;
	
	//bone selection
	private int currentBone = 1;

	//angle update
	private double angleOffset = 0.1;

    //animation
    private int keyFrameIndex = 0, FPS = 30, time;
    private boolean animationFlag = false;
    
    //weights
    private boolean weightInit = false;

    //movements
    private float CAMERA_ZOOM = -50;
    private float CAMERA_X = 10;
    private float CAMERA_Y = 10;
    private int lastX = 0;
    private int lastY = 0;
    
	public Animator(){

		super("Animator");
		this.setBounds(400, 20, Main.WIDTH, Main.HEIGHT);
		//this.setFocusable(true);
		
		//dispose
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				canvas.destroy();
				animator.stop();
				setVisible(false);
			}
		});

		//get data
		Bone root = Main.root;
		faces = Main.faces;

		/*
		Bone root;
		root = new Bone(new Point3D(0,0,0), null);
		root.setBonesCount(7);
		root.setName(1);
		root.addChild(new Point3D(2.555,0,0));
		root.getChild().get(0).setName(2);
		root.addChild(new Point3D(0,5.5,0));
		root.getChild().get(1).setName(3);
		root.addChild(new Point3D(0,0,3.33));
		root.getChild().get(2).setName(4);
		//root.addChild(new Point3D(-10,0,0));
		root.getChild().get(0).addChild(new Point3D(10,10,10));
		root.getChild().get(0).getChild().get(0).setName(5);
		root.getChild().get(1).addChild(new Point3D(-10,10,-10));
		root.getChild().get(1).getChild().get(0).setName(6);
		root.getChild().get(2).addChild(new Point3D(-10,-10,10));
		root.getChild().get(2).getChild().get(0).setName(7);
		*/
		mesh = new Mesh(Main.vertices, Main.faces, root);
		

		//intialization
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

		//glu
		glu = new GLU();

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

		// Enable z- (depth) buffer for hidden surface removal. 
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);

		// Enable smooth shading.
		gl.glShadeModel(GL2.GL_SMOOTH);

		// Define "clear" color
		gl.glClearColor(0f, 0f, 0f, 0f);

		// We want a nice perspective.
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	@Override
	public void display(GLAutoDrawable drawable) {
		render(drawable);
		if(weightInit==false){
			Animation.initSkinWeights(mesh, Main.SKIN_DEPENDENCIES);
			weightInit = true;
		}
		if(animationFlag){
			Animation.interpolate(mesh.getRoot(), time);
			time++;
			if(time==mesh.getRoot().getKeyFrameSize()*FPS){
				time = 0;
				//root = Animation.getInitialPose();
			}
		}
		updateSkin();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);

	}

	private void render(GLAutoDrawable drawable) {

		gl = drawable.getGL().getGL2();
		
		// clear screen.
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
		//set camera
		setCamera(gl, glu, CAMERA_X, CAMERA_Y, CAMERA_ZOOM);
        
		//draw bone system
      	drawBone(mesh.getRoot(), gl);

      	//draw skin
      	drawSkin(gl);
      		
      	//draw axis and grid
        drawAxis(gl);
        drawGrid(100, gl);
        
		gl.glFlush();
	}
	
	private void updateSkin(){

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
	
private void drawBone(Bone bone, GL2 gl){
		
		gl.glPushMatrix();
		
		/*
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
		*/
		double[] m = new double[16];
		
		if(bone.getParent()==null){
			gl.glTranslated(bone.getInitPosition().getX(),
				bone.getInitPosition().getY(),
				bone.getInitPosition().getZ());
		}
		
		
		Vector3D rotXYZ = bone.getRotXYZ();
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
		//System.out.println(bone.getAbsoluteMatrix().toString());
		//System.out.println(m[12]+" "+m[13]+" "+m[14]);
		for(Bone child : bone.getChild()){
			drawBone(child, gl);
		}
		
		gl.glPopMatrix();

	}

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
	
	private void setCamera(GL2 gl, GLU glu, double cX, double cY, double cZ) {
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
			Animation.recordKeyFrame(mesh.getRoot(), keyFrameIndex);
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
			CAMERA_ZOOM -= 10;
		}else if(e.getWheelRotation()<0){
			CAMERA_ZOOM += 10;
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
