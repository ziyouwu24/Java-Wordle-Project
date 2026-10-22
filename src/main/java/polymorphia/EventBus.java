package polymorphia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import polymorphia.observers.GameEvent;
import polymorphia.observers.Observer;

public class EventBus {
    private static final EventBus instance = new EventBus();
    private final Map<GameEvent, List<Observer>> subscriberMap = new HashMap<>();

    private EventBus() {

    }

    public static EventBus getInstance() {
        return instance;
    }

    public void subscribe(GameEvent gameEvent, Observer observer) {
        if(observer.canSubscribe(gameEvent)) {
            subscriberMap.computeIfAbsent(gameEvent, key -> new ArrayList<>())
                .add(observer);
        }
    }

    public void publish(GameEvent gameEvent, List<String> info) {
        List<Observer> subscribers = subscriberMap.get(gameEvent);
        
        if(subscribers != null) {
            for(Observer subscriber: subscribers) {
                subscriber.update(gameEvent, info);
            }
        }
    }
}
