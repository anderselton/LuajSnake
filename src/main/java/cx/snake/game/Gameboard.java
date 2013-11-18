package cx.snake.game;

import cx.snake.game.event.GameEvent;
import cx.snake.util.Vector2d;

import java.util.ArrayList;
import java.util.List;

/**
    Gameboard contains an entire simulation of the snake game.
    It contains a list of active snakes, dead snakes and walls that defines boundaries of game.
    It also contains *one* piece of food, that will respawn at a different location if eaten.
 */
public class Gameboard extends GameObject {
    private List<Snake> activeSnakeList = new ArrayList<Snake>();
    private List<Snake> deadSnakeList = new ArrayList<Snake>();
    private LineSegment leftWall, rightWall, topWall, bottomWall;

    private List<CollisionSegment> collisionSegments = new ArrayList<CollisionSegment>(4);

    private int maxX;
    private int maxY;
    private Food food;

    public Gameboard(int maxX, int maxY) {
        leftWall = new LineSegment(new Vector2d(0,0), new Vector2d(0,maxY));
        rightWall = new LineSegment(new Vector2d(maxX,0), new Vector2d(maxX,maxY));
        topWall = new LineSegment(new Vector2d(0,0), new Vector2d(maxX,0));
        bottomWall = new LineSegment(new Vector2d(0,maxY), new Vector2d(maxX,maxY));
        collisionSegments.add(leftWall);
        collisionSegments.add(rightWall);
        collisionSegments.add(topWall);
        collisionSegments.add(bottomWall);
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public List<CollisionSegment> getWalls() { return collisionSegments; }
    public List<Snake> getSnakes() { return activeSnakeList; }

    public void addSnake(Snake snake) {
        activeSnakeList.add(snake);
    }

    public Food getFood() {
        return food;
    }

    /**
     *
     * @return true if the simulation should continue.
     */
    public boolean run() {
        sendEvent(new GameEvent(this, "RunFrame"));
        for (int snakeArrayPos = activeSnakeList.size() -1;snakeArrayPos >= 0; snakeArrayPos--) {
            Snake snake = activeSnakeList.get(snakeArrayPos);
            snake.sendEvent(new GameEvent(snake, "RunFrame"));
            Vector2d startPos = snake.getHead().getFirst();
            boolean isDead = snake.advance(1);
            Vector2d endPos = snake.getHead().getFirst();
            boolean collision = isDead ||  doCollision(snake, new LineSegment(startPos, endPos));
            if (collision) {
                snake.setIsDead();
                deadSnakeList.add(snake);
                activeSnakeList.remove(snakeArrayPos);
            }

            if (food != null && !food.isConsumed() && food.isColliding(snake.getHeadPos())) {
                snake.eat(food);
                snake.sendEvent(new GameEvent(snake, "EatFood"));
                sendEvent(new GameEvent(this, "EatFood"));
            }
        }

        if (food == null || food.isConsumed()) {
            createFood(10 + ((int) Math.random() * 10));
        }
        return !activeSnakeList.isEmpty();
    }

    /**
     * Tries to create a food piece with @size.
     * Each time a valid position can not be found, size will be decreased to a minimum of 1.
     *
     * @param size
     */
    public void createFood(int size) {
        if (size >= maxX || size >= maxY) {
            return;
        }
        boolean validPos = false;
        while (!validPos) {
            food = new Food(size, new Vector2d((int)(Math.random()*(maxX-size)), (int)(Math.random()*(maxY-size))));
            for (Snake snake : activeSnakeList) {
                validPos = !food.isColliding(snake.getHeadPos());
                if (!validPos) break;
            }
            if (!validPos && size > 1) {
                size -= 1;
            }
        }
    }

    /**
     * does all collision checks for snake, but only for the segment segment (a piece of the snake).
     *
     * @param snake
     * @param segment
     * @return
     */
    private boolean doCollision(Snake snake, CollisionSegment segment) {
        for (Snake testCollision: activeSnakeList) {
            if (snake != testCollision && testCollision.isCollidingWithSegment(segment)) {
                return true;
            }
        }
        boolean wallCollision =    (snake.isCollidingWithSegment(leftWall) ||
                                    snake.isCollidingWithSegment(rightWall) ||
                                    snake.isCollidingWithSegment(topWall) ||
                                    snake.isCollidingWithSegment(bottomWall));

        return wallCollision;
    }

    /**
     * Tests if the pos will cause collision or not.
     * @param pos
     * @return
     */
    public boolean testCollision(Vector2d pos) {
        CollisionSegment segment = new LineSegment(pos,pos);
        for (Snake testCollision: activeSnakeList) {

            if (testCollision.isCollidingWithSegment(segment)) {
                return true;
            }
        }
        boolean wallCollision =    (segment.isIntersecting(leftWall) ||
                segment.isIntersecting(rightWall) ||
                segment.isIntersecting(topWall) ||
                segment.isIntersecting(bottomWall));

        return wallCollision;
    }
}
