package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.pathfinding.AStar;
import ateamproject.kezuino.com.github.utility.game.Direction;
import ateamproject.kezuino.com.github.utility.game.Nodes;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Map {

    /**
     * Handles pathfinding for this {@link Map}.
     */
    private AStar pathfinder;
    /**
     * LibGDX 2D {@link TiledMap} for rendering purposes.
     */
    private TiledMap baseMap;
    /**
     * Amount of nodes in the X-axis of this {@link Map}.
     */
    private int width;
    /**
     * Amount of nodes in the Y-axis of this {@link Map}.
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

    private List<GameObject> gameObjects;

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
        gameObjects = new ArrayList<>();
        resetNodes(width, height);
        pathfinder = new AStar(this);
    }

    /**
     * Initializes a {@link Map} using the .TMX file generated by Tiled.
     *
     * @param session {@link GameSession} that hosts this {@link Map}.
     * @param mapName Name of the file that has the {@link Map} data.
     * @return MapLoader Information that was loaded from the file.
     */
    public static Map load(GameSession session, String mapName) {
        if (mapName == null || mapName.isEmpty())
            throw new IllegalArgumentException("Parameter mapName must not be null or empty.");

        MapLoader loader = new MapLoader(session, mapName);
        loader.load();
        return loader.getMap();
    }

    /**
     * Gets the {@link MapLoader} associated with the .TMX file.
     *
     * @param session {@link GameSession} that hosts this {@link Map}.
     * @param mapName Name of the file that has the {@link Map} data.
     * @return MapLoader Information that was loaded from the file.
     */
    public static MapLoader getLoader(GameSession session, String mapName) {
        if (mapName == null || mapName.isEmpty())
            throw new IllegalArgumentException("Parameter mapName must not be null or empty.");

        MapLoader loader = new MapLoader(session, mapName);
        return loader;
    }

    public TiledMap getBaseMap() {
        return baseMap;
    }

    public void setBaseMap(TiledMap baseMap) {
        this.baseMap = baseMap;
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
        this.width = width;
        this.height = height;
        nodes = new Nodes(width, height);
        resetNodes();
    }

    /**
     * Resets the 2D array based on the current {@link #width} and {@link #height}.
     */
    protected void resetNodes() {
        baseMap = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(width, height, 32, 32);
        baseMap.getLayers().add(layer);

        for (int x = 0; x < nodes.getLength(); x++) {
            for (int y = 0; y < nodes.getLength(x); y++) {
                nodes.set(x, y, new Node(this, x, y));
                layer.setCell(x, y, nodes.get(x, y));
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
     * Returns a {@link Node} if found, else it will return null.
     *
     * @param position 2D position exact position of the node. Will be divided by the tile size to get the correct {@link Node}.
     * @return {@link Node} if found, else it will return null.
     */
    public Node getNode(Vector2 position) {
        Vector2 fixedPos = position.cpy();
        return getNode(MathUtils.floor(fixedPos.x / 32f), MathUtils.floor(fixedPos.y / 32f));
    }

    /**
     * Removes the given {@link GameObject} and returns if succeeded.
     *
     * @param object {@link GameObject} to remove.
     * @return {@link GameObject} that was removed.
     */
    public void removeGameObject(GameObject object) {
        if (object == null) throw new IllegalArgumentException("Parameter object must not be null.");
        this.gameObjects.remove(object);
    }

    /**
     * Adds a {@link GameObject} to this {@link Map} if it doesn't exist and returns true if it succeeded.
     *
     * @param object to add to this {@link Map}.
     */
    public GameObject addGameObject(GameObject object) {
        if (object == null) throw new IllegalArgumentException("Parameter object must not be null.");
        if (hasGameObject(object)) throw new IllegalStateException("Object already exists on this map.");
        if (gameObjects.add(object)) {
            object.setMap(this);
            return object;
        }
        return null;
    }

    /**
     * Adds a {@link GameObject} to a position on this {@link Map}.
     *
     * @param object to add to a {@link ateamproject.kezuino.com.github.singleplayer.Node} on this {@link ateamproject.kezuino.com.github.singleplayer.Map}.
     * @return {@link GameObject} that was added to the {@link Map}.
     */
    public GameObject addGameObject(Vector2 position, GameObject object) {
        if (this.addGameObject(object) == null) return null;
        object.setExactPosition(position);
        return object;
    }

    /**
     * Gives a boolean value based on if the given object exists.
     *
     * @param object to check if already exists on this {@link Map}.
     */
    public boolean hasGameObject(GameObject object) {
        return this.gameObjects.contains(object);
    }

    /**
     * Gets all {@link GameObject gameobjects} on a certain {@link Node}.
     *
     * @param node The {@link Node} to look for {@link GameObject gameobjects}.
     * @return All {@link GameObject gameobjects} on the given {@link Node}.
     */
    public List<GameObject> getGameObjectsOnNode(Node node) {
        List<GameObject> matches = new ArrayList<>();
        for (GameObject obj : gameObjects) {
            if (obj.getNode().equals(node)) {
                matches.add(obj);
            }
        }
        return matches;
    }

    /**
     * Returns all {@link GameObject gameobjects} within a {@link Map}.
     */
    public List<GameObject> getAllGameObjects() {
        return this.gameObjects;
    }

    /**
     * Returns a node which is in the direction of the given direction. Will return null if node does not exist.
     *
     * @param node
     * @param direction
     */
    public Node getAdjacentNode(Node node, Direction direction) {
        if (node == null) throw new NullPointerException("Parameter node must not be null.");
        if (direction == null) throw new NullPointerException("Parameter direction must not be null.");
        return getNode(node.getX() + direction.getX(), node.getY() + direction.getY());
    }

    /**
     * Gets all the {@link Node nodes} of this {@link Map}.
     *
     * @return All the {@link Node nodes} of this {@link Map}.
     */
    public Nodes getNodes() {
        return nodes;
    }

    /**
     * Gets the pathfinding handler for this {@link Map}.
     *
     * @return Pathfinding handler for this {@link Map}.
     */
    public AStar getPathfinder() {
        return pathfinder;
    }

    /**
     * Get the current {@link GameSession} this {@link Map} is currently in.
     *
     * @return The current {@link GameSession}
     */
    public GameSession getSession() {
        return gameSession;
    }

    /**
     * Returns all the {@link Item items} on the {@link Map}.
     *
     * @return All the {@link Item items} on the {@link Map}.
     */
    public List<Item> getAllItems() {
        List<Item> result = new ArrayList<>();
        for (Node node : nodes) {
            if(node.hasItem()) {
                result.add(node.getItem());
            }
        }

        return result;
    }
}
