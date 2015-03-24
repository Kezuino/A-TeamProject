/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

/**
 * @author Fatih
 */
public class TestWall {

    private Node node;
    private Map map;

    @Before
    public void setup() {
        GameSession session = new GameSession(10);
        map = session.getMap();
        node = new Node(map, 1, 1);
    }

    @Test
    public void constructorValidation() {
        /**
         * Initializes a wall at the position of the given node.
         *
         * @param node
         */
        Wall wall = new Wall(node);
        assertNotNull(wall);
    }

    @Test
    public void testGetPortal() {
        /**
         * Returns the {@link Portal} on the {@link Wall} on the side specified by {@code direction}.
         *
         * @param direction of the side of the {@link Wall} to get the {@link Portal} from.
         * @return portal at the direction of the {@link Wall} or null.
         */

        Wall wall = new Wall(node);
        Pactale pactale = new Pactale(map, 1, 1, 1, 1.1f, Direction.Right, Color.CLEAR);

        // Add Left portal
        Portal portalL = new Portal(pactale, wall, Direction.Left);
        wall.setPortal(Direction.Left, portalL);

        // Get Left portal
        Portal Left = wall.getPortal(Direction.Left);
        assertNotNull(Left);

        // Get Right portal
        Portal Right = wall.getPortal(Direction.Right);
        assertNull(Right);
    }


    @Test
    public void testSetPortal() {

        /**
         * Sets a {@link Portal} to the side of the {@link Wall} specified by
         * the {@code direction}.
         *
         * @param direction to set the {@link Portal} on.
         * @param portal to set on the side of the {@link Wall}.
         */
        Wall wall = new Wall(node);
        Pactale pactale = new Pactale(map, 1, 1, 1, 1.1f, Direction.Right, Color.CLEAR);

        // Left portal
        Portal portalL = new Portal(pactale, wall, Direction.Left);
        boolean succesL = wall.setPortal(Direction.Left, portalL);
        assertTrue(succesL);
        assertNotNull(wall.getPortal(Direction.Left));

        // Right portal
        Portal portalR = new Portal(pactale, wall, Direction.Right);
        boolean succesR = wall.setPortal(Direction.Right, portalR);
        assertTrue(succesR);
        assertNotNull(wall.getPortal(Direction.Right));

        // Left portal (again)
        Portal portalFL = new Portal(pactale, wall, Direction.Left);
        boolean FailL = wall.setPortal(Direction.Left, portalFL);
        assertFalse(FailL);

    }

    @Test
    public void RemovePortal() {
        /**
         * Removes a {@link Portal} from the side of the {@link Wall} if it exists.
         *
         * @param direction of the side on the {@link Wall} to look for a {@link Portal} to remove.
         * @return if true, removed a {@link Portal} from the {@link Wall}.
         */
        Wall wall = new Wall(node);

        Pactale pactale = new Pactale(map, 1, 1, 1, 1.1f, Direction.Right, Color.CLEAR);

        Portal portalL = new Portal(pactale, wall, Direction.Left);
        wall.setPortal(Direction.Left, portalL);
        Portal portalR = new Portal(pactale, wall, Direction.Right);
        wall.setPortal(Direction.Right, portalR);

        // existing portal
        assertNotNull(wall.getPortal(Direction.Left));
        assertTrue(wall.removePortal(Direction.Left));
        assertNull(wall.getPortal(Direction.Left));

        // non existing portal
        assertFalse(wall.removePortal(Direction.Down));
    }

    @Test
    public void testGetPortals() {
        /**
         * Gets all the {@link Portal portals} on this {@link Wall}.
         *
         * @return list of {@link Portal portals} on this {@link Wall}.
         */
        Wall wall = new Wall(node);
        Pactale pactale = new Pactale(map, 1, 1, 1, 1.1f, Direction.Right, Color.CLEAR);

        Portal portalL = new Portal(pactale, wall, Direction.Left);
        wall.setPortal(Direction.Left, portalL);
        Portal portalR = new Portal(pactale, wall, Direction.Right);
        wall.setPortal(Direction.Right, portalR);

        int count = 0;
        for (Portal curportal : wall.getPortals()) {
            count++;
        }

        assertEquals(2, count);
    }


    @Test
    public void clear() {
        /**
         * Removes all the portals on this wall.
         */

        Wall wall = new Wall(node);
        Pactale pactale = new Pactale(map, 1, 1, 1, 1.1f, Direction.Right, Color.CLEAR);

        // add walls
        Portal portalL = new Portal(pactale, wall, Direction.Left);
        wall.setPortal(Direction.Left, portalL);
        Portal portalR = new Portal(pactale, wall, Direction.Right);
        wall.setPortal(Direction.Right, portalR);

        // clear walls
        wall.clear();

        int count = 0;
        for (Portal curportal : wall.getPortals()) {
            count++;
        }

        assertEquals(0, count);
    }
}
