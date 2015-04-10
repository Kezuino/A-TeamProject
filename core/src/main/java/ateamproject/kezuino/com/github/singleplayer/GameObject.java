package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.render.IRenderable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
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

    /**
     * {@link Node} that's adjacent to this {@link GameObject} based on the {@link #direction}.
     */
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
     * If true, this {@link GameObject} will be rotated based on it's direction (assuming texture is facing downwards by default).
     */
    protected boolean drawOnDirection;
    /**
     * If false, this {@link GameObject} will be removed whenever possible and won't be visible or usuable in the {@link ateamproject.kezuino.com.github.PactaleGame}.
     */
    private boolean isActive;
    /**
     * {@link Map} that contains this {@link GameObject}.
     */
    private Map map;
    /**
     * Exact world exactPosition of this {@link GameObject}. Use {@link #getNode()} to get the {@link Node} at the current exactPosition.
     */
    private Vector2 exactPosition;
    /**
     * Position that this {@link GameObject} was created on.
     */
    private Vector2 startingPosition;
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

    /**
     * Total added deltatime since last {@link Node} movement occured.
     */
    private float moveTotalStep;

    /**
     * Initializes this {@link GameObject}.
     * <p>
     * {@link ateamproject.kezuino.com.github.singleplayer.GameObject}.
     *
     * @param movementSpeed Speed in seconds that this
     *                      {@link ateamproject.kezuino.com.github.singleplayer.GameObject} takes to
     *                      move to another adjacent
     *                      {@link ateamproject.kezuino.com.github.singleplayer.Node}.
     * @param direction     {@link ateamproject.kezuino.com.github.singleplayer.Direction} that this
     *                      {@link ateamproject.kezuino.com.github.singleplayer.GameObject} is
     *                      currently facing.
     * @param color         {@link com.badlogic.gdx.graphics.Color} that this
     *                      {@link GameObject} will be
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
        this.startingPosition = exactPosition.cpy();
        this.movementSpeed = movementSpeed;
        this.direction = direction;
        this.exactPosition = exactPosition.cpy();
        this.color = color;
        this.isActive = true;
    }

    /**
     * Initializes this {@link GameObject} with a default {@code Color.WHITE}
     * color.
     *
     * @param movementSpeed Speed in seconds that this {@link GameObject} takes
     *                      to move to another adjacent {@link Node}.
     * @param direction     {@link Direction} that this {@link GameObject} is
     *                      currently facing.
     */
    public GameObject(Vector2 exactPosition, float movementSpeed, Direction direction) {
        this(exactPosition, movementSpeed, direction, Color.WHITE);
    }

    /**
     * Gets the starting exactPosition of this {@link GameObject} when it was created.
     *
     * @return Starting exactPosition of this {@link GameObject} when it was created.
     */
    public Vector2 getStartingPosition() {
        return startingPosition;
    }

    /**
     * Gets the absolute X and Y exactPosition of this {@link GameObject}.
     *
     * @return
     */
    public Vector2 getExactPosition() {
        return exactPosition;
    }

    /**
     * Sets the absolute X and Y exactPosition of this {@link GameObject}.
     *
     * @param exactPosition Absolute X and Y exactPosition of this {@link GameObject}.
     */
    public void setExactPosition(Vector2 exactPosition) {
        this.exactPosition = exactPosition;
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
     *                      {@link GameObject} to move to another adjacent {@link Node}.
     */
    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "exactPosition=" + exactPosition +
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
     *                  currently facing towards.
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
        return map.getNode((int) this.getExactPosition().x / 32, (int) this.getExactPosition().y / 32);
    }

    /**
     * Teleports this {@link GameObject} to the specified {@code x} and
     * {@code y} coordinates. Doesn't take into account
     * {@link GameObject#movementSpeed} and doesn't use pathfinding.
     *
     * @param x X {@link Node} position to set this {@link GameObject} to.
     * @param y Y {@link Node} position to set this {@link GameObject} to.
     * @return True if succesfully changed the Position, false if it didn't.
     */
    public boolean setNodePosition(float x, float y) {
        return setExactPosition(x * 32, y * 32);
    }

    public boolean setExactPosition(float x, float y) {
        this.exactPosition = new Vector2(x, y);
        return true;
    }

    public boolean getActive() {
        return this.isActive;
    }

    /**
     * Marks this {@link GameObject} for deletion. Must not be undone.
     */
    public void setInactive() {
        this.isActive = false;
    }

    /**
     * Moves this {@link GameObject} to another adjacent {@link Node} based on
     * the given {@code direction}.
     *
     * @param direction {@link Direction} to move in (to an adjacent
     *                  {@link Node}).
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
     *               colliding with.
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
            movementStartTime = moveTotalStep;
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

        // Target node is beyond bounds, do not check for collision beyond this point
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
        moveTotalStep += movementSpeed * Gdx.graphics.getDeltaTime();

        // Capture node and texture.
        Node node = getNode();
        if (node == null || this.texture == null) {
            return;
        }

        // Preprocess batch.
        batch.setColor(this.getColor());

        // Rotate texture based on direction.
        float rotation = 0;
        if (drawOnDirection) {
            rotation = this.getDirection().getRotation();
        }

        // TODO: Animate from sprite region (spritesheet animation).

        // Move object in direction.
        if (isMoving) {
            Vector2 curPos = getNode().getExactPosition();
            Vector2 nextPos = nextNode.getExactPosition();
            Vector2 interpolation = curPos.interpolate(nextPos, moveTotalStep - movementStartTime, Interpolation.linear);
            this.exactPosition = interpolation;

            // Check target reached.
            if (moveTotalStep - movementStartTime >= 1) {
                isMoving = false;
                movementStartTime = moveTotalStep;
            }
        }

        // Draw centered in node.
        float xOffset = (32 - texture.getWidth()) / 2f;
        float yOffset = (32 - texture.getHeight()) / 2f;
        batch.draw(texture, this.exactPosition.x + xOffset, this.exactPosition.y + yOffset, texture.getWidth() / 2, texture
                .getHeight() / 2, texture
                .getWidth(), texture.getHeight(), 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
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
