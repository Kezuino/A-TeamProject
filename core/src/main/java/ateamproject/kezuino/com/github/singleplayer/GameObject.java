package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public abstract class GameObject {
    /**
     * {@link Map} that contains this {@link GameObject}.
     */
    private Map map;
    /**
     * X position of this {@link GameObject}.
     */
    private int x;
    /**
     * Y position of this {@link GameObject}.
     */
    private int y;
    /**
     * Speed in seconds that it takes for this {@link GameObject} to move to another adjacent {@link Node}.
     */
    private float movementSpeed;
    /**
     * {@link com.badlogic.gdx.graphics.Color} at that will be used to draw this {@link GameObject}.
     */
    private Color color;
    /**
     * {@link Direction} that this {@link GameObject} is currently facing towards.
     */
    private Direction direction;

    /**
     * Gets the speed in seconds that it takes for this {@link GameObject} to move to another adjacent {@link Node}.
     *
     * @return Speed in seconds that it takes for this {@link GameObject} to move to another adjacent {@link Node}.
     */
    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    /**
     * Sets the speed in seconds that it takes for this {@link GameObject} to move to another adjacent {@link Node}.
     *
     * @param movementSpeed Speed in seconds that it takes for this {@link GameObject} to move to another adjacent {@link Node}.
     */
    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    /**
     * Gets the {@link com.badlogic.gdx.graphics.Color} that will be used to draw this {@link GameObject}.
     *
     * @return {@link com.badlogic.gdx.graphics.Color} for drawing this {@link GameObject}.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Gets the {@link Direction} that this {@link GameObject} is currently facing towards.
     *
     * @return {@link Direction} that this {@link GameObject} is currently facing towards.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Sets the {@link Direction} that this {@link GameObject} is currently facing towards.
     *
     * @param direction {@link Direction} that this {@link GameObject} is currently facing towards.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Returns the node where the {@link GameObject} currently resides.
     */
    public Node getNode() {
        if (map == null) return null;
        return map.getNode(x, y);
    }

    /**
     * Initializes this {@link GameObject}.
     *
     * @param map           That hosts this {@link GameObject}.
     * @param x             X position of this {@link GameObject}.
     * @param y             Y position of this {@link GameObject}.
     * @param movementSpeed Speed in seconds that this {@link GameObject} takes to move to another adjacent {@link Node}.
     * @param direction     {@link Direction} that this {@link GameObject} is currently facing.
     * @param color         {@link com.badlogic.gdx.graphics.Color} that this {@link ateamproject.kezuino.com.github.singleplayer.GameObject} will be drawn at.
     */
    public GameObject(Map map, int x, int y, float movementSpeed, Direction direction, Color color) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.movementSpeed = movementSpeed;
        this.direction = direction;
        this.color = color;
    }

    /**
     * Initializes this {@link GameObject} with a default {@code Color.WHITE} color.
     *
     * @param map           That hosts this {@link GameObject}.
     * @param x             X position of this {@link GameObject}.
     * @param y             Y position of this {@link GameObject}.
     * @param movementSpeed Speed in seconds that this {@link GameObject} takes to move to another adjacent {@link Node}.
     * @param direction     {@link Direction} that this {@link GameObject} is currently facing.
     */
    public GameObject(Map map, int x, int y, float movementSpeed, Color color, Direction direction) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.movementSpeed = movementSpeed;
        this.color = color;
        this.direction = direction;
        this.color = Color.WHITE;
    }

    /**
     * Moves this {@link GameObject} to a different {@link Map}.
     *
     * @param map {@link Map} to move this {@link GameObject} to.
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Teleports this {@link GameObject} to the specified {@code x} and {@code y} coordinates.
     * Doesn't take into account {@link GameObject#movementSpeed} and doesn't use pathfinding.
     *
     * @param x X position to set this {@link GameObject} to.
     * @param y Y position to set this {@link GameObject} to.
     * @return
     */
    public boolean setPosition(int x, int y) {
        if (map == null) return false;
        Node targetNode = map.getNode(x, y);
        if (targetNode == null) return false;
        this.x = x;
        this.y = y;
        return true;
    }

    /**
     * Tries to move this {@link GameObject} to another {@link Node} using pathfinding based on the {@link #movementSpeed}.
     *
     * @param node {@link Node} to move towards.
     */
    public void move(Node node) {
        // TODO: Implement A* pathfinding to move this GameObject to another node based on the speed at which it can move between one node multiplied by the nodes it needs to pass.

        throw new UnsupportedOperationException();
    }
}