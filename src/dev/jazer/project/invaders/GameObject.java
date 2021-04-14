package dev.jazer.project.invaders;

public class GameObject {

	private Vector position, motion;
	private int width, height;
	
	
	/* CONSTRUCTORS */
	
	public GameObject(int x, int y, int width, int height) {
		this.position = new Vector(x, y);
		this.width = width;
		this.height = height;
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
	

	/* CALCULATIONS */
	
	public Vector nextPosition() {
		position.add(motion);
		return position;
	}
	
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
