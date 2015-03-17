package github.singleplayer;


import ateamproject.kezuino.com.github.singleplayer.Map;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestMap {
    @Test
    public void constructorValidation() {
        /**
         * Initializes a map with a 2D array filled with @see Node.
         */
        Map map = new Map(20);
        assertNotNull("Map should initialize node x:0 y:0.", map.getNode(0, 0));
        assertNotNull("Map should initialize node x:19 y:19.", map.getNode(19, 19));

        /**
        * @param size Width and height dimension length.
        */
        try {
            map.getNode(19, 20);
            map.getNode(20, 19);
            map.getNode(20, 20);
            fail("Nodes at position x:20 or y:20 should be out of bounds.");
        } catch (IndexOutOfBoundsException ex) {
        }
    }
}