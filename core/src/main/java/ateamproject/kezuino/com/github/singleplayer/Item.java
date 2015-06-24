package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.render.IRenderable;
import ateamproject.kezuino.com.github.render.screens.BaseScreen;
import ateamproject.kezuino.com.github.utility.game.IPositionable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class Item implements IRenderable, IPositionable {

    private Vector2 exactPosition;
    private String name;
    private ItemType type;
    private Map map;
    private TextureRegion texture;
    private UUID id;

    /**
     * Empty constructor needed for reflection instantiation.
     */
    public Item() {

    }

    /**
     * Initializes an {@link Item} at the given {@link Node}.
     *
     * @param name Name of the {@link Item}.
     * @param type {@link ItemType} dat determines the {@code name} and {@link #activate(GameObject)}'s body.
     */
    public Item(String name, Vector2 exactPosition, ItemType type) {
        if (type == null) throw new IllegalArgumentException("Parameter type must not be null.");
        this.name = name;
        this.type = type;
        this.exactPosition = exactPosition.cpy();
    }

    /**
     * Initializes an {@link Item} at the given {@link Node} using the default name of the {@link ItemType}.
     *
     * @param exactPosition {@link Vector2 Position} op this {@link Item}.
     * @param type          {@link ItemType} dat determines the {@code name} and {@link #activate(GameObject)}'s body.
     */
    public Item(Vector2 exactPosition, ItemType type) {
        this(String.valueOf(type), exactPosition, type);
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }

    @Override
    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    @Override
    public void update() {

    }

    /**
     * Gets the {@link Map} that hosts this {@link Item}.
     *
     * @return {@link Map} that hosts this {@link Item}.
     */
    @Override
    public Map getMap() {
        return map;
    }

    /**
     * Sets the {@link Map} that hosts this {@link Item}.
     *
     * @param map {@link Map} that hosts this {@link Item}.
     */
    public void setMap(Map map) {
        this.map = map;
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
     * Sets the {@link ItemType} of the {@link Item}. If no {@link #name} was set, the default name will be a friendly name based on this {@code type}.
     *
     * @param type {@link ItemType} to set the new type to.
     */
    public void setItemType(ItemType type) {
        if (type == null) return;
        name = type.toString();
        if (this.type != null || type.equals(this.type)) return;
        this.type = type;
    }

    /**
     * The target who picks up this item activates the effect this item is
     * carrying
     *
     *
     * @param target Target standing on the node with the item
     */
    public void activate(GameObject target) {
        switch (this.type) {
            case BigObject:
                this.getMap()
                    .getAllGameObjects()
                    .stream()
                    .filter((GameObject o) -> o instanceof Enemy)
                    .map((GameObject e) -> (Enemy) e)
                    .forEach(e -> e.setEdible(true));
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
    }

    public Node getNode() {
        return this.getMap().getNode((int) this.exactPosition.x / 32, (int) this.exactPosition.y / 32);
    }

    /**
     * Gets the {@link Rectangle bounds} of this {@link Item} inside the {@link Map} using the {@link Vector2 position} and width, height of the texture.
     *
     * @return {@link Rectangle bounds} of this {@link Item} inside the {@link Map} using the {@link Vector2 position} and width, height of the texture.
     */
    public Rectangle getBounds() {
        Vector2 pos = getExactPosition();
        return new Rectangle(pos.x, pos.y, texture.getRegionWidth(), texture.getRegionHeight());
    }

    /**
     * Gets the absolute position of this {@link Item}.
     *
     * @return Absolute position of this {@link Item}.
     */
    public Vector2 getExactPosition() {
        return exactPosition;
    }

    /**
     * Sets the absolute position of this {@link Item}.
     *
     * @param exactPosition Absolute position of this {@link Item}.
     */
    public void setExactPosition(Vector2 exactPosition) {
        this.exactPosition = exactPosition;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (texture == null) return;
        float xOffset = (32 - texture.getRegionWidth()) / 2f;
        float yOffset = (32 - texture.getRegionHeight()) / 2f;
        batch.draw(texture, this.getExactPosition().x + xOffset, this.getExactPosition().y + yOffset);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", exactPosition=" + exactPosition +
                ", type=" + type +
                '}';
    }

    public UUID getId() {
        return id;
    }
    
    public void setId() {
        this.id = UUID.randomUUID();
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
