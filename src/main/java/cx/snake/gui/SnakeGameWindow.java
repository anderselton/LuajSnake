package cx.snake.gui;

import cx.snake.game.*;
import cx.snake.game.Snake;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ael
 * Date: 08.10.13
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class SnakeGameWindow extends JPanel implements ActionListener{
    private Gameboard gameboard = new Gameboard(250 ,250);

    private List<SnakeController> snakeControllerList = new ArrayList<SnakeController>();

    private JLabel renderLabel;
    private BufferedImage image;
    private int height;
    private int width;

    Timer timer;

    public SnakeGameWindow(int speed) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        height = 251; //(int)(screenSize.getHeight()*0.8);
        width = 251; //(int)(screenSize.getWidth()*0.8);
        screenSize.setSize(width, height);
        setMinimumSize(screenSize);
        image =  new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        renderLabel  = new JLabel( new ImageIcon(image));
        add(renderLabel);
        timer = new Timer(speed, this);
    }

    public void addSnakeController(SnakeController snakeController) {
        snakeControllerList.add(snakeController);
        gameboard.addSnake( snakeController.getSnake() );
    }

    public Gameboard getGameboard() { return gameboard; }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("RunFrame")) {
            runSimulation();
        }
    }

    public void start() {
        timer.setActionCommand("RunFrame");
        timer.start();
    }

    private void runSimulation() {
        boolean gameFinished = !gameboard.run();
        if (!gameFinished) {
            // draw everything.
            drawEverything();
            renderLabel.repaint(0,0,getWidth(),getHeight());
        } else {
            timer.stop();

            Snake bestSnake = null;
            for (SnakeController ctrl: snakeControllerList) {
                if (bestSnake == null || ctrl.getSnake().getSize() > bestSnake.getSize()) {
                    bestSnake = ctrl.getSnake();
                }
            }
            if (bestSnake != null) {
                renderLabel.setText("GAME OVER: Score " + bestSnake.getSize() );
            }
        }
    }

    private void drawEverything() {
        // clear all from lbl
        Graphics graphics = image.getGraphics();
        Color background = Color.BLACK;
        graphics.setColor(background);
        graphics.fillRect(0,0,width,height);

        for (CollisionSegment segment: gameboard.getWalls()) {
            drawCollisionSegment(Color.RED, graphics, segment);
        }

        for (Snake snake : gameboard.getSnakes()) {
            for (CollisionSegment segment: snake.getAllSegments()) {
                drawCollisionSegment(snake.getColor(), graphics, segment);
            }
        }

        drawFood(graphics);
    }

    private int translateX(double x) {
        return ((int) x)* translationScale();
    }
    private int translateY(double y) {
        return ((int) y)* translationScale();
    }
    private int translationScale() {
        return 1;
    }

    private void drawCollisionSegment(Color color, Graphics graphics, CollisionSegment segment) {
        graphics.setColor(color);
        int x1 = translateX(segment.getFirst().getX());
        int y1 = translateY(segment.getFirst().getY());

        int x2 = translateX(segment.getLast().getX());
        int y2 = translateY(segment.getLast().getY());

        graphics.drawLine(x1, y1,x2,y2);
    }

    private void drawFood(Graphics graphics) {
        Food food = gameboard.getFood();
        graphics.setColor(food.getColor());
        int x = (int)food.getPos().getX();
        int y = (int)food.getPos().getY();
        int size = food.getSize();
        graphics.fillRect(x, y, size, size);
    }
}
