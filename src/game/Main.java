/* 
 * Name: Krisha Patel
 * Date: Dec 17, 2023 
 * Purpose: Main method for CPT game, which holds card layout  
 * ICS4U
 */

package game;
import javax.swing.*;
import java.awt.*; 
public class Main {
	//Static variables 
	/* DIMENSIONS */
	public static final int WINDOW_WIDTH = 600; //holds width of window screen  
	public static final int WINDOW_HEIGHT = 600; //height of window screen 
	public static final double TILE_SIZE = 18.333333333333333; //tile size (length/width) of each tile in 2D array for the map 
	/* GAME STATE/LEVEL*/ 
	public static int level = 1; //stores the level of the game (default 1) 
	public static int gameState = 0; //0 for user not being in game, 1 for in game 
	public static boolean paused = false; //boolean stores if the game is paused or not 
	public static final CardLayout navigation = new CardLayout(); //holds card layout for panels
	public static JPanel panel; //holds Jpanel 
	public static JFrame frame; //holds Jframe where everything will be added 
	public static Font lacquer = new Font("Lacquer", Font.PLAIN, 30); //set a font to use in the program, font is imported into fontbook/fonts file on windows 
	public static Font lacquer2 = new Font("Lacquer", Font.PLAIN, 50); //set a font to use in the program, font is imported into fontbook/fonts file on windows 
	//Main method 
	public static void main(String[] args) {
		//Main settings 
		frame = new JFrame("Fireboy and WaterGirl"); //set name of frame 
        frame.setLayout(new BorderLayout()); //set frame to border layout
		frame.setSize(WINDOW_WIDTH,628); //set size of window (slightly larger due to top part of window (buttons) 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //automatically closes when X is clicked 
		frame.setLocationRelativeTo(null); //frame is always located in centre of screen 
		
		//Declare panel with card layout and add it to jframe 
		panel = new JPanel(navigation); 
		frame.add(panel, BorderLayout.CENTER); 
		
		//Add panels to card navigation 
		panel.add(new MenuPage(), "Menu"); //name the card menu (menu page) 
		panel.add(new Instructions(), "Instructions"); //name the card instructions (instructions page) 
		panel.add(new Legend(), "Legend"); //name the card legend (legend page) 
		panel.add(new WinLose(), "WinLose"); //name the card winLose (win/lose page) 
		//Show navigation     
		navigation.show(panel, "Menu"); //shows the menu screen when game is played 
		frame.setVisible(true); //set frame to visible
		
	}

}
