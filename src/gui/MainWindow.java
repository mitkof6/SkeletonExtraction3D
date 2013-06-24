package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import animation.Animator;

import main.Main;

/**
 * Main Window
 * 
 * @author Jim Stenev
 */
public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public MainWindow() {
		initComponents();
	}

	/**
	 * Initializer
	 */
	private void initComponents() {

		jSeparator2 = new javax.swing.JSeparator();
		pathTF = new javax.swing.JTextField();
		loadButton = new javax.swing.JButton();
		pathLabel = new javax.swing.JLabel();
		skeletonExtractionButton = new javax.swing.JButton();
		viewButton = new javax.swing.JButton();
		pushingFactorSlider = new javax.swing.JSlider();
		pushingFactorLabel = new javax.swing.JLabel();
		iterationSlider = new javax.swing.JSlider();
		itetationLabel = new javax.swing.JLabel();
		samplingSlider = new javax.swing.JSlider();
		stepSlider = new javax.swing.JSlider();
		stepLabel = new javax.swing.JLabel();
		samplingLabel = new javax.swing.JLabel();
		distanceToleranceSlider = new javax.swing.JSlider();
		distanceToleranceLabel = new javax.swing.JLabel();
		mergeToleranceSlider = new javax.swing.JSlider();
		mergeToleranceLabel = new javax.swing.JLabel();
		skinDependencesSlider = new javax.swing.JSlider();
		chainSizeToleranceLabel = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		skinDependencesLabel = new javax.swing.JLabel();
		chainSizeToleranceSlider = new javax.swing.JSlider();
		animationButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("3D Skeleton Extraction/Animation");
		setBounds(new java.awt.Rectangle(0, 0, 0, 0));
		setResizable(false);

		pathTF.setText("obj/cube.obj");

		loadButton.setText("Load Model");
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadButtonActionPerformed(arg0);
			}
		});
		
		pathLabel.setText("Path:");

		skeletonExtractionButton.setText("Skeleton Extraction");
		skeletonExtractionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				skeletonExtractionButtonActionPerformed(evt);
			}
		});
		skeletonExtractionButton.setEnabled(false);

		viewButton.setText("View Skeleton");
		viewButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				viewButtonActionPerformed(evt);
			}
		});
		viewButton.setEnabled(false);

		pushingFactorSlider.setMajorTickSpacing(1);
		pushingFactorSlider.setMaximum(10);
		pushingFactorSlider.setMinorTickSpacing(1);
		pushingFactorSlider.setPaintLabels(true);
		pushingFactorSlider.setPaintTicks(true);
		pushingFactorSlider.setSnapToTicks(true);
		pushingFactorSlider.setValue(1);

		pushingFactorLabel.setText("PUSHF");
		pushingFactorLabel.setToolTipText("pushing factor");

		iterationSlider.setMajorTickSpacing(50);
		iterationSlider.setMaximum(400);
		iterationSlider.setMinorTickSpacing(50);
		iterationSlider.setPaintLabels(true);
		iterationSlider.setPaintTicks(true);
		iterationSlider.setSnapToTicks(true);

		itetationLabel.setText("ITER");
		itetationLabel.setToolTipText("iterations");

		samplingSlider.setMajorTickSpacing(10);
		samplingSlider.setMinorTickSpacing(5);
		samplingSlider.setPaintLabels(true);
		samplingSlider.setPaintTicks(true);
		samplingSlider.setSnapToTicks(true);
		samplingSlider.setValue(10);

		stepSlider.setMajorTickSpacing(2);
		stepSlider.setMaximum(20);
		stepSlider.setMinorTickSpacing(2);
		stepSlider.setPaintLabels(true);
		stepSlider.setPaintTicks(true);
		stepSlider.setSnapToTicks(true);
		stepSlider.setValue(10);

		stepLabel.setText("STEP");
		stepLabel.setToolTipText("step");

		samplingLabel.setText("SAMP");
		samplingLabel.setToolTipText("sampling");

		distanceToleranceSlider.setMajorTickSpacing(10);
		distanceToleranceSlider.setMinorTickSpacing(2);
		distanceToleranceSlider.setPaintLabels(true);
		distanceToleranceSlider.setPaintTicks(true);
		distanceToleranceSlider.setSnapToTicks(true);
		distanceToleranceSlider.setToolTipText("");
		distanceToleranceSlider.setValue(2);

		distanceToleranceLabel.setText("DT");
		distanceToleranceLabel.setToolTipText("distance tolerance");

		mergeToleranceSlider.setMajorTickSpacing(10);
		mergeToleranceSlider.setMinorTickSpacing(5);
		mergeToleranceSlider.setPaintLabels(true);
		mergeToleranceSlider.setPaintTicks(true);
		mergeToleranceSlider.setSnapToTicks(true);
		mergeToleranceSlider.setToolTipText("");
		mergeToleranceSlider.setValue(5);

		mergeToleranceLabel.setText("MT");
		mergeToleranceLabel.setToolTipText("merge tolerance");

		skinDependencesSlider.setMajorTickSpacing(1);
		skinDependencesSlider.setMaximum(10);
		skinDependencesSlider.setMinorTickSpacing(1);
		skinDependencesSlider.setPaintLabels(true);
		skinDependencesSlider.setPaintTicks(true);
		skinDependencesSlider.setSnapToTicks(true);
		skinDependencesSlider.setToolTipText("");
		skinDependencesSlider.setValue(2);

		chainSizeToleranceLabel.setText("CHAINS");
		chainSizeToleranceLabel.setToolTipText("chain size tolerance");

		skinDependencesLabel.setText("SKIND");
		skinDependencesLabel.setToolTipText("skin dependence");

		chainSizeToleranceSlider.setMajorTickSpacing(1);
		chainSizeToleranceSlider.setMaximum(10);
		chainSizeToleranceSlider.setMinorTickSpacing(1);
		chainSizeToleranceSlider.setPaintLabels(true);
		chainSizeToleranceSlider.setPaintTicks(true);
		chainSizeToleranceSlider.setSnapToTicks(true);
		chainSizeToleranceSlider.setToolTipText("");
		chainSizeToleranceSlider.setValue(3);

		animationButton.setText("Animate");
		animationButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				animationButtonActionPerformed(evt);
			}
		});
		animationButton.setEnabled(false);


		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	       getContentPane().setLayout(layout);
	       layout.setHorizontalGroup(
	           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	           .addComponent(jSeparator2)
	           .addComponent(jSeparator1)
	           .addGroup(layout.createSequentialGroup()
	               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                   .addGroup(layout.createSequentialGroup()
	                       .addGap(22, 22, 22)
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                           .addComponent(pushingFactorLabel)
	                           .addComponent(itetationLabel)
	                           .addComponent(stepLabel)
	                           .addComponent(samplingLabel)
	                           .addComponent(distanceToleranceLabel)
	                           .addComponent(mergeToleranceLabel)
	                           .addComponent(chainSizeToleranceLabel))
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                           .addComponent(stepSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                           .addComponent(samplingSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                           .addComponent(iterationSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                           .addComponent(pushingFactorSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                           .addComponent(distanceToleranceSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                           .addComponent(mergeToleranceSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                           .addComponent(chainSizeToleranceSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                       .addGap(18, 18, 18)
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                           .addComponent(skeletonExtractionButton, javax.swing.GroupLayout.Alignment.TRAILING)
	                           .addComponent(viewButton, javax.swing.GroupLayout.Alignment.TRAILING)))
	                   .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                       .addGroup(layout.createSequentialGroup()
	                           .addGap(20, 20, 20)
	                           .addComponent(pathLabel)
	                           .addGap(18, 18, 18)
	                           .addComponent(pathTF, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
	                           .addGap(18, 18, 18)
	                           .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
	                           .addGap(0, 0, Short.MAX_VALUE))
	                       .addGroup(layout.createSequentialGroup()
	                           .addGap(23, 23, 23)
	                           .addComponent(skinDependencesLabel)
	                           .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                           .addComponent(skinDependencesSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
	                           .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                           .addComponent(animationButton))))
	               .addContainerGap())
	       );
	       layout.setVerticalGroup(
	           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	           .addGroup(layout.createSequentialGroup()
	               .addContainerGap(15, Short.MAX_VALUE)
	               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                   .addGroup(layout.createSequentialGroup()
	                       .addComponent(skeletonExtractionButton)
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                           .addComponent(viewButton)
	                           .addComponent(chainSizeToleranceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
	                   .addGroup(layout.createSequentialGroup()
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                           .addComponent(pathTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                           .addComponent(loadButton)
	                           .addComponent(pathLabel))
	                       .addGap(18, 18, 18)
	                       .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                           .addGroup(layout.createSequentialGroup()
	                               .addComponent(pushingFactorLabel)
	                               .addGap(37, 37, 37)
	                               .addComponent(itetationLabel))
	                           .addGroup(layout.createSequentialGroup()
	                               .addComponent(pushingFactorSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                               .addComponent(iterationSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                           .addComponent(stepSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                           .addComponent(stepLabel))
	                       .addGap(13, 13, 13)
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                           .addComponent(samplingSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                           .addComponent(samplingLabel))
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                           .addComponent(distanceToleranceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                           .addComponent(distanceToleranceLabel))
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                           .addComponent(mergeToleranceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                           .addComponent(mergeToleranceLabel))
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                       .addComponent(chainSizeToleranceLabel)
	                       .addGap(31, 31, 31)))
	               .addGap(5, 5, 5)
	               .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	               .addGap(18, 18, 18)
	               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                   .addComponent(skinDependencesLabel)
	                   .addComponent(skinDependencesSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                   .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                       .addComponent(animationButton)
	                       .addContainerGap())))
	       );
	       
		pack();
	}                      

	/**
	 * Load button function
	 * 
	 * @param event the event
	 */
	private void loadButtonActionPerformed(java.awt.event.ActionEvent event) {                                           
		Main.loadModel(this.pathTF.getText());
		skeletonExtractionButton.setEnabled(true);
    } 
	
	/**
	 * Skeleton extraction button function
	 * 
	 * @param event the event
	 */
	private void skeletonExtractionButtonActionPerformed(java.awt.event.ActionEvent event) {
		Main.root = null;
		Main.chains = null;
		Main.PUSHING_FACTOR = pushingFactorSlider.getValue();
		Main.ITERATIONS = iterationSlider.getValue();
		Main.STEP = stepSlider.getValue();
		Main.SAMPLING = samplingSlider.getValue();
		Main.DISTANCE_TOLERANCE = distanceToleranceSlider.getValue();
		Main.MERGE_TOLERANCE = mergeToleranceSlider.getValue();
		Main.CHAIN_SIZE_TOLERANCE = chainSizeToleranceSlider.getValue();
		
		Main.correctFacesNormal();
		Main.skeletonExtraction();
		Main.refineSkeleton();
		Main.generateSkeletonSystem();
		viewButton.setEnabled(true);
		animationButton.setEnabled(true);
	}                                                        

	/**
	 * Model viewer button function
	 * 
	 * @param event the event
	 */
	private void viewButtonActionPerformed(java.awt.event.ActionEvent event) {                                           
		ModelViewer modelViewer = new ModelViewer();
		modelViewer.setVisible(true);
	}                                          

	/**
	 * Animation button function
	 * 
	 * @param event the event
	 */
	private void animationButtonActionPerformed(java.awt.event.ActionEvent event) {
		Main.SKIN_DEPENDENCIES = skinDependencesSlider.getValue();
		Main.printAnimationKeyInfo();
		
		Animator viewer3D = new Animator();
		viewer3D.setVisible(true);
	}                                               
                    
	private javax.swing.JButton animationButton;
	private javax.swing.JLabel chainSizeToleranceLabel;
	private javax.swing.JSlider chainSizeToleranceSlider;
	private javax.swing.JLabel distanceToleranceLabel;
	private javax.swing.JSlider distanceToleranceSlider;
	private javax.swing.JLabel itetationLabel;
	private javax.swing.JSlider iterationSlider;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JButton loadButton;
	private javax.swing.JLabel mergeToleranceLabel;
	private javax.swing.JSlider mergeToleranceSlider;
	private javax.swing.JLabel pathLabel;
	private javax.swing.JTextField pathTF;
	private javax.swing.JLabel pushingFactorLabel;
	private javax.swing.JSlider pushingFactorSlider;
	private javax.swing.JLabel samplingLabel;
	private javax.swing.JSlider samplingSlider;
	private javax.swing.JButton skeletonExtractionButton;
	private javax.swing.JLabel skinDependencesLabel;
	private javax.swing.JSlider skinDependencesSlider;
	private javax.swing.JLabel stepLabel;
	private javax.swing.JSlider stepSlider;
	private javax.swing.JButton viewButton;               
}