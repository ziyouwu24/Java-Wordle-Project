package polymorphia.maze;

import org.slf4j.Logger;
import polymorphia.artifacts.*;
import polymorphia.characters.Character;

import java.util.*;

public class Room {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Room.class);
    static private final Random rand = new Random();

    private final String name;
    private final Set<Room> neighbors = new HashSet<>();
    private final Set<Character> characters = new HashSet<>();
    private final ArtifactBag artifactBag = new ArtifactBag();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Character> getLivingCreatures() {
        return getLivingCharacters().stream()
                .filter(Character::isCreature)
                .toList();
    }

    public List<Character> getLivingAdventurers() {
        return getLivingCharacters().stream()
                .filter(Character::isAdventurer)
                .toList();
    }

    public List<String> getContents() {
        List<String> characterStrings = new ArrayList<>(getLivingCharacters().stream()
                .map(Object::toString)
                .toList());

        for (Artifact artifact : artifactBag.allItems()) {
            characterStrings.add(artifact.toString());
        }

        return characterStrings;
    }

    public void addNeighbor(Room neighbor) {
        // Make sure we are never a neighbor of ourselves
        if (this == neighbor) {
            logger.warn("A room cannot be its own neighbor: " + name + ". Ignoring neighbor " + neighbor.getName());
            return;
        }
        this.neighbors.add(neighbor);
    }


    void addNeighbor(Room neighbor, boolean bidirectional) {
        this.addNeighbor(neighbor);
        if (bidirectional) {
            neighbor.addNeighbor(this);
        }
    }

    public String toString() {
        String representation = "\t" + name + ":\n\t\t";
        representation += String.join("\n\t\t", getContents());
        representation += "\n";
        return representation;
    }

    public void add(Character character) {
        characters.add(character);
    }

    public Boolean hasLivingCreatures() {
        return getLivingCharacters().stream().anyMatch(Character::isCreature);
    }

    public Boolean hasLivingAdventurers() {
        return getLivingCharacters().stream().anyMatch(Character::isAdventurer);
    }

    public void remove(Character character) {
        characters.remove(character);
    }

    public Optional<Character> getRandomCreature() {
        return getLivingCreatures().stream().findFirst();
    }

    public Optional<Character> getRandomAdventurer() {
        return getLivingAdventurers().stream().findFirst();
    }

    public Optional<Room> getRandomNeighbor() {
        if (neighbors.isEmpty()) {
            return Optional.empty();
        }

        List<Room> neighborList = new ArrayList<>(neighbors);
        return Optional.of(neighborList.get(rand.nextInt(neighborList.size())));
    }

    public Optional<DieArtifact> takeDie() {
        return artifactBag.removeDie();
    }

    public void enter(Character character, Room previousRoom) {
        previousRoom.remove(character);
        add(character);
    }

    public List<Character> getLivingCharacters() {
        return characters.stream()
                .filter(Character::isAlive)
                .toList();
    }

    public boolean hasNeighbor(Room neighbor) {
        return neighbors.contains(neighbor);
    }

    public int numberOfNeighbors() {
        return neighbors.size();
    }

    public boolean contains(Character character) {
        return characters.contains(character);
    }

    public boolean contains(Artifact artifact) {
        return artifactBag.contains(artifact);
    }

    public void add(Artifact artifact) {
        artifactBag.add(artifact);
    }

    public boolean hasFood() {
        return artifactBag.hasFood();
    }
    public boolean hasTreasure() {
        return artifactBag.hasTreasure();
    }

    public Optional<Food> takeFood() {
        return artifactBag.removeFoodItem();
    }

    public Optional<Treasure> takeTreasure() {
        return artifactBag.removeTreasureItem();
    }

    public Optional<Character> getCreature() {
        return getLivingCreatures().stream().findFirst();
    }

    public Set<Room> getNeighbors() {
        // Shuffling this order each time for more randomness in the game
        List<Room> shuffled = new ArrayList<>(neighbors);
        Collections.shuffle(shuffled);
        return new LinkedHashSet<>(shuffled);
    }

    public List<Food> getFoodItems() {
        return artifactBag.foodItems();
    }

    public Optional<Character> getRandomCharacterNotMe(Character myself) {
        return getLivingCharacters().stream()
                .filter(character -> !character.equals(myself))
                .findFirst();
    }

    public Optional<TreasureMap> takeTreasureMap() {
        return artifactBag.removeTreasureMap();
    }
}
