package math;

import math.geom3d.Point3D;
import math.geom3d.Vector3D;

/**
 * This class represent a triangle entity
 * 
 * @author Jim Stanev
 */
public class Triangle implements Cloneable{
	
	private Point3D a, b, c; 
	private Point3D center;
	private Vector3D normalVector;
	
	public Triangle(Point3D a, Point3D b, Point3D c) {
		this.a = a;
		this.b = b;
		this.c = c;
		
		this.center = new Point3D(
						(a.getX()+b.getX()+c.getX())/3,
						(a.getY()+b.getY()+c.getY())/3,
						(a.getZ()+b.getZ()+c.getZ())/3);
		this.normalVector = Vector3D.crossProduct(new Vector3D(a,b), new Vector3D(b,c));
		
		this.normalVector = this.normalVector.normalize();
	}

	public Point3D getA() {
		return a;
	}

	public void setA(Point3D a) {
		this.a = a;
	}

	public Point3D getB() {
		return b;
	}

	public void setB(Point3D b) {
		this.b = b;
	}

	public Point3D getC() {
		return c;
	}

	public void setC(Point3D c) {
		this.c = c;
	}

	public Vector3D getNormalVector() {
		return normalVector;
	}

	public void setNormal(Vector3D normal) {
		this.normalVector = normal;
	}
	
	public Point3D getCenter(){
		return this.center;
	}
	
	@Override
	public Triangle clone(){
		return new Triangle(this.a, this.b, this.c);
	}
	
}
