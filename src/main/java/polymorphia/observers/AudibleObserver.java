package polymorphia.observers;

import java.util.List;

public class AudibleObserver implements Observer{
    @Override 
    public void update(GameEvent gameEvent, List<String> info) {
        String spokenText = "Audible Observer: ";

        switch (gameEvent) {
            case GAME_ENDED:
                spokenText += " Game Ended";
                break;
            case FIGHT:
                spokenText += String.join(", ", info) + " are fighting";
                break;
            case DEATH:
                spokenText += info.getFirst() + " has died";
                break;
            default:
                break;
        }

        System.out.println(spokenText); // Pretend this is a speaker
    }

     @Override 
    public boolean canSubscribe(GameEvent gameEvent) {
        switch (gameEvent) {
            case GAME_ENDED:
                return true;
            case FIGHT:
                return true;
            case DEATH:
                return true;
            default:
                return false;
        }
    } 
}
