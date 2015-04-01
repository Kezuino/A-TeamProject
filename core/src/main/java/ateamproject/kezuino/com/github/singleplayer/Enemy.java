package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Enemy extends GameObject {

    /**
     * If true, {@link Enemy} is currently dead and doesn't participate in the game until respawned.
     */
    private boolean dead;
    
    /**
     * If true, {@link Enemy} can be eaten by a {@link Pactale}.
     */
    private boolean edible;
    
    /**
     * The {@link GameObject} this {@link Enemy} currently follows. Can be null.
     */
    private GameObject objectToFollow;

    /**
     * The {@link Node} this {@link Enemy} spawned on.
     */
    private Node respawnNode;

    /**
     * Constructs a new {@link Enemy}.
     * The newly constructed {@link Enemy} is not dead.
     * The newly constructed {@link Enemy} is not edible.
     * 
     * @param objectToFollow {@link GameObject} to follow. Can be null.
     * @param map            {@link Map} this {@link Enemy} is currently in.
     * @param x              X position of this {@link Enemy}.
     * @param y              Y position of this {@link Enemy}.
     * @param movementSpeed  Speed in seconds that it takes to move to another {@link Node}.
     * @param direction      Direction this {@link Enemy} is currently facing.
     * @param color          Color of this {@link Enemy}.
     */
    public Enemy(GameObject objectToFollow, Map map, int x, int y, float movementSpeed, Direction direction, Color color) {
        super(map, x, y, movementSpeed, direction, color);
        this.objectToFollow = objectToFollow;
        this.respawnNode = map.getNode(x, y);
        this.dead = false;
        this.edible = false;
    }

    /**
     * Constructs a new {@link Enemy}.
     * The newly constructed {@link Enemy} is not dead.
     * The newly constructed {@link Enemy} is not edible.
     * The default draw color is set to {@code Color.WHITE}.
     *
     * @param objectToFollow {@link GameObject} to follow. Can be null.
     * @param map            {@link Map} that this {@link Enemy} is currently in.
     * @param x              X position of this {@link Enemy}.
     * @param y              Y position of this {@link Enemy}.
     * @param movementSpeed  Speed in seconds that it takes to move to another {@link Node}.
     * @param direction      Direction this {@link Enemy} is currently facing.
     */
    public Enemy(GameObject objectToFollow, Map map, int x, int y, float movementSpeed, Direction direction) {
        this(objectToFollow, map, x, y, movementSpeed, direction, Color.WHITE);
    }

    /**
     * Move an Enemy to its spawn and reset some of its properties.
     */
    public void respawn() {
        setPosition(respawnNode.getX(), respawnNode.getY());
    }

    /**
     * Gets the current state of this {@link Enemy).
     * If this enemy is currently dead it will return true, else false.
     * 
     * @return The current state of this {@link Enemy}.
     */
    public boolean isDead() {
        return this.dead;
    }

    /**
     * Sets the dead property of the enemy. If the enemy is dead it should be set to true, if the enemy is not dead should be false.
     *
     * @param dead Must be true or false, cannot be null
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Gets wether this {@link Enemy} is edbile.
     * 
     * @return True if the enemy is edible at the moment, false if the enemy is not edible.
     */
    public boolean isEdible() {
        return this.edible;
    }

    /**
     * Sets wether this {@link Enemy} is edible.
     * This {@link Enemy} is edible if true.
     * This {@link Enemy} is not edible if false.
     *
     * @param eatable Boolean, cannot be null
     */
    public void setEdible(boolean edible) {
        this.edible = edible;
    }

    /**
     * @return {@link GameObject} that this {@link Enemy} follows. Can return null.
     */
    public GameObject getObjectToFollow() {
        return this.objectToFollow;
    }
}