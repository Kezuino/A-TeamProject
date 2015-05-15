package ateamproject.kezuino.com.github.utility.game;

import ateamproject.kezuino.com.github.singleplayer.Node;

import java.util.Iterator;

public class NodeIterator implements Iterator<Node> {
    protected int x;
    protected int y;
    protected Node[][] nodes;
    boolean isFirst = true;

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
     * Retrieves the Background at the current cursor position.
     *
     * @return Background at the current position of the cursor.
     */
    public Node current() {
        return nodes[x][y];
    }

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