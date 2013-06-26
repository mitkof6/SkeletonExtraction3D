package animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import Jama.Matrix;

import main.Main;
import skeleton.Bone;
import skeleton.Node;

/**
 * Some static accessed animation functions used in the project
 * 
 * @author Jim Stanev
 */
public class AnimationFunction {
	
	/**
	 * used to find minimum distanced bones
	 */
	private static HashMap<Double, Bone> weightedBones;

	/**
	 * Records a key frame with the current position of the
	 * bone system to every bone
	 * 
	 * @param bone the bone
	 * @param time the time index of the key frame
	 */
	public static void recordKeyFrame(Bone bone, int time){
		
		if(bone==null) throw new NullPointerException("Animation: bone is null");
		
		bone.addToKeyFrames(new KeyFrame(time, bone.getAngle()));
		System.out.println("Time: "+time+" bone: "+bone.getName()+" angle: "+bone.getAngle());
		for(Bone child: bone.getChild()){
			recordKeyFrame(child, time);
		}
	}

	/**
	 * Uses linear interpolation between kay frames
	 * 
	 * @param bone the bone 
	 * @param time the time
	 */
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
	
	/**
	 * Initializes the skin-bone relation
	 * 
	 * @param mesh the mesh object
	 * @param dependencies the maximum number of attached bones to a node in the skin
	 */
	public static void initializeSkinBoneRelation(Mesh mesh, int dependencies){
		
		Vector<Bone> bones = new Vector<>();
		getBones(mesh.getRoot(), bones);
		

		for(Node v: mesh.getVertices()){
			
			weightedBones = new HashMap<>();
			
			for(int i = 0;i<bones.size();i++){
				double dist = bones.get(i).getInitPosition().distance(v.getInitialPositioln());
				weightedBones.put(dist, bones.get(i));
			}
			
			List<Double> distance = new ArrayList<Double>(weightedBones.keySet());
			Collections.sort(distance);
			
			for(int i = 0;i<dependencies;i++){
				Bone b = weightedBones.get(distance.get(i));
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
	
	/**
	 * Used by initializeSkinBoneRelation to get a vector with the bones
	 * 
	 * @param bone the bone (give root to start)
	 * @param bones the vector with the bones returned (must be initialized)
	 */
	private static void getBones(Bone bone, Vector<Bone> bones){
		bones.add(bone);
		for(int i = 0;i<bone.getChild().size();i++){
			bones.add(bone.getChild().get(i));
			getBones(bone.getChild().get(i), bones);
		}
	}

	/**
	 * Used by initializeSkinBoneRelation to generate the binding matrix
	 * 
	 * @param v the node
	 * @param b the attached bone
	 * @return the binding matrix
	 */
	private static Matrix getBindingMatrix(Node v, Bone b){

		double thi = 0;
		double dx = v.getInitialPositioln().getX() - b.getInitPosition().getX();
		double dy = v.getInitialPositioln().getY() - b.getInitPosition().getY();
		double dz = v.getInitialPositioln().getZ() - b.getInitPosition().getZ();
		double[][] array = {{Math.cos(thi), Math.sin(thi), 0, dx},
						{-Math.sin(thi), Math.cos(thi), 0, dy},
						{0, 0, 1, dz},
						{0, 0, 0, 1}};

		//System.out.println(Arrays.deepToString(array));
		return new Matrix(array);
	}

	/**
	 * Sets the initial pose
	 * 
	 * @return the new initialized mesh
	 */
	public static Mesh getInitialPose(){
		resetBoneAngle(Main.root);
		return new Mesh(Main.vertices, Main.faces, Main.root);
	}
	
	/**
	 * Used to initialize the angles of the bone system
	 * @param bone
	 */
	private static void resetBoneAngle(Bone bone){
		bone.resetAngle();
		
		for(Bone child: bone.getChild()){
			resetBoneAngle(child);
		}
	}
	
}
