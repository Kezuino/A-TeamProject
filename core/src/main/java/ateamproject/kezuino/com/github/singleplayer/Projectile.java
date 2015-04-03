package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Projectile extends GameObject {
    /*
        Field created for the {@link Pactale} this {@link Projectile}
        origined from.
    */
    private final Pactale owner;

    /**
     * Initializes a new {@link Projectile}.
     *
     * @param owner         {@link Pactale} this {@link Projectile} origined from.
     * @param movementSpeed Speed in seconds it takes for this
     *                      {@link Projectile} to move to another adjacent {@link Node}.
     * @param direction     Direction this {@link Projectile} is moviong
     *                      towards.
     * @param color         {@link com.badlogic.gdx.graphics.Color} of this
     *                      {@link Projectile}.
     */
    public Projectile(Map map, int x, int y, Pactale owner, float movementSpeed, Direction direction, Color color) {
        super(map, x, y, movementSpeed, direction, color);
        this.owner = owner;
    }

    /**
     * Gets the {@link Pactale} this {@link Projectile} origined from.
     *
     * @return {@link Pactale} that this {@link Projectile} origined from.
     */
    public Pactale getOwner() {
        return this.owner;
    }

    /**
     * Checks whether the next {@link Node} this {@link Projectile} is heading
     * will collide with a different {@link GameObject} or impenetrable tile {@link Node}.
     *
     * @return True if it will collide with a different object or impenetrable {@link Node}, else false.
     */
    public Boolean hasCollision() {
        if (direction == null) return null;

        Node NextNode;
        int x = this.getX();
        int y = this.getY();

        if (direction == null) {
            return null;
        }

        NextNode = this.getMap().getNode(x + direction.getX(), y + direction.getY());
        if (NextNode.isWall()) {
                    // Next Node is a wall, colision detected, return true
            return true;
        } else if (!NextNode.getGameObjects().isEmpty()) {
            // Collision with a GameObject.;
            for (GameObject obj : NextNode.getGameObjects()) {
                boolean result = collisionWithGameObject(obj);
                if (result)
                    return true;
            }
        }

        // No collision.
        return false;
    }

    /**
     * Called when a collision was detected by this {@link Projectile}. Return true if collision has been handled and shouldn't continue.
     *
     * @param object {@link GameObject} that this {@link Projectile} is colliding with.
     * @return True if collision has been handled and {@link Projectile} should be destroyed.
     */
    protected boolean collisionWithGameObject(GameObject object) {
        // TODO: Handle collision with a GameObject.
        return false;
    }
}

