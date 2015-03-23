package ateamproject.kezuino.com.github.singleplayer;

import java.awt.Point;

public class Item {

    private String name;
    private Point offSetPosition;
    private Node node;

   
    /**
     * Initializes a item at the given node.
     *
     * @param node
     */
    public Item(String name,Node node) {
        this.name = name;
        this.node = node;
    }

     public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getOffSetPosition() {
        return this.offSetPosition;
    }

    public void setOffSetPosition(Point offSetPosition) {
        this.offSetPosition = offSetPosition;
    }
    
    /**
     * @param target
     */
    public void activate(GameObject target) {
        // TODO - implement Item.activate
        throw new UnsupportedOperationException();
    }

}