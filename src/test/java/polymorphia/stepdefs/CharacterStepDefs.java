package polymorphia.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import polymorphia.Die;
import polymorphia.FixedDie;
import polymorphia.RandomDie;
import polymorphia.characters.Character;
import polymorphia.characters.CharacterFactory;
import polymorphia.strategy.StrategyFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterStepDefs {
    CharacterFactory characterFactory = new CharacterFactory(new StrategyFactory());

    Map<String, Character> characters = new HashMap<>();
    Map<String, Double> initialHealth = new HashMap<>();

    void putCharacter(Character character) {
        characters.put(character.getName(), character);
        initialHealth.put(character.getName(), character.getHealth());
    }

    Character getCharacter(String characterName) {
        return characters.get(characterName);
    }

    public double getInitialHealth(String characterName) {
        return initialHealth.get(characterName);
    }

    Character createCharacter(String characterType, String characterName) {
        Character character = null;
        switch (characterType) {
            case "knight":
                character = characterFactory.createKnight(characterName);
                break;
            case "glutton":
                character = characterFactory.createGlutton(characterName);
                break;
            case "coward":
                character = characterFactory.createCoward(characterName);
                break;
            case "creature":
                character = characterFactory.createCreature(characterName);
                break;
            case "demon":
                character = characterFactory.createDemon(characterName);
                break;
            case "seeker":
                character = characterFactory.createSeeker(characterName);
                break;
            case "guard":
                character = characterFactory.createGuard(characterName);
                break;
            default:
                System.out.println("Error -- shouldn't get here. Character type: " + characterType);
        }
        putCharacter(character);
        assert character != null;
        initialHealth.put(characterName, character.getHealth());
        return character;
    }

    @Then("^(\\w+) has health (.+)$")
    public void hasHealth(String name, double expectedHealth) {
        Character character = characters.get(name);
        System.out.println(character.getName() + " has health " + character.getHealth());
        System.out.println("Expected health: " + expectedHealth);
        assertEquals(expectedHealth, character.getHealth());
    }

    @Given("^a (knight|glutton|coward|creature|demon|seeker|guard) (\\w+) with health (\\d{1,2}\\.?\\d{0,2})$")
    public Character createCharacterWithHealth(String characterType, String characterName, double health) {
        Character character = createCharacter(characterType, characterName, health, 0);
        characters.put(characterName, character);
        return character;
    }

    @Given("^a (knight|glutton|coward|creature|demon|seeker|guard) (\\w+) with health (\\d{1,2}\\.?\\d{0,2}) and a fixed die roll of (\\d{1,2})$")
    public Character createCharacter(String characterType, String characterName, double health, int rollValue) {
        Die die = new FixedDie(rollValue);
        if (rollValue < 1) {
            die = RandomDie.sixSided();
        }
        Character character = null;
        switch (characterType) {
            case "knight":
                character = characterFactory.createKnight(characterName, health, die);
                break;
            case "glutton":
                character = characterFactory.createGlutton(characterName, health, die);
                break;
            case "coward":
                character = characterFactory.createCoward(characterName, health, die);
                break;
            case "creature":
            case "demon":
                character = characterFactory.createCreature(characterName, health, die);
                break;
            case "seeker":
                character = characterFactory.createSeeker(characterName, health, die);
                break;
            case "guard":
                character = characterFactory.createGuard(characterName, health, die);
                break;
            default:
                System.out.println("Error -- shouldn't get here");
        }
        characters.put(characterName, character);
        assert character != null;
        initialHealth.put(characterName, character.getHealth());
        return character;
    }

    @And("^a (knight|glutton|coward|creature|demon) named (^\\w+)$")
    public void aNamedCharacter(String characterType, String characterName) {
        createCharacter(characterType, characterName);
    }

    public List<Character> createCharacters(String characterType, int numberOfItems) {
        List<Character> charactersList = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            String characterName = characterType + (i + 1);
            Character character = createCharacter(characterType, characterName);
            charactersList.add(character);
        }
        return charactersList;
    }

    public Character get(String name) {
        if (characters.containsKey(name)) {
            return characters.get(name);
        }
        throw new IllegalArgumentException("Character with name " + name + " does not exist");
    }

    @And("{word} has gained {double} in health")
    public void characterHasGainedInHealth(String characterName, Double lostHealth) {
        Character character = characters.get(characterName);
        assertEquals(initialHealth.get(characterName) + lostHealth, character.getHealth());
    }

    @And("{word} did not lose some health")
    public void characterDidNotLoseSomeHealth(String characterName) {
        Character character = characters.get(characterName);
        assertEquals(initialHealth.get(characterName), character.getHealth());
    }

    @And("{word} lost some health")
    public void characterLostSomeHealth(String characterName) {
        Character character = characters.get(characterName);
        assertTrue(character.getHealth() < initialHealth.get(characterName));
    }

    @And("^a (knight|glutton|coward|creature|demon) named (\\w+)$")
    public void createNamedCharacter(String characterType, String characterName) {
        createCharacter(characterType, characterName);
    }

    @And("{word} is still alive")
    public void characterIsStillAlive(String characterName) {
        Character character = characters.get(characterName);
        assertTrue(character.isAlive());
    }

    @And("{word} has found the treasure")
    public void characterHasFoundTheTreasure(String characterName) {
        Character character = characters.get(characterName);
        assertTrue(character.hasTreasure());
    }

    @Then("{word} is now dead")
    public void characterIsDead(String characterName) {
        Character character = characters.get(characterName);
        assertFalse(character.isAlive());
    }

    @And("the Coward Threshold is {int}")
    public void theCowardThresholdIs(double threshold) {
        Character.COWARD_THRESHOLD = threshold;
    }

    @Then("{word} or {word} has found the treasure")
    public void eitherCharacterHasFoundTheTreasure(
            String firstName, String secondName) {
        assertTrue(
                get(firstName).hasTreasure() || get(secondName).hasTreasure(),
                "Neither adventurer found the treasure in this run");
    }

}
