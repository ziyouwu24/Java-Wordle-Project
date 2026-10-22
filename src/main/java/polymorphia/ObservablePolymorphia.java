package polymorphia;

import polymorphia.maze.Maze;
import polymorphia.observers.GameEvent;

public class ObservablePolymorphia extends Polymorphia{
    public ObservablePolymorphia(Maze maze) {
        super(maze);
    }

    @Override
    public void play() {
        logger.info("Starting play...\n");
        EventBus.getInstance().publish(GameEvent.GAME_STARTED, null);
        while (!isOver()) {
            logger.info(super.toString());
            super.playTurn();
            turnCount++;
        }
        logger.info("The game ended after {} turns.\n", turnCount);
        EventBus.getInstance().publish(GameEvent.GAME_ENDED, null);
        String results = super.getFinalResults();
        logger.info(results);
    }
}
