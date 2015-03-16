package ATeamProject.Kezuino.com.github.SingePlayer;

import java.util.*;

public class Map {

	/**
	 * Initializes a map.
	 */
	public Map() {
		// TODO - implement Map.Map
		throw new UnsupportedOperationException();
	}

	/**
	 * Will return a node if found, else it will return null.
	 * @param position
	 */
	public Node getNode(Node position) {
		// TODO - implement Map.getNode
		throw new UnsupportedOperationException();
	}

	/**
	 * Will add a node that is "empty" at the given position, will return true or false based on the fact if the node could be added.
	 * @param position
	 */
	public boolean addNode(Node position) {
		// TODO - implement Map.addNode
		throw new UnsupportedOperationException();
	}

	/**
	 * Will add a node that is a wall at the given position, will return true or false based on the fact if the node could be added.
	 * @param position
	 * @param wall
	 */
	public boolean addNode(Node position, Wall wall) {
		// TODO - implement Map.addNode
		throw new UnsupportedOperationException();
	}

	/**
	 * Will add a node that contains a item at the given position, will return true or false based on the fact if the node could be added.
	 * @param position
	 * @param item
	 */
	public boolean addNode(Node position, Item item) {
		// TODO - implement Map.addNode
		throw new UnsupportedOperationException();
	}

	/**
	 * Add a gameobject and returns true if it succeeded.
	 * @param position
	 * @param gameObject
	 */
	public boolean addGameObject(Node position, GameObject gameObject) {
		// TODO - implement Map.addGameObject
		throw new UnsupportedOperationException();
	}

	/**
	 * Will return all gameobjects within a game.
	 */
	public GameObject getAllGameObjects() {
		// TODO - implement Map.getAllGameObjects
		throw new UnsupportedOperationException();
	}

	/**
	 * Will return a node which is in the direction of the given direction. Will return null if node does not exist.
	 * @param node
	 * @param direction
	 */
	public Node getAdjecentNode(Node node, Direction direction) {
		// TODO - implement Map.getAdjecentNode
		throw new UnsupportedOperationException();
	}

	private Collection<Node> nodes;
	private GameSession gameSession;

}