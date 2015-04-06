package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Node extends TiledMapTileLayer.Cell implements IndexedNode<Node>, IPositionable {

    private final Map map;
    /**
     * All {@link GameObject GameObjects} on this {@link Node}.
     */
    //private final List<GameObject> gameObjects;
    private final int x;
    private final int y;
    private int tileId;
    /**
     * List of {@link Portal portals} that can be on a side of this {@link Node}.
     */
    private final HashMap<Direction, Portal> portals;
    /**
     * {@link Item} that is on this {@link Node}.
     */
    private Item item;

    /**
     * If true, this {@link Node} is a wall (no matter what).
     */
    private boolean isForcedWall;

    /**
     * Initializes a {@link Node} in the given {@link Map} at the specific position.
     *
     * @param map that contains the {@link Node}.
     * @param x   position that the {@link Node} is at.
     * @param y   position that the {@link Node} is at.
     */
    Node(Map map, int x, int y) {
        //gameObjects = new ArrayList<>();
        portals = new HashMap<>();
        this.tileId = 0;
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public int getTileId() {
        return tileId;
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    /**
     * Sets the {@link Item} and returns true if succeeded.
     *
     * @param itemName Name of the {@link Item} to create and set on the {@link Node}.
     * @param type     The type of the item
     * @return {@link Item} that has been created and set on this {@link Node}.
     */
    public Item setItem(String itemName, ItemType type) {
        if (itemName == null || itemName.isEmpty())
            throw new IllegalArgumentException("Parameter itemName must not be null or empty.");
        this.item = new Item(itemName, getMap(), this.x, this.y, type);
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
     * Returns the {@link Map} from where the node currently resides.
     */
    @Override
    public Map getMap() {
        return this.map;
    }

    @Override
    public Node getNode() {
        return this;
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
     * Gets if this {@link Node} is a wall or not.
     *
     * @return True if this {@link Node} is a wall.
     */
    public boolean isWall() {
        if (isForcedWall) return true;
        return Boolean.valueOf(getProperty("isWall"));
    }

    /**
     * Force sets this {@link Node} to a wall.
     *
     * @param value If true, this {@link Node} is a wall.
     * @deprecated Method only used for testing collision. Do not use in game loop!
     */
    @Deprecated
    public void setWallForced(boolean value) {
        isForcedWall = value;
    }

    /**
     * Gets the value of the custom property set in the Tiled program based on the {@link #tileId}.
     * Returns null if the property was not found of the {@link #tileId}.
     *
     * @param name Property name of the custom property set in the Tiled program based on {@link #tileId}.
     * @return The value of the custom property or null if no property with the {@code name} was found.
     */
    public String getProperty(String name) {
        if (map == null) return null;
        TiledMap baseMap = map.getBaseMap();
        if (baseMap == null) return null;
        TiledMapTileSet tileSet;
        try {
            tileSet = baseMap.getTileSets().getTileSet(0);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
        TiledMapTile tile = tileSet.getTile(tileId);
        MapProperties props = tile.getProperties();
        if (props == null) return null;
        if (!props.containsKey(name)) return null;
        return (String) props.get(name);
    }

    /**
     * Gets all the {@link GameObject GameObjects} that are on this {@link Node}.
     *
     * @return all {@link GameObject GameObjects} on this {@link Node}.
     */
    /*public List<GameObject> getGameObjects() {
        return gameObjects;
    }*/

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", item=" + item +
                ", wall=" + isWall() +
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

    /**
     * Returns the {@link Portal} on the {@link Node} on the side specified by {@code direction}.
     *
     * @param direction of the side of the {@link Node} to get the {@link Portal} from.
     * @return portal at the direction of the {@link Node} or null.
     */
    public Portal getPortal(Direction direction) {
        if (!portals.containsKey(direction)) return null;
        return portals.get(direction);
    }

    /**
     * Sets a {@link Portal} to the side of the {@link Node} specified by the {@code direction}.
     *
     * @param direction to set the {@link Portal} on.
     * @param portal    to set on the side of the {@link Node}.
     */
    public boolean setPortal(Direction direction, Portal portal) {
        this.portals.put(direction, portal);
        return true;
    }

    /**
     * Removes a {@link Portal} from the side of the {@link Node} if it exists.
     *
     * @param direction of the side on the {@link Node} to look for a {@link Portal} to remove.
     * @return if true, removed a {@link Portal} from the {@link Node}.
     */
    public boolean removePortal(Direction direction) {
        if (!portals.containsKey(direction)) return false;
        this.portals.remove(direction);
        return true;
    }


    /**
     * Gets all the {@link Portal portals} on this {@link Node}.
     *
     * @return list of {@link Portal portals} on this {@link Node}.
     */
    public List<Portal> getPortals() {
        return portals.values().stream().collect(Collectors.toList());
    }

    /**
     * Removes all {@link Portal portals} from this {@link Node}.
     */
    public void clearPortals() {
        for (Portal p : getPortals()) {
            p.getOwner().removePortal();
        }
        portals.clear();
    }

    /**
     * Removes all {@link Portal portals} in the given {@link Direction side} of the {@link Node}.
     *
     * @param side {@link Direction side} of the {@link Node} to remove the {@link Portal portals} from.
     */
    public void clearDirection(Direction side) {
        if (side == null) throw new IllegalArgumentException("Parameter side must not be null.");
        for (Portal p : getPortals().stream().filter(p2 -> p2.getDirection() == side).collect(Collectors.toList())) {
            p.getOwner().removePortal();
            portals.remove(p);
        }
    }

    /**
     * Gets the index based on the {@link Map#getSize()}.
     *
     * @return Index based on the {@link Map#getSize()}.
     */
    @Override
    public int getIndex() {
        if (map == null) throw new IllegalArgumentException("Map must not be null.");
        return (getX() * getMap().getWidth()) + getY();
    }

    /**
     * Returns all the adjacent connections to this {@link Node}. Connections should be valid "able-to-move-to" {@link Node nodes} for a {@link GameObject}.
     *
     * @return All the adjacent connections to this {@link Node}. Connections should be valid "able-to-move-to" {@link Node nodes} for a {@link GameObject}.
     */
    @Override
    public Array<Connection<Node>> getConnections() {
        Node curNode = getMap().getNode(x, y);
        Array<Connection<Node>> connections = new Array<>();

        for (Direction dir : Direction.values()) {
            // Get adjacent node if not null and isn't a wall.
            Node adjacentNode = getMap().getAdjacentNode(curNode, dir);
            if (adjacentNode == null || adjacentNode.isWall()) continue;

            // Add adjacent node to connections.
            Connection<Node> con = new DefaultConnection<>(curNode, adjacentNode);
            connections.add(con);
        }
        return connections;
    }
}