package ateamproject.kezuino.com.github.singleplayer;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Nodes implements Iterable<Node> {
    protected Node[][] nodes;

    public Nodes(int width, int height) {
        nodes = new Node[width][height];
    }

    public Nodes(Node[][] nodes) {
        this.nodes = nodes;
    }

    public int size() {
        if (nodes.length == 0) return 0;

        int size = 0;
        for (int x = 0; x < nodes.length; x++) {
            size += nodes[x].length;
        }
        return size;
    }


    public int getLength() {
        return nodes.length;
    }

    public int getLength(int dimension) {
        return nodes[dimension].length;
    }

    /**
     * Retrieves the node from the specified x and y dimensions.
     *
     * @param x Dimension of the internal 2d node array.
     * @param y Dimension of the internal 2d node array.
     * @return Node if found.
     */
    public Node get(int x, int y) {
        return nodes[x][y];
    }

    /**
     * Changes the value in the 2d array by the x and y.
     *  @param x    Dimension of the internal 2d node array.
     * @param y    Dimension of the internal 2d node array.
     * @param node Value to set on dimension.
     */
    public void set(int x, int y, Node node) {
        nodes[x][y] = node;
    }

    @Override
    public Iterator<Node> iterator() {
        return new NodeIterator(nodes);
    }

    @Override
    public Spliterator<Node> spliterator() {
        return Spliterators.spliterator(iterator(), size(), 0);
    }

    public Stream<Node> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}