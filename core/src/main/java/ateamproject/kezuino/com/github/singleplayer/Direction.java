package ateamproject.kezuino.com.github.singleplayer;

public enum Direction {
    Left(-1, 0),
    Right(1, 0),
    Up(0, 1),
    Down(0, -1);


    private int x;
    private int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the difference in the X dimension that should take place when moving in a {@link Direction}.
     *
     * @return Difference in the X dimension that should take place when moving in a {@link Direction}.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the difference in the Y dimension that should take place when moving in a {@link Direction}.
     *
     * @return Difference in the Y dimension that should take place when moving in a {@link Direction}.
     */
    public int getY() {
        return y;
    }
    
    public Direction reverse()
    {
        switch(this)
        {
            case Up:
                return Direction.Down;
            case Down:
                return Direction.Up;
            case Left:
                return Direction.Right;
            case Right:
                return Direction.Left;
            default:
                return null;
        }
    }
}