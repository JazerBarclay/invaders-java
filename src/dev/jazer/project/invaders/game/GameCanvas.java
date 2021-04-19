package dev.jazer.project.invaders.game;

import dev.jazer.project.invaders.objects.GameObject;
import dev.jazer.project.invaders.objects.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameCanvas extends Canvas {
	
	private GraphicsContext gc;
	private Color defaultColour;
	
	public GameCanvas(int width, int height) {
		gc = getGraphicsContext2D();
		defaultColour = Color.WHITE;
		setWidth(width);
		setHeight(height);
	}
	
	public void setDefaultColour(Color colour) {
		this.defaultColour = colour;
	}
	
	public Color getDefaultColour() {
		return defaultColour;
	}
	
	public void clear() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void drawOutline(Color outlineColour, GameObject object) {
		gc.setFill( outlineColour );
		gc.fillRect(object.getX(), object.getY(), object.getWidth(), 1);
		gc.fillRect(object.getX(), object.getY()+object.getHeight(), object.getWidth(), 1);
		gc.fillRect(object.getX(), object.getY(), 1, object.getHeight());
		gc.fillRect(object.getX()+object.getWidth(), object.getY(), 1, object.getHeight());
	}
	
	public void drawBox(Color fillColour, GameObject object) {
		gc.setFill(fillColour);
		gc.fillRect(object.getX(), object.getY(), object.getWidth(), object.getHeight());
	}
	
	public void drawGrid(Vector startPosition, String[][] grid, Color fillColour, int blockSize) {
		double x = startPosition.getX();
		double y = startPosition.getY();
		gc.setFill(fillColour);
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].equals("x")) gc.fillRect(x+(j*blockSize), y+(i*blockSize), blockSize, blockSize);
			}
		}
	}
	
	public void drawXCenteredGrid(Vector centerPosition, String[][] grid, Color fillColour, int blockSize) {
		double x = centerPosition.getX()-(blockSize*(grid[0].length/2.0));
		double y = centerPosition.getY()-(blockSize*(grid.length/2.0));
		drawGrid(new Vector(x,y), grid, fillColour, blockSize);
	}
	
	

}
