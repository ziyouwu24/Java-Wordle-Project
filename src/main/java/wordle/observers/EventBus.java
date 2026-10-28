package wordle.observers;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    public static final EventBus instance = new EventBus();
    public final List<Observer> observers = new ArrayList<>();

    private EventBus() {}

    public static EventBus getInstance() {
        return instance;
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void deregister(Observer observer) {
        observers.remove(observer);
    }

    public void newEvent(GameEvent gameEvent) {
        for(Observer observer: observers) {
            observer.notifyEvent(gameEvent);
        }
    }
}
