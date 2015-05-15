package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.game.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDirection {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testXAndY() throws Exception {
        assertEquals("Direction.Up must have x to be 0.", 0, Direction.Up.getX());
        assertEquals("Direction.Up must have y to be 1.", 1, Direction.Up.getY());

        assertEquals("Direction.Down must have x to be 0.", 0, Direction.Down.getX());
        assertEquals("Direction.Down must have y to be -1.", -1, Direction.Down.getY());

        assertEquals("Direction.Right must have x to be 1.", 1, Direction.Right.getX());
        assertEquals("Direction.Right must have y to be 0.", 0, Direction.Right.getY());

        assertEquals("Direction.Left must have x to be -1.", -1, Direction.Left.getX());
        assertEquals("Direction.Left must have y to be 0.", 0, Direction.Left.getY());
    }

    @Test
    public void testReverse() throws Exception {
        assertEquals("Direction.Up must be Down when reversed.", Direction.Down, Direction.Up.reverse());
        assertEquals("Direction.Down must be Up when reversed.", Direction.Up, Direction.Down.reverse());
        assertEquals("Direction.Right must be Left when reversed.", Direction.Left, Direction.Right.reverse());
        assertEquals("Direction.Left must be Right when reversed.", Direction.Right, Direction.Left.reverse());
    }

    @Test
    public void testValueOf() throws Exception {
        assertSame("Direction should be Up.", Direction.Up, Direction.valueOf(0, 1));
        assertSame("Direction should be Down.", Direction.Down, Direction.valueOf(0, -1));
        assertSame("Direction should be Left.", Direction.Left, Direction.valueOf(-1, 0));
        assertSame("Direction should be Right.", Direction.Right, Direction.valueOf(1, 0));
    }

    @Test
    public void testGetDirection() throws Exception {
        assertSame("Direction 0,0 to 0,1 should be Up.", Direction.Up, Direction.getDirection(0, 0, 0, 1));
        assertSame("Direction 0,1 to 0,10 should be Up.", Direction.Up, Direction.getDirection(0, 1, 0, 10));
        assertSame("Direction 0,10 to 0,12 should be Up.", Direction.Up, Direction.getDirection(0, 10, 0, 12));
        assertSame("Direction 0,-1 to 0,0 should be Up.", Direction.Up, Direction.getDirection(0, -1, 0, 0));
        assertSame("Direction 0,-1 to 0,1 should be Up.", Direction.Up, Direction.getDirection(0, -1, 0, 1));
        assertSame("Direction 0,-12 to 0,12 should be Up.", Direction.Up, Direction.getDirection(0, -12, 0, 12));

        assertSame("Direction 0,1 to 0,0 should be Down.", Direction.Down, Direction.getDirection(0, 1, 0, 0));
        assertSame("Direction 0,10 to 0,1 should be Down.", Direction.Down, Direction.getDirection(0, 10, 0, 1));
        assertSame("Direction 0,12 to 0,10 should be Down.", Direction.Down, Direction.getDirection(0, 12, 0, 10));
        assertSame("Direction 0,1 to 0,0 should be Down.", Direction.Down, Direction.getDirection(0, 1, 0, 0));
        assertSame("Direction 0,1 to 0,-1 should be Down.", Direction.Down, Direction.getDirection(0, 1, 0, -1));
        assertSame("Direction 0,12 to 0,-12 should be Down.", Direction.Down, Direction.getDirection(0, 12, 0, -12));

        assertSame("Direction 0,0 to 1,0 should be Right.", Direction.Right, Direction.getDirection(0, 0, 1, 0));
        assertSame("Direction -1,0 to 1,0 should be Right.", Direction.Right, Direction.getDirection(-1, 0, 1, 0));
        assertSame("Direction -12,0 to 1,0 should be Right.", Direction.Right, Direction.getDirection(-12, 0, 1, 0));
        assertSame("Direction 0,0 to 10,0 should be Right.", Direction.Right, Direction.getDirection(0, 0, 10, 0));
        assertSame("Direction -1,0 to 0,0 should be Right.", Direction.Right, Direction.getDirection(-1, 0, 0, 0));
        assertSame("Direction -12,0 to -1,0 should be Right.", Direction.Right, Direction.getDirection(-12, 0, -1, 0));

        assertSame("Direction 0,0 to -1,0 should be Left.", Direction.Left, Direction.getDirection(0, 0, -1, 0));
        assertSame("Direction 1,0 to -1,0 should be Left.", Direction.Left, Direction.getDirection(1, 0, -1, 0));
        assertSame("Direction 12,0 to -1,0 should be Left.", Direction.Left, Direction.getDirection(12, 0, -1, 0));
        assertSame("Direction 0,0 to -10,0 should be Left.", Direction.Left, Direction.getDirection(0, 0, -10, 0));
        assertSame("Direction 1,0 to 0,0 should be Left.", Direction.Left, Direction.getDirection(1, 0, 0, 0));
        assertSame("Direction 12,0 to 1,0 should be Left.", Direction.Left, Direction.getDirection(12, 0, 1, 0));

        // Test diagonal direction.
        assertNull("Direction must not be diagonal.", Direction.getDirection(0, 0, 1, 1));
        assertNull("Direction must not be diagonal.", Direction.getDirection(0, 0, 12, 15));
        assertNull("Direction must not be diagonal.", Direction.getDirection(0, 0, -1, -1));
        assertNull("Direction must not be diagonal.", Direction.getDirection(1, 1, 0, 0));
        assertNull("Direction must not be diagonal.", Direction.getDirection(12, 15, 0, 0));
        assertNull("Direction must not be diagonal.", Direction.getDirection(-1, -1, 0, 0));

        // Test for null values.
        assertNull("Direction should be null because all values are the same.", Direction.getDirection(0, 0, 0, 0));
        assertNull("Direction should be null because all values are the same.", Direction.getDirection(-1, -1, -1, -1));
        assertNull("Direction should be null because all values are the same.", Direction.getDirection(-10, -10, -10, -10));
        assertNull("Direction should be null because all values are the same.", Direction.getDirection(-100, -100, -100, -100));
    }

    @Test
    public void testGetDirectionDiagonal() throws Exception {
        assertSame("Direction 0,0 to 0,1 should be Up.", Direction.Up, Direction.getDirectionDiagonal(0, 0, 0, 1));
        assertSame("Direction 0,1 to 0,10 should be Up.", Direction.Up, Direction.getDirectionDiagonal(0, 1, 0, 10));
        assertSame("Direction 0,10 to 0,12 should be Up.", Direction.Up, Direction.getDirectionDiagonal(0, 10, 0, 12));
        assertSame("Direction 0,-1 to 0,0 should be Up.", Direction.Up, Direction.getDirectionDiagonal(0, -1, 0, 0));
        assertSame("Direction 0,-1 to 0,1 should be Up.", Direction.Up, Direction.getDirectionDiagonal(0, -1, 0, 1));
        assertSame("Direction 0,-12 to 0,12 should be Up.", Direction.Up, Direction.getDirectionDiagonal(0, -12, 0, 12));

        assertSame("Direction 0,1 to 0,0 should be Down.", Direction.Down, Direction.getDirectionDiagonal(0, 1, 0, 0));
        assertSame("Direction 0,10 to 0,1 should be Down.", Direction.Down, Direction.getDirectionDiagonal(0, 10, 0, 1));
        assertSame("Direction 0,12 to 0,10 should be Down.", Direction.Down, Direction.getDirectionDiagonal(0, 12, 0, 10));
        assertSame("Direction 0,1 to 0,0 should be Down.", Direction.Down, Direction.getDirectionDiagonal(0, 1, 0, 0));
        assertSame("Direction 0,1 to 0,-1 should be Down.", Direction.Down, Direction.getDirectionDiagonal(0, 1, 0, -1));
        assertSame("Direction 0,12 to 0,-12 should be Down.", Direction.Down, Direction.getDirectionDiagonal(0, 12, 0, -12));

        assertSame("Direction 0,0 to 1,0 should be Right.", Direction.Right, Direction.getDirectionDiagonal(0, 0, 1, 0));
        assertSame("Direction -1,0 to 1,0 should be Right.", Direction.Right, Direction.getDirectionDiagonal(-1, 0, 1, 0));
        assertSame("Direction -12,0 to 1,0 should be Right.", Direction.Right, Direction.getDirectionDiagonal(-12, 0, 1, 0));
        assertSame("Direction 0,0 to 10,0 should be Right.", Direction.Right, Direction.getDirectionDiagonal(0, 0, 10, 0));
        assertSame("Direction -1,0 to 0,0 should be Right.", Direction.Right, Direction.getDirectionDiagonal(-1, 0, 0, 0));
        assertSame("Direction -12,0 to -1,0 should be Right.", Direction.Right, Direction.getDirectionDiagonal(-12, 0, -1, 0));

        assertSame("Direction 0,0 to -1,0 should be Left.", Direction.Left, Direction.getDirectionDiagonal(0, 0, -1, 0));
        assertSame("Direction 1,0 to -1,0 should be Left.", Direction.Left, Direction.getDirectionDiagonal(1, 0, -1, 0));
        assertSame("Direction 12,0 to -1,0 should be Left.", Direction.Left, Direction.getDirectionDiagonal(12, 0, -1, 0));
        assertSame("Direction 0,0 to -10,0 should be Left.", Direction.Left, Direction.getDirectionDiagonal(0, 0, -10, 0));
        assertSame("Direction 1,0 to 0,0 should be Left.", Direction.Left, Direction.getDirectionDiagonal(1, 0, 0, 0));
        assertSame("Direction 12,0 to 1,0 should be Left.", Direction.Left, Direction.getDirectionDiagonal(12, 0, 1, 0));

        // Test diagonal direction.
        assertEquals("Direction is diagonal and should return Up.", Direction.Up, Direction.getDirectionDiagonal(0, 0, 1, 1));
        assertEquals("Direction is diagonal and should return Up.", Direction.Up, Direction.getDirectionDiagonal(0, 0, 12, 15));
        assertEquals("Direction is diagonal and should return Up.", Direction.Up, Direction.getDirectionDiagonal(-1, -1, 0, 0));
        assertEquals("Direction is diagonal and should return Down.", Direction.Down, Direction.getDirectionDiagonal(0, 0, -1, -1));
        assertEquals("Direction is diagonal and should return Down.", Direction.Down, Direction.getDirectionDiagonal(1, 1, 0, 0));
        assertEquals("Direction is diagonal and should return Down.", Direction.Down, Direction.getDirectionDiagonal(12, 15, 0, 0));
        assertEquals("Direction is diagonal and should return Up.", Direction.Down, Direction.getDirectionDiagonal(-5, 4, -1, 0));
        assertEquals("Direction is diagonal and should return Left.", Direction.Left, Direction.getDirectionDiagonal(16, 15, 0, 0));
        assertEquals("Direction is diagonal and should return Left.", Direction.Left, Direction.getDirectionDiagonal(0, 0, -1, 0));
        assertEquals("Direction is diagonal and should return Left.", Direction.Left, Direction.getDirectionDiagonal(4, -2, -4, 0));
        assertEquals("Direction is diagonal and should return Right.", Direction.Right, Direction.getDirectionDiagonal(0, 0, 16, 15));
        assertEquals("Direction is diagonal and should return Right.", Direction.Right, Direction.getDirectionDiagonal(-5, 4, 0, 8));
        assertEquals("Direction is diagonal and should return Right.", Direction.Right, Direction.getDirectionDiagonal(152, 100, 255, 200));

        // Test for null values.
        assertNull("Direction should be null because all values are the same.", Direction.getDirectionDiagonal(0, 0, 0, 0));
        assertNull("Direction should be null because all values are the same.", Direction.getDirectionDiagonal(-1, -1, -1, -1));
        assertNull("Direction should be null because all values are the same.", Direction.getDirectionDiagonal(-10, -10, -10, -10));
        assertNull("Direction should be null because all values are the same.", Direction.getDirectionDiagonal(-100, -100, -100, -100));
    }
}
