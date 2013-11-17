package cx.snake.game;

import cx.snake.util.Vector2d;

import java.awt.geom.Line2D;

/**
 * Created with IntelliJ IDEA.
 * User: ael
 * Date: 04.10.13
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
public interface CollisionSegment {
    public boolean isIntersecting(CollisionSegment test);
    public Vector2d getFirst();
    public Vector2d getLast();
    public Line2D.Double asLine();
}
