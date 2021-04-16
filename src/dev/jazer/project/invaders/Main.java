package dev.jazer.project.invaders;

import dev.jazer.project.invaders.game.ScoreReturnPromise;
import dev.jazer.project.invaders.screen.InvadersScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application implements ScoreReturnPromise {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {
		
		InvadersScreen i = new InvadersScreen(window);
		i.play();
		
	}

	@Override
	public void onReturn(int value) {
		System.out.println("Score = " + value);
	}

}
