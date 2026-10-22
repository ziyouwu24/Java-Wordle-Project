package polymorphia.observers;

import java.util.List;

public class TestObserver implements Observer{
    private int totalFights = 0;
    private int totalDeaths = 0;

    @Override 
    public void update(GameEvent gameEvent, List<String> info) {
        switch (gameEvent) {
            case FIGHT:
                totalFights++;
                break;
            case DEATH:
                totalDeaths++;
                break;
            default:
                break;
        }
    }

     @Override 
    public boolean canSubscribe(GameEvent gameEvent) {
        switch (gameEvent) {
            default:
                return true;
        }
    } 

    public boolean fightOccured() {
        return totalFights > 0;
    }

    public int totalDeaths() {
        return totalDeaths;
    }
}
