package dev.jazer.project.invaders;

import java.util.ArrayList;

import javafx.application.Platform;

/**
 * Represents the game state data and hold the run method exposed to start the model
 * @author Jazer Barclay
 */
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

	// Bullet data
	private ArrayList<GameObject> bullets;


	public GameModel(int screenWidth, int screenHeight) {
		// Assign class variable values using parsed values
		this.gameWidth = screenWidth;
		this.gameHeight = screenHeight;
		this.state = GameState.RUNNING;

		// Init game stats
		lives = 3;
		score = 0;
		tick = 0;
		rate = MAX_RATE;
		playerCooldown = 0;
		enemyCooldown = 0;

		// Init player
		generatePlayer();

		// Init enemies
		generateEnemies();

		// Init bullet array
		this.bullets = new ArrayList<GameObject>();

	}

	/**
	 * @return the game screen width
	 */
	public int getGameWidth() {
		return gameWidth;
	}

	/**
	 * @return the game screen height
	 */
	public int getGameHeight() {
		return gameHeight;
	}

	/**
	 * @return the current state of the game (RUNNING, PAUSED, STOPPED)
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * Sets the current state of the game (RUNNING, PAUSED, STOPPED)
	 */
	public void setState(GameState state) {
		this.state = state;
	}

	/**
	 * Sets the game state to PAUSED
	 */
	public void pause() {
		setState(GameState.PAUSED);
	}

	/**
	 * Sets the current game state to RUNNING
	 */
	public void resume() {
		setState(GameState.RUNNING);
	}

	/**
	 * Sets the current game state to STOPPED
	 */
	public void stop() {
		setState(GameState.STOPPED);
	}

	/**
	 * @return the current player score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the number of lives remaining
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * @return the player's current cooldown timer
	 */
	public int getPlayerCooldown() {
		return playerCooldown;
	}

	/**
	 * 
	 * @return the player game object
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * 
	 * @return the 2d array of enemies
	 */
	public Enemy[][] getEnemies() {
		return enemies;
	}

	/**
	 * 
	 * @return the array of bullets
	 */
	public GameObject[] getBullets() {
		return bullets.toArray(new GameObject[bullets.size()]);
	}

	public int getTick() {
		return tick;
	}


	public int getRate() {
		return rate;
	}
	
	/**
	 * Advances the current game tick and updates cooldowns
	 */
	public void tick() {
		tick++;
		if (playerCooldown > 0) playerCooldown--;
		if (enemyCooldown > 0) enemyCooldown--;
		if (tick > rate) tick = 0;
	}

	/**
	 * Creates the player object at its starting position
	 */
	private void generatePlayer() {
		this.player = new Player(0,gameHeight-new Player(0,0).getHeight()-70);
	}

	/**
	 * Creates the 2d array of enemies at their starting points 
	 * with their given types on each row
	 */
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

	/**
	 * Creates a bullet fired by the player (set to go up)
	 */
	public void playerFire() {
		generateBullet(player);
	}

	/**
	 * Creates a bullet fired by the given enemy
	 * @param enemy
	 */
	public void enemyFire(Enemy enemy) {
		generateBullet(enemy);
	}

	/**
	 * Generates a bullet and position and directional data based on the type of entity
	 * that is parsed
	 * @param entity
	 */
	private void generateBullet(Entity entity) {
		if (entity instanceof Player) {
			playerCooldown = PLAYER_CD_DURATION;
			bullets.add(new GameObject(
					new Vector(player.getX()+player.getWidth()/2-3, 
							player.getY()-30), 
					new Vector(0, -5), 
					6, 20, 1));
		} else {
			bullets.add(new GameObject(
					new Vector(entity.getX()+entity.getWidth()/2, 
							entity.getY()+60), 
					new Vector(0, 5), 
					6, 20, 1));
		}
	}

	/**
	 * Checks for bullet collision with the player and enemies
	 * and handles incrementing score based on enemy value, removing the colliding
	 * bullet and increasing the game rate
	 */
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
		}

		// Remove bullets listed as collided
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

	/**
	 * The main loop that runs while game is running
	 * @param view
	 * @param controller
	 */
	private void runGame(GameView view, GameController controller) {
		try {
			while (state != GameState.STOPPED) {
				if (state == GameState.RUNNING) {
					if (controller != null) controller.update();
					this.update();
					Platform.runLater(() -> view.render());
				}
				Thread.sleep( 10 );
			}
			// When the game stops, show end of game stats
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the state of the model by performing checks,
	 * moving and removing objects
	 */
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
