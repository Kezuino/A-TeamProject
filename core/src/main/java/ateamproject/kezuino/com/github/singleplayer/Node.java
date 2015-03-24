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
    private final List<GameObject> gameObjects;
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
     * @param object {@link GameObject} to remove.
     * @return {@link GameObject} that was removed.
     */
    public GameObject removeGameObject(GameObject object) {
        if (!gameObjects.remove(object)) return null;
        return object;

    }

    /**
     * Sets the {@link Item} and returns true if succeeded.
     *
     * @param itemName Name of the {@link Item} to create and set on the {@link Node}.
     * @return {@link Item} that has been created and set on this {@link Node}.
     */
    public Item setItem(String itemName) {
        if (itemName == null || itemName.isEmpty())
            throw new IllegalArgumentException("Parameter itemName must not be null or empty.");
        this.item = new Item(itemName, this);
        return this.item;
    }

    /**
     * Sets the {@link Item} and returns true if succeeded.
     *
     * @param item {@link Item} to set on this {@link Node}.
     * @return {@link Item} that has been set on this {@link Node}.
     */
    public Item setItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Parameter item must not be null.");
        this.item = item;
        return this.item;
    }

    /**
     * Removes the {@link Item} and returns true if succeeded.
     *
     * @return If true, {@link Item} was successfully removed from this {@link Node}.
     */
    public boolean removeItem() {
        this.item = null;
        return true;
    }

    /**
     * Sets a new default {@link Wall} on the {@link Node}.
     *
     * @return The newly created {@link Wall}.
     */
    public Wall setWall() {
        this.wall = new Wall(this);
        return wall;
    }

    /**
     * Sets a new {@link Wall} on the {@link Node}.
     *
     * @param wall {@link Wall} to set on this {@link Node}.
     * @return {@link Wall} that has been set on this {@link Node}.
     */
    public Wall setWall(Wall wall) {
        if (wall == null) throw new IllegalArgumentException("Parameter wall must not be null.");
        this.wall = wall;
        return wall;
    }

    /**
     * Removes the {@link Wall} and returns true if succeeded.
     *
     * @return If true, {@link Wall} was removed.
     */
    public boolean removeWall() {
        if (wall == null) return true;
        wall.clear();
        this.wall = null;
        return true;
    }

    /**
     * Returns the {@link Map} from where the node currently resides.
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Gets the {@link Wall} from a {@link Node}.
     */
    public Wall getWall() {
        return this.wall;
    }

    /**
     * Gets the Y position of this {@link Node}.
     *
     * @return X position of this {@link Node}.
     */
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
    public List<GameObject> getGameObjects() {
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

    /**
     * Returns {@link Item} contained by this {@link Node}.
     *
     * @return {@link Item} contained by this {@link Node}.
     */
    public Item getItem() {
        return item;
    }
}