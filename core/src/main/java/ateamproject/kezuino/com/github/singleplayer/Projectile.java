package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Projectile extends GameObject {

	/**
	 * Initializes a new projectile.
	 * @param owner
	 * @param position
	 * @param movementSpeed
	 * @param walkingDirection
	 * @param color
	 */
	public Projectile(Pactale owner, Node position, float movementSpeed, Direction walkingDirection, Color color) {
		super(position, movementSpeed, walkingDirection, color);
		// TODO - implement Projectile.Projectile
		throw new UnsupportedOperationException();
	}

	/**
	 * Will be called at the moment that the projectile has collided.
	 * @param wall
	 * @param direction
	 */
	public void collide(Wall wall, Direction direction) {
		// TODO - implement Projectile.collide
		throw new UnsupportedOperationException();
	}

	private Pactale Pactale;

}