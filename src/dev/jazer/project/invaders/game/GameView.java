package dev.jazer.project.invaders.game;

import dev.jazer.project.invaders.objects.Enemy;
import dev.jazer.project.invaders.objects.GameObject;
import dev.jazer.project.invaders.objects.Player;
import dev.jazer.project.invaders.objects.Vector;
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
		lives.setTranslateX(20);
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
	 * Set the controller that contains the input event handlers
	 * @param controller
	 */
	public void setController(GameController controller) {
		screen.setOnKeyPressed(controller.keyPressedHandler());
		screen.setOnKeyReleased(controller.keyReleasedHandler());
	}
	
	private void drawBox(GraphicsContext gc, GameObject obj) {
		gc.setFill(Color.LIGHTGRAY);
		gc.fillRect(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
	}
	
	private void drawGrid(Canvas canvas, Vector startPosition, String[][] grid, Color fillColour, int blockSize) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		double x = startPosition.getX();
		double y = startPosition.getY();
		gc.setFill(fillColour);
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].equals("x")) gc.fillRect(x+(j*blockSize), y+(i*blockSize), blockSize, blockSize);
			}
		}
	}
	
	private void drawBullet(GameObject bullet) {
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.setFill( Color.WHITE );
		gc.fillRect( bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight() );
	}
	
	private void drawPlayer(GraphicsContext gc, Player p) {
		gc.setFill( Color.WHITE );
		gc.fillRect( p.getX()+((p.getWidth()/5)*2), p.getY(), p.getWidth()/5, p.getHeight()/2 );
		gc.fillRect( p.getX(), p.getY()+p.getHeight()/2, p.getWidth(), p.getHeight()/2 );	
	}
	
	private void drawSquid(GraphicsContext gc, Enemy e) {
		int offset = 0;
		String[][] grid = new String[][] {
			{" "," ","x","x"," "," "},
			{" ","x","x","x","x"," "},
			{"x","x","x","x","x","x"},
			{"x","x","x","x","x","x"},
			{" ","x"," ","x"," ","x"},
		};
		if (model.getTick() > model.getRate()/2) {
			grid = new String[][] {
				{" "," ","x","x"," "," "},
				{" ","x","x","x","x"," "},
				{"x","x","x","x","x","x"},
				{"x","x","x","x","x","x"},
				{"x"," ","x"," ","x"," "},
			};
			offset = 5;
		}
		drawGrid(gameCanvas, e.getPosition(), grid, Color.WHITE, 10);
		gc.setFill( Color.BLACK );
		gc.fillRect(e.getX()+15+offset, e.getY()+20, 5, 5);
		gc.fillRect(e.getX()+35+offset, e.getY()+20, 5, 5);
	}
	
	private void drawLoader(GraphicsContext gc, Enemy e) {
		int offset = 0;
		String[][] grid = new String[][] {
			{"x","x"," "," "," "," "},
			{"x"," "," "," ","x","x"},
			{"x"," "," "," "," ","x"},
			{"x","x","x","x","x","x"},
			{"x","x","x","x","x","x"},
		};
		if (model.getTick() > model.getRate()/2) {
			grid = new String[][] {
				{" "," "," "," ","x","x"},
				{"x","x"," "," "," ","x"},
				{"x"," "," "," "," ","x"},
				{"x","x","x","x","x","x"},
				{"x","x","x","x","x","x"},
			};
			offset = -5;
		}
		drawGrid(gameCanvas, e.getPosition(), grid, Color.WHITE, 10);
		gc.setFill( Color.BLACK );
		gc.fillRect(e.getX()+20+offset, e.getY()+35, 5, 5);
		gc.fillRect(e.getX()+40+offset, e.getY()+35, 5, 5);
	}
	
	private void drawBunny(GraphicsContext gc, Enemy e) {
		int offset = 0;
		String[][] grid = new String[][] {
			{" ","x","x"," ","x","x"},
			{" ","x"," "," ","x"," "},
			{"x","x","x","x","x"," "},
			{" ","x","x","x","x","x"},
			{" ","x"," "," ","x"," "},
		};
		if (model.getTick() > model.getRate()/2) {
			grid = new String[][] {
				{"x","x"," ","x","x"," "},
				{" ","x"," "," ","x"," "},
				{" ","x","x","x","x","x"},
				{"x","x","x","x","x"," "},
				{" ","x"," "," ","x"," "},
			};
			offset = -5;
		}
		drawGrid(gameCanvas, e.getPosition(), grid, Color.WHITE, 10);
		gc.setFill( Color.BLACK );
		gc.fillRect(e.getX()+20+offset, e.getY()+25, 5, 5);
		gc.fillRect(e.getX()+40+offset, e.getY()+25, 5, 5);
	}
	
	/**
	 * Draws the bounds object to the game canvas
	 * @param bounds - The bounds object
	 */
	private void paintBounds(GameObject bounds) {
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.setFill( Color.RED );
		gc.fillRect(bounds.getX(), bounds.getY(), bounds.getWidth(), 1);
		gc.fillRect(bounds.getX(), bounds.getY()+bounds.getHeight(), bounds.getWidth(), 1);
		gc.fillRect(bounds.getX(), bounds.getY(), 1, bounds.getHeight());
		gc.fillRect(bounds.getX()+bounds.getWidth(), bounds.getY(), 1, bounds.getHeight());
	}
	
	private void paintDash(GraphicsContext gc) {
		gc.setFill( Color.BLACK );
		gc.fillRect(0, model.getGameHeight()-65, model.getGameWidth(), 65);
		gc.setFill( Color.GREY );
		gc.fillRect(0, model.getGameHeight()-65, model.getGameWidth(), 1);
	}
	
	/**
	 * Draws the model objects to the screen based on the game state
	 */
	public void render() {
		clearGameCanvas();
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		
		// Draw player
		drawPlayer(gc, model.getPlayer());
		
		// Draw enemies
		for (Enemy[] enemies : model.getEnemies()) {
			for (Enemy e : enemies) {
				if (!e.isAlive()) continue;
				switch (e.getType()) {
					case SQUID:
						drawSquid(gc, e);
						break;
					case LOADER:
						drawLoader(gc, e);
						break;
					case BUNNY:
						drawBunny(gc, e);
						break;
					default:
						drawBox(gc, e);
				}
			}
		}
		
		// If developer mode is set to true, draw the enemy bounds
		if (model.isDevmode()) paintBounds(model.getEnemyBounds());
		
		for (GameObject o : model.getBullets()) {
			drawBullet(o);
		}

		paintDash(gc);
		
		lives.setText("Lives: " + model.getLives());
		score.setText("Score: " + model.getScore());
		
	}
	
}
