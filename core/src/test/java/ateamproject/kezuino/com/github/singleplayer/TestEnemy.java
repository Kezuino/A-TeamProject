/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Sven
 */
public class TestEnemy {

    GameSession session;
    Map map;
    Node spawn;
    Enemy enemy;
    Node teleportNode;

    @Before
    public void setUp() {
        session = new GameSession(20);
        map = session.getMap();
        spawn = map.getNode(19, 19);
        enemy = new Enemy(null, map, spawn.getX(), spawn.getY(), 100, Direction.Down, Color.BLACK);
        teleportNode = map.getNode(19, 19);
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
        enemy = new Enemy(null, map, spawn.getX(), spawn.getY(), 100, Direction.Down);
        assertEquals("Enemy should not follow any object.", null, enemy.getObjectToFollow());
        assertFalse("Enemy should not be dead.", enemy.isDead());
        assertFalse("Enemy's status should not be edible.", enemy.isEdible());
        assertEquals("Enemy should currently be on spawn because it has not moved and was just spawned.", spawn, enemy.getNode());
        assertEquals("Enemy's default direction should be downward.", Direction.Down, enemy.getDirection());
        assertEquals("Enemy's default color should be WHITE.", Color.WHITE, enemy.getColor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongConstructor() {
        Enemy wrongEnemy = new Enemy(null, map, spawn.getX(), spawn.getY(), 10, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongConstructorSpeed0() {
        Enemy wrongEnemy = new Enemy(null, map, spawn.getX(), spawn.getY(), 0, Direction.Up, Color.YELLOW);
    }

    @Test
    public void testSetIsDead() {
        /**
         * Sets the dead propertie of the enemy. If the enemy is dead it should be set to true, if the enemy is not dead should be false.
         *
         * @param dead Must be true or false, cannot be null
         */
        assertFalse("Enemy must not be dead.", enemy.isDead());
        enemy.setDead(true);
        assertTrue("Enemy must be dead.", enemy.isDead());
    }

    @Test
    public void testEnemyRespawn() {
        /**
         * Will move a Enemy to its spawn and reset some of its properties.
         */
        assertEquals("Enemy hasn't moved and should be on spawn.", spawn, enemy.getNode());
        assertTrue("Movement must succeed.", enemy.setPosition(15, 14));
        assertNotEquals("Enemy position has been moved and should not be on spawn.", spawn, enemy.getNode());
        enemy.respawn();
        assertEquals("Enemy has respawned and should be on spawn.", spawn, enemy.getNode());
    }

    @Test
    public void testEnemyTeleport() {
        /**
         * Will move the enemy to a specific location.
         *
         * @param position Node, cannot be null
         */
        enemy.setPosition(teleportNode.getX(), teleportNode.getY());
        assertEquals(teleportNode, enemy.getNode());
    }
}

