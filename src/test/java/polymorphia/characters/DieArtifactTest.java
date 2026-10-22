package polymorphia.characters;

import org.junit.jupiter.api.Test;
import polymorphia.artifacts.ArtifactFactory;
import polymorphia.artifacts.DieArtifact;
import polymorphia.maze.Maze;
import polymorphia.maze.Room;
import polymorphia.maze.RoomFactory;
import polymorphia.strategy.FighterStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DieArtifactTest {
    private final ArtifactFactory artifactFactory = new ArtifactFactory();

    @Test
    void fighterPicksUpDieAndUsesItInNextFight() {
        DieArtifact die = artifactFactory.createDie("One-sided die", 1);

        Character fighter = new Adventurer(
                "Fighter", 10.0, () -> 6, new FighterStrategy());

        Character creature = new Creature("Orc", 10.0, () -> 2);

        Maze maze = Maze.getNewBuilder(new RoomFactory())
                .createRooms(List.of("Starting"))
                .addToRoom("Starting", fighter)
                .addToRoom("Starting", creature)
                .addToRoom("Starting", die)
                .build();

        Room room = maze.getRoom("Starting");
        assertTrue(room.contains(die));

        fighter.doAction(room);

        assertFalse(room.contains(die));
        assertTrue(room.takeDie().isEmpty());
        assertEquals(10.0, creature.getHealth(), 0.0001);
        assertEquals(9.95, fighter.getHealth(), 0.0001);

        fighter.doAction(room);

        assertEquals(8.40, fighter.getHealth(), 0.0001);
        assertEquals(9.50, creature.getHealth(), 0.0001);
    }

    @Test
    void factoryCreatesDieWithRequestedRollRange() {
        DieArtifact die = artifactFactory.createDie("Twenty-sided die", 20);

        for (int rollNumber = 0; rollNumber < 100; rollNumber++) {
            int roll = die.roll();
            assertTrue(roll >= 1 && roll <= 20);
        }
    }

    @Test
    void factoryRejectsNonpositiveSideCounts() {
        assertThrows(IllegalArgumentException.class,
                () -> artifactFactory.createDie("Invalid", 0));

        assertThrows(IllegalArgumentException.class,
                () -> artifactFactory.createDie("Invalid", -1));
    }
}