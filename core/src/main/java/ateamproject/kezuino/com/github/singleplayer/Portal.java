package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.render.IRenderable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Portal implements IRenderable {

    private Node node;
    private Pactale owner;
    private Direction direction;
    private Texture texture;
    
    public Direction getDirection() {
        return direction;
    }

    /**
     * Initializes a {@link Portal} from a specific {@link Pactale owner} on a {@link Direction side} of a {@link Node}.
     *
     * @param owner     That caused this {@link Portal} to be created.
     * @param position      That contains the {@link Portal} on a side.
     * @param direction Side on the {@link Node} that this {@link Portal} should appear on.
     */
    public Portal(Pactale owner, Node position, Direction direction) {
        if (owner == null) throw new IllegalArgumentException("Parameter owner must not be null.");
        if (position == null) throw new IllegalArgumentException("Parameter wall must not be null.");
        if (direction == null) throw new IllegalArgumentException("Parameter direction must not be null.");
        this.owner = owner;
        owner.setPortal(this);
        this.node = position;
        this.direction = direction;
    }

    public Node getNode() {
        return node;
    }

    public Pactale getOwner() {
        return owner;
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
    public void update() {

    }

    @Override
    public void draw(SpriteBatch batch) {
        // TODO: Draw portal on edge of Node.
        if (texture == null) return;
        float xOffset = (32 - texture.getWidth()) / 2f;
        float yOffset = (32 - texture.getHeight()) / 2f;
        batch.draw(texture, this.node.getX() * 32 + xOffset, this.node.getY() * 32 + yOffset);
    }
}