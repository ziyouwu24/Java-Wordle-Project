package polymorphia.artifacts;

import polymorphia.Die;
import polymorphia.RandomDie;

public class DieArtifact extends Artifact implements Die {
    private final Die die;

    public DieArtifact(String name, int sides) {
        super(name);
        if (sides < 1) {
            throw new IllegalArgumentException(
                    "A die must have at least one side");
        }
        this.die = new RandomDie(sides);
    }

    @Override
    public int roll() {
        return die.roll();
    }
}