/* 
 * Name: Krisha Patel 
 * Purpose: Method shows database with names 
 * Date: Jan 11, 2024 
 * ICS4U
 */


package game;
import javax.swing.*; 
import java.awt.*;
import java.io.*;
import java.util.*; 
public class DataBase extends JPanel {
	//Instance variables 
	JFrame frame = new JFrame(); //holds the JFrame (as it is a pop up window)  
	JPanel panel = new JPanel(); 
	String s; //String gets scanner data 

	/* TEXT AREA PANE VARIABLES */
	JTextArea text = new JTextArea(80,300); //textarea holds all the data 
	JScrollPane info = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); //the textarea is added to this scrollpane, so we can scroll through all info 

	public DataBase() {
		super(); 
		//Set settings to frame 
		frame.add(panel); 
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //set to dispose on close, so it will not close the main class as well 
		frame.setSize(600,300); //set size
		frame.setVisible(true); //set visibility to true 
		frame.setLayout(null); //layout to null (coordinates)

		/* SET BOUNDS AND ADD COMPONENTS */ 
		panel.setBackground(new Color(247, 221, 148)); //set background colour 
		panel.setBounds(0,0,600,300); //set bounds to panel 
		panel.setLayout(null); //set layout to null (coordinates)
		//Add scrollpane  
		info.setBounds(50,30,500,200); //set bounds and add to frame 
		panel.add(info); 
		
		/* READ FILE AND GRAB DATA */ 
		//Surround with try and catch method (instead of throws IOException as that has to be added to a main method declaration)  
		try { 
		Scanner in = new Scanner (new File ("FIREBOYWATERGIRL_Names.txt"));  //create a scanner to read the database file 
		//While loop checks if there is a next line 
		while(in.hasNextLine()){
			s=in.nextLine(); //store the line 
			text.append(s + "\n"); //add line to text area 
		} 
		}
		catch(IOException e) { 	
		}
	}
	
}
