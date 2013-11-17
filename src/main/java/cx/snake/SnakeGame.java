package cx.snake;

import cx.snake.game.Gameboard;
import cx.snake.game.SnakeController;
import cx.snake.game.Snake;
import cx.snake.gui.SnakeGameWindow;
import cx.snake.util.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * User: ael
 * Date: 08.10.13
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public class SnakeGame {
    protected JFrame mainFrame = new JFrame("Snake");
    private JPanel mainPanel = new JPanel();
    private SnakeGameWindow snakeGameWindow = null;

    protected int getGameSpeed() { return 40; }

    protected SnakeGameWindow createNewGame() {
        if (snakeGameWindow != null) {
            mainPanel.remove(snakeGameWindow);
            snakeGameWindow = null;
        }

        snakeGameWindow = new SnakeGameWindow(getGameSpeed());
        mainPanel.add(snakeGameWindow);
        mainFrame.repaint();
        mainFrame.revalidate();
        snakeGameWindow.setVisible(true);
        return snakeGameWindow;
    }

    protected void createMenu(JPanel menuPanel) {
        JButton startGameBtn = new JButton("Start Game");
        startGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameWindow newGame = createNewGame();
                //newGame.pack();


                final SnakeController snakeCtrl = new SnakeController();
                snakeCtrl.init(new Snake(10, Vector2d.Down, new Vector2d(10, 10)), newGame.getGameboard());
                newGame.addSnakeController(snakeCtrl);

                KeyListener listener = new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_UP: snakeCtrl.moveUp(); break;
                            case KeyEvent.VK_DOWN: snakeCtrl.moveDown(); break;
                            case KeyEvent.VK_LEFT: snakeCtrl.moveLeft(); break;
                            case KeyEvent.VK_RIGHT: snakeCtrl.moveRight(); break;
                            case KeyEvent.VK_SPACE:
                                Gameboard gameboard = snakeCtrl.getGameboard();
                                gameboard.createFood(gameboard.getFood().getSize());
                                break;
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                };
                newGame.setFocusable(true);
                newGame.requestFocus();
                newGame.addKeyListener(listener);
                newGame.start();
            }
        });
        menuPanel.add(startGameBtn);
        menuPanel.add(new JButton("Exit"));

    }

    public void startGame() {
        //Create and set up the window.
        Dimension screenSize = new Dimension(260  , 260);
        mainFrame.setMinimumSize(screenSize);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        mainFrame.add(mainPanel);
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(menuPanel);
        snakeGameWindow = new SnakeGameWindow(getGameSpeed());
        mainPanel.add(snakeGameWindow);
        createMenu(menuPanel);
        //Display the window.
        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    public static void main(String[] args) {

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SnakeGame game = new SnakeGame();
                game.startGame();
            }
        });
    }
}
