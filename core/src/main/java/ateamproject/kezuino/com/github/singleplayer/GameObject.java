package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.render.IRenderable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject implements IRenderable, IPositionable {
    /**
     * {@link Direction} that this {@link GameObject} is currently facing
     * towards.
     */
    protected Direction direction;
    /**
     * {@link Direction} this {@link GameObject} will continue onto next.
     */
    protected Direction nextDirection;
    protected Node nextNode;
    /**
     * If true, this {@link GameObject} is currently transitioning between
     * {@link Node nodes}.
     */
    protected boolean isMoving;
    /**
     * Gametime when this {@link GameObject} started transitioning from another
     * {@link Node}.
     */
    protected float movementStartTime;
    protected float lifeTime;
    /**
     * {@link Texture} of this {@link GameObject} for drawing.
     */
    protected Texture texture;
    /**
     * {@link com.badlogic.gdx.graphics.Color} this {@link GameObject}
     * previously originated from.
     */
    protected Color previousColor;
    /**
     * If false, this {@link GameObject} will be removed whenever possible and won't be visible or usuable in the {@link ateamproject.kezuino.com.github.PactaleGame}.
     */
    private boolean isActive;
    /**
     * {@link Map} that contains this {@link GameObject}.
     */
    private Map map;
    /**
     * Exact world position of this {@link GameObject}. Use {@link #getNode()} to get the {@link Node} at the current position.
     */
    private Vector2 position;
    /**
     * starting X position of this {@link GameObject}.
     */
    private int startingX;
    /**
     * starting Y position of this {@link GameObject}.
     */
    private int startingY;
    /**
     * Speed in seconds that it takes for this {@link GameObject} to move to
     * another adjacent {@link Node}.
     */
    private float movementSpeed;
    /**
     * {@link com.badlogic.gdx.graphics.Color} at that will be used to draw this
     * {@link GameObject}.
     */
    private Color color;

    protected boolean drawOnDirection;

    /**
     * Initializes this {@link GameObject}.
     *
     * @param map That hosts this
     * {@link ateamproject.kezuino.com.github.singleplayer.GameObject}.
     * @param x X position of this
     * {@link ateamproject.kezuino.com.github.singleplayer.GameObject}.
     * @param y Y position of this
     * {@link ateamproject.kezuino.com.github.singleplayer.GameObject}.
     * @param movementSpeed Speed in seconds that this
     * {@link ateamproject.kezuino.com.github.singleplayer.GameObject} takes to
     * move to another adjacent
     * {@link ateamproject.kezuino.com.github.singleplayer.Node}.
     * @param direction
     * {@link ateamproject.kezuino.com.github.singleplayer.Direction} that this
     * {@link ateamproject.kezuino.com.github.singleplayer.GameObject} is
     * currently facing.
     * @param color {@link com.badlogic.gdx.graphics.Color} that this
     * {@link GameObject} will be
     */
    public GameObject(Vector2 exactPosition, float movementSpeed, Direction direction, Color color) {
        if (direction == null) {
            throw new IllegalArgumentException("Parameter direction must not be null.");
        }
        if (color == null) {
            throw new IllegalArgumentException("Parameter color must not be null.");
        }
        if (movementSpeed <= 0) {
            throw new IllegalArgumentException("Parameter movementSpeed must be higher than 0.");
        }
        if (color.equals(Color.CLEAR)) {
            throw new IllegalArgumentException("Parameter color must not be Color.CLEAR.");
        }
        this.startingX = x;
        this.startingY = y;
        this.movementSpeed = movementSpeed;
        this.direction = direction;
        this.setNodePosition(exactPosition);
        this.color = color;
        this.isActive = true;
    }

    /**
     * Initializes this {@link GameObject} with a default {@code Color.WHITE}
     * color.
     *
     * @param map That hosts this {@link GameObject}.
     * @param x X position of this {@link GameObject}.
     * @param y Y position of this {@link GameObject}.
     * @param movementSpeed Speed in seconds that this {@link GameObject} takes
     * to move to another adjacent {@link Node}.
     * @param direction {@link Direction} that this {@link GameObject} is
     * currently facing.
     */
    public GameObject(Vector2 position, float movementSpeed, Direction direction) {
        this(position, movementSpeed, direction, Color.WHITE);
    }

    /**
     * Gets the position based on the {@link Map#nodes}.
     *
     * @return Position based on the {@link Map#nodes}.
     */
    public Vector2 getNodePosition() {
        return new Vector2(this.position.x / 32, this.position.y / 32);
    }

    /**
     * Sets the position based on the {@link Map#nodes}.
     *
     * @param nodePosition Position based on the {@link Map#nodes}.
     */
    public void setNodePosition(Vector2 nodePosition) {
        position = new Vector2(nodePosition.x * 32, nodePosition.y * 32);
    }

    /**
     * Gets the absolute X and Y position of this {@link GameObject}.
     *
     * @return
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the absolute X and Y position of this {@link GameObject}.
     *
     * @param position Absolute X and Y position of this {@link GameObject}.
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Gets the {@link Texture} of this {@link GameObject} for drawing.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Sets the {@link Texture} of this {@link GameObject} for drawing.
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Gets the startingX dimension that this {@link GameObject} is on.
     *
     * @return startingX dimension that this {@link GameObject} is on.
     */
    public int getStartingX() {
        return startingX;
    }

    /**
     * Gets the startingY dimension that this {@link GameObject} is on.
     *
     * @return startingY dimension that this {@link GameObject} is on.
     */
    public int getStartingY() {
        return startingY;
    }

    /**
     * Gets the {@link Map} that hosts this {@link GameObject}.
     *
     * @return {@link Map} that hosts this {@link GameObject}.
     */
    @Override
    public Map getMap() {
        return map;
    }

    /**
     * Moves this {@link GameObject} to a different {@link Map}.
     *
     * @param map {@link Map} to move this {@link GameObject} to.
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Gets the speed in seconds that it takes for this {@link GameObject} to
     * move to another adjacent {@link Node}.
     *
     * @return Speed in seconds that it takes for this {@link GameObject} to
     * move to another adjacent {@link Node}.
     */
    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    /**
     * Sets the speed in seconds that it takes for this {@link GameObject} to
     * move to another adjacent {@link Node}.
     *
     * @param movementSpeed Speed in seconds that it takes for this
     * {@link GameObject} to move to another adjacent {@link Node}.
     */
    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "position=" + position +
                ", movementSpeed=" + movementSpeed +
                ", isMoving=" + isMoving +
                ", direction=" + direction +
                ", nextDirection=" + nextDirection +
                ", color=" + color +
                ", texture=" + texture +
                '}';
    }

    /**
     * Gets the {@link com.badlogic.gdx.graphics.Color} that will be used to
     * draw this {@link GameObject}.
     *
     * @return {@link com.badlogic.gdx.graphics.Color} for drawing this
     * {@link GameObject}.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Changes the current color of the this {@link GameObject}
     *
     * @param color Color this {@link GameObject} will be.
     */
    public void setColor(Color color) {
        this.previousColor = this.color;
        this.color = color;
    }

    /**
     * Gets the {@link Direction} that this {@link GameObject} is currently
     * facing towards.
     *
     * @return {@link Direction} that this {@link GameObject} is currently
     * facing towards.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Sets the {@link Direction} that this {@link GameObject} is currently
     * facing towards.
     *
     * @param direction {@link Direction} that this {@link GameObject} is
     * currently facing towards.
     */
    public void setDirection(Direction direction) {
        if (direction == null) {
            return;
        }
        this.nextDirection = this.isMoving ? direction : (this.direction = direction);
    }

    @Override
    public Node getNode() {
        if (map == null) {
            return null;
        }
        return map.getNode(position);
    }

    /**
     * Teleports this {@link GameObject} to the specified {@code x} and
     * {@code y} coordinates. Doesn't take into account
     * {@link GameObject#movementSpeed} and doesn't use pathfinding.
     *
     * @param x X position to set this {@link GameObject} to.
     * @param y Y position to set this {@link GameObject} to.
     * @return True if succesfully changed the Position, false if it didn't.
     */
    public boolean setPosition(float x, float y) {
        // Pre-check if all input data is valid.
        if (map == null) {
            return false;
        }

        this.position = new Vector2(x, y);

        return true;
    }

    public boolean getActive() {
        return this.isActive;
    }

    /**
     * Marks this {@link GameObject} for deletion. Must not be undone.
     */
    protected void setInactive() {
        this.isActive = false;
    }

    /**
     * Moves this {@link GameObject} to another adjacent {@link Node} based on
     * the given {@code direction}.
     *
     * @param direction {@link Direction} to move in (to an adjacent
     * {@link Node}).
     */
    public void moveAdjacent(Direction direction) {
        this.direction = direction;

        if (!isMoving) {
            isMoving = true;
            movementStartTime = System.nanoTime();
        }
    }

    /**
     * Called when a collision was detected by this {@link GameObject}. Return
     * true if collision has been handled and shouldn't continue.
     *
     * @param object {@link GameObject} that this {@link GameObject} is
     * colliding with.
     * @return True if collision has been handled and {@link GameObject}.
     */
    protected boolean collisionWithGameObject(GameObject object) {
        return false;
    }

    /**
     * Called when collision was detected by this {@link GameObject}, with a
     * wall. Return true of collision has been handled.
     *
     * @param node {@link Node} this {@link GameObject} collided with.
     * @return True if the collision has been handled.
     */
    protected boolean collisionWithWall(Node node) {
        return false;
    }

    protected boolean collisionWithItem(Item item) {
        return false;
    }

    /**
     * Moves this {@link GameObject} to another adjacent {@link Node} based on
     * the given {@code direction}.
     */
    public void moveAdjacent() {
        if (map == null) throw new IllegalArgumentException("Field map must not be null.");

        Direction previousDirection = this.direction;
        this.direction = this.nextDirection != null ? this.nextDirection : direction;
        Node targetNode = getMap().getAdjacentNode(getNode(), this.direction);
        if (targetNode == null || targetNode.isWall()) {
            this.direction = previousDirection;
            targetNode = getMap().getAdjacentNode(getNode(), this.direction);
            if (targetNode == null || targetNode.isWall()) {
                return;
            }
        }

        if (!isMoving) {
            isMoving = true;
            nextNode = getMap().getAdjacentNode(getNode(), direction);
            movementStartTime = System.nanoTime();
        }
    }

    /**
     * Updates this {@link GameObject}.
     */
    @Override
    public void update() {
        if (!this.isMoving) {
            this.moveAdjacent();
        }

        Node targetNode = this.getMap().getAdjacentNode(getNode(), this.direction);

        //Target node is beyond bounds, do not check for collision beyond this point
        if (targetNode == null) {
            return;
        }

        for (GameObject obj : this.map.getGameObjectsOnNode(this.getNode())) {
            if (obj.equals(this)) {
                continue;
            }
            if (collisionWithGameObject(obj)) {
                System.out.println("Collision handled");
                break;
            }
        }

        if (getNode().hasItem()) {
            collisionWithItem(getNode().getItem());
        }

        if (collisionWithWall(getNode())) {
            return;
        }
    }

    /**
     * Draws this {@link GameObject} inside the {@link Map}.
     */
    @Override
    public void draw(SpriteBatch batch) {
        lifeTime += Gdx.graphics.getDeltaTime();

        // Capture node and texture.
        Node node = getNode();
        if (node == null || this.texture == null) {
            return;
        }

        // Preprocess batch.
        batch.setColor(this.getColor());

        // TODO: Animate from sprite region.
        float rotation = 0;
        if (drawOnDirection) {
            rotation = this.getDirection().getRotation();
        }

        // Move object in direction.
        if (isMoving) {
            this.position.add(direction.getX() * movementSpeed * Gdx.graphics.getDeltaTime(), direction.getY() * movementSpeed * Gdx.graphics.getDeltaTime());

            // Check target reached.
            if (movementSpeed / lifeTime) {
                isMoving = false;
            }
        }

        // Draw centered in node.
        float xOffset = (32 - texture.getWidth()) / 2f;
        float yOffset = (32 - texture.getHeight()) / 2f;
        batch.draw(texture, this.x * 32 + xOffset, this.y * 32 + yOffset, texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth(), texture.getHeight(), 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    /**
     * Tries to move this {@link GameObject} to another {@link Node} using
     * {@link Map#getPathfinder()} based on the {@link #movementSpeed}.
     *
     * @param targetNode {@link Node} to move towards.
     * @deprecated unused, prepared for removed on cleanup, if still unused
     */
    public void move(Node targetNode) {
        throw new UnsupportedOperationException();//no movement inplemented
    }
}
