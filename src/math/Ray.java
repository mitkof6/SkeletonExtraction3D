package math;

import javax.media.j3d.PickRay;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.behaviors.picking.Intersect;

import math.geom3d.Point3D;
import math.geom3d.Vector3D;

@SuppressWarnings("deprecation")
public class Ray{
	
	private Point3D from;
	private Vector3D direction;
	
	public Ray(Point3D from, Vector3D direction){
		this.from = from;
		this.direction = direction;
		
	}
	
	public boolean intersectRayTriangle(Triangle t, double[] distance) {
		
		//double[] dist = new double[1];
		Point3d[] points = new Point3d[3];
		points[0] = new Point3d(t.getA().getX(), t.getA().getY(), t.getA().getZ());
		points[1] = new Point3d(t.getB().getX(), t.getB().getY(), t.getB().getZ());
		points[2] = new Point3d(t.getC().getX(), t.getC().getY(), t.getC().getZ());
		
		if(Intersect.rayAndTriangle(new PickRay(new Point3d(this.from.getX(),
				this.from.getY(), this.from.getZ()), new Vector3d(this.direction.getX(),
						this.direction.getY(), this.direction.getZ())), points, 0, distance)){
			
			return true;
		}else{
			return false;
		}
			
    }
	
}