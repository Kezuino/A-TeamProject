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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Fatih
 */
public class TestItem {

    private Node node;
    private Map map;

    @Before
    public void setup() {
        GameSession session = new GameSession(1);
        session.setMap(10);
        map = session.getMap();
        node = new Node(map, 1, 1);
    }

    @Test
    public void testItemConstructor() {
        /**
         * Initializes a item at the given node.
         *
         * @param name the name of the item
         * @param node the node the item is located on
         */
        Item item = new Item("Small gold nugger", node.getExactPosition(), ItemType.BigObject);
        item.setMap(this.map);
        assertNotNull(item);

        assertEquals("Item name must be the same as given by constructor and returned by item.getName().", "Small gold nugger", item.getName());
    }

    @Test
    public void setName() {
        /**
         * sets the item name of the object
         *
         * @param name Name of the item
         */
        Item item = new Item("Small gold nugget", node.getExactPosition(), ItemType.BigObject);
        item.setMap(this.map);
        item.setName("Big gold nugget");
        assertEquals("Big gold nugget", item.getName());
    }

    @Test
    public void testActivate() {
        /**
         * the target who picks up this item activates the effect this item is
         * carrying
         *
         * @param target target standing on the node with the item
         */

        Item item = new Item("Small gold nugget", node.getExactPosition(), ItemType.BigObject);
        item.setMap(map);
        GameObject UserPactale = new Pactale(new Vector2(1, 1), 1, 1.1f, Direction.Right, Color.WHITE);

        item.activate(UserPactale);

        // not complete
    }

}
