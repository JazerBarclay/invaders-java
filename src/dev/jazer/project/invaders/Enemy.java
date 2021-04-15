package dev.jazer.project.invaders;

public class Enemy extends Entity {

	private EnemyType type;
	
	public Enemy(EnemyType type) {
		this(0,0);
		this.type = type;
	}
	
	public Enemy(int x, int y) {
		super(x, y, 60, 50, 5, 1);
	}
	
	public void setType(EnemyType type) {
		this.type = type;
	}
	
	public EnemyType getType() {
		return type;
	}

}
