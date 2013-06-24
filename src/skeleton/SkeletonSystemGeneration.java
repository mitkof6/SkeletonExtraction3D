package skeleton;

import java.util.ArrayList;
import java.util.Vector;

import math.geom3d.Point3D;
import mst.Edge;
import mst.EdgeWeightedGraph;
import mst.KruskalMST;

/**
 * Generates a bone system in hierarchy structure
 * 
 * @author Jim Stanev
 */
public class SkeletonSystemGeneration {

	private Vector<Chain> chains;
	/**
	 * keeps the root candidates which are the last point
	 * in every chain
	 */
	private ArrayList<Point3D> rootSet = new ArrayList<>();
	/**
	 * the root of the bone system
	 */
	private Bone root;
	/**
	 * name of the bones, increments when bone is added
	 */
	private static int name = 1;
	
	/**
	 * Constructor
	 * 
	 * @param chains the refine chains
	 */
	public SkeletonSystemGeneration(Vector<Chain> chains){
		this.chains = chains;
	}
	
	/**
	 * The begin method
	 */
	public void beginGeneration(){
		//find root candidates
		getRootCandidates();
		
		if(rootSet.size()>1){
			System.out.println("Root candidates: "+ rootSet.size());
			generateTreeSystem();
		}else{
			System.out.println("Root candidate: 1");
			root = new Bone(rootSet.get(0), null);
			rootSet.remove(0);
		}
		
		//generate children by scanning the chains
		generateChildren(root);
		
		//give name to every bone
		setName(root);
		
		//set the total count of bones
		root.setBonesCount(name);
	}
	
	/**
	 * Set name method
	 * 
	 * @param bone the root bone
	 */
	private void setName(Bone bone){
		bone.setName(name);
		name++;
		for(Bone child: bone.getChild()){
			setName(child);
		}
		
	}
	
	/**
	 * Traverse the chains to generate parent-child bone system
	 * 
	 * @param bone
	 */
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
	
	/**
	 * Used by generateChildren
	 * 
	 * @param bone the bone 
	 * @param c the chain
	 */
	private void addToBone(Bone bone, Chain c){
		if(c.size()==0) return;
		bone.addChild(c.getLast());
		c.getChain().remove(c.size()-1);
		addToBone(bone.getChild().lastElement(), c);
		
	}
	
	/**
	 * Because of the skeleton extraction algorithm some chains my be
	 * orphans with respect to the whole bone system. To make a whole
	 * bone system I implemented the minimum spanning tree algorithm 
	 * by Kruskal to generate a graph with the connections, which is
	 * used to generate the core bone system to which will be attaced
	 * the remaining points of the chain.
	 */
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
			if(result==null){//TODO maybe problematic
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
	
	/**
	 * Finds a bone in the core system by giving 2 points
	 * which must be either initial positions
	 * 
	 * @param bone the initial bone
	 * @param p point p
	 * @param q point q
	 * @return the bone if exist
	 */
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
	
	private void getRootCandidates(){
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
	
	/**
	 * The root of the result bone system
	 * 
	 * @return the root
	 */
	public Bone getRoot(){
		return this.root;
	}
}
