package animation;

import java.util.Vector;

import math.Triangle;

import skeleton.Bone;
import skeleton.Node;

public class Mesh {

	private Vector<Node> vertices = new Vector<>();
	private Vector<Triangle> faces = new Vector<>();
	private Bone root;
	
	public Mesh(Vector<Node> vertices, Vector<Triangle> triangles, Bone root){
		for(Node n: vertices){
			this.vertices.add(n.clone());
		}
		for(Triangle t: triangles){
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
