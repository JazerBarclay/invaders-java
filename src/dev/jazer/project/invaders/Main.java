package dev.jazer.project.invaders;

import dev.jazer.project.invaders.game.ScoreReturnPromise;
import dev.jazer.project.invaders.screen.GameOverScreen;
import dev.jazer.project.invaders.screen.InvadersScreen;
import dev.jazer.project.invaders.screen.StartScreen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Entry point of the Invaders game where the JavaFX is set up
 * @author Jazer Barclay
 */
public class Main extends Application {
	
	private StartScreen startScreen;
	private InvadersScreen invadersScreen;
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
		Logger.info(this, "--- Starting Initialisation ---");
		
		Logger.info(this, "Initialising start screen");
		startScreen = new StartScreen(window, 1200, 900);
		startScreen.setOnPlayHandler(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Logger.info(Main.this, "Play button pressed");
				invadersScreen.play();
			}
		});

		Logger.info(this, "Initialising game screen");
		invadersScreen = new InvadersScreen(window, new ScoreReturnPromise() {
			@Override
			public void onReturn(int value) {
				gameOverScreen.show(value);
			}
		});
		
		Logger.info(this, "Initialising game over screen");
		gameOverScreen = new GameOverScreen(window, 1200, 900);
		gameOverScreen.setOnRetryHandler(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				invadersScreen.reset();
				invadersScreen.play();
			}
		});
		
		Logger.info(this, "Setting window to start screen");
		startScreen.show();
		
		window.setResizable(false);
		
		window.setOnCloseRequest((WindowEvent event) -> {
			Logger.warn(Main.this, "Exiting Game! Closing window");
		});
		
		Logger.info(this, "Displaying window");
		window.show();
		
		Logger.info(this, "--- Finished Initialisation ---");
	}

}
