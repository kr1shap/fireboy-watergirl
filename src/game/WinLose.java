/* 
 * Name: Krisha Patel 
 * Date: Dec 21, 2023 
 * Purpose: Class holds win/lose screen 
 * ICS4U
 */

package game;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.text.*;  
import java.util.*;  
public class WinLose extends JPanel implements ActionListener {
	//Static variables 
	/* STATIC IMAGES */ 
	static ImageIcon youWon = StaticMethods.loadImageIcon("img/win.png", 200, 35); //holds you won text label image
	static ImageIcon youLost = StaticMethods.loadImageIcon("img/lose.png", 200, 35); //holds you lost text label image 
	//Array holds all the grade levels of the user (A/B/C/D/F)
	static JLabel [] grades = {new JLabel(StaticMethods.loadImageIcon("img/gradeA.png",80,80)), new JLabel(StaticMethods.loadImageIcon("img/gradeB.png",80,80)), new JLabel(StaticMethods.loadImageIcon("img/gradeC.png",80,80)), new JLabel(StaticMethods.loadImageIcon("img/gradeD.png",80,80)), new JLabel(StaticMethods.loadImageIcon("img/gradeF.png",80,80))}; 
	static JLabel check = new JLabel(StaticMethods.loadImageIcon("img/check.png",40,40)); //holds checkmark image, used if they collected all diamonds 
	static JLabel wrong = new JLabel(StaticMethods.loadImageIcon("img/wrong.png",40,40)); //holds X-mark image, used if they did not collect all diamonds 
	static JLabel time = new JLabel(); //label holds the time the user took 
	static JLabel loseWin = new JLabel(youWon); //stores you lost or you won message //JLabel holds the youWon label by default 
	static boolean hasWon = false; //holds true if user did win, false if they did not win 
	//Instance variables 
	JButton retryB = new JButton(StaticMethods.loadImageIcon("img/retry.png",180,40)); //holds the retry button 
	JButton recordB = new JButton(StaticMethods.loadImageIcon("img/keepR.png",180,40)); //holds the keep record button, for database  
	JButton quitB = new JButton(StaticMethods.loadImageIcon("img/quit.png",180,41)); //holds the quit button 
	JButton [] buttons = {retryB, recordB, quitB}; //array stores all the buttons in the class
	Image WLBkgnd = StaticMethods.loadImage("img/WLBkgnd.png", 600, 600); //holds the background image of the panel 
	
	//Constructor
	public WinLose() { 
		super(); 
		//Set layout to null 
		setLayout(null); 
		//Set bounds of buttons 
		retryB.setBounds(100,450,180,40);
		quitB.setBounds(350,450,180,41);
		recordB.setBounds(210,500,180,41);
		//For loop goes through arrays of buttons 
		for(int i = 0; i < buttons.length; i++) { 
			StaticMethods.transparent(buttons[i]); //sets to transparent 
			add(buttons[i]); //adds button to panel
			buttons[i].addActionListener(this); //adds action listener 
		}
		
		//Add lose/win message to frame 
		loseWin.setFont(Main.lacquer);
		loseWin.setBounds(210,100,200,35);
		add(loseWin); 
		//Add check/wrong labels to panel but dont make them visible yet 
		check.setBounds(215,268,40,40);
		wrong.setBounds(215,268,40,40);
		check.setVisible(false);
		wrong.setVisible(false);
		add(check); 
		add(wrong); 
		
		//Add time label to panel (time has not been set yet)  
		time.setBounds(205,200,100,50);
		time.setFont(Main.lacquer); //set font 
		time.setForeground(new Color(204, 168, 47)); //set text to colour 
		add(time); 
		
		//Add grades to panel, set visibility to false 
		for(int i = 0; i < grades.length; i++) {
			grades[i].setBounds(410,220,100,100); 
			grades[i].setVisible(false);
			add(grades[i]); 
		}
	}
	
	/* METHODS */ 
	
	/*  Purpose: Method sets the grade of the user
	 * Pre: 1 parameter, int num which is the number correlating to element position in array 
	 * Post: N/A, changes visibility of JLabel */
	public static void setGrade(int num) { 
		//For loop goes through array 
		for(int i = 0; i < grades.length; i++) {
			//If i is equal to num input (grade index) 
			if(i == num) { 
				grades[num].setVisible(true);//set visibility to true 
			}
			else
				grades[i].setVisible(false); //if not, then set visibility to false 
		}
	}
	
	/* Purpose: Method writes name in database 
	 * Pre: String name, which is name of user 
	 * Post: N/A, writes name in database  */
	public void nameInDataBase(String name) {
		//Surround with try and catch to catch any IOException errors
		//Since this is a method, you need to use a try and catch method 
		try {
			//Create a date by adding date formatter 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			Date date = new Date();  
			//Create a filewriter instead of printwriter, so our output does not overwrite 
		    FileWriter file = new FileWriter("FIREBOYWATERGIRL_Names.txt", true);
		    //Create a buffered writer, which helps write more efficiently 
		    BufferedWriter bufferedWriter = new BufferedWriter(file);
		    //Check if user has collected all the diamonds 
		    String didCollect; //string to store yes/no
		    if(check.isVisible()) //if check mark is visible (means collected) 
		    	didCollect = "YES! |"; 
		    else //if not visible
		    	didCollect = "NO! |"; 
		    /* Write string to file, and then create a new line (goes to next line) */ 
		    //Check if user has won, and base message off that 
		    if(hasWon)
			    bufferedWriter.write(name + " | TIME: " + time.getText() + " | LEVEL: " + Main.level +  " | THEY WON!" + " | ALL DIAMONDS COLLECTED: " + didCollect +  " (" + formatter.format(date) + ")"); //has they won string 
		    else
			    bufferedWriter.write(name + " | TIME: " + time.getText() + " | LEVEL: " + Main.level +  " | THEY LOST!" + " | ALL DIAMONDS COLLECTED: " + didCollect+ " (" + formatter.format(date) + ")"); //has they lost string 
			bufferedWriter.newLine(); //go to the next line in the reader
		    bufferedWriter.close(); //close buffered writer after 
		} 
		//Catch part of try and catch 
		catch(IOException e) {
		}
	}
	
	/* Purpose: Method paintComponent draws the background and other components 
	 * Pre: Graphics 2D g
	 * Post: N/A   */
	public void paintComponent(Graphics g) {
		 Graphics2D comp2D = (Graphics2D)g; //make graphics 2D object 
	     comp2D.drawImage(WLBkgnd,0, 0,Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT,this); //draw background 
	}

	/* Purpose: Method checks which event has occured (button pressed, etc) 
	 * Pre: 1 parameter, ActionEvent e which is the event that has occured 
	 * Post: N/A, statements execute */
	public void actionPerformed(ActionEvent e) {
		//If user clicks retry button 
		if(e.getSource() == retryB) { 
			Main.gameState = 1; //change the gamestate to 1 (in game) 
			Main.navigation.show(Main.panel, "Game"); //show game panel 	
		}
		//If user clicks quit button 
		else if(e.getSource() == quitB)
			System.exit(0); //close the window  
		//If user clicks record button 
		else if(e.getSource() == recordB) { 
			//Show an input dialog, where user can input their name
        	String name = JOptionPane.showInputDialog(null,"ENTER YOUR NAME TO BE STORED IN A PLAYER DATABASE!", "NAME",JOptionPane.QUESTION_MESSAGE); 
        	//Check if name isn't null, then call method to put name in database 
        	if(name != null) { 
        		nameInDataBase(name);
        	}
		}
		
	}

}
