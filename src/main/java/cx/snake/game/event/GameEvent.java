package cx.snake.game.event;

import cx.snake.game.GameObject;

/**
 * Created with IntelliJ IDEA.
 * User: ael
 * Date: 11.08.13
 * Time: 09:12
 * To change this template use File | Settings | File Templates.
 */
public class GameEvent {
    private GameObject sender;
    private String type;

    public GameObject getSender() {
        return sender;
    }

    public String getType() {
        return type;
    }

    public GameEvent(GameObject sender, String eventType) {
        this.sender = sender;
        this.type = eventType;
    }
}
