package polymorphia.strategy;

import polymorphia.artifacts.Food;
import polymorphia.artifacts.TreasureMap;
import polymorphia.characters.Character;
import polymorphia.maze.Room;

import java.util.Optional;

public class CowardStrategy extends Strategy {

    @Override
    public void doAction(Character myself, Room room) {
        if (room.hasLivingCreatures()) {
            myself.move(room);
            return;
        }
        Optional<Food> food = room.takeFood();
        if (food.isPresent()) {
            myself.eat(food.get());
            return;
        }
        Optional<TreasureMap> treasureMap = room.takeTreasureMap();
        if (treasureMap.isPresent()) {
            myself.take(treasureMap.get());
            return;
        }
        myself.move(room);
    }

    @Override
    public boolean isCoward() {
        return true;
    }

}
