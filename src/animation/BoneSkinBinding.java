package animation;

import org.jeom3d.core.Matrix4;

import skeleton.Bone;

public class BoneSkinBinding {
	
	private Matrix4 bind;
	private double weight;
	private Bone bone;
	
	public BoneSkinBinding(Matrix4 bind, double weight, Bone bone){
		this.bind = bind;
		this.weight = weight;
		this.bone = bone;
	}

	public Matrix4 getBind() {
		return bind;
	}

	public double getWeight() {
		return weight;
	}

	public Bone getBone() {
		return bone;
	}
	
	
}
