/* 
 * Name: Krisha Patel
 * Date: Dec 30, 2023 
 * Purpose: Class serves as the legend page of the game 
 * ICS4U
 */


package game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Legend extends JPanel{
	//Instance variables 
	JButton backB = new JButton(StaticMethods.loadImageIcon("img/back.png",180,45)); //holds the back button  
	Image legPanel = StaticMethods.loadImage("img/legend.png", 600, 600); //holds the background image of the panel
	public Legend() {
		super();
		//Set layout of panel to null 
		setLayout(null);
		//Set bounds and add button 
		backB.setBounds(360,520,180,45);
		StaticMethods.transparent(backB); //set button to transparent background by calling method 
		add(backB); 
		
		//Add action listener for back button 
		backB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.navigation.show(Main.panel, "Menu"); //show menu panel if clicked
            }
        });
		
	}
	
	/* METHODS */
	/* Purpose: Method paintComponent draws the background 
	 * Pre: Graphics 2D g
	 * Post: N/A   */
	public void paintComponent(Graphics g) {
		 Graphics2D comp2D = (Graphics2D)g; //make graphics 2D object 
	     comp2D.drawImage(legPanel,0, 0,Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT,this); //draw background 

	}
}
