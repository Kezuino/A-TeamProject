package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.render.IRenderable;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item implements IRenderable, IPositionable {

    private final int x;
    private final int y;
    private String name;
    private ItemType type;
    private Map map;
    private Texture texture;

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

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
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
        System.out.println(this.getMap().getSession().getScore().getScore());
    }


    public Node getNode() {
        return this.getMap().getNode(this.x, this.y);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(SpriteBatch batch) {
        if (texture == null) return;
        float xOffset = (32 - texture.getWidth()) / 2f;
        float yOffset = (32 - texture.getHeight()) / 2f;
        batch.draw(texture, this.x * 32 + xOffset, this.y * 32 + yOffset);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
    }
}
