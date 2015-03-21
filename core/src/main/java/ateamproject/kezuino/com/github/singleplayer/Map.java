package ateamproject.kezuino.com.github.singleplayer;

import java.util.*;

public class Map {

    /**
     * Returns the {@link Node} count that this map has.
     *
     * @return Amount of nodes used by this map.
     */
    public int getSize() {
        return nodes.length;
    }

    /**
     * X dimension of the size of this {@link Map}.
     *
     * @return number of {@link Node nodes} that this {@link Map} is in the X dimension.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Y dimension of the size of this {@link Map}.
     *
     * @return number of {@link Node nodes} that this {@link Map} is in the Y dimension.
     */
    public int getHeight() {
        return height;
    }

    /**
     * X dimension of the size of this {@link Map}.
     */
    private int width;

    /**
     * Y dimension of the size of this {@link Map}.
     */
    private int height;

    /**
     * Initializes a map with a 2D array filled with {@link Node nodes}.
     *
     * @param session    {@link GameSession} that will host this @see Map.
     * @param squareSize Width and height dimension length.
     */
    public Map(GameSession session, int squareSize) {
        gameSession = session;
        width = squareSize;
        height = squareSize;

        resetNodes(squareSize, squareSize);
    }

    /**
     * Initializes a {@link Map} with a 2D array filled with {@link Node nodes}.
     *
     * @param session {@link GameSession} that will host this {@link Map}.
     * @param width   X dimension of the map.
     * @param height  Y dimension of the map.
     */
    public Map(GameSession session, int width, int height) {
        gameSession = session;
        this.width = width;
        this.height = height;

        resetNodes(width, height);
    }

    /**
     * Resets the 2D array to the new dimensions.
     *
     * @param width  X dimension of the {@link Map}.
     * @param height Y dimension of the {@link Map}.
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
     * Will return a {@link Node} if found, else it will return null.
     *
     * @param x position to get {@link Node} from.
     * @param y position to get {@link Node} from.
     * @return {@link Node} if {@code x} and {@code y} are in-bounds. Null otherwise.
     */
    public Node getNode(int x, int y) {
        if (x < width && y < height) {
            return this.nodes[x][y];
        }
        return null;
    }

    /**
     * Will return all {@link GameObject gameobjects} within a game.
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
        if (node == null) throw new NullPointerException("Parameter node must not be null.");
        if (direction == null) throw new NullPointerException("Parameter direction must not be null.");
        switch (direction) {
            case Up:
                return getNode(node.getX(), node.getY() - 1);
            case Down:
                return getNode(node.getX(), node.getY() + 1);
            case Left:
                return getNode(node.getX() - 1, node.getY());
            case Right:
                return getNode(node.getX() + 1, node.getY());
            default:
                return null;
        }
    }

    /**
     * Adds a {@link GameObject} to a position on this {@link Map}.
     *
     * @param object to add to a {@link Node} on this {@link Map}.
     */
    public void addGameObject(int x, int y, GameObject object) {
        object.setPosition(x, y);
        object.setMap(this);
    }

    private Node[][] nodes;
    private GameSession gameSession;

}