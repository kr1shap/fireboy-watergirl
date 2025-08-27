/*
 * Name: Krisha Patel 
 * Date: Dec 21, 2023 
 * Purpose: Method will create monsters to avoid 
 * ICS4U
 */

package game;
import java.awt.*;
import javax.swing.*;
public class Monster {
	//Instance variables 
	int x, y; //holds x/y coordinate of monster 
	int ySpeed = 3; //y speed of monster
	int xSpeed = 5; //x speed of monster 
	Image Sprite = StaticMethods.loadImage("img/monStill.png", 15, 15); //holds image of monster sprite 

	//Constructor 
	public Monster() { 
		//Randomize x/y coordinates 
		this.x = (int)(Math.random()*(300-90+1)+90);
		this.y = (int)(Math.random()*(300-30+1)+30); 
	}
	
	
	/* METHODS */ 
	
	/* Purpose: Method updates monster movement by checking collision and changing speed  
	 * Pre: N/A 
	 * Post: N/A, executes statements  */
	public void update() { 
		/* MONSTER COLLISION */
		//Multiply x/y speed by -1 each time to change direction  
		//If x is to the right or left wall 
		if(x > 582-15 || x < 18)
			xSpeed *= -1; 
		//If y is to the down or up wall 
		else if(y > 582-15 || y < 18) 
			ySpeed *= -1;
		/* CHANGE COORDINATE OF MONSTER/MOVE IT*/ 
		x+= xSpeed;
		y+= ySpeed; 
	}

	
	
}
