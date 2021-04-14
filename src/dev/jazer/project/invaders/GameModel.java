package dev.jazer.project.invaders;

import javafx.application.Platform;

public class GameModel {

	private GameState state;
	
	// Screen dimensions
	private int gameWidth, gameHeight;
	
	// Player data
	private Player player;
	
	public GameModel(int screenWidth, int screenHeight) {
		this.gameWidth = screenWidth;
		this.gameHeight = screenHeight;
		this.player = new Player(0,gameHeight-new Player(0,0).getHeight());
		this.state = GameState.RUNNING;
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
		player.updatePosition();
		System.out.println(player.getMotion().getX());
	}
	
}
