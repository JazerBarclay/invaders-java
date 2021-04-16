package dev.jazer.project.invaders.screen;

import dev.jazer.project.invaders.game.GameController;
import dev.jazer.project.invaders.game.GameModel;
import dev.jazer.project.invaders.game.GameView;
import dev.jazer.project.invaders.game.ScoreReturnPromise;
import javafx.stage.Stage;

public class InvadersScreen {

	private GameModel model;
	private GameView view;
	private GameController controller;
	
	public InvadersScreen(Stage window) {
		this(window, null);
	}
	
	public InvadersScreen(Stage window, ScoreReturnPromise promise) {
		model = new GameModel(1200, 900, promise);
		view = new GameView(window, model);
		controller = new GameController(model);
		
		view.setController(controller);
	}
	
	public void play() {
		view.show();
		model.startGame(view, controller);
	}
	
}
