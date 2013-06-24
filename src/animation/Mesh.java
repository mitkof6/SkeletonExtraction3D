package animation;

import java.util.Vector;

import skeleton.Bone;
import skeleton.Node;

import math.Triangle;

public class Mesh {

	private Vector<Node<Triangle>> vertices = new Vector<>();
	private Bone root;
	
	public Mesh(Vector<Node<Triangle>> vertices, Bone root){
		for(Node<Triangle> n: vertices){
			this.vertices.add(n.clone());
		}
		this.root = root;
	}

	public Vector<Node<Triangle>> getVertices() {
		return vertices;
	}

	public Bone getRoot() {
		return root;
	}
	
}
