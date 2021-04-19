package dev.jazer.project.invaders;

import dev.jazer.project.invaders.game.ScoreReturnPromise;
import dev.jazer.project.invaders.screen.GameOverScreen;
import dev.jazer.project.invaders.screen.InvadersScreen;
import dev.jazer.project.invaders.screen.StartScreen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Entry point of the Invaders game where the JavaFX is set up
 * @author Jazer Barclay
 */
public class Main extends Application {
	
	/**
	 * The starting screen at the beginning of the game
	 */
	private StartScreen startScreen;
	
	/**
	 * The main game screen where the game runs
	 */
	private InvadersScreen invadersScreen;
	
	/**
	 * The game over screen that displays the final score
	 */
	private GameOverScreen gameOverScreen;
	
	/**
	 * Entry point
	 * @param args - Command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Entry point of JavaFX
	 */
	@Override
	public void start(Stage window) throws Exception {
		startScreen = new StartScreen(window, 1200, 900);
		startScreen.setOnPlayHandler(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				invadersScreen.play();
			}
		});
		
		invadersScreen = new InvadersScreen(window, new ScoreReturnPromise() {
			@Override
			public void onReturn(int value) {
				gameOverScreen.show(value);
			}
		});
		
		gameOverScreen = new GameOverScreen(window, 1200, 900);
		
		startScreen.show();
		window.setResizable(false);
		window.show();
		
	}

}
