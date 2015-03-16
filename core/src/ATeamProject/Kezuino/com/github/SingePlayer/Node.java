package ATeamProject.Kezuino.com.github.SingePlayer;

import java.util.*;

public class Node {

	private Node position;
	private Map Map;
	private Wall Wall;
	private Item Item;
	private Enemy Enemy;
	private Pactale Pactale;
	private Collection<GameObject> GameObject;

	public Node getPosition() {
		return this.position;
	}

	/**
	 * Initializes a node in the given map at the specific point.
	 * @param map
	 * @param position
	 */
	public Node(Map map, Node position) {
		// TODO - implement Node.Node
		throw new UnsupportedOperationException();
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

}