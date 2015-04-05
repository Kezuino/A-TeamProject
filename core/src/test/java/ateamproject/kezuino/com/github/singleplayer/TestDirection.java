package ateamproject.kezuino.com.github.singleplayer;

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
}
