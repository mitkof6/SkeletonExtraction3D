package main;

import java.io.FileNotFoundException;
import java.util.Vector;

import animation.Animator;

import skeleton.Bone;
import skeleton.Chain;
import skeleton.Node;
import skeleton.SkeletonExtraction;
import skeleton.SkeletonRefine;
import skeleton.SkeletonSystemGeneration;

import math.Triangle;

import export.Load3D;
import gui.InfoScreen;

public class Main {

	public static int WIDTH = 700, HEIGHT = 700;
	private static Load3D obj3D;
	public static Vector<Triangle> triangles;
	public static Vector<Node<Triangle>> vertices;
	public static Vector<Chain> chains;
	public static Bone root;
	
	public static int PUSHING_FACTOR = 1;
	public static int ITERATIONS = 40;
	public static int STEP = 10;
	public static int SAMPLING = 6;
	public static double DISTANCE_TOLERANCE = 3;
	public static double MERGE_TOLERANCE = 2;
	public static int CHAIN_SIZE_TOLERANCE = 3;
	public static int SKIN_DEPENDENCIES = 2;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//window
		InfoScreen mainWindow = new InfoScreen();
		mainWindow.setVisible(true);
		
		//load obj file
		loadModel("obj/cube.obj");
		//b66_L2.obj
		//cube.obj
		
		//generate skeleton
		generateSkeleton();
		
		//refine skeleton
		refineSkeleton();
		
		//generate skeleton system
		//generateSkeletonSystem();
		
		//show model
		Animator viewer3D = new Animator();
		viewer3D.setVisible(true);
	}
	
	private static void loadModel(String path){
		
		try {
			obj3D = new Load3D(path);
			triangles = obj3D.getTriangles();
			vertices = obj3D.getVertices();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		System.out.println("Model loaded: triangles-"+triangles.size()+
				" vertices-"+vertices.size());
	}
	
	private static void generateSkeleton(){
		
		SkeletonExtraction skeletonExtraction = new SkeletonExtraction(triangles, PUSHING_FACTOR,
				ITERATIONS, STEP);
		
		long start = System.currentTimeMillis();    
		skeletonExtraction.getVDS(vertices);
		long elapsedTime = System.currentTimeMillis() - start;
		System.out.println("Time: "+elapsedTime+"ms");
		
		chains = skeletonExtraction.getChain();
		System.out.println("Skeleton generated: "+chains.size());
	}
	
	private static void refineSkeleton(){
		
		SkeletonRefine skeletonRefine = new SkeletonRefine(chains, SAMPLING,
				DISTANCE_TOLERANCE, MERGE_TOLERANCE, CHAIN_SIZE_TOLERANCE);
		
		skeletonRefine.beginRefinement();
		chains = skeletonRefine.getChains();
	}
	
	private static void generateSkeletonSystem(){
		SkeletonSystemGeneration skeletonSystem = new SkeletonSystemGeneration(chains);
		skeletonSystem.beginGeneration();
		
		root = skeletonSystem.getRoot();
	}

}
