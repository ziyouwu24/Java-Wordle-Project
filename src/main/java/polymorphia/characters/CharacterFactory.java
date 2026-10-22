package polymorphia.characters;

import polymorphia.Die;
import polymorphia.RandomDie;
import polymorphia.strategy.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;


public class CharacterFactory {
    private static final Random random = new Random();
    public static final Double DEFAULT_KNIGHT_HEALTH = 10.0;
    public static final Double DEFAULT_GUARD_HEALTH = 5.0;
    public static final Double DEFAULT_GLUTTON_HEALTH = 6.0;
    public static final Double DEFAULT_DEMON_HEALTH = 15.0;
    public static final Double DEFAULT_CREATURE_HEALTH = 5.0;
    public static final Double DEFAULT_COWARD_HEALTH = 8.0;
    public static final Double DEFAULT_SEEKER_HEALTH = 10.0;

    public static String[] KNIGHT_NAMES = new String[]{"Sir Lancelot", "Lady Brienne", "King Arthur", "Sir Jamey", "Aragorn", "Isildur"};
    public static String[] COWARD_NAMES = new String[]{"Sir Robin", "Sir Scaredy Cat", "Lady Faints-a-lot", "Lady Runaway", "Sir Chicken", "Lady Hides-a-lot"};
    public static String[] GLUTTON_NAMES = new String[]{"Sir Eats-a-lot", "Sir Gobbles", "Lady Munches", "Lady Snacks", "Sir Nibbles", "Lady Noshes"};
    public static String[] CREATURE_NAMES = new String[]{"Dragon", "Ogre", "Orc", "Shelob", "Troll", "Evil Wizard"};
    public static String[] DEMON_NAMES = new String[]{"Satan", "Beelzebub", "Devil", "Incubus", "Lucifer", "Succubus"};
    public static String[] SEEKER_NAMES = new String[]{"Seeker", "Finder", "Searcher"};
    public static String[] GUARD_NAMES = new String[]{"Guard", "Black Night", "Defender", "Night Watcher"};
    public static Map<String, String[]> NAMES = new HashMap<>();

    private StrategyFactory strategyFactory;

    static {
        NAMES.put("Knight", KNIGHT_NAMES);
        NAMES.put("Coward", COWARD_NAMES);
        NAMES.put("Glutton", GLUTTON_NAMES);
        NAMES.put("Creature", CREATURE_NAMES);
        NAMES.put("Demon", DEMON_NAMES);
        NAMES.put("Seeker", SEEKER_NAMES);
        NAMES.put("Guard", GUARD_NAMES);
    }

    public CharacterFactory(StrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public Character createKnight() {
        return createKnight(getRandomName("Knight"), DEFAULT_KNIGHT_HEALTH);
    }

    public Character createKnight(String name) {
        return createKnight(name, DEFAULT_KNIGHT_HEALTH);
    }

    public Character createKnight(String name, double initialHealth) {
        return new Adventurer(name, initialHealth, RandomDie.sixSided(), strategyFactory.newFighterStrategy());
    }

    public Character createKnight(String name, double initialHealth, Die die) {
        return new Adventurer(name, initialHealth, die, strategyFactory.newFighterStrategy());
    }

    public Character createGlutton(String name) {
        return new Adventurer(name, DEFAULT_GLUTTON_HEALTH, RandomDie.sixSided(),strategyFactory.newGluttonStrategy());
    }

    public Character createGlutton(String name, double health, Die die) {
        return new Adventurer(name, health, die, strategyFactory.newGluttonStrategy());
    }
    public Character createCoward(String name, double health, Die die) {
        return new Adventurer(name, health, die, strategyFactory.newCowardStrategy());
    }
    public Character createCreature(String name, double health, Die die) {
        return new Creature(name, health, die, strategyFactory.newCreatureStrategy());
    }
    public Character createSeeker(String name, double health, Die die) {
        return new Adventurer(name, health, die, strategyFactory.newSeekerStrategy());
    }
    public Character createGuard(String name, double health, Die die) {
        return new Creature(name, health, die, strategyFactory.newGuardStrategy());
    }

    public Character createCoward(String name) {
        return new Adventurer(name, DEFAULT_COWARD_HEALTH, RandomDie.sixSided(), strategyFactory.newCowardStrategy());
    }
    public Character createCreature(String name) {
        return createCreature(name, DEFAULT_CREATURE_HEALTH);
    }
    public Character createCreature(String name, double initialHealth) {
        return new Creature(name, initialHealth, RandomDie.sixSided(), strategyFactory.newCreatureStrategy());
    }

    public Character createDemon(String name) {
        return new Creature(name, DEFAULT_DEMON_HEALTH, RandomDie.sixSided(), strategyFactory.newCreatureStrategy());
    }

    public Character createSeeker(String name) {
        return new Adventurer(name, DEFAULT_SEEKER_HEALTH, RandomDie.sixSided(), strategyFactory.newSeekerStrategy());
    }

    public Character createGuard(String name) {
        return new Creature(name, DEFAULT_GUARD_HEALTH, RandomDie.sixSided(), strategyFactory.newGuardStrategy());
    }

    public List<Character> createKnights(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(_ -> createKnight())
                .toList();
    }

    public List<Character> createCowards(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(_ -> createCoward())
                .toList();
    }

    public List<Character> createGluttons(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(_ -> createGlutton())
                .toList();
    }

    public List<Character> createCreatures(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(_ -> createCreature())
                .toList();
    }

    public List<Character> createDemons(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(_ -> createDemon())
                .toList();
    }

    private Character createCoward() {
        return createCoward(getRandomName("Coward"));
    }

    private Character createSeeker() {
        return createSeeker(getRandomName("Seeker"));
    }

    private Character createGuard() {
        return createGuard(getRandomName("Guard"));
    }

    private Character createGlutton() {
        return createGlutton(getRandomName("Glutton"));
    }

    private Character createCreature() {
        return createCreature(getRandomName("Creature"));
    }

    private Character createDemon() {
        return createDemon(getRandomName("Demon"));
    }

    private String getRandomName(String characterType) {
        String[] names = NAMES.get(characterType);
        return names[random.nextInt(names.length)];
    }
}
