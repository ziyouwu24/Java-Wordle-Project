
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wordle.Game;
import wordle.boards.BoardFactory;
import wordle.observers.EventBus;
import wordle.observers.TestObserver;

class GameTest {

    private final BoardFactory boardFactory = new BoardFactory();
    private final EventBus eventBus = EventBus.getInstance();

    private TestObserver testObserver;

    @BeforeEach
    void setUp() {
        testObserver = new TestObserver();
        eventBus.register(testObserver);
    }

    @AfterEach
    void tearDown() {
        eventBus.deregister(testObserver);
    }

    private Game createGame(int lettersPerWord, int totalGuesses) {
        return Game.getBuilder(boardFactory)
                .setLettersPerWord(lettersPerWord)
                .setTotalGuesses(totalGuesses)
                .build();
    }

    @Test
    void shouldNotifyWhenMaximumGuessesAreReached() {
        Game game = createGame(5, 1);

        game.submitGuess("guess");
        game.submitGuess("guess");

        assertTrue(testObserver.guessesMaxed());
    }

    @Test
    void shouldCountValidGuess() {
        Game game = createGame(2, 6);

        game.submitGuess("aa");

        assertEquals(1, testObserver.validGuesses());
    }

    @Test
    void shouldRejectGuessWithIncorrectLength() {
        Game game = createGame(2, 6);

        game.submitGuess("aaa");

        assertEquals(1, testObserver.invalidGuesses());
    }

    @Test
    void shouldRejectNullGuess() {
        Game game = createGame(5, 6);

        game.submitGuess(null);

        assertEquals(1, testObserver.invalidGuesses());
    }

    @Test
    void shouldRejectGuessContainingSymbolsAndNumbers() {
        Game game = createGame(5, 6);

        game.submitGuess("5%02a");

        assertEquals(1, testObserver.invalidGuesses());
    }
}
