package polymorphia.characters;


import polymorphia.Die;
import polymorphia.maze.Room;
import polymorphia.strategy.IStrategy;

public class Adventurer extends Character {

    public Adventurer(String name, Double initialHealth, Die die, IStrategy strategy) {
        super(name, initialHealth, die, strategy);
    }

    @Override
    public Boolean isAdventurer() {
        return true;
    }
}