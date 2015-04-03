package ateamproject.kezuino.com.github.singleplayer;

import java.awt.Point;

public class Item {

    private String Name;
    private ItemType Type;
    private int xCoordinate;
    private int yCoordinate;

    /**
     * Initializes a item at the given node.
     *
     * @param name the name of the item
     * @param node the node the item is located on
     * @param type the type of the item standing on the node
     */
    public Item(String name, int xCoordinate, int yCoordinate, ItemType type) {
        this.Name = name;
        this.Type = type;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

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
     * The target who picks up this item activates the effect this item is
     * carrying
     *
     * @param target target standing on the node with the item
     */
    public void activate(GameObject target) {
        // TODO - implement Item.activate
        switch (this.Type) {
            case BigObject:

                break;
            case SmallObject:

                break;
            default:
                break;
        }
    }

}
