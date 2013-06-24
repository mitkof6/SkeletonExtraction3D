package skeleton;

import java.util.ArrayList;
import java.util.Vector;

import animation.BoneSkinBinding;

import math.geom3d.Point3D;

public class Node implements Cloneable{

	private Vector<Integer> attachedFaceIndex = new Vector<>();
	private ArrayList<BoneSkinBinding> boneSkinBindings = new ArrayList<>();
	private Point3D initialPosition;
	
	public Node(double x, double y, double z){
		initialPosition = new Point3D(x, y, z);
	}
	
	public Vector<Integer> getAttachedFaces(){
		return this.attachedFaceIndex;
	}
	
	public void addFace(Integer index){
		this.attachedFaceIndex.add(index);
	}
	
	public Point3D getInitialPositioln(){
		return this.initialPosition;
	}
	
	public void addBoneSkinBinding(BoneSkinBinding bSB){
		this.boneSkinBindings.add(bSB);
	}
	
	public ArrayList<BoneSkinBinding> getBoneSkinBindings(){
		return this.boneSkinBindings;
	}
	public Node clone(){
		Node result = new Node(initialPosition.getX(), 
						initialPosition.getY(), initialPosition.getZ());
		for(Integer t: attachedFaceIndex){
			result.addFace(t);
		}
		return result;
	}
}
