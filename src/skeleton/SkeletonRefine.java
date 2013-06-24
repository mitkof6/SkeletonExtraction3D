package skeleton;

import java.util.ArrayList;
import java.util.Vector;

import math.geom3d.Point3D;

/**
 * Refines the result of skeleton extraction
 * 
 * @author Jim Stanev
 */
public class SkeletonRefine {

	private final int SAMPLING, CHAIN_SIZE_TOLERANCE;
	private final double DISTANCE_TOLERANCE, MERGE_TOLERANCE;
	
	private Vector<Chain> chains;
	
	/**
	 * Constructor
	 * 
	 * @param chains the chain vector
	 * @param sampling the sampling index
	 * @param distanceTolerance the distance tolerance
	 * @param mergeTolerance the merge tolerance
	 * @param chainSizeTolerance the chain size tolerance
	 */
	public SkeletonRefine(Vector<Chain> chains, int sampling,
			double distanceTolerance, double mergeTolerance, int chainSizeTolerance){
		this.chains = chains;
		this.SAMPLING = sampling;
		this.DISTANCE_TOLERANCE = distanceTolerance;
		this.MERGE_TOLERANCE = mergeTolerance;
		this.CHAIN_SIZE_TOLERANCE = chainSizeTolerance;
	}
	
	/**
	 * Begin method
	 */
	public void beginRefinement(){
		sampling();
		mergeNodes();
		pruning();
	}
	
	/**
	 * Rerafy the chains
	 */
	private void sampling(){
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
	
	/**
	 * Merges nodes
	 */
	private void mergeNodes(){
		for(Chain c: chains){
			for(int i = 0;i<chains.size();i++){
				if(c.getLast().distance(chains.get(i).getLast())<MERGE_TOLERANCE){
					chains.get(i).setLast(c.getLast());
				}
			}
		}
	}
	
	/**
	 * Pruning of small chains
	 */
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
	
	/**
	 * The refined chain vector getter
	 * 
	 * @return the chains
	 */
	public Vector<Chain> getChains(){
		return this.chains;
	}
}
