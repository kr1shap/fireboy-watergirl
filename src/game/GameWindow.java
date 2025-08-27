/* 
 * Name: Krisha Patel 
 * Date: Dec 18, 2023 
 * Purpose: Holds Game window (main game) of CPT
 * ICS4U 
 */

package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
public class GameWindow extends JPanel implements Runnable, KeyListener{
	/* VARIABLES */ 
	Thread runner; //thread allows to run game and to catch any errors
	/* PLAYERS */ 
	Player boy; //creates fireboy player object 
	Player girl; //creates watergirl player object 
	/* IMAGES */ 
	Image bkgnd; //stores the background image (the map) 
	/* DOOR */ 
	JLabel door = new JLabel(); //stores the door image
	/* MAP */
	static char [][] map = new char [33][33]; //stores the tile map for collision  
	/* ARRAYS */ 
	ArrayList<Diamonds>diamonds = new ArrayList<Diamonds>(); //stores all the diamond objects  
	ArrayList<Monster>monsters = new ArrayList<Monster>(); //stores all the monster objects  
	/* TIMERS */ 
	Timer timer = new Timer(); //holds the timer object
	static int seconds; //holds the seconds passed 
	JLabel time = new JLabel(""); //label holds time, which is displayed on screen  
	/* PAUSED*/ 
	JLabel paused = new JLabel(StaticMethods.loadImageIcon("img/paused.png", 300, 300));  //stores the paused label 

	
	public GameWindow() {
		//Inherit panel 
		super(); 
		setLayout(null);//set layout to null 
        addKeyListener(this); //add key listener 
        //Check level selected, choose background, set door coordinates, get map  
        if(Main.level == 1) { 
            levelData("img/lvl1.png", "level1.txt", 100, 470, 260, 540); //call level data method  
        	//Set coordinates for door and players, and add door to panel  
        	door = new JLabel(StaticMethods.loadImageIcon("img/door.png",18,90));
            door.setBounds(255,276,20,90);
            add(door); 
            createMonster(1); //call method to create only 1 monster 
        }
        else if(Main.level == 2) { 
            levelData("img/lvl2.png", "level2.txt", 70, 470, 50, 470);  //call level data method  
        	//Set coordinates for door and players, and add door to panel  
        	door = new JLabel(StaticMethods.loadImageIcon("img/door.png",18,55));
            door.setBounds(200,385,20,55);
            add(door);
    		createMonster(2); //call method to create two monsters 
        }
        //Level 3 
        else if(Main.level == 3) { 
            levelData("img/lvl3.png", "level3.txt", 520, 540, 60, 540);  //call level data method  
    		createMonster(3); //call method to create three monsters 
        }
        //Generate diamond
        generateDiamond(); 
        //Create a runner thread 
	    runner = new Thread(this);
	    //Start runner 
        runner.start();	
        
        //Set bounds of timer panel and add to frame 
        time.setBounds(280,-10,100,50);
        time.setFont(Main.lacquer);
        time.setForeground(Color.WHITE); 
        add(time); 
        runTime(); //call runTime method to start the timer      
        //Set pause label 
        paused.setFont(Main.lacquer2);
        paused.setForeground(new Color(194, 158, 60)); //set colour to label 
		paused.setBounds(150,150,300,300);
		add(paused); //add paused to panel 
		paused.setVisible(false); //set to false, as user does not pause when game starts 
     
	}
	
	/* METHODS */ 
	
	/* IMPLEMENTED METHODS */ 
	public void run() {
        Thread thisThread = Thread.currentThread(); //set thread to current thread 
        //While the thread in constructor is equal to the current thread 
        while (runner == thisThread) { 
        	/* IF THE PLAYER IS CURRENTLY IN GAME */
        	if(Main.gameState == 1) { //gamestate 1 = in game 
            	/* IF THE PLAYER HAS NOT PAUSED THE GAME */
        		if(!Main.paused) { 
    	        	//Call update method for players and for monster  
    	        	girl.update(); //helps update position 
    	        	boy.update(); 
    	        	for(int i = 0; i < monsters.size();i++)
    	        		monsters.get(i).update(); //call monster update for each monster
    	        	
    	        	/*CHECK IF USER HAS PRESSED BUTTON */
    	        	if(Main.level != 3) { //buttons are on all levels but level 3
    	        		//If girl or boy is on button 
    	        		if(boy.onButton() || girl.onButton())
    		        		door.setVisible(false); //change visibility of door to false  
    		        	else
    		        		door.setVisible(true); //set door to visible 
    	        		/* CHECK IF USER IS COLLIDING WITH DOOR */ 
    		        	if(door.isVisible()) { 
    		        		girl.collideDoor(); //check collision only if door is visible
    		        		boy.collideDoor();
    		        	}
    	        	}
    	        	//Diamond collision method
    	        	if(diamonds.size() > 0) { //have to check size of array or else will run into an error at times 
    	        		for(int i = 0; i < diamonds.size();i++) {
    	        			//Check for collision for each diamond, and for boy and girl 
    	        			diamonds.get(i).collisionDiamond(girl.x, girl.y, girl.isGirl);
    	        			diamonds.get(i).collisionDiamond(boy.x, boy.y, boy.isGirl);
    	        		}
    	        	}
    	        	/* USER LOST METHODS */ 
    	        	//If user in pool that will kill them 
    	        	if(boy.playerInPool() || girl.playerInPool())
    	        		setStatistics(false); //call method to set up lose screen 
    	        	
    	        	//Use for loop to check if user has collided with monster 
    	        	for(int i = 0; i < monsters.size(); i++) { 
    	        		if(girl.collisionMonster(monsters.get(i).x, monsters.get(i).y) || boy.collisionMonster(monsters.get(i).x, monsters.get(i).y))
    		        		setStatistics(false); //call method to set up lose screen 
    	        	} 	
    	        	/* USER WON METHODS */
    	        	//If user by doors 
    	        	if(boy.onDoor && girl.onDoor) { 
    	        		setStatistics(true); //call method to set up win svreen 
    	        	} 	
        		}
            	/* IF THE PLAYER HAS PAUSED */
        		else
        			paused.setVisible(true); //set pause screen to visible 
	        	
	        	/* REPAINT, TRY AND CATCH */ 
	        	repaint(); //Animates the program 
	            requestFocusInWindow(); //sets the window in focus so its interactable            	
	            //Determines FPS rate (how how many times the animation is repainted) 
	            try { Thread.sleep(44); } //stops thread from going too fast, suspends execution for a period of time 
	             catch (InterruptedException e) {} //catch error when thread is interrupted  
	        }
        	/* IF THE PLAYER IS NOT IN GAME */
        	else if(Main.gameState == 0) { //0 game state = not in game 
        		//Check the level, and change coordinates based on that 
    			//Call method to change coordinates for each player to default spot 
        		if(Main.level == 1) {
        			changeValue(260,540,boy);
        			changeValue(100,470,girl); 
        		}
        		else if(Main.level == 2) { 
        			changeValue(50,470,boy);
        			changeValue(70,470,girl); 
        		}
        		else  { 
        			changeValue(60,540,boy);
        			changeValue(520,540,girl); 
        		} 
        		//Regenerate monster x/y values (randomly)
        		for(int i = 0; i < monsters.size(); i++) { 
        			monsters.get(i).x = (int)(Math.random()*(500-100+1)+100); 
        			monsters.get(i).y = (int)(Math.random()*(300-100+1)+100); 
        		}
        		//Reset all diamond pictures 
        		for(int i = 0; i < diamonds.size();i++) {
        			diamonds.get(i).isCaught = false; //set diamond to not caught; 
        			//Check if the diamond isWater diamond
            		if(diamonds.get(i).isWater)
            			diamonds.get(i).diamond = Diamonds.blueD; 
            		//Fire diamond 
            		else if(diamonds.get(i).isWater == false && diamonds.get(i).isNone == false)
            			diamonds.get(i).diamond = Diamonds.redD; 
            		//White diamond
            		else
            			diamonds.get(i).diamond = Diamonds.whiteD; 
        		}
        		//Set seconds to 0, and set the diamonds they collected to 0 as well 
        		seconds = 0;  
        		Diamonds.dCollected = 0; 
        	}
        }
	}
	
	
	/* Purpose: Method draws components and sprites 
	 * Pre: Graphics comp  
	 * Post:Does not return anything, but animates character */ 
	public void paintComponent(Graphics comp) {
        Graphics2D comp2D = (Graphics2D) comp; //make graphics 2D object 
        comp2D.drawImage(bkgnd,0,0,Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT,this); //draw background 
        //Draw diamonds 
        for(int i = 0; i < diamonds.size(); i++)
            comp2D.drawImage(diamonds.get(i).diamond,diamonds.get(i).x,diamonds.get(i).y,18, 18,this); //draw  sprite
        //Draw monsters 
        for(int i = 0; i < monsters.size();i++)
    		comp2D.drawImage(monsters.get(i).Sprite,monsters.get(i).x,monsters.get(i).y,15,15,this); 
        //Draw sprites 
        comp2D.drawImage(boy.Sprite,(int)boy.x,(int)boy.y,this); //fireboy 
        comp2D.drawImage(girl.Sprite,(int)girl.x,(int)girl.y,this); //watergirl  
        /* PAUSE SCREEN */
        Rectangle pauseS = new Rectangle(0,0,600,600); //create rectangle for screen background 
        //Check if the game has been paused, then draw the rectangle 
        if(Main.paused) {
        	comp2D.setPaint(new Color(64, 58, 42,120)); //set paint to a translucent colour 
            comp2D.fill(pauseS); //fill rectangle 
            comp2D.draw(pauseS); //draw it 
        }
        else { 
        	comp2D.setPaint(new Color(0,0,0,0)); //set colour to transparent 
            comp2D.fill(pauseS); //fill screen to the transparent colour 
        } 
        
    }	
		
	
	/* TIMER METHOD */ 
	
	//Create new timer 
	/* THIS IS NOT A METHOD */ 
	TimerTask task = new TimerTask() { 
		public void run() { //Will run the timer 
			//Check if the user has not paused the game, then change the time 
			if(!Main.paused) { 
				seconds++; //increase seconds each time 
				time.setText(""+seconds); 	
			}	
		}
	}; 
	
	/* Purpose: Method will schedule the timer for every second (1000ms) 
	 * Pre: N/A
	 * Post: N/A, will schedule timer */
	public void runTime() { 
		timer.schedule(task,0,1000); //set a schedule per 1000ms (1 second)  
	}
	
	/*  Purpose: Method sets the statistics of the game 
	 * Pre: boolean hasWon, which detects if the user has won or not  
	 * Post: N/A */
	public void setStatistics(boolean hasWon) { 
		//Check if user has collected all diamonds 
		if(Diamonds.dCollected == diamonds.size()) { 
			WinLose.check.setVisible(true); //set checkmark to true 
			WinLose.wrong.setVisible(false); 
		}
		else { 
			WinLose.check.setVisible(false);//set checkmark to false if not true  
			WinLose.wrong.setVisible(true); 
		}
		//Check if user has won, and change label to "you won" 
		if(hasWon) { 
			WinLose.loseWin.setIcon(WinLose.youWon); //set you won label 
			WinLose.hasWon = true; //set hasWon to true 
		}
		else { 
			WinLose.loseWin.setIcon(WinLose.youLost); //set you lost label 
			WinLose.hasWon = false; //set to false 
		} 
		
		/* CHECK GRADES */ 
		//If user won and was under 60 seconds 
		if(seconds <= 60 && hasWon) { 
			if(WinLose.check.isVisible()) //user won and got all diamonds 
				WinLose.setGrade(0); //call method to set grade to A 
			else //won but did not get all diamonds 
				WinLose.setGrade(1); //call method to set grade to B 
		}
		//User won, but over 60 seconds 
		else if(seconds > 60 && hasWon) { 
			if(WinLose.check.isVisible()) //user won but not on time but got all diamonds  
				WinLose.setGrade(2); //call method to set grade to C 
			else //won but not on time, did not get diamonds 
				WinLose.setGrade(3); //call method to set grade to D 
		}
		//If user lost, set grade to F 
		else 
			WinLose.setGrade(4);
		//Add time to label 
		WinLose.time.setText(Integer.toString(seconds)); //convert integer value to string 
		//show panel  
		Main.gameState = 0; //change game state to 0 
		Main.navigation.show(Main.panel,"WinLose");//show winlose panel 
	}
	
	/* Purpose: Method gets all the data of the level selected 
	 * Pre: 6 parameters, String mapImg (file name), String mapFile (txt file name), int girlX/Y (x/y coordinates of girl) and int boyX/Y (x/y coordinates of boy) 
	 * Post: N/A, declares any objects  */
	public void levelData(String mapImg, String mapFile, int girlX, int girlY, int boyX, int boyY) { 
		//Grab background image 
		bkgnd = StaticMethods.loadImage(mapImg,Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT); 
      	StaticMethods.getMap(map,mapFile); //get map 2D array from file name 
      	boy = new Player(boyX,boyY, "fire"); //instantiate boy and girl objects 
       	girl = new Player(girlX,girlY, "water");
    }
	
	/*  Purpose: Method creates monsters 
	 * Pre: 1 paramter, int num which is num of monsters
	 * Post: N/A,monsters created  */
	public void createMonster(int num) { 
		//For loop adds monsters based on variable num 
		for(int i = 0; i < num; i++)
			monsters.add(new Monster()); //add to arraylist
	}
	
	/* Purpose: Get the x value of the diamond from the array 
	 * Pre: 2 parameters, int num, which is the number representing the diamond (found in array), and char xOrY representing if x/y coordinate is wanted  
	 * Post: Returns the x coordinate of the diamond  */
	public int getDValueXorY(int num, char xOrY) { 
		//Counter variable to see which diamond is found 
		int count = 0; 
		//For loop goes through map 
		for(int row = 0; row < 33; row++) { 
			for(int col = 0; col < 33; col++) { 
				if(map[row][col] == '4') { //if value equals to 4 (diamond) 
					if(count == num)  { //if the count is equal to paramter num 
						//Check if they want x or y coordinate, and return correct coordinate 
						if(xOrY == 'x')
							return (int)(col*Main.TILE_SIZE); 
						else if(xOrY == 'y')
							return (int)(row*Main.TILE_SIZE); 
						else //If wrong char is inputted 
							return -20;
					}
					count++; //increase counter 
				}
			}
		}
		return -20; //return if not found 
	}
	
	/* Purpose: Method generates a random diamond type
	 * Pre: N/A 
	 * Post: N/A, but generates diamond objects  */
	public void generateDiamond() { 
		//Variables 
		int ranNum = (int)(Math.random()*(6-4+1)+4); //Generate random number; num of diamonds (max 6) 
		int ranType; //random type of diamond 
		int specialDiamond; //randomly generated number for special diamond 
		for(int i = 0; i < ranNum; i++) {	
			ranType = (int)(Math.random()*(2-1+1)+1); //2 for fire, 1 for water 
			specialDiamond = (int)(Math.random()*(20-1+1)+1); //generate a random number for generating a white diamond
			//Check the numbers
			if(specialDiamond == 17) //if equal to 17, then create object 
				diamonds.add(new Diamonds("none",getDValueXorY(i,'x'),getDValueXorY(i,'y')));
			//For fire and water diamonds, create object 	
			else if(ranType == 2)
				diamonds.add(new Diamonds("fire",getDValueXorY(i,'x'),getDValueXorY(i,'y')));
			else if(ranType == 1)
				diamonds.add(new Diamonds("water",getDValueXorY(i,'x'),getDValueXorY(i,'y')));
		}	 	
	}
	
	/*Purpose: Method changes value of x and y and resets booleans for movement 
	 * Pre: 3 parameters, int x, int y (x/y coordinates) and Player player which is the player object 
	 * Post: N/A, reset coordinates */
	public void changeValue(int x, int y, Player player) { 
		//Set sprite to front by default
		player.Sprite = player.front; 
		//Set all booleans to false, so player will not move once game restarts 
		player.left = false; 
		player.right = false; 
		player.jump = false; 
		//Set player x and y values based on parameters 
		player.x = x; 
		player.y = y; 
	}
	
	/* KEY LISTENER METHODS */
	/* Purpose: Method keyPressed detects which key has been pressed 
	 * Pre: KeyEvent event to indicate event 
	 * Post: N/A, executes statements made */
	public void keyPressed(KeyEvent event) {
		//Switch case for key released 
		//Use cases and booleans, so both users can move at the same time
		//Check if the game has not been paused (so player sprites do not move) 
		if(!Main.paused) { 
			switch (event.getKeyCode()) { 
			case KeyEvent.VK_LEFT: //left
				boy.left = true; //is moving left 
				break; //break from switch 
			case KeyEvent.VK_RIGHT: //right 
				boy.right = true;
				break; 
			case KeyEvent.VK_UP: //up
				boy.jump = true; 
				break; 
			case KeyEvent.VK_A: //left 
				girl.left = true; 
				break; 
			case KeyEvent.VK_D: //right
				girl.right = true;
				break; 
			case KeyEvent.VK_W: //up 
				girl.jump = true; 
				break; 
			}
		}
		//If user wants to pause the game (click P) 
		if(event.getKeyCode() == KeyEvent.VK_P) { 
		//Check if paused is true, then set to false (as in resume game) 
		if(Main.paused) { 
			//Check if the JLabel door is not visible, and if it is not level 3, then set it to visible 
			if(!door.isVisible() && Main.level != 3)
				door.setVisible(true); //even if user is on button, and game resumes, the door will go back to not visible due to the run method, as I check button collision 
			Main.paused = false; //set paused to false 
			paused.setVisible(false); //set jlabel to not visible 
		} 
		//If user has not paused 
		else  { 
			//Check if the door is visible, and if it is not level 3 
			if(door.isVisible() && Main.level != 3)
				door.setVisible(false); //then remove the door from visibility as pause screen has appeared 
			Main.paused = true; //set paused to true 
			paused.setVisible(true); //set jlabel as visible 
		}
		} 

	}

	/* Purpose: Method keyPressed detects which key has been released 
	 * Pre: KeyEvent event to indicate event 
	 * Post: N/A, executes statements made */
	public void keyReleased(KeyEvent event) {
		//Switch case for key released 
		//Use cases and booleans, so both users can move at the same time
		//Check if the game has not been paused (so player sprites do not move) 
		if(!Main.paused) { 
			switch (event.getKeyCode()) { 
			/* FIREBOY */ 
			case KeyEvent.VK_LEFT: //left 
				boy.Sprite = boy.back; //switch sprite 
				boy.left = false; // is not moving left anymore 
				break; //break from switch 
			case KeyEvent.VK_RIGHT: //right 
				boy.Sprite = boy.front; 
				boy.right = false;
				break; 
			case KeyEvent.VK_UP: //up 
				boy.jump = false; 
				break; 
			/* WATER GIRL*/ 
			case KeyEvent.VK_A: //left 
				girl.Sprite = girl.back; 
				girl.left = false; 
				break; 
			case KeyEvent.VK_D: //right 
				girl.Sprite = girl.front; 
				girl.right = false;
				break; 
			case KeyEvent.VK_W: //up 
				girl.jump = false; 
				break; 
			
			}
		}
	}
	
	/* Purpose: Method keyPressed detects which key has been typed
	 * Pre: KeyEvent event to indicate event 
	 * Post: N/A, executes statements made */
	public void keyTyped(KeyEvent event) {	
	}
}
