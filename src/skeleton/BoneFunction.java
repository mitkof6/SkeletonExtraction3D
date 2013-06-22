package skeleton;


public final class BoneFunction {

	public static Bone findByName(Bone bone, int name){
		if(bone==null) return null;
		
		if(bone.getName()==name) return bone;
		
		for(Bone child: bone.getChild()){
			Bone result = findByName(child, name);
			if(result!=null) return result;
		}
		
		return null;
	}
	
	public static void printBoneSystem(Bone bone, int level){
		if(bone==null) throw new NullPointerException("BoneFunction: null bone");
		
		for(int i = 0;i<level;i++){
			System.out.print("#");
		}
		
		System.out.println("name: "+bone.getName()+
						" angle: "+bone.getAngle()+
						"rotAxis: "+
						bone.getRotXYZ().getX()+" "+
						bone.getRotXYZ().getY()+" "+
						bone.getRotXYZ().getZ());
		
		for(Bone child: bone.getChild()){
			printBoneSystem(child, level+1);
		}
	}
}
