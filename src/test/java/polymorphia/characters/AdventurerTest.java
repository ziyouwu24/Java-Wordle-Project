package polymorphia.characters;

import org.junit.jupiter.api.Test;
import polymorphia.strategy.StrategyFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static polymorphia.characters.CharacterFactory.DEFAULT_KNIGHT_HEALTH;

class AdventurerTest {

    CharacterFactory characterFactory = new CharacterFactory(new StrategyFactory());

    @Test
    public void testConstructor() {
        Character hero = characterFactory.createKnight("Hero");
        assertEquals("Hero", hero.getName());
        assertEquals(DEFAULT_KNIGHT_HEALTH, hero.getHealth());
    }

}