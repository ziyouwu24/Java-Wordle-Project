package polymorphia.strategy;

import polymorphia.characters.Character;
import polymorphia.maze.Room;

public class SleepStrategy extends Strategy {

    @Override
    public void doAction(Character myself, Room room) {
        // Sleep strategy does nothing, the character sleeps;
    }
}
