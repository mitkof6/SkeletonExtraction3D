package math;

import math.geom3d.Point3D;
import math.geom3d.Vector3D;



public class Triangle implements Cloneable{
	
	private Point3D a, b, c; 
	private Vector3D normal;
	
	public Triangle(Point3D a, Point3D b, Point3D c) {
		this.a = a;
		this.b = b;
		this.c = c;
		
		this.normal = Vector3D.crossProduct(new Vector3D(a,b), new Vector3D(b,c));
		
		this.normal = this.normal.normalize();
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

	public Vector3D getNormal() {
		return normal;
	}

	public void setNormal(Vector3D normal) {
		this.normal = normal;
	}
	
	@Override
	public Triangle clone(){
		return new Triangle(this.a, this.b, this.c);
	}
	
}
