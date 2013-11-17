package cx.snake.game;

import cx.snake.util.Vector2d;

import java.awt.geom.Line2D;

/**
 * Represents a line in the snake game that enables collision.
 */
public class LineSegment implements CollisionSegment {
    protected Vector2d first, last;

    public LineSegment(Vector2d f, Vector2d l) {
        this.first = f;
        this.last = l;
    }
    @Override
    public boolean isIntersecting(CollisionSegment test) {
        Line2D.Double self = asLine();
        Line2D.Double testTarget = test.asLine();

        // two points will cause a false positive in line intersect test.
        // This is workaround/hack.
        if (self.getP1().equals(self.getP2())) {
            if (testTarget.getP1().equals(testTarget.getP2())) {
                return self.getP1() == testTarget.getP2();
            }
        }

        return self.intersectsLine(testTarget);
    }

    @Override
    public Vector2d getFirst() {
        return new Vector2d(first);
    }

    @Override
    public Vector2d getLast() {
        return new Vector2d(last);
    }

    @Override
    public Line2D.Double asLine() {
        return new Line2D.Double(getFirst(), getLast());
    }

    @Override
    public String toString() {
        return first.toString() + " " + last.toString();
    }
}
