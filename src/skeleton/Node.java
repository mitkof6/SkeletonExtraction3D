package skeleton;

import java.util.ArrayList;

import animation.BoneSkinBinding;

import math.geom3d.Point3D;

public class Node<T> implements Cloneable{

	private ArrayList<T> attachedTriangles = new ArrayList<>();
	private ArrayList<BoneSkinBinding> boneSkinBindings = new ArrayList<>();
	private Point3D initialPosition, newPosition;
	
	public Node(double x, double y, double z){
		initialPosition = new Point3D(x, y, z);
		newPosition = new Point3D(x, y, x);
	}
	
	public ArrayList<T> getAttachedTriangles(){
		return this.attachedTriangles;
	}
	
	public void addTriangle(T t){
		this.attachedTriangles.add(t);
	}
	
	public Point3D getInitialPositioln(){
		return this.initialPosition;
	}
	
	public void addBoneSkinBinding(BoneSkinBinding bSB){
		this.boneSkinBindings.add(bSB);
	}
	
	public Point3D getNewPosition(){
		return newPosition;
	}
	
	public void setNewPosition(double x, double y, double z){
		newPosition = new Point3D(x, y, z);
	}
	
	public ArrayList<BoneSkinBinding> getBoneSkinBindings(){
		return this.boneSkinBindings;
	}
	public Node<T> clone(){
		Node<T> result = new Node<>(initialPosition.getX(), 
						initialPosition.getY(), initialPosition.getZ());
		for(T t: attachedTriangles){
			result.addTriangle(t);
		}
		return result;
	}
}
