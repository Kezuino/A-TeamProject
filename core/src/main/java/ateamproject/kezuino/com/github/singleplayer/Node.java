package ateamproject.kezuino.com.github.singleplayer;

import java.util.*;

public class Node {

	private Node position;
	private Map map;
	private Wall wall;
	private Item item;
	private Enemy enemy;
	private Pactale pactale;
	private Collection<GameObject> gameObjects;
    private int x;
    private int y;

	public Node getPosition() {
		return this.position;
	}

	/**
	 * Initializes a node in the given map at the specific point.
	 * @param map
	 * @param x
     * @param y
	 */
	public Node(Map map, int x, int y) {
		gameObjects = new ArrayList<GameObject>();
        this.map = map;
        this.x = x;
        this.y = y;
	}

	/**
	 * Gives a boolean value based on if the given object exists.
	 * @param gameObject
	 */
	public boolean hasGameObject(GameObject gameObject) {
		// TODO - implement Node.hasGameObject
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds a gameobject to a node an returns true if it succeeded.
	 * @param gameObject
	 */
	public boolean addGameObject(GameObject gameObject) {
		// TODO - implement Node.addGameObject
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes the given object and returns if succeeded.
	 * @param gameObject
	 */
	public boolean removeGameObject(GameObject gameObject) {
		// TODO - implement Node.removeGameObject
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets the item and returns if succeeded.
	 * @param item
	 */
	public boolean setItem(Item item) {
		// TODO - implement Node.setItem
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes the item and returns if succeeded.
	 * @param item
	 */
	public boolean removeItem(Item item) {
		// TODO - implement Node.removeItem
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets the wall and returns if succeeded.
	 * @param wall
	 */
	public boolean setWall(Wall wall) {
		// TODO - implement Node.setWall
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes the wall and returns if succeeded.
	 */
	public boolean removeWall() {
		// TODO - implement Node.removeWall
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the map from where the node currently resides.
	 */
	public Map getMap() {
		// TODO - implement Node.getMap
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the wall from a node
	 */
	public Wall getWall() {
		// TODO - implement Node.getWall
		throw new UnsupportedOperationException();
	}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

	public Collection<GameObject> getGameObjects() {
		return gameObjects;
	}
}