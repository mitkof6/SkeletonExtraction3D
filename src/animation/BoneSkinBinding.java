package animation;


import Jama.Matrix;
import skeleton.Bone;

public class BoneSkinBinding {
	
	private Matrix bind;
	private double weight;
	private Bone bone;
	
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
