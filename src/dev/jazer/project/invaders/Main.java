package dev.jazer.project.invaders;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {
		
		GameModel model = new GameModel(1200, 900);
		GameView view = new GameView(window, model);
		GameController controller = new GameController(model);

		view.setController(controller);
		view.show();
		
		model.startGame(view, controller);
		
	}

}
