package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Enemy extends GameObject {

    /**
     * If true, {@link Enemy} is currently dead and doesn't partake in the game until respawned.
     */
    private boolean dead;
    /**
     * If true, {@link Enemy} can currently be eaten by a {@link Pactale}.
     */
    private boolean edible;
    /**
     * {@link GameObject} that this {@link Enemy} currently follows. Can be null.
     */
    private GameObject objectToFollow;
    /**
     * {@link Node} that this {@link Enemy} is currently on.
     */
    private Node node;

    /**
     * Will move a Enemy to its spawn and reset some of its properties.
     */
    public void respawn() {
        // TODO - implement Enemy.respawn
        throw new UnsupportedOperationException();
    }

    /**
     * @return If the enemy is dead this will return true, if the enemy is still alive will return false
     */
    public boolean isDead() {
        return this.dead;
    }

    /**
     * Sets the dead propertie of the enemy. If the enemy is dead it should be set to true, if the enemy is not dead should be false.
     *
     * @param dead Must be true or false, cannot be null
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * @return True if the enemy is edible at the moment, false if the enemy is not edible.
     */
    public boolean isEdible() {
        return this.edible;
    }

    /**
     * Sets wether the enemy is edible or not, the enemy is edible if true. The enemy is not edible if false
     *
     * @param eatable Boolean, cannot be null
     */
    public void setEdible(boolean eatable) {
        this.edible = eatable;
    }

    /**
     * Initializes an {@link Enemy} which isn't dead and isn't currently edible.
     *
     * @param objectToFollow {@link GameObject} to follow. Can be null.
     * @param map            {@link Map} that this {@link Enemy} is currently in.
     * @param x              X position of this {@link Enemy}.
     * @param y              Y position of this {@link Enemy}.
     * @param movementSpeed  Speed in seconds that it takes to move to another {@link Node}.
     * @param direction      Direction that this {@link Enemy} is currently facing towards.
     */
    public Enemy(GameObject objectToFollow, Map map, int x, int y, float movementSpeed, Direction direction, Color color) {
        super(map, x, y, movementSpeed, direction, color);
        this.objectToFollow = objectToFollow;
    }

    /**
     * Initializes an {@link Enemy} which isn't dead and isn't currently edible with a default draw color of {@code Color.WHITE}.
     *
     * @param objectToFollow {@link GameObject} to follow. Can be null.
     * @param map            {@link Map} that this {@link Enemy} is currently in.
     * @param x              X position of this {@link Enemy}.
     * @param y              Y position of this {@link Enemy}.
     * @param movementSpeed  Speed in seconds that it takes to move to another {@link Node}.
     * @param direction      Direction that this {@link Enemy} is currently facing towards.
     */
    public Enemy(GameObject objectToFollow, Map map, int x, int y, float movementSpeed, Direction direction) {
        this(objectToFollow, map, x, y, movementSpeed, direction, Color.WHITE);
    }

    /**
     * @return {@link GameObject} that this {@link Enemy} follows. Can return null.
     */
    public GameObject getObjectToFollow() {
        return this.objectToFollow;
    }
}