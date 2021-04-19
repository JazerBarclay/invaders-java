package dev.jazer.project.invaders.screen;

import dev.jazer.project.invaders.game.GameCanvas;
import dev.jazer.project.invaders.game.GameView;
import dev.jazer.project.invaders.objects.Vector;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameOverScreen {

	private Stage window;
	private Scene scene;
	private Pane pane;
	private GameCanvas c;
	private Label lblTitle, lblScore;
	
	public GameOverScreen(Stage window, int width, int height) {
		this.window = window;
		
		pane = new Pane();
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		c = new GameCanvas(width, height);
		pane.getChildren().add(c);
		
		c.drawXCenteredGrid(new Vector(width/2, height/2-180), new String[][] {
			{"x","x","x","x","x", "", " "," ","x"," "," ", "", "x"," "," "," ","x", "", "x","x","x","x","x"},
			{"x"," "," "," "," ", "", " ","x"," ","x"," ", "", "x","x"," ","x","x", "", "x"," "," "," "," "},
			{"x"," ","x","x","x", "", "x"," "," "," ","x", "", "x"," ","x"," ","x", "", "x","x","x"," "," "},
			{"x"," "," "," ","x", "", "x","x","x","x","x", "", "x"," "," "," ","x", "", "x"," "," "," "," "},
			{"x","x","x","x","x", "", "x"," "," "," ","x", "", "x"," "," "," ","x", "", "x","x","x","x","x"},
			{" "," "," "," "," ", "", " "," "," "," "," ", "", " "," "," "," "," ", "", " "," "," "," "," "},
			{"x","x","x","x","x", "", "x"," "," "," ","x", "", "x","x","x","x","x", "", "x","x","x","x"," "},
			{"x"," "," "," ","x", "", "x"," "," "," ","x", "", "x"," "," "," "," ", "", "x"," "," "," ","x"},
			{"x"," "," "," ","x", "", "x"," "," "," ","x", "", "x","x","x"," "," ", "", "x","x","x","x"," "},
			{"x"," "," "," ","x", "", " ","x"," ","x"," ", "", "x"," "," "," "," ", "", "x"," "," "," ","x"},
			{"x","x","x","x","x", "", " "," ","x"," "," ", "", "x","x","x","x","x", "", "x"," "," "," ","x"},
				}, Color.WHITE, 20);
		
		lblTitle = new Label("SCORE");
		lblTitle.setAlignment(Pos.CENTER);

		lblTitle.setPrefWidth(width);
//		lblTitle.setLayoutX(model.getWindowWidth()/2-400/2);
		lblTitle.setLayoutY(420);
		lblTitle.setFont(new Font("Arial", 72));
		lblTitle.setTextFill(Color.WHITE);
		pane.getChildren().add(lblTitle);
		
		lblScore = new Label("");
		lblScore.setAlignment(Pos.CENTER);
		lblScore.setPrefWidth(width);
		lblScore.setLayoutY(520);
		lblScore.setFont(new Font("Arial", 72));
		lblScore.setTextFill(Color.WHITE);
		
		pane.getChildren().add(lblScore);
		
		scene = new Scene(pane);
		
	}
	
	public void show(int score) {
		GameView.playWAV("lose");
		lblScore.setText(""+score);
		Platform.runLater(() -> window.setScene(scene));
	}
	
}
