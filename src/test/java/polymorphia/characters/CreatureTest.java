package polymorphia.characters;

import org.junit.jupiter.api.Test;
import polymorphia.RandomDie;
import polymorphia.strategy.StrategyFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatureTest {

    CharacterFactory characterFactory = new CharacterFactory(new StrategyFactory());

    @Test
    public void testConstructor() {
        Character hero = characterFactory.createCreature("Orc", CharacterFactory.DEFAULT_CREATURE_HEALTH);
        assertEquals("Orc", hero.getName());
        assertEquals(CharacterFactory.DEFAULT_CREATURE_HEALTH, hero.getHealth());
    }
}