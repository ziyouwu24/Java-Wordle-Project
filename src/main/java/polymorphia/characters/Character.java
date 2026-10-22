package polymorphia.characters;

import org.slf4j.Logger;
import polymorphia.Die;
import polymorphia.EventBus;
import polymorphia.RandomDie;
import polymorphia.artifacts.Treasure;
import polymorphia.artifacts.TreasureMap;
import polymorphia.maze.Room;
import polymorphia.observers.GameEvent;
import polymorphia.artifacts.Food;
import polymorphia.strategy.*;
import polymorphia.artifacts.DieArtifact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

abstract public class Character {
    public static Double COWARD_THRESHOLD = 4.0;

    public static final Double HEALTH_LOST_IN_AGING_EACH_TURN = 0.05;
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Character.class);

    static final Double HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME = 0.5;
    public static final Double HEALTH_LOST_IN_MOVING_ROOMS = 0.25;

    static StrategyFactory strategyFactory = new StrategyFactory();

    protected String name;
    private Double health;
    private Die die;
    private IStrategy strategy;
    private boolean hasTreasure = false;

    public Character(String name, Double health) {
        this(name, health, RandomDie.sixSided());
    }

    public Character(String name, Double health, Die die) {
        this(name, health, die, new SleepStrategy());
    }

    public Character(String name, Double initialHealth, Die die, IStrategy strategy) {
        this.name = name;
        this.health = initialHealth;
        this.die = die;
        this.strategy = strategy;
    }

    public String toString() {
        return String.format("%s(health: %.2f, %s)", getName(), health, die);
    }

    void loseHealth(Double healthPoints) {
        if (health <= 0) {
            return;
        }     // already dead, probably called for mandatory health loss for having a fight

        health -= healthPoints;
        if (health <= 0) {
            List<String> info = new ArrayList<>(Arrays.asList(this.getName()));

            logger.info(name + " just died!");
            EventBus.getInstance().publish(GameEvent.DEATH, info);
        }

        if (isAlive() && isFighter() && health < COWARD_THRESHOLD) {
            setStrategy(strategyFactory.newCowardStrategy());
            logger.info(name + " is now a coward!");
        }
    }

    boolean isFighter() {
        return getStrategy().isFighter();
    }

    private IStrategy getStrategy() {
        return strategy;
    }

    private void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public Double getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public Boolean isAlive() {
        return getHealth() > 0;
    }

    void loseFightDamage(double fightDamage) {
        loseHealth(fightDamage);
    }

    public Boolean isAdventurer() {
        return false;
    }

    public Boolean isCreature() {
        return false;
    }

    public Boolean isGuard() {
        return false;
    }

    public void fight(Character opponent) {
        Integer adventurerRoll = getRoll();
        Integer opponentRoll = opponent.getRoll();
        List<String> info = new ArrayList<>(Arrays.asList(this.getName(), opponent.getName()));

        logger.info(getName() + " is fighting " + opponent);
        EventBus.getInstance().publish(GameEvent.FIGHT, info);

        logger.info(getName() + " rolled " + adventurerRoll);
        logger.info(opponent + " rolled " + opponentRoll);

        if (adventurerRoll > opponentRoll) {
            opponent.loseFightDamage(adventurerRoll - opponentRoll);
        } else if (opponentRoll > adventurerRoll) {
            loseFightDamage(opponentRoll - adventurerRoll);
        }

        loseHealth(Character.HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME);
        opponent.loseHealth(Character.HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME);

        // If I died and my opponent was a coward, change their strategy
        if (isDead() && opponent.isGlutton() && opponent.isAlive()) {
            opponent.setStrategy(strategyFactory.newFighterStrategy());
            logger.info(opponent.getName() + " is no longer a coward, but a fighter");
        }
    }

    private boolean isGlutton() {
        return getStrategy().isGlutton();
    }

    public boolean isDead() {
        return !isAlive();
    }

    private boolean isCoward() {
        return getStrategy().isCoward();
    }

    protected int getRoll() {
        return die.roll();
    }

    public final void doAction(Room currentLocation) {
        if (isAlive()) {
            doLivingAction(currentLocation);
        }
        loseHealth(Character.HEALTH_LOST_IN_AGING_EACH_TURN);
    }

    private void doLivingAction(Room currentLocation) {
        this.strategy.doAction(this, currentLocation);
    }

    public void move(Room currentRoom) {
        Optional<Room> nextRoom = currentRoom.getRandomNeighbor();
        if (nextRoom.isPresent()) {
            move(nextRoom.get(), currentRoom);
        }
    }

    public void move(Room nextLocation, Room currentLocation) {
        logger.info(getName() + " moved from " + currentLocation.getName() + " to " + nextLocation.getName());
        nextLocation.enter(this, currentLocation);
        loseHealth(HEALTH_LOST_IN_MOVING_ROOMS);
    }

    public void eat(Food food) {
        this.health += food.getHealthValue();
    }

    public boolean hasTreasure() {
        return hasTreasure;
    }

    public void take(Treasure treasure) {
        this.hasTreasure = true;
    }

    public void take(TreasureMap treasureMap) {
        if (isCoward()) {
            setStrategy(strategyFactory.newSeekerStrategy());
        }
    }

    public void take(DieArtifact die) {
        this.die = die;
    }
}
