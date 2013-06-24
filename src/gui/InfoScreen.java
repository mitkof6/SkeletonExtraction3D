package gui;

import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InfoScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfoScreen(){
		super("Info Screen");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.setLayout(new BorderLayout());
		
		//text area
		JTextArea textArea = new JTextArea(100, 60);
		textArea.setEditable(false);
		textArea.setAutoscrolls(true);
		
		//info screen
		JTextAreaRedirecction infoScrean = new JTextAreaRedirecction(textArea);
		System.setOut(new PrintStream(infoScrean));
		System.setErr(new PrintStream(infoScrean));
		
		//add
		//this.add(textArea, BorderLayout.CENTER);
		this.add(new JScrollPane(textArea));
		this.pack();
	}
	
}
