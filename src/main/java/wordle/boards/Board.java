package wordle.boards;

import java.util.ArrayList;
import java.util.List;

import wordle.Gamemode;
import wordle.observers.EventBus;
import wordle.observers.GameEvent;

public abstract class Board {
    public static final int DEFAULT_TOTAL_GUESSES = 6;
    public static final int DEFAULT_LETTERS_PER_WORD = 5;
    public static final Gamemode DEFAULT_GAMEMODE = Gamemode.NORMAL;

    private final String targetWord;
    private final int totalGuesses;
    private final int lettersPerWord;
    private final List<String> validWords;
    private final List<String> guesses = new ArrayList<>();

    public Board(String targetWord, List<String> validWords, int totalGuesses, int lettersPerWord) {
        this.targetWord = targetWord;
        this.validWords = validWords;
        this.totalGuesses = totalGuesses;
        this.lettersPerWord = lettersPerWord;
    }

    public final void addGuess(String guess) {
        if(guess == null) {
            EventBus.getInstance().newEvent(GameEvent.INVALID_GUESS);
            return;
        }

        if(guesses.size() >= totalGuesses) {
            EventBus.getInstance().newEvent(GameEvent.MAX_GUESSES);
            return;
        }

        String cleanedGuess = guess.toUpperCase();

        if(isValid(cleanedGuess)) {
            EventBus.getInstance().newEvent(GameEvent.VALID_GUESS);
            guesses.add(cleanedGuess); 
        }else{
            EventBus.getInstance().newEvent(GameEvent.INVALID_GUESS);
        }
    }

    protected boolean lettersPerWordMatch(String guess) {
        return guess.length() == lettersPerWord;
    }

    protected boolean onlyAlphabet(String guess) {
        return guess.matches("[a-zA-Z]+");
    }

    protected abstract boolean isValid(String guess);
}


