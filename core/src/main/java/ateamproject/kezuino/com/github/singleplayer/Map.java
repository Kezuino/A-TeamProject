package ateamproject.kezuino.com.github.singleplayer;

import java.util.*;

public class Map {

    public int getSize() {
        return size;
    }

    private int size;

    /**
     * Initializes a map with a 2D array filled with @see Node.
     *
     * @param size Width and height dimension length.
     */
    public Map(int size) {
        this.size = size;

        // Fill 2D array with nodes.
        nodes = new Node[size][size];
        for (int y = 0; y < nodes.length; y++) {
            for (int x = 0; x < nodes[y].length; x++) {
                nodes[y][x] = new Node(this, x, y);
            }
        }
    }

    /**
     * Will return a node if found, else it will return null.
     *
     * @param x
     * @param y
     */
    public Node getNode(int x, int y) {
        if (x < size && y < size) {
            return this.nodes[x][y];
        }
        return null;
    }

    /**
     * Will add a node that is "empty" at the given position, will return true or false based on the fact if the node could be added.
     *
     * @param position
     */
    public boolean addNode(Node position) {
        // TODO - implement Map.addNode
        throw new UnsupportedOperationException();
    }

    /**
     * Will add a node that is a wall at the given position, will return true or false based on the fact if the node could be added.
     *
     * @param position
     * @param wall
     */
    public boolean addNode(Node position, Wall wall) {
        // TODO - implement Map.addNode
        throw new UnsupportedOperationException();
    }

    /**
     * Will add a node that contains a item at the given position, will return true or false based on the fact if the node could be added.
     *
     * @param position
     * @param item
     */
    public boolean addNode(Node position, Item item) {
        // TODO - implement Map.addNode
        throw new UnsupportedOperationException();
    }

    /**
     * Add a gameobject and returns true if it succeeded.
     *
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
     *
     * @param node
     * @param direction
     */
    public Node getAdjecentNode(Node node, Direction direction) {
        // TODO - implement Map.getAdjecentNode
        throw new UnsupportedOperationException();
    }

    private Node[][] nodes;
    private GameSession gameSession;

}