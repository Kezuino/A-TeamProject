package ateamproject.kezuino.com.github.singleplayer;

public class Portal {

    private Node position;
    private Wall wall;
    private Pactale owner;

    public Direction getDirection() {
        return direction;
    }

    private Direction direction;

    /**
     * Initializes a {@link Portal} from a specific {@link Pactale owner} on a {@link Direction side} of a {@link Wall}.
     *
     * @param owner     That caused this {@link Portal} to be created.
     * @param wall      That contains the {@link Portal} on a side.
     * @param direction Side on the {@link Wall} that this {@link Portal} should appear on.
     */
    public Portal(Pactale owner, Wall wall, Direction direction) {
        if (owner == null) throw new IllegalArgumentException("Parameter owner must not be null.");
        if (wall == null) throw new IllegalArgumentException("Parameter wall must not be null.");
        if (direction == null) throw new IllegalArgumentException("Parameter direction must not be null.");
        this.owner = owner;
        this.wall = wall;
        this.direction = direction;
    }

    /**
     * {@link Node} that has this {@link Portal}.
     *
     * @return {@link Node} that has this {@link Portal}.
     */
    public Node getPosition() {
        return position;
    }

    public Wall getWall() {
        return wall;
    }

    public Pactale getOwner() {
        return owner;
    }
}