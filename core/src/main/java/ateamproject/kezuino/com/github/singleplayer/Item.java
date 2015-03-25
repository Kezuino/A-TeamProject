package ateamproject.kezuino.com.github.singleplayer;

import java.awt.Point;

public class Item {

    private String Name;
    private Point offsetPosition;
    private Node Node;
    private ItemType Type;


    /**
     * Initializes a item at the given node.
     *
     * @param name the name of the item
     * @param node the node the item is located on
     * @param type the type of the item standing on the node
     */
    public Item(String name, Node node,ItemType type) {
        this.Name = name;
        this.Node = node;
        this.Node.setItem(this);
        this.Type = type;

        // TODO: Discuss usage of "offsetPosition" for position of item in Node.
    }

    /**
     * Gets the name of the item
     *
     * @return name of the item
     */
    public String getName() {
        return this.Name;
    }

    /**
     * sets the item name of the object
     *
     * @param name Name of the item
     */
    public void setName(String name) {
        this.Name = name;
    }

    /**
     * Gets the type of the item
     *
     * @return type of the item
     */
    public ItemType getItemType() {
        return this.Type;
    }

    /**
     * sets the item type of the object
     *
     * @param type
     */
    public void setItemType(ItemType type) {
        this.Type = type;
    }
    
    /**
     * Gets the offset position of the item
     *
     * @return point object of the offset of the item
     */
    public Point getOffsetPosition() {
        return this.offsetPosition;
    }

    /**
     * Sets the point from the left and top corner to set the location of the item in the node
     *
     * @param offsetPosition point of the item is located on the node
     */
    public void setOffsetPosition(Point offsetPosition) {
        this.offsetPosition = offsetPosition;
    }

    /**
     * The target who picks up this item activates the effect this item is carrying
     *
     * @param target target standing on the node with the item
     */
    public void activate(GameObject target) {
        // TODO - implement Item.activate
        switch(this.Type){
            case BigNugget:
                
                break;
                
            case SmallNugget :
                
                break;
                
            default:
                break;
        }
    }

}