package dev.jazer.project.invaders.game;

import dev.jazer.project.invaders.objects.GameObject;
import dev.jazer.project.invaders.objects.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * An extension of JavaFX's canvas object with set methods of drawing and clearing contents
 * from the graphics context
 * @author Jazer Barclay
 *
 */
public class GameCanvas extends Canvas {
	
	private GraphicsContext gc;
	private Color defaultColour;
	
	/**
	 * Creates a new canvas with a given width and height
	 * @param width
	 * @param height
	 */
	public GameCanvas(int width, int height) {
		gc = getGraphicsContext2D();
		defaultColour = Color.BLACK;
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * Sets the default clear colour (If not set, defaults to black)
	 * @param colour
	 */
	public void setDefaultColour(Color colour) {
		this.defaultColour = colour;
	}
	
	/**
	 * @return the default colour used to clear the screen
	 */
	public Color getDefaultColour() {
		return defaultColour;
	}
	
	/**
	 * Clears by filling the canvas with the default set colour
	 */
	public void clear() {
		gc.setFill(getDefaultColour());
		gc.fillRect(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * Draws an outline with a set colour around the given object
	 * @param outlineColour
	 * @param object
	 */
	public void drawOutline(Color outlineColour, GameObject object) {
		gc.setFill( outlineColour );
		gc.fillRect(object.getX(), object.getY(), object.getWidth(), 1);
		gc.fillRect(object.getX(), object.getY()+object.getHeight(), object.getWidth(), 1);
		gc.fillRect(object.getX(), object.getY(), 1, object.getHeight());
		gc.fillRect(object.getX()+object.getWidth(), object.getY(), 1, object.getHeight());
	}
	
	/**
	 * Draws a solid box using the given object's position and size
	 * @param fillColour
	 * @param object
	 */
	public void drawBox(Color fillColour, GameObject object) {
		gc.setFill(fillColour);
		gc.fillRect(object.getX(), object.getY(), object.getWidth(), object.getHeight());
	}
	
	/**
	 * Draws a box pattern at a given starting position and size using a 
	 * 2d grid with the provided fill colour
	 * 
	 * @param startPosition
	 * @param grid
	 * @param fillColour
	 * @param blockSize
	 */
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
	
	/**
	 * Draws a box pattern at the center of the X axis using a 2d grid with 
	 * the provided fill colour
	 * 
	 * @param centerPosition
	 * @param grid
	 * @param fillColour
	 * @param blockSize
	 */
	public void drawXCenteredGrid(Vector centerPosition, String[][] grid, Color fillColour, int blockSize) {
		double x = centerPosition.getX()-(blockSize*(grid[0].length/2.0));
		double y = centerPosition.getY()-(blockSize*(grid.length/2.0));
		drawGrid(new Vector(x,y), grid, fillColour, blockSize);
	}

}
