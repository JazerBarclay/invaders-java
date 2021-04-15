package dev.jazer.project.invaders;

import java.util.ArrayList;

import javafx.application.Platform;

public class GameModel {

	// Static data
	private final int MAX_RATE = 205, PLAYER_CD_DURATION = 100, ENEMY_CD_DURATION = 100;
	
	// Game data
	private GameState state;
	private int gameWidth, gameHeight;
	private int lives, score, tick, rate, playerCooldown, enemyCooldown;
	
	
	// Player data
	private Player player;
	
	// Enemy data
	private Enemy[][] enemies;
	private Bounds enemyBounds;

	
	private ArrayList<GameObject> bullets;
	
	public GameModel(int screenWidth, int screenHeight) {
		this.gameWidth = screenWidth;
		this.gameHeight = screenHeight;
		this.state = GameState.RUNNING;
		
		lives = 3;
		score = 0;
		tick = 0;
		rate = MAX_RATE;
		playerCooldown = 0;
		enemyCooldown = 0;

		generatePlayer();
		generateEnemies();
		this.bullets = new ArrayList<GameObject>();
		
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}
	
	public GameState getState() {
		return state;
	}
	
	public void setState(GameState state) {
		this.state = state;
	}
	
	public void pause() {
		setState(GameState.PAUSED);
	}
	
	public void resume() {
		setState(GameState.RUNNING);
	}
	
	public int getPlayerCooldown() {
		return playerCooldown;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Enemy[][] getEnemies() {
		return enemies;
	}
	
	public GameObject[] getBullets() {
		return bullets.toArray(new GameObject[bullets.size()]);
	}
	
	public void tick() {
		tick++;
		if (playerCooldown > 0) playerCooldown--;
		if (enemyCooldown > 0) enemyCooldown--;
		if (tick > rate) tick = 0;
	}
	
	private void generatePlayer() {
		this.player = new Player(0,gameHeight-new Player(0,0).getHeight());
	}
	
	private void generateEnemies() {
		enemies = new Enemy[5][10];
		
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[0].length; j++) {
				Enemy e = null;
				if (i <= 1) e = new Enemy(EnemyType.BUNNY, 30);
				if (i > 1 && i <= 3) e = new Enemy(EnemyType.LOADER, 20);
				if (i == 4) e = new Enemy(EnemyType.SQUID, 10);
				e.setPosition(new Vector(e.getWidth()*j+j*30, e.getHeight()*i+i*20+10));
				e.setBaseSpeed(0);
				e.setMotion(new Vector(e.getBaseSpeed(),0));
				enemies[i][j] = e;
			}
		}
	}
	
	public void playerFire() {
		generateBullet(player);
	}
	
	private void generateBullet(Entity e) {
		if (e instanceof Player) {
			playerCooldown = PLAYER_CD_DURATION;
			bullets.add(
					new GameObject(
							new Vector(player.getX()+player.getWidth()/2-3, 
									   player.getY()-30), 
							new Vector(0, -5), 
							6, 20, 1));
		} else {
			bullets.add(
					new GameObject(
							new Vector(e.getX()+e.getWidth()/2, 
									e.getY()+60), 
							new Vector(0, 5), 
							6, 20, 1));
		}
	}
	
	private void detectBulletCollision() {
		
		// Return immediately if no bullets available
		if (bullets.size() <= 0) return;
		// Create a list of bullets to delete as we cant remove while iterating through them
		ArrayList<GameObject> delBullet = new ArrayList<GameObject>();
		
		// Loop through all enemies
		for (Enemy[] enemies : this.enemies) {
			for (Enemy e : enemies) {
				// If the enemy is already dead, skip
				if (!e.isAlive()) continue;
				// For each bullet, check if colliding with the enemy
				for (GameObject bullet : bullets) {
					
					// If not colliding, skip the rest of the code
					if (!bullet.isColliding(e)) continue;
					
					// If it is colliding, mark the bullet for deletion
					delBullet.add(bullet);
					// set the enemy health to 0
					e.setHealth(0);
					// increase the speed of the game by reducing the rate
					rate -= 4;
					// add the enemy value to the score
					score += e.getValue();
					
				}
			}
		} // end of all loops
		
		for (GameObject o : delBullet) bullets.remove(o);
		
	}
	
	/**
	 * Starts the game with this model using the given view and controller
	 * @param view
	 * @param controller
	 */
	public void startGame(GameView view, GameController controller) {
		Thread t = new Thread(() -> runGame(view, controller));
		t.setDaemon(true);
		t.start();
	}
	
	public void runGame(GameView view, GameController controller) {
		try {
			while (true) {
				if (state == GameState.RUNNING) {
					if (controller != null) controller.update();
					this.update();
					Platform.runLater(() -> view.render());
				}
				Thread.sleep( 10 );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		detectBulletCollision();
		if (tick == 0 || tick == rate/2) {
			for (Enemy[] enemies : this.enemies) {
				for (Enemy e : enemies) {
					e.updatePosition();
				}
			}
		}
		player.updatePosition();
		for (GameObject o : getBullets()) {
			o.updatePosition();
		}
		tick();
	}
	
}
