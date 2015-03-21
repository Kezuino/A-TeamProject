package ateamproject.kezuino.com.github.singleplayer;

public class Item {

    private String name;
    private Node offSetPosition;
    private Node node;
    private Node Node;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getOffSetPosition() {
        return this.offSetPosition;
    }

    public void setOffSetPosition(Node offSetPosition) {
        this.offSetPosition = offSetPosition;
    }

    /**
     * Initializes a item at the given node.
     *
     * @param node
     */
    public Item(Node node) {
        // TODO - implement Item.Item
        throw new UnsupportedOperationException();
    }

    /**
     * @param target
     */
    public void activate(GameObject target) {
        // TODO - implement Item.activate
        throw new UnsupportedOperationException();
    }

}