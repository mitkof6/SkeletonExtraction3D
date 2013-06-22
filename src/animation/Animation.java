package animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.jeom3d.core.Matrix4;

import main.Main;
import math.Triangle;
import skeleton.Bone;
import skeleton.Node;

public class Animation {
	
	private static HashMap<Double, Bone> weightedBones;

	public static void recordKeyFrame(Bone bone, int time){
		
		if(bone==null) throw new NullPointerException("Animation: bone is null");
		
		bone.addToKeyFrames(new KeyFrame(time, bone.getAngle()));
		System.out.println("Time: "+time+" bone: "+bone.getName()+" angle: "+bone.getAngle());
		for(Bone child: bone.getChild()){
			recordKeyFrame(child, time);
		}
	}

	public static void interpolate(Bone bone, int time){

		if(bone==null) return;

		double angle, tim;

		for(int i = 0;i<bone.getKeyFrameSize();i++){
			if(bone.getKeyFrame(i).getTime()==time){
				if(i!=bone.getKeyFrameSize()-1){
					tim = bone.getKeyFrame(i+1).getTime()-
									bone.getKeyFrame(i).getTime();
					angle = bone.getKeyFrame(i+1).getAngle()-
									bone.getKeyFrame(i).getAngle();
					

					bone.setAngleOffset(angle/tim);
				}else{
					bone.setAngleOffset(0);
				}
			}
		}

		bone.setAngle(bone.getAngle()+bone.getAngleOffset());

		for(Bone child: bone.getChild()){
			interpolate(child, time);
		}
	}
	
	public static void initSkinWeights(Mesh mesh, int dependencies){
		
		Vector<Bone> bones = new Vector<>();
		getBones(mesh.getRoot(), bones);
		
		for(Node<Triangle> v: mesh.getVertices()){
			
			weightedBones = new HashMap<>();
			double total = 0;
			
			for(int i = 0;i<bones.size();i++){
				double dist = bones.get(i).getInitPosition().distance(v.getPoint());
				weightedBones.put(1/dist, bones.get(i));
				total += 1/dist;
			}
			
			List<Double> weights = new ArrayList<Double>(weightedBones.keySet());
			Collections.sort(weights);
			
			for(int i = 0;i<dependencies;i++){
				Bone b = weightedBones.get(weights.get(i));
				v.addBoneSkinBinding(new BoneSkinBinding(
								getBindingMatrix(v, b), ((double) weights.get(i))/total, b));
			}
		}
	}
	
	private static void getBones(Bone root, Vector<Bone> bones){
		bones.add(root);
		for(int i = 0;i<root.getChild().size();i++){
			bones.add(root.getChild().get(i));
			getBones(root.getChild().get(i), bones);
		}
	}

	private static Matrix4 getBindingMatrix(Node<Triangle> v, Bone b){
		//Vector2D v1 = new Vector2D(b.getParent().getAbsolutePosition(),b.getAbsolutePosition());
		//Vector2D v2 = new Vector2D(b.getAbsolutePosition(), v.getPoint());
		//double thi = v1.angleBetween(v2);
		//double thi = 0;
		double dx = v.getX() - b.getInitPosition().getX();
		double dy = v.getY() - b.getInitPosition().getY();
		double dz = v.getZ() - b.getInitPosition().getZ();
		//System.out.println("Thi: "+Math.toDegrees(thi));
		double[] m = {
						1, 0, 0, 0,
						0, 1, 0, 0,
						0, 0, 1, 0,
						dx, dy, dz, 1};

		//System.out.println(Arrays.deepToString(array));
		//System.out.println(v.getPoint().toString()+" -> "+b.getAbsolutePosition().toString());
		return new Matrix4(m);
	}

	public static Bone getInitialPose(){
		return Main.root;
	}
	
}
