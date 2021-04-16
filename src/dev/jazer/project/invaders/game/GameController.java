package dev.jazer.project.invaders.game;

import dev.jazer.project.invaders.objects.Vector;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameController {
	
	private GameModel model;
	
	private boolean leftPressed = false, rightPressed = false, spacePressed = false;
	
	public GameController(GameModel model) {
		this.model = model;
	}

	/**
	 * Handles keyboard events from the GameView initWindow() method
	 * @return the handler for key press
	 */
	public EventHandler<KeyEvent> keyPressedHandler() {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch ( event.getCode() ) {
				case A:
				case LEFT:
					leftPressed = true;
					break;
				case D:
				case RIGHT:
					rightPressed = true;
					break;
				case SPACE:
				case UP:
					spacePressed = true;
//					model.playerFire();
					break;
				case ESCAPE:
					if (model.getState() == GameState.RUNNING) model.pause();
					else model.resume();
					break;
				case P:
					model.toggleDevmode();
					break;
				default:
					break;
				}
			}
		};
	}

	/**
	 * Handles keyboard events from the GameView initWindow() method
	 * @return the handler for key release
	 */
	public EventHandler<KeyEvent> keyReleasedHandler() {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch ( event.getCode() ) {
				case A:
				case LEFT:
					leftPressed = false;
					break;
				case D:
				case RIGHT:
					rightPressed = false;
					break;
				case SPACE:
				case UP:
					spacePressed = false;
					break;
				default:
					break;
				}
			}
		};
	}
	
	/**
	 * @return true if the left button is currently pressed
	 */
	public boolean isLeftPressed() {
		return leftPressed;
	}

	/**
	 * @return true if the right button is currently pressed
	 */
	public boolean isRightPressed() {
		return rightPressed;
	}

	/**
	 * @return true if the space button is currently pressed
	 */
	public boolean isSpacePressed() {
		return spacePressed;
	}
	
	public void update() {
		if (leftPressed && !rightPressed) model.getPlayer().setMotion(new Vector(-model.getPlayer().getBaseSpeed(), 0));
		if (!leftPressed && rightPressed) model.getPlayer().setMotion(new Vector(model.getPlayer().getBaseSpeed(), 0));
		if (!leftPressed && !rightPressed) model.getPlayer().setMotion(new Vector(0, 0));
		if (spacePressed && model.getPlayerCooldown() <= 0) {
			model.playerFire();
		}
	}
	
}
