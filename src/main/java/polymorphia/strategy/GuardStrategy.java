package polymorphia.strategy;

import polymorphia.characters.Character;
import polymorphia.maze.Room;

import java.util.Optional;

public class GuardStrategy extends Strategy {

    @Override
    public void doAction(Character myself, Room currentRoom) {
        if (currentRoom.hasTreasure()) {
            return;     // stand guard and do nothing if there is treasure in the room
        }
        // Else, behave like a normal creature
        Optional<Character> character = currentRoom.getRandomCharacterNotMe(myself);
        if (character.isPresent()) {
            myself.fight(character.get());
        } else {
            myself.move(currentRoom);
        }
    }

}
