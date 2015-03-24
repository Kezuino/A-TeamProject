package ateamproject.kezuino.com.github.singleplayer;

import java.util.*;
import java.util.stream.Collectors;

public class Wall {

    private HashMap<Direction, Portal> portals;
    private Node node;

    /**
     * Initializes a {@link Wall} at the position of the given {@link Node}.
     *
     * @param node {@link Node} that this {@link Wall} should be set on.
     */
    public Wall(Node node) {
        portals = new HashMap<>();
        this.node = node;
        this.node.setWall(this);
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
        return portals.values().stream().collect(Collectors.toList());
    }

    /**
     * Removes all {@link Portal portals} from this {@link Wall}.
     */
    public void clear() {
        for (Portal p : getPortals()) {
            p.getOwner().removePortal();
        }
        portals.clear();
    }

    /**
     * Removes all {@link Portal portals} in the given {@link Direction side} of the {@link Wall}.
     *
     * @param side {@link Direction side} of the {@link Wall} to remove the {@link Portal portals} from.
     */
    public void clearDirection(Direction side) {
        if (side == null) throw new IllegalArgumentException("Parameter side must not be null.");
        for (Portal p : getPortals().stream().filter(p2 -> p2.getDirection() == side).collect(Collectors.toList())) {
            p.getOwner().removePortal();
            portals.remove(p);
        }
    }
}