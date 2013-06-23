package skeleton;

import java.util.ArrayList;
import java.util.Vector;

import math.geom3d.Point3D;
import mst.Edge;
import mst.EdgeWeightedGraph;
import mst.KruskalMST;

public class SkeletonSystemGeneration {

	private Vector<Chain> chains;
	private ArrayList<Point3D> rootSet = new ArrayList<>();
	private Bone root;
	private static int name = 1;
	
	public SkeletonSystemGeneration(Vector<Chain> chains){
		this.chains = chains;
	}
	
	public void beginGeneration(){
		getRoots();
		
		if(rootSet.size()>1){
			System.out.println("Root candidate: "+ rootSet.size());
			generateTreeSystem();
		}else{
			System.out.println("Root candidate: 1");
			root = new Bone(rootSet.get(0), null);
			rootSet.remove(0);
		}
		
		generateChildren(root);
		
		setName(root);
		
		root.setBonesCount(name);
	}
	
	private void setName(Bone bone){
		bone.setName(name);
		name++;
		for(Bone child: bone.getChild()){
			setName(child);
		}
		
	}
	
	private void generateChildren(Bone bone){
		for(Chain c: chains){
			if(c.size()!=0&&c.getLast().equals(bone.getInitPosition())){
				c.getChain().remove(c.size()-1);
				addToBone(bone, c);
			}
		}
		
		for(Bone child: bone.getChild()){
			generateChildren(child);
		}
	}
	
	private void addToBone(Bone bone, Chain c){
		if(c.size()==0) return;
		bone.addChild(c.getLast());
		c.getChain().remove(c.size()-1);
		addToBone(bone.getChild().lastElement(), c);
		
	}
	
	private void generateTreeSystem(){
		Point3D[] rootArray = new Point3D[rootSet.size()];
		rootSet.toArray(rootArray);
		
		EdgeWeightedGraph G = new EdgeWeightedGraph(rootArray.length);
		
		for(int i = 0;i<rootArray.length;i++){
			for(int j = 0;j<rootArray.length;j++){
				if(rootArray[i].equals(rootArray[j])) continue;
				G.addEdge(new Edge(i, j, rootArray[i].distance(rootArray[j])));
				
			}
		}
		
		
		KruskalMST mst = new KruskalMST(G);
		System.out.println("Minimum spanning tree");
		for(Edge e: mst.edges()){
			System.out.println(e);
			addToBoneSystem(rootArray[e.v()], rootArray[e.w()]);
		}
		
	}
	
	private void addToBoneSystem(Point3D p, Point3D q){
		if(root==null){//no root
			root = new Bone(p, null);
			root.addChild(q);
		}else{
			Bone result = findBone(root, p, q);
			if(result==null){//TODO
				root.addChild(p);
				result = findBone(root, p, q);
			}
			if(result.getInitPosition().equals(p)){
				result.addChild(q);
			}else{
				result.addChild(p);
			}
		}
	}
	
	private Bone findBone(Bone bone, Point3D p, Point3D q){
		if(bone.getInitPosition().equals(q)||
				bone.getInitPosition().equals(p)){
			return bone;
		}
		
		for(Bone child: bone.getChild()){
			Bone result = findBone(child, p, q);
			if(result!=null) return result;
		}
		
		return null;
	}
	
	private void getRoots(){
		for(Chain c: chains){
			addToSet(c.getLast(), rootSet);
		}
		System.out.println("Roots #: "+rootSet.size());
	}
	
	private <T> void addToSet(T p, ArrayList<T> set){
		for(T q: set){
			if(p.equals(q)) return;
		}
		set.add(p);
	}
	
	public Bone getRoot(){
		return this.root;
	}
}
