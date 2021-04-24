package dev.jazer.project.invaders.game;

/**
 * An interface to perform an action when the score value is returned
 * @author Jazer Barclay
 */
public interface ScoreReturnPromise {

	/**
	 * Perform an action using the value returned
	 * @param value
	 */
	public void onReturn(int value);
	
}
