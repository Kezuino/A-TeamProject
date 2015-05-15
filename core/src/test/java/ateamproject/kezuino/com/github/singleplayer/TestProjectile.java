/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jip
 */
public class TestProjectile {

    private GameSession session;
    private Map map;

    @Before
    public void setUp() throws Exception {
        session = new GameSession();
        session.setMap(new Map(session, 3));
        map = session.getMap();
    }

    @Test
    public void testProjectileConstructor() {
        /**
         * Initializes a new projectile.
         *
         * @param owner
         * @param position
         * @param movementSpeed
         * @param walkingDirection
         * @param color
         */
        Node position = map.getNode(1, 1);
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Left;
        Color color = new Color(1, 111, 11, 111);
        Pactale owner = new Pactale(new Vector2(1, 1), 1, movementSpeed, walkingDirection, color);

        Projectile p;
        p = new Projectile(new Vector2(1, 1), owner, movementSpeed, walkingDirection, color);
        
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
        map.getNode(2, 1).setWallForced(true);
        float movementSpeed = 3;
        Direction walkingDirection = Direction.Right;
        Color color = new Color(1, 111, 11, 111);
        Pactale owner = new Pactale(new Vector2(1, 1), 1, movementSpeed, walkingDirection, color);
        Projectile p = new Projectile(new Vector2(1, 1), owner, movementSpeed, walkingDirection, color);

//        assertTrue("It must collide with the wall when the pactale moves to the right.", p.hasCollision());
//        p.setDirection(Direction.Left);
//        assertFalse("It musn't collide with the wall when the pactale moves to the left.", p.hasCollision());
//        p.setDirection(Direction.Up);
//        assertFalse("It musn't collide with the wall when the pactale moves up.", p.hasCollision());
//        p.setDirection(Direction.Down);
//        assertFalse("It musn't collide with the wall when the pactale moves down.", p.hasCollision());
    }
}
