package cx.snake.game;

import cx.snake.game.event.GameEvent;
import cx.snake.game.event.GameEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Baseclass for all objects that has any meaning in the snake game.  Contains basic event functionallity
 */
public class GameObject {
    private List<GameEventListener> eventListenerList = new ArrayList<GameEventListener>();

    public void addEventListener(GameEventListener listener) {
        removeEventListener(listener);
        eventListenerList.add(listener);
    }

    public void removeEventListener(GameEventListener listener) {
        eventListenerList.remove(listener);
    }

    public void sendEvent(GameEvent event) {
        for (GameEventListener listener: eventListenerList) {
            listener.OnEvent(event);
        }
    }
}
