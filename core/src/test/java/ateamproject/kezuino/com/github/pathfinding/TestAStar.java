package ateamproject.kezuino.com.github.pathfinding;

import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import org.junit.Before;
import org.junit.Test;

public class TestAStar {

    private AStar aStar;
    private GameSession session;
    private Map map;

    @Before
    public void setUp() throws Exception {
        session = new GameSession();
        map = new Map(session, 20);
        aStar = new AStar(map);
    }

    @Test
    public void testGeneratePath() {
        Node node = map.getNode(3, 14);
        Node endNode = map.getNode(3, 3);
        GraphPath<Node> result = new DefaultGraphPath<>();
        aStar.searchNodePath(node, endNode, (node1, endNode1) -> 0, result);

        for (Node n : result) {
            System.out.println(n);
        }
    }
}
