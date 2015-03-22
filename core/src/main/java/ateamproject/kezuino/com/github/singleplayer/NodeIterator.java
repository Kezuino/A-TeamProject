package ateamproject.kezuino.com.github.singleplayer;

import java.util.Iterator;

public class NodeIterator implements Iterator<Node> {
    protected int x;
    protected int y;
    protected Node[][] nodes;

    public NodeIterator(Node[][] nodes) {
        this.nodes = nodes;
    }

    /**
     * Decreases the position of the cursor.
     *
     * @return True if position was changed.
     */
    protected boolean decrease() {
        if (y > 0) {
            y--;
            return true;
        } else if (x > 0) {
            x--;
            y = nodes[x].length - 1;
            return true;
        }
        return false;
    }

    /**
     * Increases the position of the cursor.
     *
     * @return True if position was changed.
     */
    protected boolean increase() {
        if (y < nodes[x].length - 1) {
            y++;
            return true;
        } else if (x < nodes.length - 1) {
            x++;
            y = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasNext() {
        boolean canIncrease = increase();
        if (canIncrease) {
            decrease();
        }
        return canIncrease;
    }

    /**
     * Retrieves the Node at the current cursor position.
     *
     * @return Node at the current position of the cursor.
     */
    public Node current() {
        return nodes[x][y];
    }

    boolean isFirst = true;

    @Override
    public Node next() {
        if (isFirst) {
            isFirst = false;
            return current();
        }

        increase();
        return current();
    }
}