package animation;

/**
 * This entity represents a key frame
 * 
 * @author Jim Stanev
 */
public class KeyFrame {

	/**
	 * the angle of the bone in this key frame
	 */
	private double angle;
	/**
	 * the time of the key frame
	 */
	private int time;
	
	/**
	 * Constructor
	 * 
	 * @param time the time
	 * @param angle the angle of the bone
	 */
	public KeyFrame(int time, double angle){
		this.time = time;
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}

	public int getTime() {
		return time;
	}
}
