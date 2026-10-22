package wordle;

public class BoardFactory {
    public Board createBoard(int lettersPerWord, int totalGuesses, Gamemode gamemode) {
        return new Board(lettersPerWord, totalGuesses, gamemode);
    }
}
