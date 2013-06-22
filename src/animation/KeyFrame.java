package animation;

public class KeyFrame {

	private double angle;
	private int time;
	
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
