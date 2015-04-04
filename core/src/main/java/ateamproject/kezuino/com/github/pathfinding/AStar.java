package ateamproject.kezuino.com.github.pathfinding;

import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

public class AStar {
    /**
     * {@link Heuristic} used when no {@link Heuristic} was specified in a method.
     */
    public static final Heuristic<Node> DEFAULT_HEURISTIC = (node, endNode) -> 0;
    protected IndexedAStarPathFinder<Node> basePathFinder;

    public AStar(Map map) {
        basePathFinder = new IndexedAStarPathFinder<>(new IndexedGraph<Node>() {
            @Override
            public int getNodeCount() {
                return map != null ? map.getSize() : 0;
            }

            @Override
            public Array<Connection<Node>> getConnections(Node fromNode) {
                if (fromNode == null) throw new IllegalArgumentException("Parameter fromNode must not be null.");
                return fromNode != null ? fromNode.getConnections() : new Array<>();
            }
        });
        if (map == null) throw new IllegalArgumentException("Parameter map must not be null.");

    }

    public IndexedAStarPathFinder.Metrics getMetrics() {
        return basePathFinder.metrics;
    }

    /**
     * Searches the {@link Map} for a {@link GraphPath Path} through the {@link Map} using the available {@link Connection Connections} given for each {@link Node}.
     * The {@link GraphPath Path} will be empty if false was returned and thus a {@link GraphPath} could not be made from the {@link IndexedAStarPathFinder AStar} search.
     *
     * @param startNode {@link Node} to start from. Must be passable!
     * @param endNode   {@link Node} as destination. If unreachable, an empty {@link GraphPath Path} will be returned.
     * @param heuristic {@link Heuristic} to calculate the cost of moving from one type of {@link Node} to another type of {@link Node}.
     * @param outPath   {@link GraphPath Path} that will be returned. Might be empty if no {@link Node Nodes} are found.
     * @return True if a {@link GraphPath Path} was successfully searched.
     */
    public boolean searchNodePath(Node startNode, Node endNode, Heuristic<Node> heuristic, GraphPath<Node> outPath) {
        return basePathFinder.searchNodePath(startNode, endNode, heuristic, outPath);
    }

    /**
     * Searches the {@link Map} for a {@link GraphPath Path} through the {@link Map} using the available {@link Connection Connections} given for each {@link Node}.
     * The {@link GraphPath Path} will be empty if false was returned and thus a {@link GraphPath} could not be made from the {@link IndexedAStarPathFinder AStar} search.
     *
     * @param startNode {@link Node} to start from. Must be passable!
     * @param endNode   {@link Node} as destination. If unreachable, an empty {@link GraphPath Path} will be returned.
     * @return True if a {@link GraphPath Path} was successfully searched.
     */
    public GraphPath<Node> searchNodePath(Node startNode, Node endNode) {
        GraphPath<Node> outPath = new DefaultGraphPath<>();
        basePathFinder.searchNodePath(startNode, endNode, DEFAULT_HEURISTIC, outPath);
        return outPath;
    }
}
