package dev.jazer.project.invaders;

public class GameModel {

	private int gameWidth, gameHeight;
	
	public GameModel(int screenWidth, int screenHeight) {
		this.gameWidth = screenWidth;
		this.gameHeight = screenHeight;
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}
	
	public void run() {
		// TODO: Add update loop until game ends
	}
	
}
