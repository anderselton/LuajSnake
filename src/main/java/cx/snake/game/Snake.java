package cx.snake.game;

import cx.snake.util.Vector2d;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Snake is represented by a list of snakesegments.  Each snake segment represents a straight line.
 * A snake can be dead (means it has collided) or alive.
 *
 */
public class Snake extends GameObject {

    private LinkedList<SnakeSegment> snakeSegments = new LinkedList<SnakeSegment>();
    private double growSize  = 0;
    private boolean isDead = false;
    private String name = "Snake";
    Color snakeColor = Color.GREEN;

    public Color getColor() {
        return snakeColor;
    }

    public void setColor(Color c) {
        snakeColor = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public Snake(int initialSize, Vector2d direction, Vector2d startPos) {
        SnakeSegment head = new SnakeSegment(direction, startPos);
        //head.advanceHead(initialSize);
        growSize = initialSize;
        snakeSegments.add(head);

    }

    public LinkedList<SnakeSegment> getAllSegments() { return snakeSegments;}

    public double getSize() {
        double size = 0;
        for(SnakeSegment segment: snakeSegments) {
            size += segment.getSize();
        }

        return size + growSize;
    }

    public boolean advance(double units) {
        if (isDead) {
            return true;
        }
        SnakeSegment head = snakeSegments.getFirst();
        Vector2d startPos = head.getFirst();
        head.advanceHead(units);
        Vector2d endPos = head.getFirst();
        double unitsToRemove = units;
        if (growSize > 0) {
            unitsToRemove -= growSize;
            growSize = Math.max(0, growSize - units);
        }
        while (unitsToRemove > 0) {
            SnakeSegment tail = snakeSegments.getLast();
            unitsToRemove = tail.reduceTail(unitsToRemove);
            if (unitsToRemove > 0) {
                snakeSegments.removeLast();
            }
        }
        return isCollidingWithSelf(new LineSegment(startPos, endPos));
    }
    public void setIsDead() {
        isDead = true;
    }
    public boolean isDead() { return isDead; }
    public boolean isAlive() { return !isDead; }
    public SnakeSegment getHead() {
        return snakeSegments.getFirst();
    }

    /**
     * Checks if any of the snake segments representing this snake is colliding with the segment provided.
     * @param segment
     * @return
     */
    public boolean isCollidingWithSegment(CollisionSegment segment) {
        Iterator<SnakeSegment> iterator = snakeSegments.iterator();
        while (iterator.hasNext()) {
            SnakeSegment body = iterator.next();
            if (segment.isIntersecting(body)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if snake is colliding with self.
     * @param head
     * @return
     */
    private boolean isCollidingWithSelf(CollisionSegment head) {
        // no need to check if less than 3 segments.
        if (snakeSegments.size() < 3) {
            return false;
        }
        Iterator<SnakeSegment> iterator = snakeSegments.iterator();
        iterator.next();
        iterator.next();
        while (iterator.hasNext()) {
            SnakeSegment body = iterator.next();
            if (head.isIntersecting(body)) {
                return true;
            }
        }
        return false;
    }

    public Vector2d getHeadPos() {
        SnakeSegment head = snakeSegments.getFirst();
        return head.getHead();
    }
    public Vector2d getTailPos() {
        SnakeSegment tail = snakeSegments.getLast();
        return tail.getTail();
    }

    public Vector2d getDirection() {
        SnakeSegment head = snakeSegments.getFirst();
        return head.getDirection();
    }

    public void setDirection(Vector2d direction) {
        if (!getDirection().equals(direction)){
            if ((direction.getX() * getDirection().getX()) < 0.0f ||
                (direction.getY() * getDirection().getY()) < 0.0f     ) {
                isDead = true;
                return;
            }
            SnakeSegment newHead = new SnakeSegment(direction, getHeadPos());
            snakeSegments.addFirst(newHead);
        }
    }
    public int getSegmentCount() {
        return snakeSegments.size();
    }

    public void grow(int size) {
        this.growSize += size;
    }

    public void eat (Food food) {
        grow(food.consume());
    }

    @Override
    public String toString() {
        return "Snake: pos[" + getHead().toString() + "] dead: " + isDead + " dir: [" + getDirection().toString() + "]";
    }
}
