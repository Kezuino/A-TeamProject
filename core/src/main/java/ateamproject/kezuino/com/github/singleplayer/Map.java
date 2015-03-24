package ateamproject.kezuino.com.github.singleplayer;

import java.util.*;

public class Map {

    /**
     * X dimension of the size of this {@link Map}.
     */
    private int width;
    /**
     * Y dimension of the size of this {@link Map}.
     */
    private int height;

    /**
     * All the {@link Node nodes} on this {@link Map}.
     */
    private Nodes nodes;
    /**
     * {@link GameSession} that hosts the {@link Map} and allows multiplayer.
     */
    private GameSession gameSession;

    /**
     * Initializes a map with a 2D array filled with {@link Node nodes}.
     *
     * @param session    {@link GameSession} that will host this @see Map.
     * @param squareSize Width and height dimension length.
     */
    public Map(GameSession session, int squareSize) {
        this(session, squareSize, squareSize);
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

        nodes = new Nodes(width, height);
        resetNodes(width, height);
    }

    /**
     * Returns the {@link Node} count that this map has.
     *
     * @return Amount of nodes used by this map.
     */
    public int getSize() {
        return getWidth() * getHeight();
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
     * Resets the 2D array to the new dimensions.
     *
     * @param width  X dimension of the {@link Map}.
     * @param height Y dimension of the {@link Map}.
     */
    protected void resetNodes(int width, int height) {
        nodes = new Nodes(width, height);
        resetNodes();
        this.width = width;
        this.height = height;
    }

    /**
     * Resets the 2D array based on the current {@link #width} and {@link #height}.
     */
    protected void resetNodes() {
        for (int i = 0; i < nodes.getLength(); i++) {
            for (int j = 0; j < nodes.getLength(i); j++) {
                nodes.set(i, j, new Node(this, i, j));
            }
        }
    }

    /**
     * Returns a {@link Node} if found, else it will return null.
     *
     * @param x position to get {@link Node} from.
     * @param y position to get {@link Node} from.
     * @return {@link Node} if {@code x} and {@code y} are in-bounds. Null otherwise.
     */
    public Node getNode(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return nodes.get(x, y);
        }
        return null;
    }

    /**
     * Returns all {@link GameObject gameobjects} within a {@link Map}.
     */
    public List<GameObject> getAllGameObjects() {
        List<GameObject> objs = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Node node = getNode(x, y);
                objs.addAll(node.getGameObjects());
            }
        }
        return objs;
    }

    /**
     * Returns a node which is in the direction of the given direction. Will return null if node does not exist.
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
     * @param object to add to a {@link ateamproject.kezuino.com.github.singleplayer.Node} on this {@link ateamproject.kezuino.com.github.singleplayer.Map}.
     * @return {@link GameObject} that was added to the {@link Map}.
     */
    public GameObject addGameObject(int x, int y, GameObject object) {
        getNode(x, y).addGameObject(object);
        object.setPosition(x, y);
        object.setMap(this);
        return object;
    }

    /**
     * Gets all the {@link Node nodes} of this {@link Map}.
     *
     * @return All the {@link Node nodes} of this {@link Map}.
     */
    public Nodes getNodes() {
        return nodes;
    }
}