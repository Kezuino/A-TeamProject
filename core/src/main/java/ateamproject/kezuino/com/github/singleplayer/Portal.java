package ateamproject.kezuino.com.github.singleplayer;

public class Portal {

    private Node node;
    private Pactale owner;

    public Direction getDirection() {
        return direction;
    }

    private Direction direction;

    /**
     * Initializes a {@link Portal} from a specific {@link Pactale owner} on a {@link Direction side} of a {@link Wall}.
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
    }

    public Node getNode() {
        return node;
    }

    public Pactale getOwner() {
        return owner;
    }
}