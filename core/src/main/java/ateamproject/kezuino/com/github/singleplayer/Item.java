package ateamproject.kezuino.com.github.singleplayer;

import java.awt.Point;

public class Item {

    private String name;
    private Point offSetPosition;
    private Node node;

   
    /**
     * Initializes a item at the given node.
     *
     * @param name the name of the item
     * @param node the node the item is located on
     */
    public Item(String name,Node node) {
        this.name = name;
        this.node = node;
        
        // standaard offset toevoegen?
        // node = 33x33
        // small dot  = ? ( 10x10 )  > off set top 11 left 11
        // big dot    = ? ( 20x20 )  > off set top 6  left  6
    }

    /**
     * gets the name of the item
     * @return name of the item
     */
     public String getName() {
        return this.name;
    }

     /**
      * sets the item name of the object
      * @param name Name of the item
      */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the offset position of the item
     * @return point object of the offset of the item
     */
    public Point getOffSetPosition() {
        return this.offSetPosition;
    }

    /**
     * sets the point from the left and top corner to set the location of the item in the node
     * @param offSetPosition point of the item is located on the node
     */
    public void setOffSetPosition(Point offSetPosition) {
        this.offSetPosition = offSetPosition;
    }
    
    /**
     * the target who picks up this item activates the effect this item is carrying
     * @param target target standing on the node with the item
     */
    public void activate(GameObject target) {
        // TODO - implement Item.activate
        throw new UnsupportedOperationException();
    }

}