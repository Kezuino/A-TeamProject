package ateamproject.kezuino.com.github.singleplayer;

import java.util.*;

public class Wall {

    private HashMap<Portal, Direction> portals;
    private Node node;
    private Collection<Portal> portal;

    /**
     * Initializes a wall at the position of the given node.
     *
     * @param node
     */
    public Wall(Node node) {

        // TODO - implement Wall.Wall
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the portal on the wall object of the given direction
     *
     * @param direction
     * @return portal object if a portal exist on that direction, returns null
     * if no portal is found on that direction
     */
    public Portal getPortal(Direction direction) {
        // TODO - implement Wall.getPortal
        throw new UnsupportedOperationException();

    }

    /**
     * @param direction
     * @param portal
     */
    public void setPortal(Direction direction, Portal portal) {
        // TODO - implement Wall.setPortal
        throw new UnsupportedOperationException();
    }

    /**
     * @param direction
     */
    public boolean removePortal(Direction direction) {
        // TODO - implement Wall.removePortal
        throw new UnsupportedOperationException();
    }


    public HashMap<Portal, Direction> getPortals() {
        return this.portals;
    }

}