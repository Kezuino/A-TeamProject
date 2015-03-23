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
    public void TestItemConstructor() {
        /**
         * Initializes a item at the given node.
         *
         * @param name the name of the item
         * @param node the node the item is located on
         */
        Item item = new Item("Small gold nugger", node);
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
        Item item = new Item("Small gold nugget", node);
        item.setName("Big gold nugget");
        assertEquals("Big gold nugget", item.getName());
    }

    @Test
    public void setOffSetPosition() {
        /**
         * sets the point from the left and top corner to set the location of
         * the item in the node
         *
         * @param offSetPosition point of the item is located on the node
         */
        Item item = new Item("Small gold nugget", node);
        Point p = new Point(10, 10);
        item.setOffSetPosition(p);

        assertEquals("OffsetPosition of the item does not match!", p, item.getOffSetPosition());

    }

    @Test
    public void Testactivate() {
        /**
         * the target who picks up this item activates the effect this item is
         * carrying
         *
         * @param target target standing on the node with the item
         */

        Item item = new Item("Small gold nugget", node);
        GameObject UserPactale = new Pactale(map, 1, 1, 1, Color.CLEAR, 1.1f, Direction.Right);

        item.activate(UserPactale);
        
        // not complete
    }

}
