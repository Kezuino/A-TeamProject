package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.render.IRenderable;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import ateamproject.kezuino.com.github.utility.game.Direction;
import ateamproject.kezuino.com.github.utility.game.IPositionable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Portal implements IRenderable, IPositionable {

    private Node node;
    private Pactale owner;
    private Direction direction;
    private TextureRegion texture;
    private Color color;

    /**
     * Initializes a {@link Portal} from a specific {@link Pactale owner} on a {@link Direction side} of a {@link Node}.
     *
     * @param owner     That caused this {@link Portal} to be created.
     * @param position  That contains the {@link Portal} on a side.
     * @param direction Side on the {@link Node} that this {@link Portal} should appear on.
     */
    public Portal(Pactale owner, Node position, Direction direction) {
        if (owner == null) throw new IllegalArgumentException("Parameter owner must not be null.");
        if (position == null) throw new IllegalArgumentException("Parameter wall must not be null.");
        if (direction == null) throw new IllegalArgumentException("Parameter direction must not be null.");
        this.owner = owner;
        this.node = position;
        this.direction = direction;
        this.color = owner.getColor();
        owner.addPortal(this);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public Map getMap() {
        return getNode().getMap();
    }

    @Override
    public Vector2 getExactPosition() {
        return getNode().getExactPosition();
    }

    public Node getNode() {
        return node;
    }

    public Pactale getOwner() {
        return owner;
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

    @Override
    public void draw(SpriteBatch batch) {
        if (texture == null) {
            this.setTexture(new TextureRegion(Assets.getTexture("portal.png", Texture.class)));
        }

        Color oldColor = batch.getColor();
        batch.setColor(getColor());

        float xOffset = (32 - texture.getRegionWidth()) / 2f;
        float yOffset = (32 - texture.getRegionHeight()) / 2f;
        float rotation = this.getDirection().getRotation();
        batch.draw(texture, this.node.getX() * 32 + xOffset, this.node.getY() * 32 + yOffset, texture.getRegionWidth() / 2, texture.getRegionHeight() / 2, texture.getRegionWidth(), texture.getRegionHeight(), 1, 1, rotation, false);

        batch.setColor(oldColor);
    }
}