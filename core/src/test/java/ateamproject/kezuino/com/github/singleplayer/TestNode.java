/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kez and Jules, Anton
 */
public class TestNode {
    private GameSession testSession;
    private Node nodeForPactale, nodeforEnemy, nodeForItem, nodeForWall;
    private Pactale testPactale;
    private Enemy testEnemy;
    private Item testItem;
    private Wall testWall;

    @Before
    public void setUp() {
        this.testSession = new GameSession(10, 10);

        this.nodeForPactale = new Node(this.testSession.getMap(), 1, 5);
        this.testPactale = new Pactale(this.testSession.getMap(), 1, 5, 3, 0.1f, Direction.Left, Color.CLEAR);
        this.nodeForPactale.addGameObject(testPactale);

        this.nodeforEnemy = new Node(this.testSession.getMap(), 3, 1);
        this.testEnemy = new Enemy(this.testPactale, this.testSession.getMap(), 3, 1, 0.1f, Direction.Right);

        this.nodeForItem = new Node(this.testSession.getMap(), 0, 8);
        this.testItem = new Item("Item Name", this.nodeForItem,ItemType.BigNugget);

        this.nodeForWall = new Node(this.testSession.getMap(), 1, 10);
        this.testWall = new Wall(this.nodeForWall);
    }

    @Test
    public void testGetMap() {
        /**
         * Returns the map from where the node currently resides.
         */
        assertSame("Session map must be the same as the node for item.", this.testSession.getMap(), this.nodeForItem.getMap());
        assertSame("Session map must be the same as the node for pactale.", this.testSession.getMap(), this.nodeForPactale.getMap());
        assertSame("Session map must be the same as the node for enemy.", this.testSession.getMap(), this.nodeforEnemy.getMap());
        assertSame("Session map must be the same as the node for wall.", this.testSession.getMap(), this.nodeForWall.getMap());
    }

    @Test
    public void testGetWall() {
        /**
         * Gets the {@link Wall} from a {@link Node}.
         */
        assertSame("Testwall must be the same as the wall of the node for wall.", this.testWall, this.nodeForWall.getWall());
    }

    @Test
    public void testSetWall() {
        /**
         * Sets a new default {@link Wall} on the {@link Node}.
         *
         * @return The newly created {@link Wall}.
         */
        assertNotNull("Node must have a wall.", nodeForWall.getWall());
        nodeForWall.removeWall();
        assertNull("Wall must be unset on node.", nodeForWall.getWall());
        nodeForWall.setWall();
        assertNotNull("Node must have a wall again.", nodeForWall.getWall());
    }

    @Test
    public void testSetWall2() {
        /**
         * Sets a new {@link Wall} on the {@link Node}.
         *
         * @param wall {@link Wall} to set on this {@link Node}.
         * @return {@link Wall} that has been set on this {@link Node}.
         */
        assertNotNull("Node must have a wall.", nodeForWall.getWall());
        nodeForWall.removeWall();
        assertNull("Wall must be unset on node.", nodeForWall.getWall());
        nodeForWall.setWall(testWall);
        assertSame("Node must have the wall that has been set.", testWall, nodeForWall.getWall());
    }

    @Test
    public void testRemoveWall() {
        /**
         * Removes the {@link Wall} and returns if succeeded.
         *
         * @return If true, {@link Wall} was removed.
         */
        assertNotNull("Wall must not be null.", nodeForWall.getWall());
        nodeForWall.removeWall();
        assertNull("Wall must have been removed.", nodeForWall.getWall());
    }

    @Test
    public void testGetItem() {
        /**
         * Returns {@link Item} contained by this {@link Node}.
         *
         * @return {@link Item} contained by this {@link Node}.
         */
        assertSame("Item of item for node must be the same object as testitem.", this.testItem, this.nodeForItem.getItem());
    }

    @Test
    public void testSetItem() {
        /**
         * Sets the {@link Item} and returns true if succeeded.
         *
         * @param item {@link Item} to set on this {@link Node}.
         */
        assertNotNull("An item must be set on the node.", nodeForItem.getItem());
        nodeForItem.removeItem();
        assertNull("Item must have been removed from the node.", nodeForItem.getItem());
        Item item = nodeForItem.setItem("TestItem",ItemType.BigNugget);
        assertSame("Item must have been set on the node.", item, nodeForItem.getItem());
    }

    @Test
    public void testSetItem2() {
        /**
         * Sets the {@link Item} and returns true if succeeded.
         *
         * @param item {@link Item} to set on this {@link Node}.
         * @return {@link Item} that has been set on this {@link Node}.
         */
        assertNotNull("An item must be set on the node.", nodeForItem.getItem());
        nodeForItem.removeItem();
        assertNull("Item must have been removed from the node.", nodeForItem.getItem());
        Item item = new Item("TestItem", nodeForItem,ItemType.BigNugget);
        assertSame("Item must have been set on the node.", item, nodeForItem.getItem());
    }

    @Test
    public void testRemoveItem() {
        /**
         * Removes the {@link Item} and returns true if succeeded.
         *
         * @return If true, {@link Item} was successfully removed from this {@link Node}.
         */
        assertNotNull("An item must be set on the node.", nodeForItem.getItem());
        nodeForItem.removeItem();
        assertNull("The item on the node must have been removed.", nodeForItem.getItem());
    }

    @Test
    public void testGetX() {
        /**
         * Gets the Y position of this {@link Node}.
         *
         * @return X position of this {@link Node}.
         */
        assertEquals("NodeForWall x position should be 1.", 1, nodeForWall.getX());
        assertEquals("NodeForItem x position should be 0.", 0, nodeForItem.getX());

    }

    @Test
    public void testGetY() {
        /**
         * Gets the X position of this {@link Node}.
         *
         * @return X position of this {@link Node}.
         */
        assertEquals("NodeForWall y position should be 10.", 10, nodeForWall.getY());
        assertEquals("NodeForItem y position should be 8.", 8, nodeForItem.getY());
    }

    @Test
    public void testGetGameObjects() {
        /**
         * Gets all the {@link GameObject GameObjects} that are on this {@link Node}.
         *
         * @return all {@link GameObject GameObjects} on this {@link Node}.
         */
        assertEquals("NodeForPactale must have one GameObject.", 1, nodeForPactale.getGameObjects().size());
        GameObject proc = new Projectile(testSession.getMap(), nodeForPactale.getX(), nodeForPactale.getY(), testPactale, 1, Direction.Up, Color.WHITE);
        nodeForPactale.addGameObject(proc);
        assertEquals("NodeForPactale must have two GameObjects.", 2, nodeForPactale.getGameObjects().size());
        nodeForPactale.removeGameObject(proc);
        assertEquals("NodeForPactale must have one GameObject again.", 1, nodeForPactale.getGameObjects().size());
    }

    @Test
    public void testRemoveGameObjects() {
        /**
         * Removes the given {@link GameObject} and returns if succeeded.
         *
         * @param object {@link GameObject} to remove.
         * @return {@link GameObject} that was removed.
         */
        assertEquals("NodeForPactale must have one GameObject.", 1, nodeForPactale.getGameObjects().size());
        nodeForPactale.removeGameObject(nodeForPactale.getGameObjects().get(0));
        assertEquals("NodeForPactale must not have any GameObjects.", 0, nodeForPactale.getGameObjects().size());
    }

    @Test
    public void testAddGameObject() {
        /**
         * Adds a {@link GameObject} to a {@link Node} if it doesn't exist and returns true if it succeeded.
         *
         * @param object to add to the {@link Node}.
         */
        assertEquals("NodeForPactale must have one GameObject.", 1, nodeForPactale.getGameObjects().size());
        nodeForPactale.addGameObject(new Enemy(null, testSession.getMap(), 1, 5, 1, Direction.Left, Color.WHITE));
        assertEquals("NodeForPactale must have two GameObjects.", 2, nodeForPactale.getGameObjects().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddGameObjectNullObject() {
        /**
         * Adds a {@link GameObject} to a {@link Node} if it doesn't exist and returns true if it succeeded.
         *
         * @param object to add to the {@link Node}.
         */
        nodeForPactale.addGameObject(null);
    }

    @Test
    public void hasGameObject() {
        /**
         * Gives a boolean value based on if the given object exists.
         *
         * @param object to check if already exists on this {@link Node}.
         */
        assertTrue("NodeForPactale must have the assigned pactale.", nodeForPactale.hasGameObject(testPactale));
        assertFalse("NodeForPactale must not have a null gameobject.", nodeForPactale.hasGameObject(null));
    }
}
