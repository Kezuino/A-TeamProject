package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.pathfinding.AStar;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;

import java.util.ArrayList;
import java.util.List;

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
        pathfinder = new AStar(this);
    }

    /**
     * Initializes a {@link Map} using the .TMX file generated by Tiled.
     *
     * @param session
     * @param mapPath
     * @return
     */
    public static Map load(GameSession session, String mapPath) {
        if (mapPath == null || mapPath.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (mapPath == null || mapPath.isEmpty()) throw new IllegalArgumentException();

        TiledMap tiledMap = new TmxMapLoader().load(mapPath);
        MapProperties props = tiledMap.getProperties();
        Map map = new Map(session, props.get("width", Integer.class), props.get("height", Integer.class));
        map.baseMap = tiledMap;

        TiledMapTileLayer background = (TiledMapTileLayer) map.baseMap.getLayers().get(0);
        TiledMapTileLayer foreground = (TiledMapTileLayer) map.baseMap.getLayers().get(1);
        for (int x = 0; x < map.width; x++) {
            for (int y = 0; y < map.height; y++) {
                Node node = map.getNode(x, y);
                node.setTileId(background.getCell(x, y).getTile().getId());
                
                //System.out.println("TILE ID ON BACK: " + background.getCell(x, y).getTile().getId() + " POSITION: " + Integer.toString(x) + " / " + Integer.toString(y));

                if (foreground.getCell(x, y) != null) {//check if there is something that can be placed on the foreground
                    createGameObjectFromTileMap(x, y, map);//create a gameobject or item
                } else if (isPlaceForSmallObject(x, y, map)) {//check if a small object can be placed
                    createItemFromTileMap(x, y, map);//create a small object
                }
            }

        }
        return map;
    }

    private static boolean isPlaceForSmallObject(int x, int y, Map map) {
        TiledMapTileLayer background = (TiledMapTileLayer) map.baseMap.getLayers().get(0);
        int gObjectID = background.getCell(x, y).getTile().getId();

        TiledMapTileSet tileSet;
        tileSet = map.getBaseMap().getTileSets().getTileSet(0);

        TiledMapTile tile = tileSet.getTile(gObjectID);
        MapProperties properties = tile.getProperties();

        Object tileProperties = properties.get("isGround");
        if (tileProperties != null) {
            return true;
        }

        return false;
    }

    /**
     * places a small object at the given position
     *
     * @param x
     * @param y
     * @param map
     */
    private static void createItemFromTileMap(int x, int y, Map map) {
        Item i = new Item(map, x, y, ItemType.SmallObject);
        Node currNode = map.getNode(x, y);
        currNode.setItem(i);
    }

    /**
     * creates and returns a (game)object at a given position. if nothing is
     * found, null will be returned
     *
     * @param x
     * @param y
     * @param map
     * @return
     */
    private static Object createGameObjectFromTileMap(int x, int y, Map map) {
        TiledMapTileLayer foreground = (TiledMapTileLayer) map.baseMap.getLayers().get(1);
        
        //int gObjectID = foreground.getCell(x, y).getTile().getId();
        //System.out.println("TILE ID ON BACK: " + gObjectID + " POSITION: " + Integer.toString(x) + " / " + Integer.toString(y));
        
        MapProperties properties = foreground.getCell(x, y).getTile().getProperties();

        Object gObjectProperties = properties.get("isPactale");
        if (gObjectProperties != null) {
            Pactale p = new Pactale(map, x, y, 3, 3, Direction.Down, Color.BLUE);
            map.addGameObject(x, y, p);
            return p;
        }

        gObjectProperties = properties.get("isEnemy");
        if (gObjectProperties != null) {
            for (GameObject gObject : map.getAllGameObjects()) {
                if (gObject.getClass().equals(Pactale.class)) {//check if there is a pactale on allgameobject
                    Enemy e = new Enemy(gObject, map, x, y, 3, Direction.Down);//follow pactale
                    map.addGameObject(x, y, e);
                    return e;
                }
            }

            Enemy e = new Enemy(null, map, x, y, 3, Direction.Down);//follow nothing
            map.addGameObject(x, y, e);
            return e;
        }

        gObjectProperties = properties.get("isItem");
        if (gObjectProperties != null) {
            gObjectProperties = properties.get("name");
            if (gObjectProperties != null) {
                if (gObjectProperties == "bigObject") {
                    Item i = new Item(map, x, y, ItemType.BigObject);
                    Node currNode = map.getNode(x, y);
                    currNode.setItem(i);
                    return i;
                } else if (gObjectProperties == "watch") {
                    Item i = new Item(map, x, y, ItemType.Watch);
                    Node currNode = map.getNode(x, y);
                    currNode.setItem(i);
                    return i;
                }
            }

            return null;
        }

        return null;
    }

    public TiledMap getBaseMap() {
        return baseMap;
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
    public Node getAdjacentNode(Node node, Direction direction) {
        if (node == null) throw new NullPointerException("Parameter node must not be null.");
        if (direction == null) throw new NullPointerException("Parameter direction must not be null.");
        return getNode(node.getX() + direction.getX(), node.getY() + direction.getY());
    }

    /**
     * Adds a {@link GameObject} to a position on this {@link Map}.
     *
     * @param object to add to a {@link ateamproject.kezuino.com.github.singleplayer.Node} on this {@link ateamproject.kezuino.com.github.singleplayer.Map}.
     * @return {@link GameObject} that was added to the {@link Map}.
     */
    public GameObject addGameObject(int x, int y, GameObject object) {
        if (object == null) throw new IllegalArgumentException("Parameter object must not be null.");
        Node node = getNode(x, y);
        if (node == null) throw new IllegalArgumentException("Cannot add GameObject to an x and y node because node is null.");

        if (node.hasGameObject(object)) return null;
        if (!node.addGameObject(object)) return null;
        object.setPosition(x, y);
        object.setMap(this);
        return object;
    }
    
    public void removeGameObject(GameObject object) {
        if (object == null) throw new IllegalArgumentException("Parameter object must not be null.");
        this.nodes.stream().filter(n -> n.hasGameObject(object)).forEach((Node n) -> n.removeGameObject(object));
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
        return this.gameSession;
    }
}