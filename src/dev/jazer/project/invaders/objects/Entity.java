package dev.jazer.project.invaders.objects;

public class Entity extends GameObject {

	private int health;
	private boolean alive;
	private int value;
	
	public Entity(int x, int y, int width, int height, double baseSpeed, int health, int value) {
		super(x, y, width, height, baseSpeed);
		this.health = health;
		this.value = value;
		this.alive = true;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health > 0) {
			this.health = health;
		} else {
			health = 0;
			alive = false;
			setVisible(false);
		}
	}
	
	public void heal(int health) {
		setHealth(this.health + health);
	}
	
	public void injure(int damage) {
		setHealth(this.health - damage);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
}
