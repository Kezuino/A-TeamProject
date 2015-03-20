package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Projectile extends GameObject {
    public Pactale getOwner() {
        return this.owner;
    }

    /**
     * Initializes a new projectile.
     *
     * @param owner         @see Pactale that fired this projectile.
     * @param movementSpeed
     * @param direction     orientation that the projectile has and is currently going towards.
     * @param color
     */
    public Projectile(Map map, int x, int y, Pactale owner, float movementSpeed, Direction direction, Color color) {
        super(map, x, y, movementSpeed, direction, color);
        this.owner = owner;
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

    private Pactale owner;

}