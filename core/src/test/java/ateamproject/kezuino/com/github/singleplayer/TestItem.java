/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Fatih
 */
public class TestItem {

    private Node node;
    private Map map;

    @Before
    public void setup() {
        GameSession session = new GameSession(10);
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
        Item item = new Item("Small gold nugger", node,ItemType.BigNugget);
        assertNotNull(item);

        assertEquals("Small Dot/Item", item.getName());
    }

    @Test
    public void setName() {
        /**
         * sets the item name of the object
         *
         * @param name Name of the item
         */
        Item item = new Item("Small gold nugget", node,ItemType.BigNugget);
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

        Item item = new Item("Small gold nugget", node,ItemType.BigNugget);
        GameObject UserPactale = new Pactale(map, 1, 1, 1, 1.1f, Direction.Right, Color.CLEAR);

        item.activate(UserPactale);
        
        // not complete
    }

}
