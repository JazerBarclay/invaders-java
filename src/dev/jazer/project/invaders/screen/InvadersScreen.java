package dev.jazer.project.invaders.screen;

import dev.jazer.project.invaders.Logger;
import dev.jazer.project.invaders.game.GameController;
import dev.jazer.project.invaders.game.GameModel;
import dev.jazer.project.invaders.game.GameView;
import dev.jazer.project.invaders.game.ScoreReturnPromise;
import javafx.stage.Stage;

/**
 * 
 * @author Jazer Barclay
 *
 */
public class InvadersScreen {

	private Stage window;
	
	private GameModel model;
	private GameView view;
	private GameController controller;
	
	private ScoreReturnPromise promise;
	
	public InvadersScreen(Stage window) {
		this(window, null);
	}
	
	public InvadersScreen(Stage window, ScoreReturnPromise promise) {
		this.window = window;
		this.promise = promise;
		reset();
	}
	
	public void reset() {
		Logger.info(this, "MVC Set/Reset");
		model = new GameModel(1200, 900, promise);
		view = new GameView(window, model);
		controller = new GameController(model);
	}
	
	public void play() {
		Logger.info(this, "--- Launching Game ---");
		view.setController(controller);
		view.show();
		model.startGame(view, controller);
	}
	
}
