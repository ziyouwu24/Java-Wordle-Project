import org.junit.jupiter.api.Test;

import wordle.Game;
import wordle.Gamemode;
import wordle.boards.BoardFactory;

public class GameTest {
    @Test 
    void testGame() {
        BoardFactory boardFactory = new BoardFactory();

        Game game = Game.getBuilder(boardFactory)
            .setLettersPerWord(5)
            .setTotalGuesses(6)
            .setGamemode(Gamemode.NORMAL)
            .build();
        
        
    }
}
