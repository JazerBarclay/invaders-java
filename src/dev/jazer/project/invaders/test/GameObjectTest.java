package dev.jazer.project.invaders.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dev.jazer.project.invaders.objects.GameObject;
import dev.jazer.project.invaders.objects.Vector;

public class GameObjectTest {

	@Test
	public void constructorShouldInitialiseFields() {
		// Define a new game object with given parameters
		GameObject obj = new GameObject(new Vector(10, 20), new Vector(1, -1), 200, 100, 5);

		// Ensure the x and y values ares set
		assertEquals(obj.getX(), 10, 0);
		assertEquals(obj.getY(), 20, 0);

		// Ensure the motion vector is set
		assertEquals(obj.getMotion().getX(), 1, 0);
		assertEquals(obj.getMotion().getY(), -1, 0);

		// Ensure width and height are set
		assertEquals(obj.getWidth(), 200);
		assertEquals(obj.getHeight(), 100);
		
		// Ensure base movement speed is set
		assertEquals(obj.getBaseSpeed(), 5, 0);
	}

	@Test
	public void testCollision() {
		// Create 2 new objects that are next to each other but are not colliding
		GameObject obj1 = new GameObject(new Vector(10, 20), new Vector(0, 0), 200, 100, 5);
		GameObject obj2 = new GameObject(new Vector(5, 20), new Vector(5, 0), 200, 100, 5);
		
		// Ensure the objects are not currently colliding
		if (obj1.isColliding(obj2)) fail("Objects should not yet be colliding");
		
		// Ensure the objects will collide based on the motion vector set
		if (!obj1.willCollide(obj2)) fail("Objects should be colliding");
	}
	
	
}
