package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

import java.util.*;

public class Pactale extends GameObject {

	private int playerIndex;
	private int lives;
	private Portal Portal;
	private Node Node;
	private Collection<Projectile> Projectile;

	public int getPlayerIndex() {
		return this.playerIndex;
	}

	public int getLives() {
		return this.lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Initialize a pactale
	 * @param position
	 * @param map
	 * @param lives
	 * @param playerIndex
	 * @param color
	 * @param parameter
	 * @param movementSpeed
	 * @param walkingDirection
	 */
	public Pactale(Node position, Map map, int lives, int playerIndex, Color color, Node parameter, float movementSpeed, Direction walkingDirection) {
		super(position, movementSpeed, walkingDirection, color);
		// TODO - implement Pactale.Pactale
		throw new UnsupportedOperationException();
	}

	/**
	 * Will shoot a portal in the direction that the pactale currently is heading.
	 */
	public void shootPortal() {
		// TODO - implement Pactale.shootPortal
		throw new UnsupportedOperationException();
	}

	/**
	 * Will remove all listed portals from this Pactale
	 */
	public void removePortal() {
		// TODO - implement Pactale.removePortal
		throw new UnsupportedOperationException();
	}

	/**
	 * Changes the direction of the movement of the pactale.
	 * @param direction
	 */
	public void move(Direction direction) {
		// TODO - implement Pactale.move
		throw new UnsupportedOperationException();
	}

}