package skeleton;

/**
 * Some static bone functions used in the project
 * 
 * @author Jim Stanev
 */
public final class BoneFunction {

	/**
	 * Finds a bone pointer by its name
	 * 
	 * @param bone the bone to check (give root to start)
	 * @param name the name of the bone to find
	 * @return null if not found else the bone
	 */
	public static Bone findByName(Bone bone, int name){
		if(bone==null) return null;
		
		if(bone.getName()==name) return bone;
		
		for(Bone child: bone.getChild()){
			Bone result = findByName(child, name);
			if(result!=null) return result;
		}
		
		return null;
	}
	
	/**
	 * Prints the bone system
	 * 
	 * @param bone the bone (give root to start)
	 * @param level the level of the tree (give 1 to start)
	 */
	public static void printBoneSystem(Bone bone, int level){
		if(bone==null) throw new NullPointerException("BoneFunction: null bone");
		
		for(int i = 0;i<level;i++){
			System.out.print("#");
		}
		
		System.out.println("name: "+bone.getName()+
						" angle: "+bone.getAngle()+
						"rotAxis: "+
						bone.getRotationAxis().getX()+" "+
						bone.getRotationAxis().getY()+" "+
						bone.getRotationAxis().getZ());
		
		for(Bone child: bone.getChild()){
			printBoneSystem(child, level+1);
		}
	}
}
