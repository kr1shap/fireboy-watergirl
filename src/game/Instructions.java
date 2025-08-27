/* 
 * Name: Krisha Patel 
 * Date: Dec 20, 2023 
 * Purpose: Instructions class for game 
 * ICS4U
 */

package game;
import java.awt.*;
import javax.swing.*; 
import java.awt.event.*;

public class Instructions extends JPanel{
	//Instance variables 
	JButton backB = new JButton(StaticMethods.loadImageIcon("img/back.png",180,45)); //holds the back button 
	Image InsPanel = StaticMethods.loadImage("img/ins.png", 600, 600); //holds the background image of the panel
	public Instructions() {
		super();
		//Set layout of panel to null 
		setLayout(null);
		//Set bounds and add button to panel  
		backB.setBounds(370,450,180,45);
		StaticMethods.transparent(backB); //set button to transparent background by calling method 
		add(backB); 
		
		//Add action listener for back button 
		backB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.navigation.show(Main.panel, "Menu"); //show menu panel if button is clicked
            }
        });
		
	}
	
	/* METHODS */
	/* Purpose: Method paintComponent draws the background 
	 * Pre: Graphics 2D g
	 * Post: N/A   */
	public void paintComponent(Graphics g) {
		 Graphics2D comp2D = (Graphics2D)g; //make graphics 2D object 
	     comp2D.drawImage(InsPanel,0, 0,Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT,this); //draw background 
	}
}
