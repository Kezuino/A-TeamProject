package ateamproject.kezuino.com.github.pathfinding;

import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

/**
 * {@link AStar} pathfinding for {@link Node nodes}.
 */
public class AStar extends IndexedAStarPathFinder<Node> {
    
    public AStar(Map map) {
        super(new IndexedGraph<Node>() 
        {
            @Override
            public int getNodeCount() {
                return map.getSize();
            }

            @Override
            public Array<Connection<Node>> getConnections(Node fromNode) {
                return fromNode.getConnections();
            }
        });
    }
}
