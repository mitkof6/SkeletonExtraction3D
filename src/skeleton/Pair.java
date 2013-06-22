package skeleton;

import math.geom3d.Point3D;

public class Pair {

	private Point3D p1, p2;
	
	public Pair(Point3D p1, Point3D p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public boolean equals(Pair p){
		if(p.getP1().equals(this.p1)&&p.getP2().equals(this.p2)||
				p.getP1().equals(this.p2)&&p.getP2().equals(this.p1)){
			return true;
		}else{
			return false;
		}
	}
	
	public Point3D getP1(){
		return this.p1;
	}
	
	public Point3D getP2(){
		return this.p2;
	}
}
