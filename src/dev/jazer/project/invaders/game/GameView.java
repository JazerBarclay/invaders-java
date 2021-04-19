package dev.jazer.project.invaders.game;

import java.io.File;

import dev.jazer.project.invaders.objects.Enemy;
import dev.jazer.project.invaders.objects.GameObject;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GameView {

	private GameModel model;
	
	private Stage window;
	private GameCanvas canvas;
	private Pane layout;
	private Scene screen;
	
	private Label score, lives;
	
	public GameView(Stage window, GameModel model) {
		this.window = window;
		this.model = model;
		this.canvas = new GameCanvas(model.getGameWidth(), model.getGameHeight());
		
		layout = new Pane();
		screen = new Scene(layout, 1200, 900);
		layout.getChildren().add(canvas);
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
		
		canvas.clear();
	}
	
	/**
	 * Make the stage (window) visible
	 */
	public void show() {
		Platform.runLater(() -> window.setScene(screen));
	}

	/**
	 * Set the controller that contains the input event handlers
	 * @param controller
	 */
	public void setController(GameController controller) {
		screen.setOnKeyPressed(controller.keyPressedHandler());
		screen.setOnKeyReleased(controller.keyReleasedHandler());
	}
	
	/**
	 * Plays a 'wav' audio file stored in the res folder
	 * @param audioFile - Name of the file (omitting file extension)
	 */
	public static synchronized void playWAV(String audioFile) {
		AudioClip clip = new AudioClip(new File("res/" + audioFile + ".wav").toURI().toString());
		clip.play();
	}
	
	/**
	 * Plays enemy movement sound
	 */
	public static void playMoveSound() {
		playWAV("move");
	}

	/**
	 * Plays bullet fire sound
	 */
	public static void playBulletSound() {
		playWAV("bullet");
	}

	
	private void drawBullet(GameObject bullet) {
		canvas.drawBox(Color.GREY, bullet);
	}
	
	private void drawPlayer() {
		canvas.drawGrid(model.getPlayer().getPosition(), 
			new String[][] {
				{" "," ","x"," "," "},
				{"x","x","x","x","x"}
			}
			, Color.WHITE, 10);
	}
	
	private void drawSquid(Enemy e) {
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
		canvas.drawGrid(e.getPosition(), grid, Color.WHITE, 10);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill( Color.BLACK );
		gc.fillRect(e.getX()+15+offset, e.getY()+20, 5, 5);
		gc.fillRect(e.getX()+35+offset, e.getY()+20, 5, 5);
	}
	
	private void drawLoader(Enemy e) {
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
		canvas.drawGrid(e.getPosition(), grid, Color.WHITE, 10);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill( Color.BLACK );
		gc.fillRect(e.getX()+20+offset, e.getY()+35, 5, 5);
		gc.fillRect(e.getX()+40+offset, e.getY()+35, 5, 5);
	}
	
	private void drawBunny(Enemy e) {
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
		canvas.drawGrid(e.getPosition(), grid, Color.WHITE, 10);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill( Color.BLACK );
		gc.fillRect(e.getX()+20+offset, e.getY()+25, 5, 5);
		gc.fillRect(e.getX()+40+offset, e.getY()+25, 5, 5);
	}
	
	/**
	 * Draws the bounds object to the game canvas
	 * @param bounds - The bounds object
	 */
	private void paintBounds(GameObject bounds) {
		canvas.drawOutline(Color.RED, bounds);
	}
	
	private void paintDash() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill( Color.BLACK );
		gc.fillRect(0, model.getGameHeight()-65, model.getGameWidth(), 65);
		gc.setFill( Color.GREY );
		gc.fillRect(0, model.getGameHeight()-65, model.getGameWidth(), 1);
	}
	
	/**
	 * Draws the model objects to the screen based on the game state
	 */
	public void render() {
		canvas.clear();
		
		// Draw player
		drawPlayer();
		
		// Draw enemies
		for (Enemy[] enemies : model.getEnemies()) {
			for (Enemy e : enemies) {
				if (!e.isAlive()) continue;
				switch (e.getType()) {
					case SQUID:
						drawSquid(e);
						break;
					case LOADER:
						drawLoader(e);
						break;
					case BUNNY:
						drawBunny(e);
						break;
					default:
						canvas.drawBox(Color.RED, e);
				}
			}
		}
		
		// If developer mode is set to true, draw the enemy bounds
		if (model.isDevmode()) paintBounds(model.getEnemyBounds());
		
		for (GameObject o : model.getBullets()) {
			drawBullet(o);
		}

		paintDash();
		
		lives.setText("Lives: " + model.getLives());
		score.setText("Score: " + model.getScore());
		
	}
	
}
