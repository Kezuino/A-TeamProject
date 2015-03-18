package ateamproject.kezuino.com.github.singleplayer;


import ateamproject.kezuino.com.github.singleplayer.Map;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMap {
    @Test
    public void constructorValidation() {
        /**
         * Initializes a map with a 2D array filled with @see Node.
         */
        GameSession session = new GameSession(20);
        assertNotNull("Map should initialize node x:0 y:0.", map.getNode(0, 0));
        assertNotNull("Map should initialize node x:19 y:19.", map.getNode(19, 19));

        /**
        * @param size Width and height dimension length.
        */
        assertNull("Range is out of bounds and should be null.", map.getNode(map.getSize()-1, map.getSize()));
        assertNull("Range is out of bounds and should be null.", map.getNode(map.getSize(), map.getSize()-1));
        assertNull("Range is out of bounds and should be null.", map.getNode(map.getSize(), map.getSize()));

    }
}