package ateamproject.kezuino.com.github.singleplayer;

import java.util.*;

public class Wall {

    private HashMap<Direction, Portal> portals;
    private Node node;
    private Collection<Portal> portal;

    /**
     * Initializes a wall at the position of the given node.
     *
     * @param node
     */
    public Wall(Node node) {
        this.node = node;
    }

    /**
     * Returns the {@link Portal} on the {@link Wall} on the side specified by {@code direction}.
     *
     * @param direction of the side of the {@link Wall} to get the {@link Portal} from.
     * @return portal at the direction of the {@link Wall} or null.
     */
    public Portal getPortal(Direction direction) {
        if (!portals.containsKey(direction)) return null;
        return portals.get(direction);
    }

    /**
     * Sets a {@link Portal} to the side of the {@link Wall} specified by the {@code direction}.
     *
     * @param direction to set the {@link Portal} on.
     * @param portal    to set on the side of the {@link Wall}.
     */
    public boolean setPortal(Direction direction, Portal portal) {
        portals.put(direction, portal);
        return true;
    }

    /**
     * Removes a {@link Portal} from the side of the {@link Wall} if it exists.
     *
     * @param direction of the side on the {@link Wall} to look for a {@link Portal} to remove.
     * @return if true, removed a {@link Portal} from the {@link Wall}.
     */
    public boolean removePortal(Direction direction) {
        if (!portals.containsKey(direction)) return false;
        portals.put(direction, null);
        return true;
    }


    /**
     * Gets all the {@link Portal portals} on this {@link Wall}.
     *
     * @return list of {@link Portal portals} on this {@link Wall}.
     */
    public List<Portal> getPortals() {
        return (List<Portal>) portals.values();
    }

    /**
     * Removes all the portals on this wall.
     */
    public void clear() {
        for (Portal p : getPortals()) {

        }
        portals.clear();
    }
}