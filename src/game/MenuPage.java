/* 
 * Name: Krisha Patel 
 * Date: Dec 17, 2023
 * Purpose: Class is for menu page, which is the first page that appears  
 * ICS4U
 */

package game;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class MenuPage extends JPanel implements ActionListener{
	//Instance variables 
	Image bkgndMenu = StaticMethods.loadImage("img/mainMenu.png",Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT); //holds the background image of panel 
	/* BUTTONS */ 
	JButton playB = new JButton(StaticMethods.loadImageIcon("img/play.png",180,45)); //holds the play button 
	JButton helpB = new JButton(StaticMethods.loadImageIcon("img/help.png",180,45)); //holds the help button   
	JButton dataB = new JButton(StaticMethods.loadImageIcon("img/database.png", 180, 45));  //holds the legend button  
	JButton legendB = new JButton(StaticMethods.loadImageIcon("img/legend2.png", 180, 45));  //holds the legend button  
	JButton[] buttons = {playB,helpB,dataB,legendB}; //array stores all the buttons for efficiency 
	//Constructor 
	public MenuPage() { 
		super();
		//Set layout to null 
		setLayout(null); 
		//Setbounds and add buttons 
		playB.setBounds(210,250,180,45);
		helpB.setBounds(210,320,180,45);
		legendB.setBounds(210,390,180,45);
		dataB.setBounds(210,460,180,45);
		//For loop goes through arrays of buttons 
		for(int i = 0; i < buttons.length; i++) { 
			StaticMethods.transparent(buttons[i]); //sets to transparent 
			add(buttons[i]); //adds button to panel
			buttons[i].addActionListener(this); //adds action listener 
		}

	}
	
	/* METHODS */ 

	/* Purpose: Method sets the level, game state and creates the game window when the user selects a level 
	 * Pre: 1 parameter, int level which is the level of the game selected
	 * Post: N/A, variables and GameWindow class created/instantized */
	public void setLevelGameState(int level) { 
		Main.level = level; //set level to level input
		Main.gameState = 1; //change gamestate to 1 (currently playing) 
		Main.panel.add(new GameWindow(), "Game"); //add game panel 
		Main.navigation.show(Main.panel, "Game"); //show game panel 
	}
	/* Purpose: Method paintComponent draws the background 
	 * Pre: Graphics 2D g
	 * Post: N/A   */
	public void paintComponent(Graphics g) {
        Graphics2D comp2D = (Graphics2D)g; //make graphics 2D object 
        comp2D.drawImage(bkgndMenu,0, 0,Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT,this); //draw background 
	}

	/* Purpose: Method checks which event has occured (button pressed, etc) 
	 * Pre: 1 parameter, ActionEvent e which is the event that has occured 
	 * Post: N/A, statements execute 
	 */
	public void actionPerformed(ActionEvent e) {
		//If person clicks help button, show instructions 
		if(e.getSource() == helpB)
			Main.navigation.show(Main.panel, "Instructions"); //show help panel 
		//If person clicks legend button, show legend 
		else if(e.getSource() == legendB)
			Main.navigation.show(Main.panel, "Legend"); //show legend panel
		//If person clicks play, show all level options which they can then play 
		else if(e.getSource() == playB) { 
			//Create an array of options 
        	String array[] = {"Level 3","Level 2", "Level 1"};
        	//Create JOptionPane with buttons; user can select a level 
        	int choice = JOptionPane.showOptionDialog(null,"Enter your level", "LEVEL", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null, array, array[2]);
        	//Use cases to see the choice made 
        	switch(choice) { 
        	case 2: //level 1
        		setLevelGameState(1); 
        		break; 
	        case 1: //level 2
        		setLevelGameState(2); 
    			break; 
	        case 0: //level 3 
        		setLevelGameState(3); 
	    		break; 
			}	
		}
		//If person clicks database, open a new database window 
		else if(e.getSource() == dataB) { 
        	DataBase window = new DataBase(); 
		}	
	}
}
