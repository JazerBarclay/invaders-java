package dev.jazer.project.invaders;

public class Enemy extends Entity {

	private EnemyType type;
	
	public Enemy(EnemyType type, int value) {
		this(0,0, value);
		this.type = type;
	}
	
	public Enemy(int x, int y, int value) {
		super(x, y, 60, 50, 5, 1, value);
	}
	
	public void setType(EnemyType type) {
		this.type = type;
	}
	
	public EnemyType getType() {
		return type;
	}

}
