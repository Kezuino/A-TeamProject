/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Jip
 */
public class TestProjectile {

    @Test
    public void Projectile() {
        /**
         * Initializes a new projectile.
         *
         * @param owner
         * @param position
         * @param movementSpeed
         * @param walkingDirection
         * @param color
         */
        GameSession g = new GameSession(3);
        Node position = new Node(g.getMap(), 1, 1);
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Left;
        Color color = new Color(1, 111, 11, 111);
        Pactale owner = new Pactale(g.getMap(), 1, 1, 1, color, movementSpeed, walkingDirection);

        Projectile p = new Projectile(g.getMap(), 1, 1, owner, movementSpeed, walkingDirection, color);
        assertEquals("The color needs to be set", p.getColor(), color);
        assertEquals("The direction needs to be set", p.getDirection(), walkingDirection);
        assertEquals("The movementspeed needs to be set", p.getMovementSpeed(), movementSpeed, 0.000005);
        assertEquals("The position needs to be set", p.getNode(), position);
        assertEquals("The pactale needs to be set", p.getOwner(), owner);
    }

    @Test
    public void hasCollision() {
        /**
         * Will check if a colission has happened and returns a boolean
         * accordingly.
         *
         * @param direction
         */
        GameSession g = new GameSession(3);
        Node position = new Node(g.getMap(), 0, 0);
        g.getMap().getNode(1, 0).setWall(new Wall(g.getMap().getNode(1, 0)));
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Right;
        Color color = new Color(1, 111, 11, 111);
        Pactale owner = new Pactale(g.getMap(), 1, 1, 1, color, movementSpeed, walkingDirection);
        Projectile p = new Projectile(g.getMap(), 1, 1, owner, movementSpeed, walkingDirection, color);

        assertTrue("It needs to collide with the wall when it goes rights", p.hasCollision(Direction.Right));
        assertFalse("It needs not to collide with the wall when it goes down", p.hasCollision(Direction.Down));
        assertFalse("It needs not to collide with the wall when it goes up", p.hasCollision(Direction.Up));
    }
}
