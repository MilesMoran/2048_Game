import java.lang.Math;

/**
 * The tile class contains the personal attributes and behaviors of each
 * individual tile on the board. 
 * 
 * @author Miles
 */

public class Tile {
	
	/** Tile colors for all possible values of a tile **/
	private static final int[] colors = {
	0xFF968D81, 0xFFEEE4DA, 0xFFECE0C8, 0xFFF2B179, 0xFFF59563, 0xFFF67C5F,  	//    0,    2,    4,    8,   16,   32,   
	0xFFF65E3B, 0xFFEDCF72, 0xFFEDCC61, 0xFFEDC850, 0xFFEDC53F, 0xFFFBC52D, 	//   64,  128,  256,  512, 1024, 2048, 
	0xFFF46674, 0xFFF14B61, 0xFFEB4141, 0xFF6DB9DB, 0xFF5DA1E2, 0xFF007FC2		// 4096, 8192, 2^14, 2^15, 2^16, 2^17. 
	};
	
	/** Tiles are initialized with the attributes of a 0 tile (empty) **/
	private int value = 0;
	private int color = 0xFF968D81;
	private boolean filled = false;
	private boolean hasCombined = false;
	
	/** @return The numeric value of the tile **/
	public int getValue() { return value; }
	/** @return The hex value of the color of the tile **/
	public int getColor() { return color; }
	/** @return Whether or not the tile is moving **/
	public boolean isFilled() { return filled; }
	/** @return Whether or not the tile has combined (merged) recently **/
	public boolean hasCombined() { return hasCombined; }
	
	/** @param The new numeric value of the tile **/
	public void setValue(int v) { value = v; updateColor(); }
	/** @param The hex value of the new color of the tile **/
	public void setColor(int c) { color = c; }
	/** @param Whether or not the tile has combined (merged) recently **/
	public void setCombined(boolean b) { hasCombined = b; }

	
	/**
	 * Determines if a certain tile is equal to this
	 * @param The tile object to be compared to this
	 * @return Whether or not these tiles are equal
	 */
	public boolean equals(Tile t) { return this.value == t.getValue(); }
	
	
	/** @return Whether or not the value is 0 (empty) **/
	public boolean isEmpty() { return value == 0; }
	
	
	/** 
	 * Changes the colors of the tile based on what value it is. Uses log base 2
	 * to convert (value) --> (index) of the colors[] array. If the color is 0, 
	 * set it manually (since log_2(0) is undefined and we don't want that) 
	 */
	private void updateColor() {
		if(value == 0) {
			color = colors[0]; 
		} else {
			int colIndex = (int)(Math.log10(value) / Math.log10(2));
			color = colors[colIndex];
		}
	}
	
	
	/**
	 * Takes a tile and doubles its value (combines it with THIS) and then
	 * changes THIS to be an empty tile. Afterwards, update both tiles' colors
	 * 
	 * @param The tile which THIS is moving over and combining with
	 */
	public void mergeOver(Tile t) {
		t.setValue(2*value);
		value = 0;
		
		this.updateColor();
		t.updateColor();
	}
	
	
}