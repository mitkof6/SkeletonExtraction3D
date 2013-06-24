package skeleton;

import java.util.ArrayList;
import java.util.Vector;

import animation.BoneSkinBinding;

import math.geom3d.Point3D;

/**
 * Node entity used for representing the vertices and some
 * functionalities
 * 
 * @author Jim Stanev
 */
public class Node implements Cloneable{

	/**
	 * indexes of the attached faces, used for faster access
	 */
	private Vector<Integer> attachedFaceIndex = new Vector<>();
	/**
	 * the node-bone bindings
	 */
	private ArrayList<BoneSkinBinding> boneSkinBindings = new ArrayList<>();
	/**
	 * initial position of a node
	 */
	private Point3D initialPosition;
	
	/**
	 * Constructor
	 * 
	 * @param x coordinates
	 * @param y coordinates
	 * @param z coordinates
	 */
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
	
	/**
	 * Copy the node into new object
	 */
	public Node clone(){
		Node result = new Node(initialPosition.getX(), 
						initialPosition.getY(), initialPosition.getZ());
		for(Integer t: attachedFaceIndex){
			result.addFace(t);
		}
		return result;
	}
}
