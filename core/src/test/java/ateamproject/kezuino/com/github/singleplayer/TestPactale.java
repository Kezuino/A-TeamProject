/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jip
 */
public class TestPactale {

    private Map map;
    private GameSession session;
    private Pactale pactale;

    @Before
    public void setUp() throws Exception {
        session = new GameSession();
        session.setMap(3);
        map = session.getMap();
        pactale = new Pactale(map, 1, 1, 25, .5f, Direction.Down, Color.WHITE);
        map.addGameObject(1, 1, pactale);
    }

    @Test
    public void TestPactaleConstructor() {
        /**
         * Initialize a pactale
         *
         * @param position
         * @param map
         * @param lives
         * @param playerIndex
         * @param color
         * @param parameter
         * @param movementSpeed
         * @param walkingDirection
         */
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Left;
        Color color = new Color(1, 111, 11, 111);
        int lives = 3;
        Pactale p = new Pactale(map, 1, 1, lives, movementSpeed, walkingDirection, color);

        assertEquals("Color needs to be equal.", p.getColor(), color);
        assertEquals("Direction needs to be equal.", p.getDirection(), walkingDirection);
        assertEquals("Lives needs to be equal.", p.getLives(), lives);
        assertEquals("MovementSpeed needs to be equal.", p.getMovementSpeed(), movementSpeed, 0.000005);
        assertEquals("Node needs to be equal.", p.getNode(), map.getNode(1, 1));
        assertEquals("node needs to be equal.", p.getNode(), session.getMap().getNode(1, 1));
    }

    @Test
    public void testShootProjectile() {
        /**
         * Will shoot a portal in the direction that the pactale currently is
         * heading.
         */
        assertEquals("No projectile should be in the map.", 0, map.getAllGameObjects().stream().filter(o -> o instanceof Projectile).count());

    }

    @Test
    public void testRemovePortal() {
        /**
         * Will remove all listed portals from this TestPactaleConstructor
         */
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Left;
        Color color = new Color(1, 111, 11, 111);
        int lives = 3;
        Pactale p = new Pactale(map, 1, 1, lives, movementSpeed, walkingDirection, color);

        Portal portal = new Portal(p, map.getNode(0, 0), Direction.Right);
        assertEquals("The newly created portal must not be null.", portal, p.getPortal());
        p.removePortal();
        Assert.assertNull("Portal should have been removed.", p.getPortal());
    }

    @Test
    public void testMove() {
        /**
         * Changes the direction of the movement of the pactale.
         *
         * @param direction
         */
        Node position = new Node(map, 1, 1);
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Left;
        Color color = new Color(1, 111, 11, 111);
        int lives = 3;
        int playerIndex = 2;
        Pactale p = new Pactale(map, 1, 1, lives, movementSpeed, walkingDirection, color);

        assertEquals("The direction should equal the init value.", Direction.Left, p.getDirection());
        Assert.assertNotEquals("The direction should not equal the init value.", Direction.Up, p.getDirection());
        p.setDirection(walkingDirection.Down);
        assertEquals("The direction should equal the init value.", Direction.Down, p.getDirection());
        Assert.assertNotEquals("The direction should not equal the init value.", Direction.Right, p.getDirection());
    }
}
