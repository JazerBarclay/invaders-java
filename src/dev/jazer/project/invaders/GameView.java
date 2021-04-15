package dev.jazer.project.invaders;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GameView {

	private GameModel model;
	
	private Stage window;
	private Canvas gameCanvas;
	private Pane layout;
	private Scene screen;
	
	private Label score, lives;
	
	public GameView(Stage window, GameModel model) {
		this.window = window;
		this.model = model;
		this.gameCanvas = new Canvas(model.getGameWidth(), model.getGameHeight());
		
		initDisplay();
	}
	
	private void initDisplay() {
		layout = new Pane();
		screen = new Scene(layout, 1200, 900);
		layout.getChildren().add(gameCanvas);
		window.setScene(screen);
		
		// Create the score label which is displayed in the bottom right
		score = new Label(""+model.getScore());
		score.setPrefWidth(model.getGameWidth()-20);
		score.setTranslateY(model.getGameHeight()-60);
		score.setFont(new Font("Arial", 48));
		score.setTextFill(Color.LIGHTGREY);
		score.setTextAlignment(TextAlignment.RIGHT);
		score.setAlignment(Pos.CENTER_RIGHT);
		layout.getChildren().add(score);

		// Create the lives label which is displayed in the bottom left
		lives = new Label(""+model.getLives());
		lives.setTranslateY(model.getGameHeight()-60);
		lives.setFont(new Font("Arial", 48));
		lives.setTextFill(Color.LIGHTGREY);
		layout.getChildren().add(lives);
		
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
		screen.setOnKeyPressed(controller.keyPressedHandler());
		screen.setOnKeyReleased(controller.keyReleasedHandler());
	}
	
	/**
	 * Draws the model objects to the screen based on the game state
	 */
	public void render() {
		clearGameCanvas();
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		
		// Draw player
		gc.setFill(Color.WHITE);
		Player p = model.getPlayer();
		gc.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
		
		// Draw enemies
		for (Enemy[] enemies : model.getEnemies()) {
			for (Enemy e : enemies) {
				if (e.isAlive()) gc.fillRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
			}
		}
		
		for (GameObject o : model.getBullets()) {
			gc.fillRect(o.getX(), o.getY(), o.getWidth(), o.getHeight());
		}

		gc = gameCanvas.getGraphicsContext2D();
		gc.setFill( Color.BLACK );
		gc.fillRect(0, model.getGameHeight()-65, model.getGameWidth(), 65);
		gc.setFill( Color.GREY );
		gc.fillRect(0, model.getGameHeight()-65, model.getGameWidth(), 1);
		
		lives.setText(model.getLives()+"");
		score.setText(model.getScore()+"");
		
	}
	
}
