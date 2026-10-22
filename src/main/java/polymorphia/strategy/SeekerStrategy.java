package polymorphia.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import polymorphia.artifacts.Food;
import polymorphia.artifacts.Treasure;
import polymorphia.characters.Character;
import polymorphia.maze.Room;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SeekerStrategy extends Strategy {
    private static final Logger logger =
            LoggerFactory.getLogger(SeekerStrategy.class);

    private static final double EATING_THRESHOLD = 2.5;

    private final Set<Room> visitedRooms = new HashSet<>();

    @Override
    public void doAction(Character myself, Room room) {
        visitedRooms.add(room);

        if (myself.getHealth() < EATING_THRESHOLD) {
            Optional<Food> food = room.takeFood();
            if (food.isPresent()) {
                myself.eat(food.get());
                logger.info("{} ate {} in room {}",
                        myself.getName(), food.get().getName(), room.getName());
                return;
            }
        }

        logger.info("{} looking for treasure in room {}",
                myself.getName(), room.getName());

        if (!room.hasTreasure()) {
            selectNewRoom(room).ifPresent(
                    nextRoom -> myself.move(nextRoom, room));
            return;
        }

        Optional<Character> guard = room.getRandomCreature();
        if (guard.isPresent()) {
            myself.fight(guard.get());
            return;
        }

        Optional<Treasure> treasure = room.takeTreasure();
        if (treasure.isPresent()) {
            myself.take(treasure.get());
            logger.info("{} found the treasure in room {}!",
                    myself.getName(), room.getName());
        }
    }

    private Optional<Room> selectNewRoom(Room room) {
        for (Room candidate : room.getNeighbors()) {
            if (!visitedRooms.contains(candidate)) {
                return Optional.of(candidate);
            }
        }

        if (room.getNeighbors().isEmpty()) {
            return Optional.empty();
        }

        return room.getRandomNeighbor();
    }
}