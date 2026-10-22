package polymorphia.strategy;

public class StrategyFactory {
    public IStrategy newCowardStrategy() {
        return new CowardStrategy();
    }

    public IStrategy newGluttonStrategy() {
        return new GluttonStrategy();
    }

    public IStrategy newSeekerStrategy() {
        return new SeekerStrategy();
    }

    public IStrategy newFighterStrategy() {
        return new FighterStrategy();
    }

    public IStrategy newCreatureStrategy() {
        return new CreatureStrategy();
    }

    public IStrategy newGuardStrategy() {
        return new GuardStrategy();
    }
}
