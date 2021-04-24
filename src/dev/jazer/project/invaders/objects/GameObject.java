package dev.jazer.project.invaders.objects;

public class GameObject {

	private Vector position, motion;
	private int width, height;
	private double baseSpeed;
	private boolean visible;
	
	/* CONSTRUCTORS */

	/**
	 * Creates a new game object
	 * @param position - Vector starting position
	 * @param motion - Movement vector per frame
	 * @param width - Object width
	 * @param height - Object height
	 * @param baseSpeed - Object base movement speed
	 */
	public GameObject(Vector position, Vector motion, int width, int height, double baseSpeed) {
		this.position = position;
		this.motion = motion;
		this.width = width;
		this.height = height;
		this.baseSpeed = baseSpeed;
		this.visible = true;
	}
	
	/**
	 * Creates a new game object with a movement vector of (0,0)
	 * @param x - x axis starting position
	 * @param y - y axis starting position
	 * @param width - Object width
	 * @param height - Object height
	 * @param baseSpeed - Object base movement speed
	 */
	public GameObject(int x, int y, int width, int height, double baseSpeed) {
		this(new Vector(x, y), new Vector(), width, height, baseSpeed);
	}
	
	/* GETTERS AND SETTERS */
	
	/**
	 * @return the object's position vector
	 */
	public Vector getPosition() {
		return position;
	}
	
	/**
	 * @return the x axis position
	 */
	public double getX() {
		return position.getX();
	}
	
	/**
	 * @return the y axis position
	 */
	public double getY() {
		return position.getY();
	}
	
	/**
	 * Sets the position vector
	 * @param position
	 */
	public void setPosition(Vector position) {
		this.position = position;
	}
	
	/**
	 * Sets the x axis position
	 * @param x
	 */
	public void setX(double x) {
		this.position.setX(x);
	}

	/**
	 * Sets the y axis position
	 * @param y
	 */
	public void setY(double y) {
		this.position.setY(y);
	}
	
	/**
	 * @return the motion vector
	 */
	public Vector getMotion() {
		return motion;
	}

	/**
	 * Sets the motion vector
	 * @param motion
	 */
	public void setMotion(Vector motion) {
		this.motion = motion;
	}
	
	/**
	 * @return the base movement speed
	 */
	public double getBaseSpeed() {
		return baseSpeed;
	}
	
	/**
	 * Sets the base movement speed
	 * @param speed
	 */
	public void setBaseSpeed(double speed) {
		this.baseSpeed = speed;
	}
	
	/**
	 * @return the width of the object
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Sets the width of the object
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @return the height of the object
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the height of the object
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * @return if the object is visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the visibility of the object
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
	/* CALCULATIONS */
	
	/**
	 * @return the new position the object will be after moving
	 */
	public Vector nextPosition() {
		position.add(motion);
		return position;
	}
	
	/**
	 * Updates the objects position
	 */
	public void updatePosition() {
		this.setPosition(position.add(motion));
	}
	
	/**
	 * Checks if the object parsed will collide with this object once it has moved
	 * @param obj - The other object
	 * @return true if they collide otherwise false
	 */
	public boolean isColliding(GameObject obj) {
		// Flags
		boolean isIntersectingX = false,
				isIntersectingY = false;

		// Check if next position object will take will intersect x and/or y
		if (getX() < obj.getX()+obj.getWidth() && getX()+getWidth() > obj.getX()) isIntersectingX = true;
		if (getY() > obj.getY()+obj.getHeight() && getY()+getHeight() < obj.getY()) isIntersectingY = true;

		// If x and y are intersecting, return true. If not then continue to return false
		if (isIntersectingX && isIntersectingY) return true;
		return false;
	}
	
	/**
	 * Checks if the object parsed will collide with this object once it has moved
	 * @param obj - The other object
	 * @return true if they collide otherwise false
	 */
	public boolean willCollide(GameObject obj) {
		// Flags
		boolean willIntersectX = false,
				willIntersectY = false;

		// Check if next position object will take will intersect x and/or y
		if (getX()+getMotion().getX() < obj.getX()+obj.getWidth() && getX()+getMotion().getX()+getWidth() > obj.getX()) willIntersectX = true;
		if (getY()+getMotion().getY() < obj.getY()+obj.getHeight() && getY()+getMotion().getY()+getHeight() > obj.getY()) willIntersectY = true;

		// If x and y are intersecting, return true. If not then continue to return false
		if (willIntersectX && willIntersectY) return true;
		return false;
	}
	
}
