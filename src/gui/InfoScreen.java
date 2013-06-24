package gui;

import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.Main;

/**
 * The info screen redirects System.out/err to 
 * text area redirection object
 * 
 * @author Jim Stanev
 *
 */
public class InfoScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfoScreen(){
		super("Info Screen");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(900, 0, Main.WIDTH, Main.HEIGHT);
		
		//text area
		JTextArea textArea = new JTextArea(50, 40);
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
