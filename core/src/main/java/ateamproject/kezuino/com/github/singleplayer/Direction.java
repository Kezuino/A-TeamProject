package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.MathUtils;

/**
 * {@link Direction} specifies which x-axis and y-axis are linked to a {@link Direction} and are used throughout the {@link Game}.
 * Up       =   positive y-axis     =   +1.
 * Down     =   negative y-axis     =   -1.
 * Left     =   negative x-axis     =   -1.
 * Right    =   positive x-axis     =   +1.
 */
public enum Direction {
    Up(0, 1),
    Down(0, -1),
    Left(-1, 0),
    Right(1, 0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Normalizes the {@link Direction} and returns the {@link Direction} based on the difference between {@code x: 0, y: 0}
     * and the parameters {@code x} and {@code y}. If {@code x: 0, y: 0} are given. Null will be returned.
     *
     * @param x X axis to determine {@link Direction}.
     * @param y Y axis to determine {@link Direction}.
     * @return {@link Direction} based on the offset given by {@code x} and {@code y} from a {@code x: 0, y: 0} standpoint.
     */
    public static Direction valueOf(int x, int y) {
        // Normalize x and y.
        x = MathUtils.clamp(x, -1, 1);
        y = MathUtils.clamp(y, -1, 1);

        if (Direction.Up.x == x && Direction.Up.y == y) {
            return Direction.Up;
        } else if (Direction.Down.x == x && Direction.Down.y == y) {
            return Direction.Down;
        } else if (Direction.Left.x == x && Direction.Left.y == y) {
            return Direction.Left;
        } else if (Direction.Right.x == x && Direction.Right.y == y) {
            return Direction.Right;
        } else {
            return null;
        }
    }

    /**
     * Gets the {@link Direction} given a source and target x-axis and y-axis.
     * Returns null when x1, y1, x2 and y2 are all the same or if combination axis result in a diagonal direction.
     *
     * @param x1 Source x-axis.
     * @param y1 Source y-axis.
     * @param x2 Target x-axis.
     * @param y2 Target y-axis.
     * @return {@link Direction} given a source and target x-axis and y-axis.
     */
    public static Direction getDirection(int x1, int y1, int x2, int y2) {
        // Target - Source = direction.
        int resultX = x2 - x1;
        int resultY = y2 - y1;

        return valueOf(resultX, resultY);
    }

    /**
     * Gets the {@link Direction} given a source and target x-axis and y-axis.
     * If direction is diagonal, it'll return the longest direction (prefers y-axis).
     * <p>
     * (Example: (0,0,10,5) returns Direction.Right).<br>
     * (Example: (0,0,5,5) returns Direction.Up (y-axis is preferred)).
     * <p>
     * Returns null when x1, y1, x2 and y2 are all the same.
     *
     * @param x1 Source x-axis.
     * @param y1 Source y-axis.
     * @param x2 Target x-axis.
     * @param y2 Target y-axis.
     * @return {@link Direction} given a source and target x-axis and y-axis.
     * @see Direction#getDirection(int, int, int, int)
     */
    public static Direction getDirectionDiagonal(int x1, int y1, int x2, int y2) {
        // Target - Source = direction.
        int resultX = x2 - x1;
        int resultY = y2 - y1;

        // Flatten to one axis.
        if (Math.abs(resultX) > Math.abs(resultY)) {
            resultY = 0;
        } else {
            resultX = 0;
        }

        return valueOf(resultX, resultY);
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

    /**
     * Gets the inverted {@link Direction}.
     *
     * @return the inverted {@link Direction}.
     */
    public Direction reverse() {
        switch (this) {
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