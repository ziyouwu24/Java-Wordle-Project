package polymorphia.stepdefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import polymorphia.EventBus;
import polymorphia.Polymorphia;
import polymorphia.maze.Room;
import polymorphia.observers.GameEvent;
import polymorphia.observers.TestObserver;
import polymorphia.characters.Character;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolymorphiaStepDefs {
    private static Logger logger = LoggerFactory.getLogger(PolymorphiaStepDefs.class);

    MazeStepDefs mazeStepDefs;
    CharacterStepDefs characterStepDefs;
    Polymorphia game;
    TestObserver testObserver;

    public PolymorphiaStepDefs(CharacterStepDefs characterStepDefs, MazeStepDefs mazeStepDefs) {
        this.mazeStepDefs = mazeStepDefs;
        this.characterStepDefs = characterStepDefs;
    }

    @When("the game is played in the created maze")
    public void theGameIsPlayedInTheCreatedMaze() {
        getGame().play();
    }

    Polymorphia getGame() {
        if (game == null) {
            game = new Polymorphia(mazeStepDefs.getMaze());
        }
        if (testObserver == null) {
            testObserver = new TestObserver();
            EventBus.getInstance().subscribe(GameEvent.FIGHT, testObserver);
            EventBus.getInstance().subscribe(GameEvent.DEATH, testObserver);
        }
        return game;
    }

    @Then("all the adventurers or all of the creatures have died")
    public void allTheAdventurersOrAllOfTheCreaturesHaveDied() {
        assertTrue(!getGame().hasLivingAdventurers() || !getGame().hasLivingCreatures());
    }

    @Then("the game is over")
    public void theGameIsOver() {
        assertTrue(getGame().isOver());
    }

    @Then("a fight occured")
    public void aFightOccured() {
        assertTrue(testObserver.fightOccured());
    }

    @Then("{int} deaths occured")
    public void deathsHaveOccured(int deaths) {
        assertTrue(testObserver.totalDeaths() == deaths);
    }

    @When("{word} plays a turn")
    public void characterPlaysATurn(String characterName) {
        // Make sure the maze is built and game created before the character plays a turn
        getGame();
        Character character = characterStepDefs.get(characterName);
        Room currentRoom = mazeStepDefs.getMaze().getRoomContaining(character);
        logger.info("Character {} is doing their turn in room {}", character.getName(), currentRoom.getName());
        character.doAction(currentRoom);
    }

}
