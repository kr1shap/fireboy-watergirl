/* 
 * Name: Krisha Patel 
 * Date: Dec 21, 2023 
 * Purpose: Class for diamonds which the characters can get 
 * ICS4U 
 */


package game;
import java.awt.*;
public class Diamonds {
	//Static variables 
	public static int dCollected = 0; //number of diamonds collected by player 
	/* IMAGES */ 
	static Image blueD = StaticMethods.loadImage("img/blueDiamond.png",18,18); //stores blue diamond image
	static Image redD = StaticMethods.loadImage("img/redDiamond.png",18,18);  //stores red diamond image
	static Image whiteD =  StaticMethods.loadImage("img/whiteDiamond.png", 18, 18); //stores white diamond image
	//Instance Variables
	Image diamond; //stores image of the diamond for the object  
	boolean isWater; //stores if it is a water diamond or not 
	boolean isNone; //stores if it is a special diamond (white diamond) 
	int x; //x coordinate of diamond
	int y; //y coordinate  of diamond
	boolean isCaught; //checks if diamond has been caught or not 

	//Constructor 
	public Diamonds(String type, int x, int y) { 
		//Set instance variables to x/y input 
		this.x = x; 
		this.y = y; 
		//If the type is water 
		if(type.equalsIgnoreCase("water")) { 
			isWater = true; //set isWater to true (is a water diamond) 
			isNone = false; //it is not a special diamond, so set it to false
			diamond = blueD;  //set image 
		}
		else if(type.equalsIgnoreCase("fire")) { 
			isWater = false; //set isWater to false (is not a water diamond) 
			isNone = false; //it is not a special diamond, so set it to false
			diamond = redD; //set image 
		}
		else if(type.equalsIgnoreCase("none")) { 
			isWater = false; //set isWater to false (is not a water diamond) 
			isNone = true; //it is a special diamond, so set it to true
			diamond = whiteD; //set image 
		}
	}
	
	/* METHODS */ 
	
	/* Purpose: Method checks if diamond has collided with a player 
	 * Pre: 3 parameters, int xP, int yP for x/y coordinates of player , boolean isGirl to check if player can collect the diamond 
	 * Post: N/A, will execute statements if statement is true */
	public void collisionDiamond(double xP, double yP, boolean isGirl) {
		//Check if player intersects with diamond coordinate, if it has been caught already, and if the player can collect it 
		if((x + 18>= xP && x <= xP+ 30) &&  (yP +40 >= y && yP <= y + 16)  && isCaught == false && (isGirl == isWater || isNone == true)) { //statements are executed if player type matches diamond type, or if it is a special diamond 
			isCaught = true; //diamond has been caught 
			diamond = StaticMethods.loadImage("img/transparent.png", 18, 18); //change image to transparent image
			dCollected++; //diamond has been collected 
		}
	}
}
