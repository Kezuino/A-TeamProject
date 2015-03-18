package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Projectile extends GameObject {
        public Pactale getPactal(){
            return this.Pactale;
        }

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

        public boolean hasCollision(Direction direction) {
        /**
         * Will check if a colission has happened and returns a boolean
         * accordingly.
         *
         * @param direction
         */
		// TODO - implement Projectile.collide
		throw new UnsupportedOperationException();
	}

	private Pactale Pactale;

}