package ateamproject.kezuino.com.github.singleplayer;

public class Portal {

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

    private Node position;
    private Wall wall;
    private Pactale owner;

    /**
     * Initializes a {@link Portal} from a specific {@link Pactale owner} on a {@link Direction side} of a {@link Wall}.
     *
     * @param owner     That caused this {@link Portal} to be created.
     * @param wall      That contains the {@link Portal} on a side.
     * @param direction Side on the {@link Wall} that this {@link Portal} should appear on.
     */
    public Portal(Pactale owner, Wall wall, Direction direction) {
        this.owner = owner;
        this.wall = wall;
    }
}