package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public abstract class GameObject {

	private float movementSpeed;
	private Color color;
	private Direction direction;
	private Node Node;

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
		// TODO - implement GameObject.getNode
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a new gameobject
	 * @param position
	 * @param movementSpeed
	 * @param walkingDirection
	 * @param color
	 */
	public GameObject(Node position, float movementSpeed, Direction walkingDirection, Color color) {

	}
}