package polymorphia.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import polymorphia.artifacts.Artifact;
import polymorphia.artifacts.ArtifactFactory;
import polymorphia.characters.Character;
import polymorphia.characters.CharacterFactory;
import polymorphia.maze.Maze;
import polymorphia.maze.Room;
import polymorphia.maze.RoomFactory;
import polymorphia.strategy.StrategyFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


public class MazeStepDefs {
    private static final int DEFAULT_NUMBER_OF_ROOMS = 5;
    private static final int DEFAULT_MIN_CONNECTIONS = 2;
    private static final String BIDIRECTIONAL = "2-way";
    private final CharacterStepDefs characterStepDefs;
    private final ArtifactFactory artifactFactory = new ArtifactFactory();
    private final Map<String, Artifact> artifacts = new HashMap<>();
    CharacterFactory characterFactory = new CharacterFactory(new StrategyFactory());
    RoomFactory roomFactory = new RoomFactory();
    Maze.Builder mazeBuilder = Maze.getNewBuilder(roomFactory);
    private Maze maze;


    public MazeStepDefs(CharacterStepDefs characterStepDefs) {
        this.characterStepDefs = characterStepDefs;
    }

    @Given("a maze with the following attributes:")
    public void aMazeWithTheFollowingAttributes(Map<String, Integer> mazeArgs) {
        int numRooms = DEFAULT_NUMBER_OF_ROOMS;
        int numConnections = DEFAULT_MIN_CONNECTIONS;
        for (Map.Entry<String, Integer> entry : mazeArgs.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            switch (key) {
                case "number of rooms":
                    numRooms = value;
                    break;
                case "number of connections":
                    numConnections = value;
                    break;
            }
        }

        mazeBuilder.createConnectedRooms(numConnections, numRooms);
        addToRooms(mazeArgs);
    }

    @And("the maze has the following attributes:")
    public void addToRooms(Map<String, Integer> mazeArgs) {
        for (Map.Entry<String, Integer> entry : mazeArgs.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            switch (key) {
                case "number of food items":
                    mazeBuilder.addFood(artifactFactory.createFood(value));
                    break;
                case "number of treasures":
                    addTreasuresToMaze(value);
                    break;
                case "number of knights":
                    mazeBuilder.addCharacters(characterFactory.createKnights(value));
                    break;
                case "number of gluttons":
                    mazeBuilder.addCharacters(characterFactory.createGluttons(value));
                    break;
                case "number of cowards":
                    mazeBuilder.addCharacters(characterFactory.createCowards(value));
                    break;
                case "number of creatures":
                    mazeBuilder.addCharacters(characterFactory.createCreatures(value));
                    break;
                case "number of demons":
                    mazeBuilder.addCharacters(characterFactory.createDemons(value));
                    break;
            }
        }
    }

    private void addTreasuresToMaze(int value) {
        List<Artifact> treasures = artifactFactory.createTreasures(value);
        for (Artifact treasure : treasures) {
            artifacts.put(treasure.getName(), treasure);
            Room randomRoom = mazeBuilder.getRandomRoom();
            randomRoom.add(treasure);
            Character guard = characterStepDefs.createCharacter("guard", "Guard of " + treasure.getName());
            randomRoom.add(guard);
        }
    }

    @And("^a (knight|glutton|coward|creature|demon|seeker|guard) named (.+) in the (\\w+) room$")
    public void createCharacterAndPutInRoom(String characterType, String characterName, String roomName) {
        Character character = characterStepDefs.createCharacter(characterType, characterName);
        Room room = mazeBuilder.getRoom(roomName);
        room.add(character);
    }

    @Then("{word} room contains {word}")
    public void roomContainsCharacter(String roomName, String characterName) {
        Room room = getMaze().getRoom(roomName);
        Character character = characterStepDefs.get(characterName);
        assertTrue(room.contains(character));
    }


    @Given("a fully-connected maze with the following rooms:")
    public void aFullyConnectedMazeWithTheFollowingRooms(List<String> roomNames) {
        mazeBuilder.createFullyConnectedRooms(roomNames);
    }

    @And("the {word} room contains the following food items:")
    public void roomContainsTheFollowingFoodItems(String roomName, List<Map<String, String>> foodDescriptions) {
        Room room = mazeBuilder.getRoom(roomName);
        for (Map<String, String> foodDescription : foodDescriptions) {
            Artifact food = artifactFactory.createFood(
                    foodDescription.get("Name"),
                    Double.parseDouble(foodDescription.get("Value")));
            artifacts.put(food.getName(), food);
            room.add(food);
        }
    }

    @Then("^the (.+) room no longer contains (.+)$")
    public void roomNoLongerContainsArtifact(String roomName, String artifactName) {
        Room room = getMaze().getRoom(roomName);
        assertFalse(room.contains(artifacts.get(artifactName)));
    }

    @And("^(.+) is now(| not) in the (.+) room$")
    public void characterIsInRoom(String characterName, String not, String roomName) {
        Room room = getMaze().getRoom(roomName);
        Character character = characterStepDefs.get(characterName);
        if (not.equals(" not")) {
            assertFalse(room.contains(character));
        } else {
            assertTrue(room.contains(character));
        }
    }


    @Then("{word} is still in the {word} room")
    public void characterIsStillInTheRoom(String characterName, String roomName) {
        characterIsInRoom(characterName, "", roomName);
    }

    public void roomHasNoFoodItems(String roomName, int expectedNumberOfFoodItems) {
        Room room = getMaze().getRoom(roomName);
        assertEquals(expectedNumberOfFoodItems, room.getFoodItems().size());
    }

    @Given("^a (unidirectional|bidirectional), (randomly|sequentially)-distributed maze with the following attributes:$")
    public void connectedDistributedMazeWithTheFollowingAttributes(String roomConnectionType, String distributionType, Map<String, Integer> mazeArgs) {
        mazeBuilder.setConnectionDirection(Objects.equals(roomConnectionType, "bidirectional"));
        if (Objects.equals(distributionType, "randomly")) {
            mazeBuilder.distributeRandomly();
        } else {
            mazeBuilder.distributeSequentially();
        }
        aMazeWithTheFollowingAttributes(mazeArgs);
    }

    @Given("a maze with the following rooms:")
    public void aMazeWithTheFollowingRooms(List<String> roomNames) {
        mazeBuilder.createRooms(roomNames);
    }

    @Given("a maze with the following rooms and neighbors:")
    public void aMazeWithTheFollowingFooms(List<Map<String, String>> roomNeighbors) {
        for (Map<String, String> roomKeyedNeighbors : roomNeighbors) {
            String roomName = roomKeyedNeighbors.get("Room");
            mazeBuilder.addRoomIfNotAlreadyInMaze(roomName);
            String[] neighbors = roomKeyedNeighbors.get("Neighbors").split(",");
            for (String neighborName : neighbors) {
                mazeBuilder.addRoomIfNotAlreadyInMaze(neighborName.trim());
                mazeBuilder.addNeighborToRoom(roomName, neighborName.trim());
            }
        }
    }

    @And("each room is connected to {int} other rooms via {word} connections")
    public void eachRoomIsConnectedToOtherRoomsViaConnections(int numberOfConnections, String connectionType) {
        mazeBuilder.setConnectionDirection(Objects.equals(connectionType, BIDIRECTIONAL));
        mazeBuilder.connectRooms(numberOfConnections);
    }

    @And("{word} is in the {word} room")
    public void characterIsInTheRoom(String characterName, String roomName) {
        mazeBuilder.addToRoom(roomName, characterStepDefs.get(characterName));
    }

    @And("food {word} is in the {word} room")
    public void foodIsInTheRoom(String foodName, String roomName) {
        mazeBuilder.addToRoom(roomName, artifactFactory.createFood(foodName));
    }

    Maze getMaze() {
        if (maze == null) {
            try {
                maze = mazeBuilder.build();
            } catch (IllegalStateException e) {
                maze = mazeBuilder
                        .add(characterFactory.createKnight("AutoGeneratedKnight")) // Add a default knight to satisfy the validation
                        .build();
            }
        }
        return maze;
    }

    @And("{word} room does not contain {word}")
    public void roomDoesNotContainCharacter(String roomName, String characterName) {
        Room room = getMaze().getRoom(roomName);
        Character character = characterStepDefs.get(characterName);
        assertFalse(room.contains(character));
    }

    @And("the {word} room has {int} food items")
    public void theRoomHasFoodItems(String roomName, int expectedNumberOfFoodItems) {
        Room room = getMaze().getRoom(roomName);
        assertEquals(expectedNumberOfFoodItems, room.getFoodItems().size());
    }

    @And("^a treasure is hidden in (\\w+) room$")
    public void treasureIsHiddenInRoom(String roomName) {
        Room room = mazeBuilder.getRoom(roomName);
        room.add(artifactFactory.createTreasure());
    }

    @And("^the (.+) room contains the following artifacts items:$")
    public void theOtherRoomContainsTheFollowingArtifactsItems(String roomName, List<Map<String, String>> artifactDescriptions) {
        Room room = getMaze().getRoom(roomName);
        for (Map<String, String> artifactDescription : artifactDescriptions) {
            String name = artifactDescription.get("Name");
            double foodValue = Double.parseDouble(artifactDescription.get("Value"));
            switch (artifactDescription.get("Type")) {
                case "Treasure":
                    room.add(artifactFactory.createTreasure(name));
                    break;
                case "Food":
                    room.add(artifactFactory.createFood(name, foodValue));
                    break;
                case "Treasure Map":
                    room.add(artifactFactory.createTreasureMap(name));
                    break;
            }
        }
    }
    @And("a treasure map is hidden in {word} room")
    public void treasureMapIsHiddenInRoom(String roomName) {
        mazeBuilder.addToRoom(
                roomName,
                artifactFactory.createTreasureMap("TreasureMap"));
    }
}