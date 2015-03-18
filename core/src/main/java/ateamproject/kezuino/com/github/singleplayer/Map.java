package ateamproject.kezuino.com.github.singleplayer;

import java.util.*;

public class Map {

    /**
     * Returns the node count that this map has.
     *
     * @return Amount of nodes used by this map.
     */
    public int getSize() {
        return nodes.length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int width;
    private int height;

    /**
     * Initializes a map with a 2D array filled with @see Node.
     *
     * @param squareSize Width and height dimension length.
     */
    public Map(int squareSize) {
        width = squareSize;
        height = squareSize;

        resetNodes(squareSize, squareSize);
    }

    /**
     * Initializes a map with a 2D array filled with @see Node.
     *
     * @param width  X dimension of the map.
     * @param height Y dimension of the map.
     */
    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        resetNodes(width, height);
    }

    /**
     * Resets the 2D array to the new dimensions.
     *
     * @param width  X dimension of the map.
     * @param height Y dimension of the map.
     */
    protected void resetNodes(int width, int height) {
        // Fill 2D array with nodes.
        nodes = new Node[width][height];
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
     * @return Node if x and y are in-bounds. Null otherwise.
     */
    public Node getNode(int x, int y) {
        if (x < width && y < height) {
            return this.nodes[x][y];
        }
        return null;
    }

    /**
     * Will return all gameobjects within a game.
     */
    public List<GameObject> getAllGameObjects() {
        List<GameObject> objs = new ArrayList<GameObject>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Node node = getNode(x, y);
                objs.addAll(node.getGameObjects());
            }
        }
        return objs;
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