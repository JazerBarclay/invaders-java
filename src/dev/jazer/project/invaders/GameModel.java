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
				if (i <= 1) e = new Enemy(EnemyType.BUNNY);
				if (i > 1 && i <= 3) e = new Enemy(EnemyType.LOADER);
				if (i == 4) e = new Enemy(EnemyType.SQUID);
				e.setPosition(new Vector(e.getWidth()*j+j*30, e.getHeight()*i+i*20+10));
				e.setBaseSpeed(0);
				e.setMotion(new Vector(e.getBaseSpeed(),0));
				enemies[i][j] = e;
			}
		}
	}
	
	private void generateBullet(Entity e) {
		System.out.println("Pew Pew");
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
		if (tick == 0 || tick == rate/2) {
			generateBullet(player);
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
