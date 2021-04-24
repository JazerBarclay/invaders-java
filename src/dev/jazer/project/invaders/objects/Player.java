package dev.jazer.project.invaders.objects;

/**
 * The player object with set movement speed, health and score value
 * @author Jazer Barclay
 *
 */
public class Player extends Entity {

	/**
	 * Create a new player
	 * @param x - x axis starting position
	 * @param y - y axis starting position
	 */
	public Player(int x, int y) {
		super(x, y, 60, 20, 5, 1, -100);
	}
	
	/**
	 * Create a new player at position (0,0)
	 */
	public Player() {
		this(0,0);
	}

}
