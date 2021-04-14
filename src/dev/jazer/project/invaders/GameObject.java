package dev.jazer.project.invaders;

public class GameObject {

	private Vector position, motion;
	private int width, height;
	private double baseSpeed;
	private boolean visible;
	
	/* CONSTRUCTORS */
	
	public GameObject(int x, int y, int width, int height, double baseSpeed) {
		this.position = new Vector(x, y);
		this.width = width;
		this.height = height;
		this.visible = true;
		this.baseSpeed = baseSpeed;
	}
	
	
	/* GETTERS AND SETTERS */
	
	public Vector getPosition() {
		return position;
	}
	
	public double getX() {
		return position.getX();
	}
	
	public double getY() {
		return position.getY();
	}
	
	public void setPosition(Vector position) {
		this.position = position;
	}
	
	public void setX(double x) {
		this.position.setX(x);
	}
	
	public void setY(double y) {
		this.position.setY(y);
	}
	
	public Vector getMotion() {
		return motion;
	}

	public void setMotion(Vector motion) {
		this.motion = motion;
	}
	
	public double getBaseSpeed() {
		return baseSpeed;
	}
	
	public void setBaseSpeed(double speed) {
		this.baseSpeed = speed;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
	/* CALCULATIONS */
	
	public Vector nextPosition() {
		position.add(motion);
		return position;
	}
	
	public Vector updatePosition() {
		this.setPosition(position.add(motion));
		return getPosition();
	}
	
	/**
	 * Checks if the object parsed will collide with this object once it has moved
	 * @param obj - The other object
	 * @return true if they collide otherwise false
	 */
	public boolean isColliding(GameObject obj) {
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
