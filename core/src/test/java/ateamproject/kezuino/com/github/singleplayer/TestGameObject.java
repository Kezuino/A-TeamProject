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
public class TestGameObject {

    public TestGameObject() {
    }

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
    }

    //Game object is an abstract class, in this unittest we will test the child class Enemey to check it's parents methods.

    @Test
    public void testConstructorGameObject() {
        /**
         * Initializes this {@link GameObject}.
         *
         * @param map           That hosts this {@link GameObject}.
         * @param x             X position of this {@link GameObject}.
         * @param y             Y position of this {@link GameObject}.
         * @param movementSpeed Speed in seconds that this {@link GameObject} takes to move to another adjacent {@link Node}.
         * @param direction     {@link Direction} that this {@link GameObject} is currently facing.
         * @param color         {@link com.badlogic.gdx.graphics.Color} that this {@link ateamproject.kezuino.com.github.singleplayer.GameObject} will be drawn at.
         */
        assertEquals(spawn.getX(), enemy.getNode().getX());
        assertEquals(spawn.getY(), enemy.getNode().getY());
        assertEquals("De map die is meegegeven aan de enemy is niet hetzelfde", map, enemy.getNode().getMap());
        assertEquals("De snelheid is niet goed op 100 gezet", 100, enemy.getMovementSpeed());
        assertEquals("De kleur is niet goed gezet", Color.BLACK, enemy.getColor());
    }

    @Test
    public void testConstructorGameObject2() {
        /**
         * Initializes this {@link GameObject} with a default {@code Color.WHITE} color.
         *
         * @param map           That hosts this {@link GameObject}.
         * @param x             X position of this {@link GameObject}.
         * @param y             Y position of this {@link GameObject}.
         * @param movementSpeed Speed in seconds that this {@link GameObject} takes to move to another adjacent {@link Node}.
         * @param direction     {@link Direction} that this {@link GameObject} is currently facing.
         */
        //Een enemy maken zonder kleur mee te geven.
        Enemy enemy2 = new Enemy(null, map, spawn.getX(), spawn.getY(), 100, Direction.Down);
        assertEquals(Color.WHITE, enemy2.getColor());
    }

    @Test
    public void testSetPosition() {
        /**
         * Teleports this {@link GameObject} to the specified {@code x} and {@code y} coordinates.
         * Doesn't take into account {@link GameObject#movementSpeed} and doesn't use pathfinding.
         *
         * @param x X position to set this {@link GameObject} to.
         * @param y Y position to set this {@link GameObject} to.
         * @return
         */
    }

}
