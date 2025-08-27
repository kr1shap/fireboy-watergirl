/* 
 * Name: Krisha Patel
 * Date: Dec 20, 2023 
 * Purpose: Static methods to be used in any class, or any images to be used by any class 
 * ICS4U
 */


package game;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner; 
public class StaticMethods {
	/* METHODS */ 
	
	/* Purpose: Method gets map from text file 
	 * Pre: 2 Parameters, 2D Array map, String level which is the file name 
	 * Post: N/A */
	public static void getMap(char [][]map, String level) { 
		/* 0 - empty space
		 * 1 - wall 
		 * 2 - button 
		 * 3 - door 
		 * 4 - diamond 
		 * 5 - fire door 
		 * 6 - water door 
		 * 7 - lava pool 
		 * 8 - water pool 
		 * 9 - poison pool
		 * * - teleportation device */
		String s; //string holds the line read from the file 
		//Surround with try and catch method 
		try { 
		Scanner in = new Scanner (new File (level));  //create a scanner to read my own maze file 
		//For loop stores values for each coordinate in the maze 
		for(int i = 0; i < 33; i++){
			s=in.nextLine();
			for(int j = 0; j < 33; j++){
				map[i][j] = s.charAt(j); //Character.getNumericValue( 
			}
		} 
		}
		catch(IOException e) { 	
		}
	}
	
	/* Purpose: Method sets buttons to transparent 
	 * Pre: 1 parameter, JButton button to set transparent 
	 * Post: N/A  */
	public static void transparent(JButton button) { 
		button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
	}

	/* Purpose: Method loads image  
	 * Pre: 3 parameters, String name of file, int width and height which are dimensions 
	 * Post: Returns image to use  */
	public static Image loadImage(String name, int width, int height) {
		return new ImageIcon(name).getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); 
	}
	
	/* Purpose: Method loads imageIcon  
	 * Pre: 3 parameters, String name of file, int width and height which are dimensions 
	 * Post: Returns imageIcon to use  */
	public static ImageIcon loadImageIcon(String name, int width, int height) {
		return (new ImageIcon(new ImageIcon(name).getImage().getScaledInstance(width,height, java.awt.Image.SCALE_SMOOTH))); 
	}
	
	

}
