package cx.snake.util;

import java.awt.geom.Point2D;

/**
 * Simplistic representation of a 2d mathvector.  Contains commonly used vector functions.
 */
public class Vector2d extends Point2D.Double {
    public Vector2d(double x, double y) {
        super(x,y);
    }
    public Vector2d(Vector2d org) {
        super(org.getX(), org.getY());
    }
    public Vector2d multiply(double scalar) {
        return new Vector2d(getX() * scalar, getY() * scalar);
    }
    public void setX(double x) {
        setLocation(x, getY());
    }
    public void setY(double y) {
        setLocation(getX(),y);
    }
    public Vector2d add(Vector2d add) {
        return new Vector2d(getX() + add.getX(), getY() + add.getY());
    }
    double distance(Vector2d target) {
        double dx = x - target.x;
        double dy = y - target.y;

        return Math.sqrt(dx*dx + dy*dy);
    }
    @Override
    public String toString() {
        return "x: " + getX() + ", y: " + getY();
    }

    private static class UpVector extends Vector2d {
        public UpVector() {
            super(0, -1);
        }
        @Override
        public String toString() {
            return "UP";
        }
    }
    private static class DownVector extends Vector2d {
        public DownVector() {
            super(0, 1);
        }
        @Override
        public String toString() {
            return "DOWN";
        }
    }
    private static class LeftVector extends Vector2d {
        public LeftVector() {
            super(-1, 0);
        }
        @Override
        public String toString() {
            return "LEFT";
        }
    }
    private static class RightVector extends Vector2d {
        public RightVector() {
            super(1, 0);
        }
        @Override
        public String toString() {
            return "RIGHT";
        }
    }


    public static Vector2d Up =  new UpVector();
    public static Vector2d Down =  new DownVector();
    public static Vector2d Left =  new LeftVector();
    public static Vector2d Right =  new RightVector();
}
