package dev.jazer.project.invaders.game;

import java.util.ArrayList;
import java.util.Random;

import dev.jazer.project.invaders.objects.Enemy;
import dev.jazer.project.invaders.objects.EnemyType;
import dev.jazer.project.invaders.objects.Entity;
import dev.jazer.project.invaders.objects.GameObject;
import dev.jazer.project.invaders.objects.Player;
import dev.jazer.project.invaders.objects.Vector;
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
	private boolean devmode;
	private int gameWidth, gameHeight;
	private int lives, score, tick, rate, playerCooldown, enemyCooldown;

	// Player data
	private Player player;

	// Enemy data
	private Enemy[][] enemies;
	private GameObject enemyBounds;
	private int lBound = 0, rBound = 9;

	// Bullet data
	private ArrayList<GameObject> bullets;
	
	private ScoreReturnPromise scoreReturn;


	public GameModel(int screenWidth, int screenHeight, ScoreReturnPromise scoreReturn) {
		this.gameWidth = screenWidth;
		this.gameHeight = screenHeight;
		this.state = GameState.RUNNING;
		this.devmode = false;
		this.scoreReturn= scoreReturn;

		lives = 3;
		score = 0;
		tick = 0;
		rate = MAX_RATE;
		playerCooldown = 0;
		enemyCooldown = 0;

		generatePlayer();
		generateEnemies();
		generateEnemyBounds();
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
	 * 
	 * @return true if is in devmode
	 */
	public boolean isDevmode() {
		return devmode;
	}
	
	/**
	 * Set if game is in dev mode
	 * @param devmode true/false
	 */
	public void setDevmode(boolean devmode) {
		this.devmode = devmode;
	}
	
	/**
	 * Toggles devmode on or off
	 * @return true if in devmode
	 */
	public boolean toggleDevmode() {
		devmode ^= true;
		return devmode;
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
	 * @return the 2d array of enemies
	 */
	public Enemy[][] getEnemies() {
		return enemies;
	}
	
	/**
	 * @return the enemy bounding box game object
	 */
	public GameObject getEnemyBounds() {
		return enemyBounds;
	}

	/**
	 * @return the array of bullets
	 */
	public GameObject[] getBullets() {
		return bullets.toArray(new GameObject[bullets.size()]);
	}

	/**
	 * @return the current game tick
	 */
	public int getTick() {
		return tick;
	}

	/**
	 * @return the max rate which the tick resets at
	 */
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
				e.setBaseSpeed(25);
				e.setMotion(new Vector(e.getBaseSpeed(),0));
				enemies[i][j] = e;
			}
		}
	}
	
	/**
	 * Creates the enemy bounding box based on the enemies 2d array length and enemy size
	 */
	private void generateEnemyBounds() {
		lBound = 0; rBound = 9;
		
		enemyBounds = new GameObject(
				enemies[0][0].getPosition(),
				enemies[0][0].getMotion(),
				(int) (enemies[0][enemies[0].length-1].getX()+enemies[0][enemies[0].length-1].getWidth()), 
				(int) (enemies[enemies.length-1][0].getY()+enemies[enemies.length-1][0].getHeight()), 
				enemies[0][0].getBaseSpeed()
			);
	}

	/**
	 * Creates a bullet fired by the player (set to go up)
	 */
	public void playerFire() {
		generateBullet(player);
	}

	/**
	 * Fires a bullet by a random enemy when the enemy cooldown has reached 0
	 */
	public void enemyFire() {
		if (enemyCooldown != 0) return;
		
		enemyCooldown = ENEMY_CD_DURATION;
		
		Random rand = new Random();
		int y = enemies.length-1;
		int x = rand.nextInt(enemies[0].length-1);
		boolean hasFired = false;
		while (!hasFired) {
			if (enemies[y][x].isAlive()) {
				generateBullet(enemies[y][x]);
				hasFired = true;
			} else {
				y--;
				if (y < 0) {
					y = enemies.length-1;
					x++;
				}
				if (x > 9) x = 0;
				
			}
		}
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
		if (bullets.size() <= 0) return;
		ArrayList<GameObject> delBullet = new ArrayList<GameObject>();

		// Loop through all enemies
		for (Enemy[] enemies : this.enemies) {
			for (Enemy e : enemies) {
				// If the enemy is already dead, skip
				if (!e.isAlive()) continue;
				// For each bullet, check if colliding with the enemy
				for (GameObject bullet : bullets) {
					if (!bullet.isColliding(e)) continue;
					delBullet.add(bullet);
					e.setHealth(0);
					rate -= 4;
					score += e.getValue();
					checkBounds();
				}
			}
		}
		
		for (GameObject bullet : bullets) {
			if (bullet.isColliding(player)) {
				lives--;
				score+=player.getValue();
				generatePlayer();
				if (lives == 0) state = GameState.STOPPED;
				delBullet.add(bullet);
			}
			if (bullet.getX() < 0 || 
				bullet.getX() > getGameWidth() || 
				bullet.getY() < 0 || 
				bullet.getY() > getGameHeight()
			) delBullet.add(bullet);
		}

		for (GameObject o : delBullet) bullets.remove(o);
	}
	
	/**
	 * Updates the player position checking to ensure it is within the game screen 
	 */
	private void updatePlayerPosition() {
		if (player.getX()+player.getMotion().getX() < 0) player.getMotion().setX(player.getMotion().getX() - player.getX());
		else if (player.getX()+player.getWidth()+player.getMotion().getX() > gameWidth) player.getMotion().setX(gameWidth-player.getX()+player.getWidth());
		else player.updatePosition();
	}

	/**
	 * Advances the enemy position, inverts its movements and dropping down when an edge is met
	 */
	private void updateEnemyPosition() {
		if (getTick() == 0 || tick == rate/2) {
			boolean invert = false;
			if (enemyBounds.getX()+enemyBounds.getMotion().getX()+enemyBounds.getWidth() > gameWidth || 
					enemyBounds.getX() + enemyBounds.getMotion().getX() < 0) 
				invert = true;
			for (Enemy[] enemies : getEnemies()) {
				for (Enemy enemy : enemies) {
					if (invert) {
						enemy.setMotion(new Vector(-enemy.getMotion().getX(),0));
						enemy.setPosition(new Vector(enemy.getX(), enemy.getY()+enemy.getHeight()/2));
					}
					enemy.updatePosition();
				}
			}
			if (invert) {
				enemyBounds.setMotion(new Vector(-enemyBounds.getMotion().getX(),0));
				enemyBounds.setPosition(new Vector(enemyBounds.getX(), enemyBounds.getY()+enemies[0][0].getHeight()/2d));

			}
			enemyBounds.updatePosition();
		}
	}
	
	/**
	 * Reduces the size of the bounding box to fit the enemies left alive
	 */
	private void checkBounds() {
		boolean anyAliveLeft = false, anyAliveRight = false;
		
		double widthDelta;
		
		for (int i = 0; i < enemies.length; i++) {
			if (enemies[i][lBound].isAlive()) anyAliveLeft = true;
			if (enemies[i][rBound].isAlive()) anyAliveRight = true;
		}
		
		if (anyAliveLeft && anyAliveRight) return;
		
		if (!anyAliveLeft && !anyAliveRight && (lBound == rBound)) {
			generateEnemies();
			generateEnemyBounds();
			rate = MAX_RATE;
			enemyCooldown = ENEMY_CD_DURATION*2;
			return;
		}
		
		if (!anyAliveLeft) {
			lBound+=1;
			widthDelta = enemies[0][lBound].getX() - enemyBounds.getX();
			enemyBounds.setPosition(new Vector(enemies[0][lBound].getX(), enemyBounds.getY()));
			enemyBounds.setWidth((int) (enemyBounds.getWidth()-widthDelta));
		}
		
		if (!anyAliveRight) {
			rBound-=1;
			widthDelta = (enemyBounds.getX()+enemyBounds.getWidth()) - (enemies[0][rBound].getX() + enemies[0][rBound].getWidth());
			enemyBounds.setWidth((int) (enemyBounds.getWidth()-widthDelta));
		}
		
		if (anyAliveLeft || anyAliveRight) checkBounds();
		
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
			if (scoreReturn != null) scoreReturn.onReturn(score);
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
		updatePlayerPosition();
		updateEnemyPosition();
		
		enemyFire();
		
		for (GameObject o : getBullets()) o.updatePosition();
		
		tick();
	}

}