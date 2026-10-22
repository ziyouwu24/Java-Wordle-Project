package polymorphia;

import org.slf4j.Logger;
import polymorphia.characters.Character;
import polymorphia.maze.Maze;
import polymorphia.maze.Room;

import java.util.List;

public class Polymorphia {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Polymorphia.class);

    Maze maze;
    Integer turnCount = 0;

    public Polymorphia(Maze maze) {
        this.maze = maze;
    }

    public String toString() {
        return "Polymorphia MAZE: turn " + turnCount + "\n" + maze.toString();
    }

    public Boolean isOver() {
        return !hasLivingCreatures() || !hasLivingAdventurers() || treasureFound();
    }

    private boolean treasureFound() {
        return getLivingCharacters().stream().anyMatch(Character::hasTreasure);
    }

    public Boolean hasLivingCreatures() {
        return maze.hasLivingCreatures();
    }

    public Boolean hasLivingAdventurers() {
        return maze.hasLivingAdventurers();
    }

    protected void playTurn() {
        for (Room room : maze.getRooms()) {
            for (Character character : room.getLivingCharacters()) {
                character.doAction(room);
            }
        }
    }

    private List<Character> getLivingCharacters() {
        return maze.getLivingCharacters();
    }


    public void play() {
        logger.info("Starting play...\n");
        while (!isOver()) {
            logger.info(this.toString());
            playTurn();
            turnCount++;
        }
        logger.info("The game ended after {} turns.\n", turnCount);
        String results = getFinalResults();
        logger.info(results);
    }

    protected String getFinalResults() {
        String eventDescription;
        if (hasLivingAdventurers()) {
            eventDescription = "The adventurers won! Left standing are: " + getAdventurerNames() + "\n";
        } else if (hasLivingCreatures()) {
            eventDescription = "The creatures won. Boo! Left standing are: " + getCreatureNames() + "\n";
        } else {
            eventDescription = "No team won! Everyone died!\n";
        }
        return eventDescription;
    }

    private String getAdventurerNames() {
        return String.join(", ", getLivingCharacters().stream().map(Object::toString).toList());
    }

    private String getCreatureNames() {
        return String.join(", ", getLivingCreatures().stream().map(Object::toString).toList());
    }

    private List<Character> getLivingCreatures() {
        return maze.getLivingCreatures();
    }

    public Character getWinner() {
        if (!isOver() || !hasLivingCharacters()) {
            // No one has won yet, or no one won -- all died
            return null;
        }
        return getLivingCharacters().getFirst();
    }

    private boolean hasLivingCharacters() {
        return maze.hasLivingCharacters();
    }
}
