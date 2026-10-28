import org.junit.jupiter.api.Test;

import wordle.BoardFactory;
import wordle.Game;
import wordle.Gamemode;
import wordle.GuessFactory;

public class GameTest {
    @Test 
    void testGame() {
        BoardFactory boardFactory = new BoardFactory();
        GuessFactory guessFactory = new GuessFactory();

        Game game = Game.getBuilder(boardFactory, guessFactory)
            .setLettersPerWord(5)
            .setTotalGuesses(6)
            .setGamemode(Gamemode.NORMAL)
            .build();
        
            
    }
}
