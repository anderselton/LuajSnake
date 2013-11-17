package cx.snake.game;

import cx.snake.util.Vector2d;

/**
 * Created with IntelliJ IDEA.
 * User: ael
 * Date: 04.10.13
 * Time: 18:25
 * To change this template use File | Settings | File Templates.
 */
public class SnakeController {
    private Snake snake;
    private Gameboard gameboard;

    public void init(Snake snake, Gameboard board) {
        this.snake = snake;
        this.gameboard = board;
    }

    public void moveLeft() {
        snake.setDirection(Vector2d.Left);
    }
    public void moveRight() {
        snake.setDirection(Vector2d.Right);
    }
    public void moveUp() {
        snake.setDirection(Vector2d.Up);
    }
    public void moveDown() {
        snake.setDirection(Vector2d.Down);
    }
    public Snake getSnake() {
        return snake;
    }
    public Gameboard getGameboard() {
        return gameboard;
    }
}
