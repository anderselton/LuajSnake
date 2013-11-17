package cx.snake.game;

import cx.snake.util.Vector2d;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: ael
 * Date: 06.10.13
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public class testGameboard {
    @Test
    public void testWallCollision() {
        Gameboard gameboard = new Gameboard(10,10);
        Snake snake = new Snake(5, Vector2d.Down, new Vector2d(5, 1));
        gameboard.addSnake(snake);
        int maxSimulations = 100;
        int simulationsUntilCollision = 0;
        while (gameboard.run() && (simulationsUntilCollision < maxSimulations)) {
            simulationsUntilCollision++;
        }
        assertTrue("max simulations exceeded.  Something wrong with collision.", (maxSimulations != simulationsUntilCollision));
//        assertEquals(8,simulationsUntilCollision); */
    }
    /*
    @Test
    public void testSelfCollision() {
        Gameboard gameboard = new Gameboard(10,10);
        Snake snake = new Snake(5, Vector2d.Down, new Vector2d(5, 2));
        gameboard.addSnake(snake);
        snake.setDirection(Vector2d.Right);
        gameboard.run();
        assertTrue(snake.isDead() == false);
        gameboard.run();
        assertTrue(snake.isDead() == false);

    }

    @Test
    public void testTestCollision() {
        Gameboard gameboard = new Gameboard(10,10);
        Snake snake = new Snake(5, Vector2d.Down, new Vector2d(5, 2));
        gameboard.addSnake(snake);
        gameboard.run();

        assertTrue(gameboard.testCollision(snake.getHeadPos()));
        assertTrue(gameboard.testCollision(new Vector2d(0,0)));
//        assertFalse(gameboard.testCollision(new Vector2d(1, 1)));

    }
    */
}
