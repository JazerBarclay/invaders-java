package dev.jazer.project.invaders.screen;

import dev.jazer.project.invaders.game.GameCanvas;
import dev.jazer.project.invaders.objects.Vector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartScreen {
	
	private Pane pane;
	private Label lblTitle;
	private Button btnPlay;
	private Button btnExit;
	
	private Stage window;
	private Scene scene;
	
	public StartScreen(Stage window, int width, int height) {
		
		this.window = window;
		
		pane = new Pane();
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		GameCanvas c = new GameCanvas(width, height);
		pane.getChildren().add(c);
		
		c.drawXCenteredGrid(new Vector(width/2, height/2-180), new String[][] {
			{"x","x","x","x","x", "", "x"," "," "," ","x", "", "x"," "," "," ","x", "", " "," ","x"," "," ", "", "x","x","x","x"," ", "", "x","x","x","x","x", "", "x","x","x","x"," ", "", "x","x","x","x","x"},
			{" "," ","x"," "," ", "", "x","x"," "," ","x", "", "x"," "," "," ","x", "", " ","x"," ","x"," ", "", "x"," "," "," ","x", "", "x"," "," "," "," ", "", "x"," "," "," ","x", "", "x"," "," "," "," "},
			{" "," ","x"," "," ", "", "x"," ","x"," ","x", "", "x"," "," "," ","x", "", "x"," "," "," ","x", "", "x"," "," "," ","x", "", "x","x","x"," "," ", "", "x","x","x","x"," ", "", "x","x","x","x","x"},
			{" "," ","x"," "," ", "", "x"," "," ","x","x", "", " ","x"," ","x"," ", "", "x","x","x","x","x", "", "x"," "," "," ","x", "", "x"," "," "," "," ", "", "x"," "," "," ","x", "", " "," "," "," ","x"},
			{"x","x","x","x","x", "", "x"," "," "," ","x", "", " "," ","x"," "," ", "", "x"," "," "," ","x", "", "x","x","x","x"," ", "", "x","x","x","x","x", "", "x"," "," "," ","x", "", "x","x","x","x","x"},
				}, Color.WHITE, 20);
		
		lblTitle = new Label("I N V A D E R S");
		lblTitle.setAlignment(Pos.CENTER);

		lblTitle.setPrefWidth(width);
//		lblTitle.setLayoutX(model.getWindowWidth()/2-400/2);
		lblTitle.setLayoutY(220);
		lblTitle.setFont(new Font("Arial", 72));
		lblTitle.setTextFill(Color.WHITE);
//		pane.getChildren().add(lblTitle);
		
		btnPlay = new Button("PLAY");
		btnPlay.setFont(new Font("Arial", 28));
		btnPlay.setPrefWidth(400);
		btnPlay.setPrefHeight(100);
		btnPlay.setLayoutX(width/2-400/2);
		btnPlay.setLayoutY(400);
		pane.getChildren().add(btnPlay);

		btnExit = new Button("EXIT");
		btnExit.setFont(new Font("Arial", 28));
		btnExit.setPrefWidth(400);
		btnExit.setPrefHeight(100);
		btnExit.setLayoutX(width/2-400/2);
		btnExit.setLayoutY(550);
		pane.getChildren().add(btnExit);
		
		btnPlay.setOnAction((ActionEvent event) -> { new InvadersScreen(window).play(); });
		btnExit.setOnAction((ActionEvent event) -> System.exit(0));
		
		scene = new Scene(pane);

	}
	
	public void show() {
		Platform.runLater(() -> window.setScene(scene));
	}
	
	public void setOnPlayHandler(EventHandler<ActionEvent> eventHandler) {
		btnPlay.setOnAction(eventHandler);
	}

	public void setOnExitHandler(EventHandler<ActionEvent> e) {
		btnExit.setOnAction(e);
	}
	
}
