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
     * Normalizes the {@link Direction} and returns the reversed {@link Direction} based on the difference between {@code x: 0, y: 0}
     * and the parameters {@code x} and {@code y}. If {@code x: 0, y: 0} are given. Null will be returned.
     *
     * @param x X axis to determine reversed {@link Direction}.
     * @param y Y axis to determine reversed {@link Direction}.
     * @return Reversed {@link Direction} based on the offset given by {@code x} and {@code y} from a {@code x: 0, y: 0} standpoint.
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

        /* TODO: Hey Ken, ik heb hem null laten returnen als the x and y 0 is. Eerst gaf hij Left terug als het 0, 0 is. Maar indien dit gewenst was, dan zet die code maar weer terug ;P
         * Ook heb je .reverse() gedaan. Dus de valueOf geeft altijd het tegenovergestelde. Maar je zit wel midden in het debuggen.. FYI :) */
//        if (Direction.Up.x == x && Direction.Up.y == y) {
//            return Direction.Up.reverse();
//        } else if (Direction.Down.x == x && Direction.Down.y == y) {
//            return Direction.Down.reverse();
//        } else if (Direction.Left.x == x && Direction.Left.y == y) {
//            return Direction.Left.reverse();
//        } else {
//            return Direction.Right.reverse();
//        }
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