package dev.jazer.project.invaders;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameView {

	private Stage window;
	private Canvas gameCanvas;
	private Pane layout;
	private Scene screen;
	
	public GameView(Stage window, GameModel model) {
		this.window = window;
		this.gameCanvas = new Canvas(model.getGameWidth(), model.getGameHeight());
		
		initDisplay();
	}
	
	private void initDisplay() {
		layout = new Pane();
		screen = new Scene(layout, 1200, 900);
		layout.getChildren().add(gameCanvas);
		window.setScene(screen);
		
		clearGameCanvas();
	}
	
	/**
	 * Clears the canvas containing game sprites
	 */
	public void clearGameCanvas() {
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
	}
	
	/**
	 * Make the stage (window) visible
	 */
	public void show() {
		window.show();
	}

	/**
	 * Set the controller and assign input event handlers
	 * @param controller - The controller
	 */
	public void setController(GameController controller) {
		
	}
	
}
