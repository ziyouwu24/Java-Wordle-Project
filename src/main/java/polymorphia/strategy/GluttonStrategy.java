package polymorphia.strategy;

import polymorphia.artifacts.Food;
import polymorphia.characters.Character;
import polymorphia.maze.Room;

import java.util.Optional;

public class GluttonStrategy extends Strategy {

    @Override
    public void doAction(Character character, Room room) {
        Optional<Food> food = room.takeFood();
        if (food.isPresent()) {
            character.eat(food.get());
            return;
        }
        character.move(room);
    }

    @Override
    public boolean isGlutton() {
        return true;
    }

}
