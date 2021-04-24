package dev.jazer.project.invaders.objects;

/**
 * A game object that has health and a score value
 * @author Jazer Barclay
 *
 */
public class Entity extends GameObject {

	private int health;
	private boolean alive;
	private int value;
	
	/**
	 * Create a new entity
	 * @param x - Entity's x axis position
	 * @param y - Entity's y axis position
	 * @param width - Entity's width
	 * @param height - Entity's height
	 * @param baseSpeed - Entity's base movement speed
	 * @param health - Entity's health
	 * @param value - Entity's score value
	 */
	public Entity(int x, int y, int width, int height, double baseSpeed, int health, int value) {
		super(x, y, width, height, baseSpeed);
		this.health = health;
		this.value = value;
		this.alive = true;
	}
	
	/**
	 * @return the remaining health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Sets the entity's health. Sets alive status to false when health set below 1
	 * @param health
	 */
	public void setHealth(int health) {
		if (health > 0) {
			this.health = health;
		} else {
			health = 0;
			alive = false;
			setVisible(false);
		}
	}
	
	/**
	 * Adds the given health value to the entities current health value
	 * @param health
	 */
	public void heal(int health) {
		setHealth(this.health + health);
	}
	
	/**
	 * Reduces the entities health by the given damage value
	 * @param damage
	 */
	public void injure(int damage) {
		setHealth(this.health - damage);
	}
	
	/**
	 * @return entity alive status
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * @return the entity's score value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets the score value of the entity
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
}
