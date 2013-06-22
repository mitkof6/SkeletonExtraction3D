package skeleton;

import java.util.Vector;

import math.geom3d.Point3D;


public class Chain{
	
	private Vector<Point3D> positions = new Vector<>();
	
	public Chain(){

	}
	
	public void addToChain(Point3D p){
		this.positions.add(p);
	}
	
	public Vector<Point3D> getChain(){
		return this.positions;
	}
	
	public void setChain(Vector<Point3D> newChain){
		this.positions = newChain;
	}
	
	public int size(){
		return this.positions.size();
	}
	
	public Point3D getPoint(int index){
		return this.positions.get(index);
	}
	
	public Point3D getLast(){
		return this.positions.lastElement();
	}
	
	public void setLast(Point3D element){
		this.positions.set(positions.size()-1, element);
	}
}
