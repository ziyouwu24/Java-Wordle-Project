package wordle.boards;

import java.util.ArrayList;
import java.util.List;

import wordle.Gamemode;

public abstract class Board {
    public static final int DEFAULT_TOTAL_GUESSES = 6;
    public static final int DEFAULT_LETTERS_PER_WORD = 5;
    public static final Gamemode DEFAULT_GAMEMODE = Gamemode.NORMAL;

    private final String targetWord;
    private final int totalGuesses;
    private final int lettersPerWord;
    private final List<String> guesses = new ArrayList<>();

    public Board(String correctWord, int totalGuesses, int lettersPerWord) {
        this.targetWord = correctWord;
        this.totalGuesses = totalGuesses;
        this.lettersPerWord = lettersPerWord;
    }

    public final void addGuess(String guess) {
        if(isValid(guess)) {
            guesses.add(guess); 
        }
    }

    public String display() {

        throw new UnsupportedOperationException("Display is not implemented yet.");
    }

    protected abstract boolean isValid(String guess);
}


