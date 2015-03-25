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
    Node moveNode;

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
         * @param map That hosts this {@link GameObject}.
         * @param x X position of this {@link GameObject}.
         * @param y Y position of this {@link GameObject}.
         * @param movementSpeed Speed in seconds that this {@link GameObject}
         * takes to move to another adjacent {@link Node}.
         * @param direction {@link Direction} that this {@link GameObject} is
         * currently facing.
         * @param color {@link com.badlogic.gdx.graphics.Color} that this
         * {@link ateamproject.kezuino.com.github.singleplayer.GameObject} will
         * be drawn at.
         */
        assertEquals(spawn.getX(), enemy.getNode().getX());
        assertEquals(spawn.getY(), enemy.getNode().getY());
        assertEquals("The map that was used to create the Enemy can't be correctly foun again", map, enemy.getNode().getMap());
        assertEquals("The movementspeed didnt initialize at 100 propery", 100, enemy.getMovementSpeed());
        assertEquals("The colour wasn't set to black", Color.BLACK, enemy.getColor());
    }

    @Test
    public void testConstructorGameObject2() {
        /**
         * Initializes this {@link GameObject} with a default
         * {@code Color.WHITE} color.
         *
         * @param map           That hosts this {@link GameObject}.
         * @param x             X position of this {@link GameObject}.
         * @param y             Y position of this {@link GameObject}.
         * @param movementSpeed Speed in seconds that this {@link GameObject}
         * takes to move to another adjacent {@link Node}.
         * @param direction {@link Direction} that this {@link GameObject} is
         * currently facing.
         */
        //Create a enemy without colour
        Enemy enemy2 = new Enemy(null, map, spawn.getX(), spawn.getY(), 100, Direction.Down);
        assertEquals("The constructor without colour didn't make it default white.", Color.WHITE, enemy2.getColor());
    }

    @Test
    public void testSetPosition() {
        /**
         * Teleports this {@link GameObject} to the specified {@code x} and
         * {@code y} coordinates. Doesn't take into account
         * {@link GameObject#movementSpeed} and doesn't use pathfinding.
         *
         * @param x X position to set this {@link GameObject} to.
         * @param y Y position to set this {@link GameObject} to.
         * @return True if succesfully changed the Position, false if it didn't.
         */
        
        //If we try to set the position to a location on the map should return true
        assertTrue(enemy.setPosition(10,10));
        //If we try to set the position to a X and Y so high it should never be on anny map, should return false.
        assertFalse(enemy.setPosition(214700000, 2147000000));
    }
    
    @Test
    public void testMoveAdjecentNode(){
            /**
     * Moves this {@link GameObject} to another adjacent {@link Node} based on
     * the given {@code direction}. If {@link #movementInterpolation} is true,
     * this movement should be pixel-perfectly smooth between the nodes. If
     * {@link #movementInterpolation} is false, this movement should move
     * immediately and wait until it can move again based on
     * {@link #movementSpeed}.
     *
     * @param direction {@link Direction} to move in (to an adjacent
     *                  {@link Node}).
     */
        
        //Enemy was spawned on default 19 19. If we move it up one the y should go -1.
        enemy.moveAdjacent(Direction.Up);
        assertEquals(18,enemy.getY());
        //If we go back down, should be 19 again
        enemy.moveAdjacent(Direction.Down);
        assertEquals(19,enemy.getY());
        
    }
    
    @Test
    public void testMove(){
            /**
     * Tries to move this {@link GameObject} to another {@link Node} using
     * pathfinding based on the {@link #movementSpeed}.
     *
     * @param node {@link Node} to move towards.
     * @see #moveAdjacent(Direction)
     */
        //Create a node and move the enemy to that node.
        moveNode = map.getNode(10, 10);
        enemy.move(moveNode);
        assertEquals(10,enemy.getX());
        assertEquals(10,enemy.getY());
    }

}
