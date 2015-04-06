package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMap {
    Map map;

    @Before
    public void setUp() throws Exception {
        GameSession session = new GameSession();
        session.setMap(20);
        map = session.getMap();

        // Set walls.
        map.getNode(14, 19).setWallForced(true);
        map.getNode(13, 13).setWallForced(true);

        // GameObjects.
        Pactale pac = (Pactale) map.addGameObject(13, 13, new Pactale(map, 13, 13, 1, 1, Direction.Down, Color.WHITE));
        map.addGameObject(13, 14, new Enemy(null, map, 13, 14, 1, Direction.Up, Color.WHITE));
        map.addGameObject(13, 14, new Enemy(null, map, 13, 14, 1, Direction.Left, Color.WHITE));
        map.addGameObject(0, 0, new Projectile(map, 0, 0, pac, 1, Direction.Right, Color.WHITE));
    }

    @Test
    public void constructorValidation() {
        /**
         * Initializes a map with a 2D array filled with @see Node.
         */
        assertNotNull("Map should initialize node x:0 y:0.", map.getNode(0, 0));
        assertNotNull("Map should initialize node x:19 y:19.", map.getNode(19, 19));

        /**
         * @param size Width and height dimension length.
         */
        assertNull("Range is out of bounds and should be null.", map.getNode(map.getSize() - 1, map.getSize()));
        assertNull("Range is out of bounds and should be null.", map.getNode(map.getSize(), map.getSize() - 1));
        assertNull("Range is out of bounds and should be null.", map.getNode(map.getSize(), map.getSize()));
    }

    @Test
    public void testGetSize() {
        /**
         * Returns the {@link Node} count that this map has.
         *
         * @return Amount of nodes used by this map.
         */
        assertEquals("Amount of nodes should be 400", 400, map.getSize());
    }

    @Test
    public void testGetWidth() {
        /**
         * X dimension of the size of this {@link Map}.
         *
         * @return number of {@link Node nodes} that this {@link Map} is in the X dimension.
         */
        assertEquals("Width of map should be 20.", 20, map.getWidth());
    }


    @Test
    public void testGetHeight() {
        /**
         * Y dimension of the size of this {@link Map}.
         *
         * @return number of {@link Node nodes} that this {@link Map} is in the Y dimension.
         */
        assertEquals("Height of map should be 20.", 20, map.getHeight());
    }

    @Test
    public void testResetNodes() {
        /**
         * Resets the 2D array to the new dimensions.
         *
         * @param width  X dimension of the {@link Map}.
         * @param height Y dimension of the {@link Map}.
         */
        map.resetNodes(25, 35);
        assertEquals("Width of map should be 25.", 25, map.getWidth());
        assertEquals("Height of map should be 35.", 35, map.getHeight());

        // Test if all nodes are cleared.
        for (Node n : map.getNodes()) {
            assertFalse("Wall must be null.", n.isWall());
        }
    }

    @Test
    public void testGetNode() {
        /**
         * Returns a {@link Node} if found, else it will return null.
         *
         * @param x position to get {@link Node} from.
         * @param y position to get {@link Node} from.
         * @return {@link Node} if {@code x} and {@code y} are in-bounds. Null otherwise.
         */
        assertTrue("Node should have a wall.", map.getNode(14, 19).isWall());
        assertNotNull("Node on edge of map should exist.", map.getNode(19, 19));
        assertNotNull("Node on edge of map should exist.", map.getNode(0, 0));
    }

    @Test
    public void testGetNodes() {
        /**
         * Gets all the {@link Node nodes} of this {@link Map}.
         *
         * @return All the {@link Node nodes} of this {@link Map}.
         */
        assertSame("Node x:0, y:0 is not the same as through getNode.", map.getNodes().get(0, 0), map.getNode(0, 0));
    }

    @Test
    public void testGetAllGameObjects() {
        /**
         * Returns all {@link GameObject gameobjects} within a {@link Map}.
         */
        assertEquals("There must be 4 GameObjects in the map.", 4, map.getAllGameObjects().size());
        assertEquals("There must be 1 testProjectileConstructor in the map.", 1, map.getAllGameObjects().stream().filter(o -> o.getClass().equals(Projectile.class)).count());
        assertEquals("There must be 2 Enemies in the map.", 2, map.getAllGameObjects().stream().filter(o -> o.getClass().equals(Enemy.class)).count());
        map.removeGameObject(map.getAllGameObjects().get(0));
        assertEquals("There must be 1 Enemy in the map.", 1, map.getAllGameObjects().stream().filter(o -> o.getClass().equals(Enemy.class)).count());
    }

    @Test
    public void testGetAdjecentNode() {
        /**
         * Returns a node which is in the direction of the given direction. Will return null if node does not exist.
         *
         * @param node
         * @param direction
         */
        Node baseNode = map.getNode(10, 10);
        assertEquals("Node must be above the other node.", map.getNode(10, 11), map.getAdjacentNode(baseNode, Direction.Up));
        assertEquals("Node must be below the other node.", map.getNode(10, 9), map.getAdjacentNode(baseNode, Direction.Down));
        assertEquals("Node must be left of the other node.", map.getNode(9, 10), map.getAdjacentNode(baseNode, Direction.Left));
        assertEquals("Node must be right of the other node.", map.getNode(11, 10), map.getAdjacentNode(baseNode, Direction.Right));
    }

    @Test
    public void testAddGameObject() {
        /**
         * Adds a {@link GameObject} to a position on this {@link Map}.
         *
         * @param object to add to a {@link ateamproject.kezuino.com.github.singleplayer.Node} on this {@link ateamproject.kezuino.com.github.singleplayer.Map}.
         * @return {@link GameObject} that was added to the {@link Map}.
         */
        Node baseNode = map.getNode(10, 10);
        Pactale p = (Pactale) map.addGameObject(10, 10, new Pactale(map, 10, 10, 1, 1, Direction.Down, Color.WHITE));
        assertTrue("GameObject was not added to the Node.", map.getAllGameObjects().stream().anyMatch(o -> o.equals(p)));
    }
}