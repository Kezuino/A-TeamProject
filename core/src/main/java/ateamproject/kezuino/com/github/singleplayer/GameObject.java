package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public abstract class GameObject {
    private Map map;
	private float movementSpeed;
	private Color color;
	private Direction direction;
	private Node node;

	public float getMovementSpeed() {
		return this.movementSpeed;
	}

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public Color getColor() {
		return this.color;
	}

	public Direction getDirection() {
		return this.direction;
	}
        
        public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Returns the node where the gameobject currently resides.
	 */
	public Node getNode() {
        return node;
	}

	/**
	 * Creates a new gameobject
	 * @param map that hosts this @see GameObject.
     * @param x position of this @see GameObject on the @see Map.
	 * @param movementSpeed
	 * @param direction
	 * @param color
	 */
	public GameObject(Map map, int x, int y, float movementSpeed, Direction direction, Color color) {
        this.map = map;
        this.node = map.getNode(x, y);
        this.movementSpeed = movementSpeed;
        this.direction = direction;
        this.color = color;
	}

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPosition(int x, int y) {
        node = map.getNode(x, y);
    }
}