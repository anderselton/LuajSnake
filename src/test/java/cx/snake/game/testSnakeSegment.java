package cx.snake.game;

import cx.snake.util.Vector2d;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: ael
 * Date: 04.10.13
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class testSnakeSegment {

    @Test
    public void testSegmentGrow() {
        SnakeSegment segment = new SnakeSegment(Vector2d.Down, new Vector2d(0,0));
        segment.advanceHead(10);
        assertEquals("Not at expected pos", new Vector2d(0,10), segment.getHead());
    }
    @Test
    public void testSegmentReduce() {
        SnakeSegment segment = new SnakeSegment(Vector2d.Down, new Vector2d(0,0));
        segment.advanceHead(100);
        assertEquals(segment.getSize(), 100.0, 0.0);
        double remainder = segment.reduceTail(50);
        assertEquals(remainder, 0.0, 0.0);
        assertEquals(segment.getSize(), 50.0, 0.0);
        remainder = segment.reduceTail(50);
        assertEquals(remainder, 0.0, 0.0);
        assertEquals(segment.getSize(), 0.0, 0.0);
        remainder = segment.reduceTail(50);
        assertEquals(remainder, 50.0, 0.0);
    }
}
