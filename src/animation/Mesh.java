package animation;

import java.util.Vector;

import math.Triangle;

import skeleton.Bone;
import skeleton.Node;

/**
 * This entity represents the model and its skeleton as whole
 * 
 * @author Jim Stanev
 */
public class Mesh {

	private Vector<Node> vertices = new Vector<>();
	private Vector<Triangle> faces = new Vector<>();
	private Bone root;
	
	/**
	 * Copy constructor
	 * 
	 * @param vertices the vertices of the model
	 * @param faces the faces of the model 
	 * @param root the root of the bone system
	 */
	public Mesh(Vector<Node> vertices, Vector<Triangle> faces, Bone root){
		for(Node n: vertices){
			this.vertices.add(n.clone());
		}
		for(Triangle t: faces){
			this.faces.add(t.clone());
		}
		this.root = root;
	}

	public Vector<Node> getVertices() {
		return this.vertices;
	}
	
	public Vector<Triangle> getFaces(){
		return this.faces;
	}

	public Bone getRoot() {
		return this.root;
	}
	
}
