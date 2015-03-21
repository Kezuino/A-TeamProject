/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Sven
 */
public class TestEnemy {

    Map map;
    Node spawn;
    Enemy enemy;
    Node teleportNode;

    @Before
    public void setUp() {
        map = new Map(null, 40);
        spawn = new Node(null, 10, 10);
        enemy = new Enemy(null, map, spawn, 100, Direction.Down, Color.BLACK);
        teleportNode = new Node(null, 20, 20);
    }

    @Test
    public void testConstructorEnemey() {
        /**
         * Initializes an enemy. Default isDead = False, isEatable = False.
         * @param pactaleToFollow Pactale object to follow, CAN BE NULL
         * @param position
         * @param map
         * @param spawningpoint
         * @param parameter
         * @param movementSpeed
         * @param walkingDirection
         * @param color
         */
        enemy = new Enemy(null, map, spawn, 100, Direction.Down, Color.BLACK);
        assertEquals(null, enemy.getPactaleToFollow());
        assertFalse(enemy.isDead());
        assertFalse(enemy.isEdible());
        assertEquals(spawn, enemy.getNode());
        assertEquals(Direction.Down, enemy.getDirection());
        assertEquals(Color.BLACK, enemy.getColor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongConstructor() {
        Enemy wrongEnemy = new Enemy(null, map, spawn, 10, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongConstructorSpeed0() {
        Enemy wrongEnemy = new Enemy(null, map, spawn, 0, Direction.Up, Color.YELLOW);
    }

    @Test
    public void testSetIsDead() {
        /**
         * Sets the dead propertie of the enemy. If the enemy is dead it should be set to true, if the enemy is not dead should be false.
         *
         * @param dead Must be true or false, cannot be null
         */
        enemy.setDead(true);
        assertEquals(true, enemy.isDead());
    }

    @Test
    public void testEnemyRespawn() {
        /**
         * Will move a Enemy to its spawn and reset some of its properties.
         */
        enemy.setPosition(20, 20);
        enemy.respawn();
        assertEquals(spawn, enemy.getNode());
    }

    @Test
    public void testEnemeyTeleport() {
        /**
         * Will move the enemy to a specific location.
         *
         * @param position Node, cannot be null
         */
        enemy.teleport(teleportNode);
        assertEquals(teleportNode, enemy.getNode());

    }


}

