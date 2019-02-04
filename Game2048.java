import processing.core.PApplet;
import processing.core.PFont;

/**
 * !!! WARNING !!! BEFORE YOU DO ANYTHING !!!
 * TO COMPILE THIS, YOU NEED TO ADD THE PROCESSING CORE.JAR FILE TO YOUR CLASSPATH
 * 
 * IF YOU ARE USING ECLIPSE:
 * (1) Project > Properties > Java Build Path > Libraries > Add External JARs...
 * IN THIS POPUP WINDOW: 
 * (2) YOUR DIRECTORY > processing-3.4 > core > library > core.JAR
 *  
 * You can also see https://processing.org/tutorials/eclipse/ for help if needed.
 * Once the core.JAR is added to the classpath, it SHOULD compile.
 * 
 * IF NOT, I have included a runnable .JAR file exported from this code called 
 * Game2048.jar should work on any platform at any time.
 */
   
 /** 
 * 2048
 * This program is a Java implementation of the 2048 game. Using the Processing
 * library, it prints out a 4x4 matrix of tiles which can slide across the 
 * screen in one direction at a time to create tiles of even larger value. 
 * 
 * @author Miles Moran
 * @version 1.0
 * @since 2018-10-10
 */

public class Game2048 extends PApplet {
	
	/** Variable used for game-state features **/
	private enum states { PLAYING, QUITSCREEN, RESTARTSCREEN, LOSESCREEN; }
	private enum moves 	{ LEFT, RIGHT, UP, DOWN; }
	private states currState;
	private moves lastMove;
	
	/** Variables used for design features **/
	private final int OFFSET = 10;
	private final int COLORBOARD= 0xFFBBADA0;
	private PFont sansBold48;
	private PFont sansBold20;

	/** Variables used for board features **/
	private int moveCount;
	private boolean isRunning = true;
	private boolean hasShifted = false;
	private GameBoard myBoard;	
	
	
	/** 
	 * The main method makes a call to the parent function imported from the
	 * Processing library, telling the PApplet to begin the game
	 * 
	 * @param args Unused arguments from the command line
	 */
	public static void main(String[] args) {
		PApplet.main("Game2048");
	}

	
	/** 
	 * The setup method, which is called behind-the-scenes by main(), 
	 * initializes some attributes of the game such as the font and the 
	 * number of moves made, then creates the main 4x4 matrix game-board 
	 */
	public void setup() {
		sansBold48 = createFont("ClearSans-Bold", 48);
		sansBold20 = createFont("ClearSans-Bold", 20);
		noStroke();
	
		myBoard = new GameBoard(this);
		currState = states.PLAYING;
		moveCount = 0;
	}
	

	/** 
	 * The settings method , which is called behind-the-scenes by main()
	 * AFTER setup(), just sets the value for the size of the game board.
	 */
	public void settings() {
		size(450,500);	
	}

	
	/** 
	 * The draw method puts graphics on the screen depending on what state the
	 * game is currently in. If the game is still being played, it makes a call
	 * to the board telling it to draw itself. If not, a pop-up will occur. 
	 */
	public void draw() {
		if(currState == states.PLAYING) {
			background(COLORBOARD);  
			textFont(sansBold20);
			fill(255);
			
			text("Moves Made: " + moveCount, 10, 465);
			text("Maximum Number: " + myBoard.getMax(), 10, 485);
			
			text("Last Move: " + lastMove, 250, 465);
			text("Valid? " + hasShifted, 250, 485);
			
			textFont(sansBold48);
			translate(OFFSET,OFFSET);
			myBoard.printBoard();
			
		} else { 
			fill(255);
			rect(40, 40, 360, 120, 10);
			fill(0);
			textFont(sansBold20);			

			if(currState == states.QUITSCREEN) {
				text("Are you sure you would like to quit? " + "Press Q again to exit the game        " +
					 "Press any other key to resume", 50, 50, 350, 350);
			} else if(currState == states.RESTARTSCREEN) {
				text("Are you sure you would like to restart? " + "Press R again to restart the game        " +
					 "Press any other key to resume", 50, 50, 350, 350);
			} else if(currState == states.LOSESCREEN) {
				text("Game over!", 150, 80, 350, 350);
			}
		
		}
	}

	
	/** 
	 * Gets the last key released by the user through PApplet and determines
	 * whether the game will continue, quit, restart, or end. If a valid move 
	 * is made, the board shifts in that direction and a counter is incremented. 
	 * Instead of there being a shift() method for every direction, only 1 is 
	 * implemented and the board is rotated in 90* increments as needed.
	 */
	public void keyReleased() {
		if(isRunning) {
			isRunning = myBoard.moveCanBeMade();
		
			if(keyCode == UP || key == 'w') {
				myBoard.rotate(1);
				hasShifted = myBoard.shiftRight();
				myBoard.rotate(3);
				
				currState = states.PLAYING;
				lastMove = moves.UP;
				
			} else if (keyCode == DOWN || key == 's') {
				myBoard.rotate(3);
				hasShifted = myBoard.shiftRight();
				myBoard.rotate(1);
				
				currState = states.PLAYING;
				lastMove = moves.DOWN;
				
			} else if (keyCode == LEFT || key == 'a') {
				myBoard.rotate(2);
				hasShifted = myBoard.shiftRight();
				myBoard.rotate(2);
				
				currState = states.PLAYING;
				lastMove = moves.LEFT;
				
			} else if (keyCode == RIGHT || key == 'd') {
				hasShifted = myBoard.shiftRight();
				
				lastMove = moves.RIGHT;
				currState = states.PLAYING;
				
			} 
			
			
			else if (key == 'q') {
				if(currState == states.QUITSCREEN) exit();
				else currState = states.QUITSCREEN;
			} else if (key == 'r') {
				if(currState == states.RESTARTSCREEN) setup();
				else currState = states.RESTARTSCREEN;
			}
			
			if(hasShifted) {
				moveCount++;
				myBoard.placeRand(1);
				System.out.println("\nMoves Made: " + moveCount);
				System.out.println("Max Num: " + myBoard.getMax());
			}

			
		} else {
			currState = states.LOSESCREEN;
		}			
	}
		
	
} 