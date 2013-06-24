package animation;


import Jama.Matrix;
import skeleton.Bone;

/**
 * This entity represents the binding between a bone and a node and
 * its weight of influence
 * 
 * @author jimst_000
 *
 */
public class BoneSkinBinding {
	
	private Matrix bind;
	private double weight;
	private Bone bone;
	
	/**
	 * Constructor
	 * 
	 * @param bind the binding matrix
	 * @param weight the influence of the bone
	 * @param bone the bone
	 */
	public BoneSkinBinding(Matrix bind, double weight, Bone bone){
		this.bind = bind;
		this.weight = weight;
		this.bone = bone;
		
	}

	public Matrix getBind() {
		return bind;
	}

	public double getWeight() {
		return weight;
	}

	public Bone getBone() {
		return bone;
	}
	
	
}
