package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public abstract class GameObject {

    /**
     * If true, all movement done by this {@link GameObject} will use
     * interpolation to smoothly move it to an adjacent {@link Node}. If false,
     * ignores interlopation and just waits based on the {link #movementSpeed}
     * until it can {@link #move(Node)} again.
     */
    protected boolean movementInterpolation;
    /**
     * {@link Direction} that this {@link GameObject} is currently facing
     * towards.
     */
    protected Direction direction;
    /**
     * Offset in the X dimension to draw this {@link GameObject} from where 0 is the most left spot and 1 is the most right spot.
     */
    protected float drawOffsetX;
    /**
     * Offset in the Y dimension to draw this {@link GameObject} from where 0 is the most bottom spot and 1 is the most top spot.
     */
    protected float drawOffsetY;
    /**
     * If true, this {@link GameObject} is currently transitioning between {@link Node nodes}.
     */
    protected boolean isMoving;
    /**
     * Gametime when this {@link GameObject} started transitioning from another {@link Node}.
     */
    protected float movementStartTime;
    /**
     * {@link Map} that contains this {@link GameObject}.
     */
    private Map map;
    /**
     * X position of this {@link GameObject}.
     */
    private int x;
    /**
     * Y position of this {@link GameObject}.
     */
    private int y;
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
     * Initializes this {@link GameObject}.
     *
     * @param map           That hosts this {@link ateamproject.kezuino.com.github.singleplayer.GameObject}.
     * @param x             X position of this {@link ateamproject.kezuino.com.github.singleplayer.GameObject}.
     * @param y             Y position of this {@link ateamproject.kezuino.com.github.singleplayer.GameObject}.
     * @param movementSpeed Speed in seconds that this {@link ateamproject.kezuino.com.github.singleplayer.GameObject} takes
     *                      to move to another adjacent {@link ateamproject.kezuino.com.github.singleplayer.Node}.
     * @param direction     {@link ateamproject.kezuino.com.github.singleplayer.Direction} that this {@link ateamproject.kezuino.com.github.singleplayer.GameObject} is
     *                      currently facing.
     * @param color         {@link com.badlogic.gdx.graphics.Color} that this
     *                      {@link GameObject} will be
     */
    public GameObject(Map map, int x, int y, float movementSpeed, Direction direction, Color color) {
        if (direction == null) throw new IllegalArgumentException("Parameter direction must not be null.");
        if (color == null) throw new IllegalArgumentException("Parameter color must not be null.");
        if (movementSpeed <= 0) throw new IllegalArgumentException("Parameter movementSpeed must be higher than 0.");
        this.map = map;
        this.x = x;
        this.y = y;
        this.movementSpeed = movementSpeed;
        this.direction = direction;
        this.color = color;
        this.movementInterpolation = false;
        this.drawOffsetX = .5f;
        this.drawOffsetY = .5f;
    }

    /**
     * Initializes this {@link GameObject} with a default {@code Color.WHITE}
     * color.
     *
     * @param map           That hosts this {@link GameObject}.
     * @param x             X position of this {@link GameObject}.
     * @param y             Y position of this {@link GameObject}.
     * @param movementSpeed Speed in seconds that this {@link GameObject} takes
     *                      to move to another adjacent {@link Node}.
     * @param direction     {@link Direction} that this {@link GameObject} is
     *                      currently facing.
     */
    public GameObject(Map map, int x, int y, float movementSpeed, Direction direction) {
        this(map, x, y, movementSpeed, direction, Color.WHITE);
    }

    public float getDrawOffsetX() {
        return drawOffsetX;
    }

    public float getDrawOffsetY() {
        return drawOffsetY;
    }

    /**
     * Gets the X dimension that this {@link GameObject} is on.
     *
     * @return X dimension that this {@link GameObject} is on.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y dimension that this {@link GameObject} is on.
     *
     * @return Y dimension that this {@link GameObject} is on.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the {@link Map} that hosts this {@link GameObject}.
     *
     * @return {@link Map} that hosts this {@link GameObject}.
     */
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

    @Override
    public String toString() {
        return "GameObject{" +
                "x=" + x +
                ", y=" + y +
                ", movementSpeed=" + movementSpeed +
                ", color=" + color +
                ", direction=" + direction +
                ", drawOffsetX=" + drawOffsetX +
                ", drawOffsetY=" + drawOffsetY +
                '}';
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
        this.direction = direction;
    }

    /**
     * Returns the node where the {@link GameObject} currently resides.
     */
    public Node getNode() {
        if (map == null) {
            return null;
        }
        return map.getNode(x, y);
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
    public boolean setPosition(int x, int y) {
        // Pre-check if all input data is valid.
        if (map == null) {
            return false;
        }
        Node currentNode = map.getNode(this.x, this.y);
        Node targetNode = map.getNode(x, y);
        if (targetNode == null || currentNode == null) {
            return false;
        }

        // Remove GameObject from current Node.
        if (currentNode.removeGameObject(this) == null) {
            return false;
        }

        // Add GameObject to new Node or revert if failing.
        if (!targetNode.addGameObject(this)) {
            // Revert position back because we failed.
            if (currentNode != null) {
                currentNode.addGameObject(this);
            }
            return false;
        }

        this.x = x;
        this.y = y;

        return true;
    }

    /**
     * Moves this {@link GameObject} to another adjacent {@link Node} based on
     * the given {@code direction}. If {@link #movementInterpolation} is true,
     * this movement should be pixel-perfectly smooth between the nodes. If
     * {@link #movementInterpolation} is false, this movement should move
     * immediately and wait until it can move again based on
     * {@link #movementSpeed}.
     *
     * @param direction {@link Direction} to move in (to an adjacent
     *                  {@link Node}).
     */
    public void moveAdjacent(Direction direction) {
        this.direction = direction;

        if (movementInterpolation) {
            if (!isMoving) {
                isMoving = true;
                drawOffsetX = .5f;
                drawOffsetY = .5f;
                movementStartTime = System.nanoTime();
            }

            float drawOffsetSpeed = movementSpeed * Gdx.graphics.getDeltaTime();
            switch (direction) {
                case Up:
                    drawOffsetY += drawOffsetSpeed;
                    break;
                case Down:
                    drawOffsetY -= drawOffsetSpeed;
                    break;
                case Left:
                    drawOffsetX -= drawOffsetSpeed;
                    break;
                case Right:
                    drawOffsetX += drawOffsetSpeed;
                    break;
            }

            // If one offset has reached it's max..
            if (drawOffsetX <= 0 || drawOffsetX >= 1 || drawOffsetY <= 0 || drawOffsetY >= 0) {
                isMoving = false;
            }
        } else {
            // The enum Direction contains information about which offset x and y is should return based on the value of the enum.
            // Change this value if Y are inverted.. do not change this code.
            this.x += direction.getX();
            this.y += direction.getY();
        }
    }

    /**
     * Tries to move this {@link GameObject} to another {@link Node} using
     * {@link Map#getPathfinder()} based on the {@link #movementSpeed}.
     *
     * @param targetNode {@link Node} to move towards.
     * @see #moveAdjacent(Direction)
     */
    public void move(Node targetNode) {
        throw new UnsupportedOperationException();//no movement inplemented
    }
}
