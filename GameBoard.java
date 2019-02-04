import java.util.ArrayList;
import processing.core.PApplet;

/** 
 * The GameBoard class contains the more intricate calculations of changing 
 * the board and its tiles without dealing too much with user interaction.
 * 
 * @author Miles Moran
 */
public class GameBoard {
	/** Variables used for design features **/
	private final int WIDTH   = 100;
	private final int SPACING = 110; 
	private final int TEXTCOLOR = 0xFF776e65;
	
	/** Variables used for board features **/
	private int maxNum = 0;
	private Tile[][] board = new Tile[4][4];
	private PApplet app;
	
	
	/** 
	 * The constructor here takes in a PApplet class, that acts like a canvas 
	 * the other methods in GameBoard can then draw to. It also fills the 
	 * board with tile objects and assigns 2 random ones starting values.
	 * 
	 * @param app The "canvas" object which GameBoard draws things onto 
	 */
	public GameBoard(PApplet app) {
		this.app = app;
		
		for(int y=0; y<4; y++) {
			for(int x=0; x<4; x++)
				board[y][x] = new Tile();
		}
		placeRand(2);
	}
	
	
	/** 
	 * @return The maximum number on the board created by the user
	 */
 	public int getMax() 
 	{ return maxNum; }
 	
 	
	/**
	 * Loops through the board and draws each tile. Does this by moving 
	 * ("translating") to the correct x,y coordinate, picking the color,
	 * drawing a square in that color, and then drawing text on top of it
	 */
	public void printBoard() {
		for(int y=0; y<4; y++) {
			for(int x=0; x<4; x++) {
				app.translate(x*SPACING, y*SPACING); 	
				
				app.fill(board[y][x].getColor());
				app.rect(0, 0, WIDTH, WIDTH, 10);

				if(!board[y][x].isEmpty()) {
					app.fill(TEXTCOLOR);
					app.text(board[y][x].getValue(), WIDTH/3, 2*WIDTH/3 );
				} 

				app.translate(-x*SPACING, -y*SPACING);
			}
		}	
	}
	
	
	/** 
	 * Takes in a number of tiles to place, creates a list of available
	 * coordinates, and then randomly picks one which it assigns a value of
	 * either 2 (80% chance) or 4 (20% chance)
	 * 
	 * @param numToPlace The number of tiles that need to be placed
	 * @return boolean Returns true if a tile was successfully placed; else false
	 */
	public boolean placeRand(int numToPlace) {		
		ArrayList<Coordinate> availCoords = new ArrayList<Coordinate>();
		for(int y=0; y<4; y++) {
			for(int x=0; x<4; x++) {
				if(board[y][x].isEmpty()) availCoords.add(new Coordinate(x, y));
			}
		}
		
		if(availCoords.size() != 0) {
			for(int i=0; i<numToPlace; i++) {
				int n = PApplet.floor(app.random(0, availCoords.size()));
				int x = availCoords.get(n).getX();
				int y = availCoords.get(n).getY();
				board[y][x].setValue( PApplet.floor(app.random((float)1.0, (float)2.25))*2 );
				if(board[y][x].getValue() > maxNum)
					maxNum = board[y][x].getValue();
			} 
			return true;
		} else {
			return false;
		}
	} 
	
	
	/** 
	 * Loops through the board, and, for each element, loops through all elements
	 * to the right of it to see if the current element can either (a) move right
	 * or (b) merge onto the tile to the right (assuming it hasn't already merged
	 * in this same action)
	 * 
	 * @return boolean Returns true if the board was successfully shifted; else false
	 */
	public boolean shiftRight() {
		boolean hasShifted = false;
		
		for(int y=0; y<4; y++) {
			for(int x=3; x>-1; x--) {
				board[y][x].setCombined(false);
				
				if(board[y][x].isEmpty()) { continue; }
				
				for(int i=0; (x+i+1) < 4
				&& (board[y][x+i+1].isEmpty() || board[y][x+i].equals(board[y][x+i+1]))
				&& !board[y][x].hasCombined(); i++) {
						
					
					if(board[y][x+i+1].isEmpty()) {
						int tmp = board[y][x+i].getValue();
						board[y][x+i+1].setValue(tmp);
						board[y][x+i].setValue(0);
						hasShifted = true;
					} else if(board[y][x+i].equals(board[y][x+i+1]) && 
						!board[y][x+i].hasCombined() && !board[y][x+i+1].hasCombined()) {
						
						board[y][x+i].mergeOver(board[y][x+i+1]);
						board[y][x+i+1].setCombined(true);
						if(board[y][x+i+1].getValue() > maxNum) 
							maxNum = board[y][x+i+1].getValue();
						hasShifted = true;
					}
						
					
				}
			
			}
		}
		return hasShifted;
	}
	

	/**
	 * Creates a new, temporary matrix of tiles and then loops through the
	 * elements of the board, rotates their coordinates, and stores them in
	 * tmp. When tmp has been filled, the board is assigned tmp. 
	 * 
	 * @param amt The number of times that the board should rotate 90* (e.g. 2=180*)
	 */
	public void rotate(int amt) {
		Tile[][] tmp = new Tile[4][4];
		
		switch(amt) {
		case 1: // rotate 90 degrees
			for(int i=0; i<4; i++)
				for(int j=0; j<4; j++) {
					tmp[i][j] = new Tile();
					tmp[i][j].setValue(board[3-j][i].getValue());
				}
			break;
		case 2: // rotate 180 degrees
			for(int i=0; i<4; i++)
				for(int j=0; j<4; j++) {
					tmp[i][j] = new Tile();
					tmp[i][j].setValue(board[3-i][3-j].getValue());
				}
			break;
		case 3: // rotate 270 degrees
			for(int i=0; i<4; i++)
				for(int j=0; j<4; j++) {
					tmp[i][j] = new Tile();
					tmp[i][j].setValue(board[j][3-i].getValue());
				}
			break;
		}

		
		board = tmp;
		
	}
	

	/** 
	 * Loops through the board and checks each tile's surroundings to see if
	 * any valid moves can be made by the user
	 * 
	 * @return boolean Returns true if a valid move can be made; else false
	 */
	public boolean moveCanBeMade() {
		for(int y=0; y<4; y++) {
			for(int x=0; x<4; x++) {
				if(board[y][x].isEmpty()) return true;
				if(y+1 < 4  && board[y][x].equals(board[y+1][x])) return true;
				if(y-1 > -1 && board[y][x].equals(board[y-1][x])) return true;
				if(x+1 < 4  && board[y][x].equals(board[y][x+1])) return true;
				if(x-1 > -1 && board[y][x].equals(board[y][x-1])) return true;
			}
		}
		return false;
	}
	
}

