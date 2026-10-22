package polymorphia.strategy;

import polymorphia.artifacts.DieArtifact;
import polymorphia.artifacts.Food;
import polymorphia.characters.Character;
import polymorphia.maze.Room;

import java.util.Optional;

public class FighterStrategy extends Strategy {

    @Override
    public void doAction(Character myself, Room room) {
        Optional<DieArtifact> die = room.takeDie();
        if (die.isPresent()) {
            myself.take(die.get());
            return;
        }

        Optional<Character> creature = room.getRandomCreature();
        if (creature.isPresent()) {
            myself.fight(creature.get());
            return;
        }

        Optional<Food> food = room.takeFood();
        if (food.isPresent()) {
            myself.eat(food.get());
            return;
        }

        myself.move(room);
    }

    @Override
    public boolean isFighter() {
        return true;
    }
}