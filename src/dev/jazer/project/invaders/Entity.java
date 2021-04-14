package dev.jazer.project.invaders;

public class Entity extends GameObject {

	private int health;
	
	public Entity(int x, int y, int width, int height, double baseSpeed, int health) {
		super(x, y, width, height, baseSpeed);
		this.health = health;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health > 0) {
			this.health = health;
		} else {
			health = 0;
			setVisible(false);
		}
	}
	
	public void heal(int health) {
		setHealth(this.health + health);
	}
	
	public void injure(int damage) {
		setHealth(this.health - damage);
	}
	
}
