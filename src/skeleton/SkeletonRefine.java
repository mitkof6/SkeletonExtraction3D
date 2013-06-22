package skeleton;

import java.util.ArrayList;
import java.util.Vector;

import math.geom3d.Point3D;

public class SkeletonRefine {

	private final int SAMPLING, CHAIN_SIZE_TOLERANCE;
	private final double DISTANCE_TOLERANCE, MERGE_TOLERANCE;
	
	private Vector<Chain> chains;
	
	public SkeletonRefine(Vector<Chain> chains, int sampling,
			double distanceTolerance, double mergeTolerance, int chainSizeTolerance){
		this.chains = chains;
		this.SAMPLING = sampling;
		this.DISTANCE_TOLERANCE = distanceTolerance;
		this.MERGE_TOLERANCE = mergeTolerance;
		this.CHAIN_SIZE_TOLERANCE = chainSizeTolerance;
	}
	
	public void beginRefinement(){
		sample();
		mergeNodes();
		pruning();
	}
	
	private void sample(){
		for(Chain c: chains){
			Vector<Point3D> newChain = new Vector<>();
			if(!c.getChain().isEmpty()){
				newChain.add(c.getPoint(0));
			}
			for(int i = SAMPLING;i<c.size();i=i+SAMPLING){
				if(newChain.lastElement().distance(c.getPoint(i))>DISTANCE_TOLERANCE){
					newChain.add(c.getPoint(i));
				}
				
			}
			c.setChain(newChain);
		}
	}
	
	private void mergeNodes(){
		for(Chain c: chains){
			for(int i = 0;i<chains.size();i++){
				if(c.getLast().distance(chains.get(i).getLast())<MERGE_TOLERANCE){
					chains.get(i).setLast(c.getLast());
				}
			}
		}
	}
	
	private void pruning(){
		ArrayList<Chain> toRemove = new ArrayList<>();
		for(Chain c: chains){
			if(c.size()<CHAIN_SIZE_TOLERANCE){
				toRemove.add(c);
			}
		}
		
		for(Chain remove: toRemove){
			chains.remove(remove);
		}
	}
	public Vector<Chain> getChains(){
		return this.chains;
	}
}
