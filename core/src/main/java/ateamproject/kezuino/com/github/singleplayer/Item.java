package ateamproject.kezuino.com.github.singleplayer;

import java.awt.Point;
import java.util.stream.Collectors;

public class Item {

    private String name;
    private Node node;
    private ItemType type;


    /**
     * Initializes a item at the given node.
     *
     * @param name the name of the item
     * @param node the node the item is located on
     * @param type the type of the item standing on the node
     */
    public Item(String name, Node node, ItemType type) {
        this.name = name;
        this.node = node;
        this.node.setItem(this);
        this.type = type;

        // TODO: Discuss usage of "offsetPosition" for position of item in Node.
    }

    /**
     * Gets the name of the item
     *
     * @return name of the item
     */
    public String getName() {
        return this.name;
    }

    /**
     * sets the item name of the object
     *
     * @param name Name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the item
     *
     * @return type of the item
     */
    public ItemType getItemType() {
        return this.type;
    }

    /**
     * sets the item type of the object
     *
     * @param type
     */
    public void setItemType(ItemType type) {
        this.type = type;
    }
    
   
    /**
     * The target who picks up this item activates the effect this item is carrying
     *
     * @param target target standing on the node with the item
     */
    public void activate(GameObject target) {
        // TODO - implement Item.activate
        switch(this.type){
            case BigNugget:
                this.node.getMap().getAllGameObjects().stream().filter((GameObject o) -> o instanceof Enemy).map((GameObject e) -> (Enemy)e).forEach(e -> e.setEdible(true));
                break;
        }
        
        this.node.getMap().getSession().getScore().incrementScore(this.type.getScore());
    }

}