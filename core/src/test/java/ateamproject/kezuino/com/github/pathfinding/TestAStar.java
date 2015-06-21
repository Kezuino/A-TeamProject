package ateamproject.kezuino.com.github.pathfinding;

import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import com.badlogic.gdx.ai.pfa.GraphPath;
import org.junit.*;
import static org.junit.Assert.*;

public class TestAStar {

    private AStar aStar;
    private GameSession session;
    private Map map;

    @Before
    public void setUp() throws Exception {
        session = new GameSession(1);
        map = new Map(session, 20);
        map.getNode(3, 10).setWallForced(true);
        aStar = new AStar(map);
    }

    @Test
    public void testGeneratePath() {
        Node node = map.getNode(3, 14);
        Node endNode = map.getNode(3, 3);
        GraphPath<Node> result = aStar.searchNodePath(node, endNode);

        // All nodes in the path must be valid.
        for (Node n : result) {
            assertNotNull("Node must not be null.", n);
            assertFalse("Node must not be a wall.", n.isWall());
        }

        // Create impossible situation for pathfinding.
        for (int i = 0; i < 20; i++) {
            map.getNode(i, 4).setWallForced(true);
        }
        aStar = new AStar(map);
        result = aStar.searchNodePath(node, endNode);
        assertEquals("Result path must be empty.", 0, result.getCount());
    }
}
