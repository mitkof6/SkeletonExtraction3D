package animation;

import java.util.Vector;

import skeleton.Bone;
import skeleton.Node;

import math.Triangle;
import math.geom3d.Point3D;

public class Mesh {

	private Vector<Node<Triangle>> vertices = new Vector<>();
	private Bone root;
	private Point3D[] skin;
	
	public Mesh(Vector<Node<Triangle>> vertices, Bone root){
		for(Node<Triangle> n: vertices){
			this.vertices.add(n.clone());
		}
		skin = new Point3D[vertices.size()];
		this.root = root;
	}

	public Vector<Node<Triangle>> getVertices() {
		return vertices;
	}

	public Bone getRoot() {
		return root;
	}
	
}
