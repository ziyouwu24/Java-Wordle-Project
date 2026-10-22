package polymorphia.observers;

import java.util.List;

public interface Observer {
    void update(GameEvent gameEvent, List<String> info);

    boolean canSubscribe(GameEvent gameEvent);
}
