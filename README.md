Miles Moran

CSC 172 / Data Structures and Algorithms, Fall 2018

## SUMMARY

This program is a remake of the 2048 game using Java + Processing. It prints out a 4x4 matrix of tiles which "slide" across the screen and combine to make tiles of greater value. Basic scoring and metadata are recorded below. 

The code for this program is split into 4 main classes as follows:
- Game2048
	- Contains the functionality brought in from the Processing library, including event handling and drawing the gameboard to the window.
	
- GameBoard
	- Contains the 2D matrix of tiles, along with the methods associated with placing new tiles, "sliding" them, and detecting collosions between tiles.

- Tile
	- Contains the individual entries of the board, including their numerical value, color, and the merge operation between tiles.

- Coordinate
	- Contains the row and column number of a tile in the matrix. Only used when counting the available coordinates used by GameBoard.placeRand()


## COMPILING

To compile this code, you NEED to add the Processing Library Core.jar file to your classpath, along with the 2 included fonts.

In Eclipse:
1. Project > Properties > Java Build Path > Libraries > Add External JARs...
2. YOUR DIRECTORY > processing-3.4 > core > library > core.JAR

You can also see the following link for help if needed: https://processing.org/tutorials/eclipse/ 
I've also included a working Demo, Game2048.jar, in case you're not excited about the classpath antics.


## NOTABLE OBSTACLES

When it came to "sliding" the tiles around the board, I ran into the issue of reusability. I originally planned to write 4 distinct methods to "slide" and combine the tiles in a given direction; however, I found that 
* my code became unreadable with how nested things were, and 
* each function was similar enough that it FELT like I could generalize the structure to just 1 method. 

This was misleading, though, because 
* the order in which I looped through the tiles (e.g. vertical THEN horizontal) would result in tiles merging incorrectly, and
* the bounds and conditions for each method would be different (e.g. y=1; y<4; y++ vs. y=2; y>-1; y--)

I read about Lambda expressions and even posted on Reddit asking for help, but in the end it turned out that the solution was much less obvious. Inspired in-part by Daniel Shiffman's Coding Challenge on Youtube, I found that the easiest solution was to write 1 method that would ONLY shift the tiles to the right, and then write a separate function that would rotate the board as needed before "sliding" tiles around.


## INCLUDED FILES
```
- Source Code
	Coordinate.java
	Game2048.java
	GameBoard.java
	Tile.java
- Peripherals
	ClearSans-Bold.ttf
	ClearSans-Regular.ttf
- Readme	
	README.txt
- Executable Demo
	Game2048.jar 
```
	
 






