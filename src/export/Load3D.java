package export;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.Vector;

import skeleton.Node;




import math.Triangle;

public class Load3D {
	
	private Vector<Node<Triangle>> vertices = new Vector<Node<Triangle>>();
	private Vector<Triangle> triangles = new Vector<Triangle>();
	
	public Load3D(String path) throws FileNotFoundException{
		
		//initialize
		Scanner inputStream = new Scanner(new File(path));
		
		//read file
		while(inputStream.hasNext()){
			
			String[] line = inputStream.nextLine().split(" ");
			if(line.length==1){
				continue;
			}
			switch (line[0].charAt(0)) {
				case 'v':
					vertices.addElement(new Node<Triangle>(Double.parseDouble(line[1]), 
							Double.parseDouble(line[2]), 
							Double.parseDouble(line[3])));
					break;
				case 'f':
					triangles.addElement(new Triangle(
							vertices.get(Integer.parseInt(line[1])-1).getInitialPositioln(),
							vertices.get(Integer.parseInt(line[2])-1).getInitialPositioln(),
							vertices.get(Integer.parseInt(line[3])-1).getInitialPositioln()));
					
					vertices.get(Integer.parseInt(line[1])-1).addTriangle(triangles.lastElement());
					vertices.get(Integer.parseInt(line[2])-1).addTriangle(triangles.lastElement());
					vertices.get(Integer.parseInt(line[3])-1).addTriangle(triangles.lastElement());
					
					break;
				default:
					continue;
			}
		}
		inputStream.close();
	}

	public Vector<Node<Triangle>> getVertices() {
		return vertices;
	}

	public Vector<Triangle> getTriangles() {
		return triangles;
	}
}
