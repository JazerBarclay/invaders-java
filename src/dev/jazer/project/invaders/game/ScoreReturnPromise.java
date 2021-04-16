package dev.jazer.project.invaders.game;

/**
 * An interface to perform an action when the score value is returned
 * @author Jazer Barclay
 */
public interface ScoreReturnPromise {

	public void onReturn(int value);
	
}
