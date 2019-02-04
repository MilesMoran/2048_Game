/**
 * A simple class that holds an x and y value. Only used when counting the 
 * available coordinates used by GameBoard.placeRand() 
 * 
 * @author Miles
 */
public class Coordinate {
	private int x, y;
	
	/**
	 * @param x the horizontal component of the coordinate
	 * @param y the vertical component of the coordinate
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** @return the horizontal component of the coordinate **/
	public int getX() { return this.x; }
	/** @return the vertical component of the coordinate **/
	public int getY() {	return this.y; }
	
}
