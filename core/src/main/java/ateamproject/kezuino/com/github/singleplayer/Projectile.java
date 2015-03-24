package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Projectile extends GameObject {
    private Pactale owner;

    /**
     * Initializes a new {@link Projectile}.
     *
     * @param owner         {@link Pactale} that fired this projectile.
     * @param movementSpeed Speed in seconds it takes for this {@link Projectile} to move to another adjacent {@link Node}.
     * @param direction     Orientation that the {@link Projectile} has and is currently going towards.
     * @param color         {@link com.badlogic.gdx.graphics.Color} used to draw this {@link Projectile}.
     */
    public Projectile(Map map, int x, int y, Pactale owner, float movementSpeed, Direction direction, Color color) {
        super(map, x, y, movementSpeed, direction, color);
        this.owner = owner;
    }

    public Pactale getOwner() {
        return this.owner;
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

}