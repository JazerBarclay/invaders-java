package dev.jazer.project.invaders.objects;

/**
 * An enemy entity object
 * @author Jazer Barclay
 *
 */
public class Enemy extends Entity {

	private EnemyType type;

	/**
	 * Create a new enemy entity object
	 * @param type - Type of enemy
	 * @param x - Enemy x axis position
	 * @param y - Enemy y axis position
	 * @param value - Enemy score value
	 */
	public Enemy(EnemyType type, int x, int y, int value) {
		super(x, y, 60, 50, 5, 1, value);
		this.type = type;
	}
	
	/**
	 * Create a new enemy entity object at position (0,0)
	 * @param type - Type of enemy
	 * @param value - Enemy score value
	 */
	public Enemy(EnemyType type, int value) {
		this(type, 0, 0, value);
	}
	
	/**
	 * Create a new bunny enemy object
	 * @param x - Enemy x axis position
	 * @param y - Enemy y axis position
	 * @param value - Enemy score value
	 */
	public Enemy(int x, int y, int value) {
		this(EnemyType.BUNNY, x, y, value);
	}
	
	/**
	 * Sets the type of enemy
	 * @param type
	 */
	public void setType(EnemyType type) {
		this.type = type;
	}
	
	/**
	 * @return the type of enemy
	 */
	public EnemyType getType() {
		return type;
	}

}
