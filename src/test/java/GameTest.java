import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import wordle.Game;
import wordle.boards.BoardFactory;
import wordle.observers.EventBus;
import wordle.observers.TestObserver;

public class GameTest {
    private BoardFactory boardFactory;

    public GameTest() {
        this.boardFactory = new BoardFactory();
    }

    @Test 
    void testMaxGuesses() {
        TestObserver testObserver = new TestObserver();
        EventBus.getInstance().register(testObserver);

        Game game = Game.getBuilder(boardFactory)
            .setTotalGuesses(1)
            .build();
        
        game.submitGuess("guess");
        game.submitGuess("guess");

        assertTrue(testObserver.guessesMaxed());

        EventBus.getInstance().deregister(testObserver);
    }

    @Test 
    void testValidGuess() {
        TestObserver testObserver = new TestObserver();
        EventBus.getInstance().register(testObserver);

        Game game = Game.getBuilder(boardFactory)
            .setLettersPerWord(2)
            .build();
        
        game.submitGuess("aa");

        assertEquals(testObserver.validGuesses(), 1);

        EventBus.getInstance().deregister(testObserver);
    }

    @Test 
    void testWrongAmountOfLetters() {
        TestObserver testObserver = new TestObserver();
        EventBus.getInstance().register(testObserver);

        Game game = Game.getBuilder(boardFactory)
            .setLettersPerWord(2)
            .build();
        
        game.submitGuess("aaa");

        assertEquals(testObserver.invalidGuesses(), 1);

        EventBus.getInstance().deregister(testObserver);
    }

    @Test 
    void testNullGuess() {
        TestObserver testObserver = new TestObserver();
        EventBus.getInstance().register(testObserver);

        Game game = Game.getBuilder(boardFactory)
            .build();
        
        game.submitGuess(null);

        assertEquals(testObserver.invalidGuesses(), 1);

        EventBus.getInstance().deregister(testObserver);
    }

    @Test 
    void testSymbolsAndNumbers() {
        TestObserver testObserver = new TestObserver();
        EventBus.getInstance().register(testObserver);

        Game game = Game.getBuilder(boardFactory)
            .setLettersPerWord(5)
            .build();
        
        game.submitGuess("5%02a");

        assertEquals(testObserver.invalidGuesses(), 1);

        EventBus.getInstance().deregister(testObserver);
    }
}
