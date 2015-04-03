package ateamproject.kezuino.com.github.singleplayer;

public class Item {

    private final int x;
    private final int y;
    private String name;
    private ItemType type;
    private Map map;

    /**
     * Initializes an {@link Item} at the given {@link Node}.
     *
     * @param name Name of the {@link Item}.
     * @param map  {@link Map} that hosts the {@link Item}.
     * @param x    X dimension of the {@link Node} that should contain this {@link Item}.
     * @param y    Y dimension of the {@link Node} that should contain this {@link Item}.
     * @param type {@link ItemType} dat determines the {@code name} and {@link #activate(GameObject)}'s body.
     */
    public Item(String name, Map map, int x, int y, ItemType type) {
        this.name = name;
        this.type = type;
        this.map = map;
        this.x = x;
        this.y = y;
    }

    /**
     * Initializes an {@link Item} at the given {@link Node} using the default name of the {@link ItemType}.
     *
     * @param map  {@link Map} that hosts the {@link Item}.
     * @param x    X dimension of the {@link Node} that should contain this {@link Item}.
     * @param y    Y dimension of the {@link Node} that should contain this {@link Item}.
     * @param type {@link ItemType} dat determines the {@code name} and {@link #activate(GameObject)}'s body.
     */
    public Item(Map map, int x, int y, ItemType type) {
        this(String.valueOf(type), map, x, y, type);
    }

    public Map getMap() {
        return map;
    }

    /**
     * Gets the name of the item
     *
     * @return Name of the item
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
     * @return Type of the item
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
        if (this.type.equals(type)) return;
        this.type = type;
    }

    /**
     * The target who picks up this item activates the effect this item is
     * carrying
     *
     * @param target Target standing on the node with the item
     */
    public void activate(GameObject target) {
        // TODO - implement Item.activate
        switch (this.type) {
            case BigObject:
                this.getMap().getAllGameObjects().stream().filter((GameObject o) -> o instanceof Enemy).map((GameObject e) -> (Enemy) e).forEach(e -> e.setEdible(true));
                break;
            case Diamond:
                //TODO apply powerup
                break;
            case Emerald:
                //TODO apply powerup
                break;
            case Sapphire:
                //TODO apply powerup
                break;
            case Ruby:
                //TODO apply powerup
                break;
        }

        this.getMap().getSession().getScore().incrementScore(this.type.getScore());
    }


    /**
     * Returns the {@link Node} based on the X and Y dimensions on the {@link Map}.
     *
     * @return {@link Node} based on the X and Y dimensions on the {@link Map}.
     */
    public Node getNode() {
        return this.getMap().getNode(this.x, this.y);
    }
}
