package ateamproject.kezuino.com.github.singleplayer;

import java.util.*;

public class Node {
    /**
     * {@link Map} that contains this {@link Node}.
     */
    private final Map map;
    /**
     * All {@link GameObject GameObjects} on this {@link Node}.
     */
    private final Collection<GameObject> gameObjects;
    /**
     * {@link Wall} information that defines this {@link Node}.
     */
    private Wall wall;
    /**
     * {@link Item} that is on this {@link Node}.
     */
    private Item item;
    /**
     * Gets the X position that this {@link Node} is on.
     */
    private int x;

    /**
     * Gets the Y position that this {@link Node} is on.
     */
    private int y;

    /**
     * Initializes a {@link Node} in the given {@link Map} at the specific position.
     *
     * @param map that contains the {@link Node}.
     * @param x   position that the {@link Node} is at.
     * @param y   position that the {@link Node} is at.
     */
    Node(Map map, int x, int y) {
        gameObjects = new ArrayList<>();
        this.map = map;
        this.x = x;
        this.y = y;
    }

    /**
     * Gives a boolean value based on if the given object exists.
     *
     * @param object to check if already exists on this {@link Node}.
     */
    public boolean hasGameObject(GameObject object) {
        return gameObjects.contains(object);
    }

    /**
     * Adds a {@link GameObject} to a {@link Node} if it doesn't exist and returns true if it succeeded.
     *
     * @param object to add to the {@link Node}.
     */
    public boolean addGameObject(GameObject object) {
        if (hasGameObject(object)) return false;
        gameObjects.add(object);
        return true;
    }

    /**
     * Removes the given {@link GameObject} and returns if succeeded.
     *
     * @param object
     */
    public boolean removeGameObject(GameObject object) {
        return gameObjects.remove(object);
    }

    /**
     * Sets the item and returns if succeeded.
     *
     * @param item
     */
    public boolean setItem(Item item) {
        this.item = item;
        return true;
    }

    /**
     * Removes the item and returns if succeeded.
     */
    public boolean removeItem() {
        this.item = null;
        return true;
    }

    /**
     * Sets the wall and returns if succeeded.
     *
     * @param wall
     */
    public boolean setWall(Wall wall) {
        this.wall = wall;
        return true;
    }

    /**
     * Removes the wall and returns if succeeded.
     */
    public boolean removeWall() {
        wall.clear();
        this.wall = null;
        return true;
    }

    /**
     * Returns the map from where the node currently resides.
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Gets the wall from a node
     */
    public Wall getWall() {
        return this.wall;
    }

    public int getX() {
        return x;
    }

    /**
     * Gets the X position of this {@link Node}.
     *
     * @return X position of this {@link Node}.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets all the {@link GameObject GameObjects} that are on this {@link Node}.
     *
     * @return all {@link GameObject GameObjects} on this {@link Node}.
     */
    public Collection<GameObject> getGameObjects() {
        return gameObjects;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", wall=" + wall +
                ", item=" + item +
                '}';
    }
}