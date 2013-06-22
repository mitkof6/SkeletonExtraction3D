package skeleton;

import java.util.ArrayList;

import animation.BoneSkinBinding;

import math.geom3d.Point3D;

public class Node<T> extends Point3D implements Cloneable{

	private ArrayList<T> attachedTriangles = new ArrayList<>();
	private ArrayList<BoneSkinBinding> boneSkinBindings = new ArrayList<>();
	
	public Node(double x, double y, double z){
		super(x, y, z);
	}
	
	public ArrayList<T> getAttachedTriangles(){
		return this.attachedTriangles;
	}
	
	public void addTriangle(T t){
		this.attachedTriangles.add(t);
	}
	
	public Point3D getPoint(){
		return new Point3D(this.getX(), this.getY(), this.getZ());
	}
	
	public void addBoneSkinBinding(BoneSkinBinding bSB){
		this.boneSkinBindings.add(bSB);
	}
	
	public Node<T> clone(){
		Node<T> result = new Node<>(this.getX(), this.getY(), this.getZ());
		for(T t: attachedTriangles){
			result.addTriangle(t);
		}
		return result;
	}
}
