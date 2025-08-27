package game;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.*;
public class Player {
	//Static variables 
	public static int speed = 5; //holds the speed of player 
	//Instance variables 
	Image Sprite, front, back, runF, runB, jumpF;  //stores all images 
	double x, y; //x and y coordinates of players
	/* BOOLEANS */ 
	boolean isGirl = false; //holds true value if it is watergirl, false if it is fireboy 
	boolean left, right; //boolean stores true/false value if user clicks right/left
	boolean onDoor = false; //boolean holds false if user is colliding with gate (number 5/6 in array) door, true if they are on door
	boolean jump = false; //stores if the player is jumping or not 
	boolean inAir = false; //if the player is in the air or not 
	/* JUMPING */ 
	double airSpeed = 0.0; //set speed in the air to 0 initally 
	//gravity is + value, so will replicate the slow/fast effect 
	double gravity = 0.3; //Stores the gravity of the game (affects physics and velocity of jump) 
	double jumpSpeed = -6; //Stores jump speed, which is negative value as we are going up 
	double fallSpeedAfterCollision = 2; //Stores speed if user collides with something, then set the speed to 2 
	
	
	//Constructor 
	public Player(int x, int y, String type) {
		//Add toolKit to get images 
	    Toolkit kit = Toolkit.getDefaultToolkit();
	    //Set values 
		this.x = x; 
		this.y = y;
		//If water player is created 
		if(type.equalsIgnoreCase("water")) { 
			isGirl = true; //set isGirl value to true 
			//Add image to toolkit, referencing all images and their source folder
			//Cannot use static method, as gifs do not compile properly
		    runF = kit.getImage("img/girlRunF.gif");
		    runB = kit.getImage("img/girlRunB.gif");
		    front =kit.getImage("img/frontGirl.png"); 
		    back = kit.getImage("img/revGirl.png");
		}
		//If fire player is created 
		else { 
			runF = kit.getImage("img/boyRunF.gif");
			runB = kit.getImage("img/boyRunB.gif");
			front = kit.getImage("img/frontBoy.png"); 
			back = kit.getImage("img/revBoy.png"); 
		}
	    Sprite = front; //set sprite to front image by default 
	
	}
	
	/* METHODS */ 
	
	/* UPDATE METHODS */ 
	/* Purpose: Method updates character to implement in run method 
	 * Pre: N/A
	 * Post: N/A, calls methods */
	public void update() {
		//Call teleport and updatePos method 
		teleport();
		updatePos(); 
	}
	
	/* Purpose: Method updates position of player 
	 * Pre: N/A
	 * Post: N/A */
	private void updatePos() { 
		//Sets speed to 0
		double speedX = 0; 
		//If user is jumping, check if they are not in air 
		//This prevents user from jumping twice if key clicked twice  
		if(jump) { 
			if(!inAir) { //if not in air 
				inAir = true; //set inair to true --> used to set the variable to true 
				airSpeed = jumpSpeed; 	//set airspeed to jumpspeed 
			}
		}
		//If user clicks left and not right, subtract from speedX 
		if(left) { 
			Sprite = runB; //change sprite animation 
			speedX -= speed; //set speed x to -value 
		}
		//If user clicks left and not right, add to speedX
		if(right) {
			Sprite = runF;  //change sprite animation 
			speedX += speed; //set speed x to +value 
		}
		
		//If user is not in air (variable set to false)
		if(!inAir) { 
			//Double check if they are not on the floor (as in they fall off platform) 
			if(!IsPlayerOnFloor()) { 
				inAir = true; //set inAir to true 
			}
		} 
		//If user is in the air, check in x and y direction 
		//User can move up and down, left and right 
		if(inAir) { 
			//Check if user can further move up/down
			if(CanMoveHere(x,y+ airSpeed)) { 
				y += airSpeed; //add airspeed to y coordinate 
				airSpeed += gravity; //Increase airspeed (to create gravity effect) 
				updateXPos(speedX); //call method to check if we can move left/right as we can move left/right in the air 
			}
			//If user cannot move further up/down 
			else { 
				//Set Y-coordinate to method called, which gets the possible y coordinate for player
				y = getPosRoofOrFloor(airSpeed); 
				//If airspeed is greater than 0 (going down, and collides with floor) 
				//This is due to the else statement, as we checked if user can move further up or down 
				if(airSpeed > 0) { 
					//Set inAir to false since cant further move up or down 
					inAir = false; //set inAir to false (will go back to above if statement !inAir, and will check if player has collided with floor to re-check ) 
					airSpeed = 0;  //set airSpeed to 0 
				}
				//If we hit the roof instead 
				else 
					airSpeed = fallSpeedAfterCollision; //set airSpeed to speed after collision
				updateXPos(speedX); //Call method to update xPosition, incase they move left and right in the air  
			}
		}
		//If user is not in air, and is on ground  
		else { 
			updateXPos(speedX); //Call method to update xPosition
		}
	}
	
	/* Purpose: Method updates x position 
	 * Pre: 1 parameter, int speedX which is the speed
	 * Post: N/A  */
	public void updateXPos(double speedX) {
		//If user can move their xPosition 
		if(CanMoveHere(x+speedX, y))
			this.x +=speedX; //If yes, then change the speed
		else
			x = GetPlayerNextToWall(speedX); //set x value to the closest value to the wall
	}

	/* CAN PLAYER MOVE METHODS */ 
	
	
	/*  Purpose: Method checks if user can move
	 * Pre: N/A
	 * Post: Returns boolean, true if yes, false if no */
	public boolean CanMoveHere(double x, double y) { 
		//Check if x, y coordinate area is solid 
		if(IsSolid(x,y) == false) {
			if(IsSolid(x+30,y) == false) {//checks both 
				if(IsSolid(x+30,y+36.5) == false) { //checks left 
					if(IsSolid(x,y+36.5) == false) {  //checks bottom 
						return true;
					}
				}
			}
		}	
		//Return false if player can't move here 
		return false; 
	}
	
	
	/* Purpose: Method checks if a tileblock is solid or not 
	 * Pre: double x, double y of user coordinates 
	 * Post: Returns true if it is solid, false if not */
	public boolean IsSolid(double x, double y) { 
		//Variables 
		char value = getValue(x,y); //call method to get char value of tile 
		//Store value 
		if(value == '5' && isGirl == false)
			onDoor = true; 
		if(value == '6' && isGirl == true)
			onDoor = true; 
		//Check if value is not 0 (no wall) or 5/6 (exit doors) or 2 (button) or 3 (door) or 4 (diamond)  
		if(value != '0' && value != '5' && value != '6' && value != '2' && value!= '3' && value != '4') { //return true if solid 
			onDoor = false; 
			return true;
		} 
		else { 
			return false; 
		}	
	}
	

	/* JUMPING METHODS */ 
	
	/* GET METHODS */ 
	
	/* Purpose: Method gets tile value of player 
	 * Pre: 2 parameters, double x/y of x-coordinate and y-coordinate of player 
	 * Post: Returns char value, which is the tile number of the map */
	private char getValue(double x, double y) { 
		//Variables 
		double xIndex = (x)/Main.TILE_SIZE; //index of tile calculated by player position / tile size  
		double yIndex = (y)/ Main.TILE_SIZE ; //index of tile calculated by player position / tile size
		char value = GameWindow.map[(int)yIndex][(int)xIndex]; //find value of tile 
		return value; 
	}
	
	/* COLLISION METHODS */ 
			
	/* Purpose: Method checks if player is on button  
	 * Pre: N/A
	 * Post: Returns boolean value, which says true if they are on button or false if not */
	public boolean onButton() { 
		//Variables 
		char value = getValue(x+4, y +36.5); //Add 4 pixels for hitbox, as well as 36 pixels for bottom corner 
		char value2 = getValue(x+25,y+36.5); //Add 25 pixels for hitbox/right corner, as well as 36 pixels for bottom corner 
		//Check if they are on button 
		if(value == '2' || value2 == '2') { 
			return true; 
		}
		else 
			return false; 
		
	}
	
	/* Purpose: Method teleports player if they step on teleportation device  
	 * Pre: N/A 
	 * Post: N/A, change player coordinates */
	private void teleport() { 
		//Variables 
		char value = getValue(x+4, y +36.5 + 18); //Add 4 pixels for hitbox, as well as 36 pixels for bottom corner 
		char value2 = getValue(x+25,y+36.5 + 18); //Add 25 pixels for hitbox/right corner (25 due to more precision), as well as 36 pixels for bottom corner 
		if(value == '*'|| value2 == '*') { 
			x = 170; //change user coordinates 
			y = 50; 
		}
	}
	/* Purpose: Method checks if player is in the lava/poison/water pool 
	 * Pre: N.A
	 * Post: Returns boolean true if in pool, false if not */
	public boolean playerInPool() { 
		//Variables 
		char value = getValue(x+4, y +36.5 + 18);  //Add 4 pixels for hitbox, as well as 18 pixels to get the next tile
		char value2 = getValue(x+25,y+36.5 + 18);  //Add 25 pixels for hitbox/right corner, as well as 18 pixels to get the next tile
		//If its watergirl 
		if(isGirl) {
			//Check if the values is the fire/poison pool 
			if(value == '7' || value == '9' || value2 == '7' || value2 == '9') 
				return true;
		}
		//If its fireboy 
		else { 
			//Check if the values if it is the water/poison pool 
			if(value == '8' || value == '9'  || value2 == '8' || value2 == '9') 
				return true; 
		}
		return false; //return false if not in pool 
	}
	
	
	/* Purpose: Method checks if player is on the floor or not 
	 * Pre: N/A
	 * Post: Returns true/false boolean if they are on the floor or not */
	public boolean IsPlayerOnFloor() {
		//Check bottom left and bottom right corners 
		if(!IsSolid(x,y + 45)) 
			if(!IsSolid(x+30,y+45))
				return false; 
		return true; 
	}

	/* Purpose: Method checks if character collided with monster 
	 * Pre: 2 parameters, int xM, int yM which are the coordinate of the monster
	 * Post: Returns boolean, true or false   */
	public boolean collisionMonster(int xM, int yM) { 
		if((x + 30>= xM && x <= xM+ 15) &&  (yM +15 >= y && yM <= y+ 40)) {
			return true; 
		}
		else 
			return false; 
	}
	
	/* Purpose: Method checks if character collided with door (the one controlled by button) 
	 * Pre: N/A
	 * Post: N/A, changes coordinate  */
	public void collideDoor() {
		//Variables 
		double prevX = x-10; //holds the previous x value if coming from left side 
		double prevX2 = x+8; //holds previous x value if coming from right side
		//Get tile values by calling methods 
		char value1 = getValue(x+4, y +36.5); 
		char value2 = getValue(x+25,y+36.5);
		//Check if tile value is a door, if yes, then change x value 
		if(value2 == '3') { //coming from left side 
			x = prevX;
		}
		if(value1 == '3') { //coming from right side 
			x = prevX2;
		}
	}
	
	/*  Purpose: Get x value closes
	 * t to the wall, so hitbox is hitting the wall 
	 * Pre: 1 parameter, double xSpeed which is the speed of the user 
	 * Post: Returns double value, which is the new xValue of user */
	private double GetPlayerNextToWall(double xSpeed) { 
		//Get current tile of player by dividing xValue by tile size (based on array) 
		int currentTile = (int)(x/Main.TILE_SIZE); 
		//If user wants to go to the right 
		if(xSpeed > 0) { 
			//Find the xCoordinate of the tile bu multiplying current tile num by tile size
			int tileXPos = currentTile * (int)Main.TILE_SIZE; 
			//Get the offset value, which is the distance between character and tile
			int xOffSet = -(int)(Main.TILE_SIZE - 28); //Add negative sign because answer will turn out to be negative value due to hitbox 
			return tileXPos + xOffSet - 1; //subtract by 1 so we hit the very edge, not overlap 
		}
		//If user wants to go to the left 
		else { 
			return currentTile* Main.TILE_SIZE + 4; //only need to return the coordinate of currentTile
		}
	}
	 
	/* Purpose: Method gets the possible y position, if they are under the roof or above the floor 
	 * Pre: 1 parameter, double airSpeed 
	 * Post: Returns double value, which is the new yValue of user  */
	private double getPosRoofOrFloor(double airSpeed) {
		//Get current tile of player by dividing yValue by tile size (based on array) 
		int currentTile = (int)(y/Main.TILE_SIZE); 
		if(airSpeed > 0) { 
			//If user is falling down 
			int tileYPos = currentTile * (int)Main.TILE_SIZE; 
			//Get the offset value, which is the distance between character and tile
			int yOffSet = -(int)(Main.TILE_SIZE - 40); //Add negative sign because answer will turn out to be negative due to hitbox
			return tileYPos + yOffSet - 1; //subtract by 1 so we hit the very edge, not overlap 
		}
		//If user is touching the roof 
		else { 
			return currentTile* Main.TILE_SIZE;  //only need to return the coordinate of currentTile
		}
	}
}
