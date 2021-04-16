package dev.jazer.project.invaders.objects;

/**
 * A simple euclidean vector that handles x and y axis double precision values
 * @author Jazer Barclay
 *
 */
public class Vector {
	
	/**
	 * Vector position on X axis
	 */
	private double x;
	/**
	 * Vector position on Y axis
	 */
	private double y;

	/* CONSTRUCTORS */
	
	/**
	 * Create a new vector with x and y set
	 * @param x - Vector position on X axis
	 * @param y - Vector position on Y axis
	 */
	public Vector(double x, double y) {
		setX(x);
		setY(y);
	}

	/**
	 * Create a new vector at (0, 0)
	 */
	public Vector() {
		setX(0);
		setY(0);
	}
	

	/* GETTERS AND SETTERS */
	
	/**
	 * Set vector coordinates to given values
	 * @param x - Vector position on X axis
	 * @param y - Vector position on Y axis
	 */
	public void set(double x, double y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Set vectors X axis value to given value
	 * @param x - Vector position on X axis
	 */
	public void setX(double x) {
		this.x = x;
	}

	
	/**
	 * Set vectors Y axis value to given value
	 * @param y - Vector position on Y axis
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * @return The value of the vector on the X axis
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return The value of the vector on the Y axis
	 */
	public double getY() {
		return y;
	}


	/* CALCULATIONS */

	/**
	 * Adds the given vector value to this vector and returns the new vector
	 * @param v - Vector to be added to the current vector
	 * @return A new vector that is the sum of the two vectors
	 */
	public Vector add(Vector v) {
		return new Vector(x+v.x, y+v.y);
	}
	

	/* GLOBAL (STATIC) METHODS */
	
	/**
	 * Returns a new vector that is the sum of the two given vector values
	 * @param v1 - First vector
	 * @param v2 - Second vector
	 * @return The sum of the two given vector values
	 */
	public static Vector addVectors(Vector v1, Vector v2) {
		return new Vector(v1.getX()+v2.getX(), v1.getY()+v2.getY());
	}
	
}
