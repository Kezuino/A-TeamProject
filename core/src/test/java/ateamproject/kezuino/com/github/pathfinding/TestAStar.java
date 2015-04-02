package ateamproject.kezuino.com.github.pathfinding;

import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import org.junit.*;
import static org.junit.Assert.*;

public class TestAStar {

    private AStar aStar;
    private GameSession session;
    private Map map;

    @Before
    public void setUp() throws Exception {
        session = new GameSession();
        map = new Map(session, 20);
        map.getNode(3, 10).setWall(true);
        aStar = new AStar(map);
    }

    @Test
    public void testGeneratePath() {
        Node node = map.getNode(3, 14);
        Node endNode = map.getNode(3, 3);
        GraphPath<Node> result = new DefaultGraphPath<>();
        aStar.searchNodePath(node, endNode, (node1, endNode1) -> 0, result);

        // All nodes in the path must be valid.
        for (Node n : result) {
            assertNotNull("Node must not be null.", n);
            assertFalse("Node must not be a wall.", n.isWall());
        }
    }
}
