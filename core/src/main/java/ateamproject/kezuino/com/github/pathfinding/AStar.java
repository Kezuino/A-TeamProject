package ateamproject.kezuino.com.github.pathfinding;

import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Collection;

public class AStar {
    /**
     * {@link Heuristic} used when no {@link Heuristic} was specified in a method.
     */
    public static final Heuristic<Node> DEFAULT_HEURISTIC = (node, endNode) -> 0;

    /**
     * Default A* algorithm to use.
     */
    protected IndexedAStarPathFinder<Node> basePathFinder;

    /**
     * {@link Map} that this {@link AStar pathfinder} should generate {@link GraphPath paths} for.
     */
    protected Map map;

    public AStar(Map map) {
        if (map == null) throw new IllegalArgumentException("Parameter map must not be null.");
        this.map = map;
        this.basePathFinder = new IndexedAStarPathFinder<>(new IndexedGraph<Node>() {
            @Override
            public int getNodeCount() {
                return map.getSize();
            }

            @Override
            public Array<Connection<Node>> getConnections(Node fromNode) {
                if (fromNode == null) throw new IllegalArgumentException("Parameter fromNode must not be null.");
                return fromNode.getConnections();
            }
        });
    }

    public IndexedAStarPathFinder.Metrics getMetrics() {
        return basePathFinder.metrics;
    }

    /**
     * Converts a {@link Collection} of vector2 to a {@link GraphPath} of {@link Node nodes}.
     * Ignores the {@link Node} if the previous {@link Node} is the same.
     *
     * @param collection {@link Collection} of vector2.
     * @return {@link GraphPath} of {@link Node nodes} generates from the {@link #map} and {@code collection}.
     */
    public GraphPath<Node> vector2ToPath(Collection<Vector2> collection) {
        if (collection == null || collection.size() <= 0) return null;

        GraphPath<Node> path = new DefaultGraphPath<>();
        Node node = null;
        for (Vector2 vector2 : collection) {
            Node prevNode = node;
            node = map.getNode(vector2);
            if (node == null || node.equals(prevNode)) continue;
            path.add(node);
        }

        return path;
    }

    /**
     * Converts a {@link GraphPath} of {@link Node} to a {@link Collection} of {@link Vector2} using the {@link #map}.
     *
     * @param graphPath {@link GraphPath} to convert to a {@link Collection} of {@link Vector2}.
     * @return {@link Collection} of {@link Vector2} generated from the {@link Node nodes} in {@code graphPath}.
     */
    public Collection<Vector2> pathToVector2(GraphPath<Node> graphPath) {
        if (graphPath == null) throw new IllegalArgumentException("Parameter graphPath must not be null.");
        Collection<Vector2> result = new ArrayList<>(graphPath.getCount());
        for (Node node : graphPath) {
            result.add(node.getExactPosition());
        }

        return result;
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
