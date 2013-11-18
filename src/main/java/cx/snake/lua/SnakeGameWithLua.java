package cx.snake.lua;

import cx.snake.SnakeGame;
import cx.snake.game.*;
import cx.snake.game.event.GameEvent;
import cx.snake.game.event.GameEventListener;
import cx.snake.gui.SnakeGameWindow;
import cx.snake.util.Vector2d;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: ael
 * Date: 13.11.13
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public class SnakeGameWithLua extends SnakeGame implements GameEventListener {

    private SnakeController currentSnakeController = null;

    // lua environment
    private Globals luaGlobals = JsePlatform.standardGlobals();

    public SnakeGameWithLua getSelf() {
        return this;
    }
    @Override
    protected int getGameSpeed() {return 1;}

    public Snake createSnake(int x, int y, Vector2d dir) {
        return new Snake(10, dir, new Vector2d(x, y));
    }

    public SnakeController createSnakeController() {
        return new SnakeController();
    }

    @Override
    protected void createMenu(JPanel menuPanel) {
        JButton startLuaGame = new JButton("Lua Game");
        final SnakeGameWithLua self = this;
        startLuaGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final JFileChooser scriptSelector = new JFileChooser();

                scriptSelector.setFileFilter(new LuaFileFilter());
                scriptSelector.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src/main/resources/luascripts/"));

                int returnVal = scriptSelector.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File script = scriptSelector.getSelectedFile();

                    try {
                        SnakeGameWindow newGame = createNewGame();

                        LuaValue gameController = CoerceJavaToLua.coerce(getSelf());
                        luaGlobals.set("gameController", gameController );

                        LuaValue game = CoerceJavaToLua.coerce(newGame);
                        luaGlobals.set("game", game );


                        luaGlobals.get("dofile").call(LuaValue.valueOf(script.getAbsolutePath()));

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        return;
                    }


                }
            }
        });
        menuPanel.add(startLuaGame);
    }

    public static void main(String[] args) {

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SnakeGame game = new SnakeGameWithLua();
                game.startGame();
            }
        });
    }

    @Override
    public void OnEvent(GameEvent event) {
    }
}
