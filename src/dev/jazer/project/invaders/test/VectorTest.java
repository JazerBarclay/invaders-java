package dev.jazer.project.invaders.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dev.jazer.project.invaders.objects.Vector;

public class VectorTest {

	@Test
	public void constructorShouldInitialiseZeroZero() {
		// Create a new vector with no constructor which should set the x,y to (0,0)
		Vector vector = new Vector();
		double xPosition = vector.getX();
		double yPosition = vector.getY();

		// Ensure the x and y values are 0 with a 0 delta
		assertEquals(0, xPosition, 0);
		assertEquals(0, yPosition, 0);
	}

	@Test
	public void constructorShouldInitialiseFields() {
		// Create a new vector with the x,y values (10,20) and store the results
		Vector vector = new Vector(10, 20);
		double xPosition = vector.getX();
		double yPosition = vector.getY();

		// Ensure the x and y values match with a 0 delta
		assertEquals(10, xPosition, 0);
		assertEquals(20, yPosition, 0);
	}
	
	@Test
	public void addShouldCombineVectors() {
		// Create 2 vectors with different values
		Vector vector1 = new Vector(5, 15);
		Vector vector2 = new Vector(25, 10);
		
		// Add vector 1 to vector 2 and store the results
		Vector combined = vector1.add(vector2);
		double xC1 = combined.getX();
		double yC1 = combined.getY();

		// Add vector 2 to vector 1 and store the results
		combined = vector2.add(vector1);
		double xC2 = combined.getX();
		double yC2 = combined.getY();
		
		// Check we get position (30,25) with 0 delta
		assertEquals(xC1, 30, 0);
		assertEquals(yC1, 25, 0);

		// Check the add matches both ways with 0 delta
		assertEquals(xC1, xC2, 0);
		assertEquals(yC1, yC2, 0);
	}

}
