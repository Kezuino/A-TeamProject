/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jip
 */
public class TestPactale {

    @Test
    public void Pactale() {
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
        GameSession g = new GameSession();
        g.setMap(3);
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Left;
        Color color = new Color(1, 111, 11, 111);
        int lives = 3;
        int playerIndex = 2;
        Pactale p = new Pactale(g.getMap(), 1, 1, lives, movementSpeed, walkingDirection, color);

        Assert.assertEquals("Color needs to be equal", p.getColor(), color);
        Assert.assertEquals("Direction needs to be equal", p.getDirection(), walkingDirection);
        Assert.assertEquals("Lives needs to be equal", p.getLives(), lives);
        Assert.assertEquals("MovementSpeed needs to be equal", p.getMovementSpeed(), movementSpeed, 0.000005);
        Assert.assertEquals("Node needs to be equal", p.getNode(), g.getMap().getNode(1, 1));
    }

    @Test
    public void shootPortal() {
        /**
         * Will shoot a portal in the direction that the pactale currently is
         * heading.
         */
        Assert.fail("This test is not made");
    }

    @Test
    public void removePortal() {
        /**
         * Will remove all listed portals from this Pactale
         */
        GameSession g = new GameSession();
        g.setMap(3);
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Left;
        Color color = new Color(1, 111, 11, 111);
        int lives = 3;
        Pactale p = new Pactale(g.getMap(), 1, 1, lives, movementSpeed, walkingDirection, color);

        Assert.assertNull("There needs to be no portal", p.getPortal());
        p.shootProjectile();
        Assert.assertEquals("The newly added portal should match", new Integer(1), p.getPortal());
        p.removePortal();
        Assert.assertNull("There needs to be no portal", p.getPortal());
    }

    @Test
    public void move() {
        /**
         * Changes the direction of the movement of the pactale.
         *
         * @param direction
         */
        GameSession g = new GameSession();
        g.setMap(3);
        Node position = new Node(g.getMap(), 1, 1);
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Left;
        Color color = new Color(1, 111, 11, 111);
        int lives = 3;
        int playerIndex = 2;
        Pactale p = new Pactale(g.getMap(), 1, 1, lives, movementSpeed, walkingDirection, color);

        Assert.assertEquals("The direction should equal the init value", Direction.Left, p.getDirection());
        Assert.assertNotEquals("The direction should not equal the init value", Direction.Up, p.getDirection());
        p.setDirection(walkingDirection.Down);
        Assert.assertEquals("The direction should equal the init value", Direction.Down, p.getDirection());
        Assert.assertNotEquals("The direction should not equal the init value", Direction.Right, p.getDirection());
    }
}
