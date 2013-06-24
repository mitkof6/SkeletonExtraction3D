package skeleton;

import java.util.Vector;

import math.geom3d.Point3D;
import math.geom3d.Vector3D;

import Jama.Matrix;
import animation.KeyFrame;

/**
 * Bone entity represents a 3D position of the joint
 * with respect to its parent
 * 
 * @author Jim Stanev
 */
public class Bone {
	
	private Point3D initPosition;
	private Bone parent;
	private Vector<Bone> child = new Vector<>();
	private Matrix absoluteMatrix = Matrix.identity(4, 4);
	private double length;
	private double angle, initialAngle;
	private Vector3D rotXYZ;
	private int name;
	private int bonesCount;
	private double angleOffset;
	private Vector<KeyFrame> keyFrames = new Vector<>();

	/**
	 * Constructor
	 * 
	 * @param initPosition the 3D position of the joint
	 * @param parent the parent of the joint if root give null
	 */
	public Bone(Point3D initPosition, Bone parent){
		this.initPosition = initPosition;
		this.parent = parent;

		if(parent!=null){
			defineRelativeParameters(parent, initPosition);
		}else{//if root
			rotXYZ = new Vector3D(0, 0, 1);
			angle = 0;
			length = 0;
		}
		this.initialAngle = this.angle;
	}
	
	public void addChild(Point3D initPosition){
		child.add(new Bone(initPosition, this));
	}
	
	public void addToKeyFrames(KeyFrame k){
		this.keyFrames.add(k);
	}
	
	/**
	 * Generates the relative parameters of the parent child system
	 * 
	 * @param parent the parent
	 * @param p child position
	 */
	private void defineRelativeParameters(Bone parent, Point3D p){
		Vector3D a, b;
		Vector3D rotationAxis;
		double prod;
		
		b = new Vector3D(parent.getInitPosition(), p);
		if(parent.getParent()!=null){
			a = new Vector3D(
					parent.getParent().getInitPosition(), parent.getInitPosition());
			
		}else{
			//a = new Vector3D(0, 
					//p.getY()-parent.getInitPosition().getY(),
					//p.getZ()-parent.getInitPosition().getZ());
			a = new Vector3D(1, 0, 0);
		}
		
		//System.out.println("A: "+a.getX()+" "+a.getY()+" "+a.getZ());
		//System.out.println("B: "+b.getX()+" "+b.getY()+" "+b.getZ());
		
		if(a.norm()==0){
			prod = 0;
		}else{
			prod = 
					Vector3D.dotProduct(a, b)/(a.norm()*b.norm());
		}
	
		rotationAxis = Vector3D.crossProduct(a, b);
		
		if(rotationAxis.getX()!=0){
			if(rotationAxis.getY()==0){
				rotationAxis = new Vector3D(0, -rotationAxis.getX(), rotationAxis.getZ());
			}else if(rotationAxis.getZ()==0){
				rotationAxis = new Vector3D(0, rotationAxis.getY(), -rotationAxis.getX());
			}
		}
		
		if(prod>1.0){
			prod = 1.0;
		}else if(prod<-1.0){
			prod = -1.0;
		}
		
		double phi = Math.acos(prod);
		
		angle = phi;
		rotXYZ = rotationAxis;
		length = Math.sqrt(
				Math.pow(p.getX()-parent.getInitPosition().getX(), 2)+
				Math.pow(p.getY()-parent.getInitPosition().getY(), 2)+
				Math.pow(p.getZ()-parent.getInitPosition().getZ(), 2));
		
		if(rotationAxis.getX()==0&&rotationAxis.getY()==0&&rotationAxis.getZ()==0){
			rotationAxis = new Vector3D(0, 0, 1);
		}
		//TODO
		//System.out.println("RA: "+rotationAxis.getX()+" "+rotationAxis.getY()+" "+rotationAxis.getZ());
		//System.out.println("Angle: "+Math.toDegrees(phi));
		//System.out.println("L: "+length);
		
		
		/*
		//phi being the rotation angle and (u,v,w) the rotation (axis) vector
		double rcos = Math.cos(phi);
		double rsin = Math.sin(phi);
		
		double u = rotationAxis.getX();
		double v = rotationAxis.getY();
		double w = rotationAxis.getZ();
		double[][] matrix = new double[4][4];
		
		matrix[0][0] =      rcos + u*u*(1-rcos);
		matrix[1][0] =  w * rsin + v*u*(1-rcos);
		matrix[2][0] = -v * rsin + w*u*(1-rcos);
		matrix[3][0] = 0;
		matrix[0][1] = -w * rsin + u*v*(1-rcos);
		matrix[1][1] =      rcos + v*v*(1-rcos);
		matrix[2][1] =  u * rsin + w*v*(1-rcos);
		matrix[3][1] = 0;
		matrix[0][2] =  v * rsin + u*w*(1-rcos);
		matrix[1][2] = -u * rsin + v*w*(1-rcos);
		matrix[2][2] =      rcos + w*w*(1-rcos);
		matrix[3][2] = 0;
		matrix[0][3] = p.getX()-parent.getInitPosition().getX();
		matrix[1][3] = p.getY()-parent.getInitPosition().getY();
		matrix[2][3] = p.getZ()-parent.getInitPosition().getZ();
		matrix[3][3] = 1;
		
		
		double[] m = new double[16];
		m[0] =      rcos + u*u*(1-rcos);
		m[1] =  w * rsin + v*u*(1-rcos);
		m[2] = -v * rsin + w*u*(1-rcos);
		m[3] = 0;
		m[4] = -w * rsin + u*v*(1-rcos);
		m[5] =      rcos + v*v*(1-rcos);
		m[6] =  u * rsin + w*v*(1-rcos);
		m[7] = 0;
		m[8] =  v * rsin + u*w*(1-rcos);
		m[9] = -u * rsin + v*w*(1-rcos);
		m[10] =      rcos + w*w*(1-rcos);
		m[11] = 0;
		m[12] = p.getX()-parent.getInitPosition().getX();
		m[13] = p.getY()-parent.getInitPosition().getY();
		m[14] = p.getZ()-parent.getInitPosition().getZ();
		m[15] = 1;
		
		relative = new Matrix4(m);
		
		
		double rcos = Math.cos(phi);
		double rsin = Math.sin(phi);
		
		double u = rotationAxis.getX();
		double v = rotationAxis.getY();
		double w = rotationAxis.getZ();
		
		double[] m = new double[16];
		m[0] =      rcos + u*u*(1-rcos);
		m[1] =  w * rsin + v*u*(1-rcos);
		m[2] = -v * rsin + w*u*(1-rcos);
		m[3] = 0;
		m[4] = -w * rsin + u*v*(1-rcos);
		m[5] =      rcos + v*v*(1-rcos);
		m[6] =  u * rsin + w*v*(1-rcos);
		m[7] = 0;
		m[8] =  v * rsin + u*w*(1-rcos);
		m[9] = -u * rsin + v*w*(1-rcos);
		m[10] =      rcos + w*w*(1-rcos);
		m[11] = 0;
		m[12] = this.length;
		m[13] = 0;
		m[14] = 0;
		m[15] = 1;
		
		relative = new Matrix4(m);
		*/
	}
	
	public void resetAngle(){
		this.angle = this.initialAngle;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public int getBonesCount(){
		return this.bonesCount;
	}


	public Vector<Bone> getChild(){
		return this.child;
	}

	public int getChildCount(){
		return child.size();
	}


	public Point3D getInitPosition(){
		return this.initPosition;
	}


	public Vector<KeyFrame> getKeyFrames(){
		return this.keyFrames;
	}	
	
	public double getLength() {
		return length;
	}
	
	public int getName(){
		return this.name;
	}
	
	public Bone getParent(){
		return this.parent;
	}
	
	public Vector3D getRotationAxis() {
		return rotXYZ;
	}
	
	public void setAbsoluteMatrix(double[] m){
		double[][] array = {{m[0], m[4], m[8], m[12]},
							{m[1], m[5], m[9], m[13]},
							{m[2], m[6], m[10], m[14]},
							{m[3], m[7], m[11], m[15]}};
		
		this.absoluteMatrix = new Matrix(array);
	}
	
	public Matrix getAbsoluteMatrix(){
		return this.absoluteMatrix;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setBonesCount(int total){
		this.bonesCount = total;
	}

	public void setKeyFrames(Vector<KeyFrame> k){
		this.keyFrames = k;
	}

	public void setName(int name){
		this.name = name;
	}
	
	public int getKeyFrameSize(){
		return this.keyFrames.size();
	}
	
	public KeyFrame getKeyFrame(int index){
		return this.keyFrames.get(index);
	}
	
	public void setAngleOffset(double angle){
		this.angleOffset = angle;
	}
	
	public double getAngleOffset(){
		return this.angleOffset;
	}
	
}
