package polymorphia.strategy;

import polymorphia.characters.Character;
import polymorphia.maze.Room;

import java.util.Optional;

public class CreatureStrategy extends Strategy {
    // Creatures fight adventurers or other creatures

    @Override
    public void doAction(Character myself, Room currentRoom) {
        Optional<Character> character = currentRoom.getRandomCharacterNotMe(myself);
        if (character.isPresent()) {
            myself.fight(character.get());
        } else {
            myself.move(currentRoom);
        }
    }
}
