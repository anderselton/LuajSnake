package cx.snake.game;

import cx.snake.util.Vector2d;


/**
 * Snakesegment is a LineSegment that also can move in a direction.
 */
public class SnakeSegment extends LineSegment {

    private Vector2d direction;

    public SnakeSegment(Vector2d dir, Vector2d pos) {
        super(pos, pos);
        this.direction = dir;
    }

    public void advanceHead(double units) {
        first = this.first.add(direction.multiply(units));
    }

    public double reduceTail(double units) {
        double headTailDelta = getSize();
        if (headTailDelta >= units) {
            this.last = this.last.add(direction.multiply(units));
            return 0;
        }
        return units - headTailDelta;
    }
    public double getSize() {
        return this.first.distance(last);
    }

    public Vector2d getHead() {
        return getFirst();
    }
    public Vector2d getTail() {
        return getLast();
    }

    public Vector2d getDirection() {
        return this.direction;
    }

    @Override
    public String toString() {
        return super.toString() + ", size:" + getSize();
    }
}
