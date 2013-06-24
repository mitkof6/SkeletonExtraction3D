package animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import Jama.Matrix;

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
			
			for(int i = 0;i<bones.size();i++){
				double dist = bones.get(i).getInitPosition().distance(v.getInitialPositioln());
				weightedBones.put(dist, bones.get(i));
			}
			
			List<Double> distance = new ArrayList<Double>(weightedBones.keySet());
			Collections.sort(distance);
			
			System.out.println("Node: "+v.getInitialPositioln().getX()+" "+v.getInitialPositioln().getY()+" "+v.getInitialPositioln().getZ());
			for(int i = 0;i<dependencies;i++){
				System.out.println("Distance: "+distance.get(i));
				Bone b = weightedBones.get(distance.get(i));
				System.out.println("Bone: "+b.getInitPosition().getX()+" "+b.getInitPosition().getY()+" "+b.getInitPosition().getZ());
				if(distance.get(i)==0){
					v.addBoneSkinBinding(new BoneSkinBinding(
							getBindingMatrix(v, b), 1, b));
					break;
				}else{
					v.addBoneSkinBinding(new BoneSkinBinding(
							getBindingMatrix(v, b), 1.0/dependencies, b));
				}
				
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

	private static Matrix getBindingMatrix(Node<Triangle> v, Bone b){

		double thi = 0;
		double dx = v.getInitialPositioln().getX() - b.getInitPosition().getX();
		double dy = v.getInitialPositioln().getY() - b.getInitPosition().getY();
		double dz = v.getInitialPositioln().getZ() - b.getInitPosition().getZ();
		double[][] array = {{Math.cos(thi), Math.sin(thi), 0, dx},
						{-Math.sin(thi), Math.cos(thi), 0, dy},
						{0, 0, 1, dz},
						{0, 0, 0, 1}};

		System.out.println(Arrays.deepToString(array));
		return new Matrix(array);
	}

	public static Bone getInitialPose(){
		return Main.root;
	}
	
}
