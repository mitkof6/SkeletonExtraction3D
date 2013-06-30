SkeletonExtraction3D
====================

Skeleton extruction 3D using Visible Repulsive Force and animation

Author: Jim Stanev

Description
===========

Language: Java

This program opens a obj (Wavefront) format model and extracts its skeleton with visible repulsive force algorithm.
Then it constructs a hierarchy structure of the bone system from the refined skeleton. Also interaction can be made
to create a simple animation, where you interact with the skeleton and the skin of the model moves accordingly with
the movement of the skeleton (linear blend skinning).

Installation
============

Just run the jar file in dist folder.

    java -jar SkeletonExtraction3D.jar
  
If there is a error, it will pop on the info screen. Maybe you need to add the jogl-nativ-[Operation-system].jar 
that corespond to your operating system.

Dependencies
============

Java library dependencies:

** JOGL opengl for Java

** JAMA matrix operation

** Java3D

** JavaGeom
