package cx.snake.game;

import cx.snake.util.Vector2d;

import java.awt.*;

/**
 * Food is a quadratic piece, that when collided with, will cause a snake to grow.
 * It can only be consumed once.
 */
public class Food extends GameObject {
    private int size;
    Vector2d pos;
    boolean isConsumed = false;

    Color snakeColor = Color.YELLOW;

    public Color getColor() {
        return snakeColor;
    }

    public void setColor(Color c) {
        snakeColor = c;
    }

    public Food(int size, Vector2d pos) {
        this.size = size;
        this.pos = new Vector2d(pos);
    }

    public int getSize() {
        return size;
    }
    public Vector2d getPos() {
        return new Vector2d(pos);
    }

    public int consume() {
        if (!isConsumed) {
            isConsumed = true;
            return size;
        }
        return 0;
    }

    public boolean isConsumed() {
        return isConsumed;
    }

    public boolean isColliding(Vector2d snakeHeadPos) {
        if (snakeHeadPos.getX() >= pos.getX() &&
            snakeHeadPos.getX() <= (pos.getX() + size) &&
            snakeHeadPos.getY() >= pos.getY() &&
            snakeHeadPos.getY() <= (pos.getY() + size)) {
            return true;
        }
        return false;
    }
}
