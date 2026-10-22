package polymorphia;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import polymorphia.artifacts.ArtifactFactory;
import polymorphia.characters.CharacterFactory;
import polymorphia.maze.Maze;
import polymorphia.maze.RoomFactory;
import polymorphia.observers.AudibleObserver;
import polymorphia.observers.GameEvent;
import polymorphia.strategy.StrategyFactory;

public class AudibleObserverTest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(PolymorphiaTest.class);
    ArtifactFactory artifactFactory = new ArtifactFactory();
    RoomFactory roomFactory = new RoomFactory();
    CharacterFactory characterFactory = new CharacterFactory(new StrategyFactory());

    @Disabled 
    @Test
    public void testGame() {
        Maze maze = Maze.getNewBuilder(roomFactory)
                .create2x2Grid()
                .addCharacters(characterFactory.createKnights(2))
                .add(characterFactory.createCreature("Ogre"))
                .build();
        Polymorphia game = new ObservablePolymorphia(maze);
        AudibleObserver observer = new AudibleObserver();
        EventBus.getInstance().subscribe(GameEvent.GAME_ENDED, observer);
        EventBus.getInstance().subscribe(GameEvent.FIGHT, observer);
        EventBus.getInstance().subscribe(GameEvent.DEATH, observer);
        game.play();
    }
}