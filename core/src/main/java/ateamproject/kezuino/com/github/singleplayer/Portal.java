package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.render.IRenderable;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import ateamproject.kezuino.com.github.utility.game.Direction;
import ateamproject.kezuino.com.github.utility.game.IPositionable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Portal implements IRenderable, IPositionable {

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
        this.node = position;
        this.direction = direction;
        owner.addPortal(this);
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
        if (texture == null) {
            this.setTexture(Assets.get("textures/portal.png", Texture.class));
        }
        float xOffset = (32 - texture.getWidth()) / 2f;
        float yOffset = (32 - texture.getHeight()) / 2f;
        float rotation = this.getDirection().getRotation();
        batch.draw(texture, this.node.getX()* 32 + xOffset, this.node.getY() * 32 + yOffset, texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth(), texture.getHeight(), 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
   
    }
}