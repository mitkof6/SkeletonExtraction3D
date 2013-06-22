package math;

import math.geom3d.Point3D;
import math.geom3d.Vector3D;



public class Triangle implements Cloneable{
	
	private Point3D a, b, c; 
	private Point3D center;
	private Vector3D normal;
	
	public Triangle(Point3D a, Point3D b, Point3D c) {
		this.a = a;
		this.b = b;
		this.c = c;
		//System.out.println("a: "+a.getX()+" "+a.getY()+" "+a.getZ());
		//System.out.println("b: "+b.getX()+" "+b.getY()+" "+b.getZ());
		//System.out.println("c: "+c.getX()+" "+c.getY()+" "+c.getZ());
		
		this.normal = Vector3D.crossProduct(new Vector3D(a,b), new Vector3D(b,c));
		
		this.normal = this.normal.normalize();
		//System.out.println(normal.getX()+" "+normal.getY()+" "+normal.getZ());
		
		center = new Point3D((a.getX()+b.getX()+c.getX())/3,
							(a.getY()+b.getY()+b.getY())/3,
							(a.getZ()+b.getZ()+b.getZ())/3);
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
	
	public Point3D getCenter(){
		return this.center;
	}
	
	@Override
	public Triangle clone(){
		return new Triangle(this.a, this.b, this.c);
	}
	
}
