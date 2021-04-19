package dev.jazer.project.invaders;

import dev.jazer.project.invaders.game.ScoreReturnPromise;
import dev.jazer.project.invaders.screen.GameOverScreen;
import dev.jazer.project.invaders.screen.InvadersScreen;
import dev.jazer.project.invaders.screen.StartScreen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Main extends Application {
	
	private StartScreen startScreen;
	private InvadersScreen invadersScreen;
	private GameOverScreen gameOverScreen;
	
	public static void main(String[] args) {
		launch(args);
	}

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
		window.show();
		
	}

}
