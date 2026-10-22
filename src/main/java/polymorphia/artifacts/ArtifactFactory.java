package polymorphia.artifacts;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ArtifactFactory {
    private static final Random random = new Random();

    private static final double MINIMUM_FOOD_VALUE = 1.0;
    private static final double MAXIMUM_FOOD_VALUE = 2.0;

    private static final String[] FOOD_NAMES = new String[]{
            "cupcake", "apple", "banana", "steak", "salad", "fries", "burger", "pizza", "eggs",
            "bacon", "muffin", "donut", "chicken", "pasta", "rice", "sushi", "taco", "burrito",
            "nachos", "chips"};

    private static final String[] TREASURE_NAMES = new String[]{
            "gold", "silver", "bronze", "diamond", "ruby", "emerald", "sapphire", "topaz", "opal",
            "amethyst", "quartz", "agate", "jade", "onyx", "pearl", "coral", "obsidian", "agate",
            "jasper", "lapis lazuli"};

    private static double getRandomFoodValue() {
        return random.nextDouble(MINIMUM_FOOD_VALUE, MAXIMUM_FOOD_VALUE);
    }

    private static String getRandomFoodName() {
        return FOOD_NAMES[random.nextInt(FOOD_NAMES.length)];
    }

    private static String getRandomTreasureName() {
        return TREASURE_NAMES[random.nextInt(TREASURE_NAMES.length)];
    }

    public Artifact createFood(String name, double healthValue) {
        return new Food(name, healthValue);
    }

    public Artifact createFood(String name) {
        return createFood(name, getRandomFoodValue());
    }

    public Artifact createTreasure(String name) {
        return new Treasure(name);
    }

    public Artifact createTreasure() {
        return createTreasure(getRandomTreasureName());
    }

    public Artifact createFood() {
        return createFood(getRandomFoodName(), getRandomFoodValue());
    }

    public List<Artifact> createFood(int numberOfItems) {
        return IntStream.range(0, numberOfItems)
                .mapToObj(_ -> createFood())
                .toList();
    }

    public List<Artifact> createTreasures(int value) {
        return IntStream.range(0, value)
                .mapToObj(_ -> createTreasure())
                .toList();
    }

    public TreasureMap createTreasureMap(String name) {
        return new TreasureMap(name);
    }

    public DieArtifact createDie(String name, int sides) {
        return new DieArtifact(name, sides);
    }
}
