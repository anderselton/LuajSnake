package cx.snake.game;

import cx.snake.util.Vector2d;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: ael
 * Date: 04.10.13
 * Time: 19:02
 * To change this template use File | Settings | File Templates.
 */
public class testSnake {
    private Snake createSnake() {
        return new Snake(100, Vector2d.Down, new Vector2d(0,0));
    }

    @Test
    public void testCreateSnakeCreate() {
        Snake snake = createSnake();
        assertEquals(100.0, snake.getSize(), 0.0);
        assertTrue(snake.isDead() == false);
    }

    @Test
    public void testSnakeMove() {
        Snake snake = createSnake();
        snake.advance(100.0);
        assertTrue(snake.isDead() == false);
        assertEquals(100.0, snake.getSize(), 0.0);
        assertEquals("head not at expected pos", snake.getHeadPos(), new Vector2d(0,100) );
        assertEquals("tail not at expected pos", snake.getTailPos(), new Vector2d(0,0) );
    }

    @Test
    public void testSnakeChangeDirectionAndGrowSegments() {
        Snake snake = createSnake();
        snake.setDirection(Vector2d.Right);
        assertEquals(Vector2d.Right, snake.getDirection());
        assertEquals(2, snake.getSegmentCount());
    }
    @Test
    public void testSnakeChangeDirectionToSameNoEffect() {
        Snake snake = createSnake();
        snake.setDirection(Vector2d.Right);
        snake.setDirection(Vector2d.Right);
        assertEquals(Vector2d.Right, snake.getDirection());
        assertEquals(2, snake.getSegmentCount());
    }

    @Test
    public void testSnakeChangeDirectionAndMove() {
        Snake snake = createSnake();
        snake.setDirection(Vector2d.Right);
        snake.advance(10);
        assertTrue(snake.isDead() == false);
        snake.setDirection(Vector2d.Down);
        snake.advance(10);
        assertTrue(snake.isDead() == false);
        assertEquals(3, snake.getSegmentCount());
        assertEquals("head not at expected pos", new Vector2d(10,10), snake.getHeadPos());
        assertEquals("tail not at expected pos", new Vector2d(0,0), snake.getTailPos());
    }
    @Test
    public void testSnakeChangeDirectionAndMoveRemoveTailSegments() {
        Snake snake = createSnake();
        snake.setDirection(Vector2d.Right);
        snake.advance(10);
        snake.setDirection(Vector2d.Down);
        snake.advance(95);
        assertEquals(2, snake.getSegmentCount() );
        assertEquals("head not at expected pos", new Vector2d(10,95), snake.getHeadPos());
        assertEquals("tail not at expected pos", new Vector2d(5,0), snake.getTailPos());
    }
    @Test
    public void testSnakeGrow() {
        Snake snake = createSnake();
        snake.grow(50);
        snake.setDirection(Vector2d.Right);
        snake.advance(150);
        assertEquals(150, snake.getSize(), 0);
    }
    @Test
    public void testSnakeMoveCollide() {
        Snake snake = createSnake();
        boolean isColliding = snake.advance(10);
        assertFalse(isColliding);
        snake.setDirection(Vector2d.Up);
        isColliding = snake.advance(10);
        assertTrue(isColliding);
    }
}
