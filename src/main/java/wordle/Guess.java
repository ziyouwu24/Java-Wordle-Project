package wordle;

import java.util.ArrayList;
import java.util.List;


public class Guess {
    public enum LetterResult {
        CORRECT,
        PRESENT,
        ABSENT
    }

    private final String word;
    private final List<LetterResult> results = new ArrayList<>();

    public Guess(String word) {
        this.word = word;
    }

    public String getWord() { return word; }
    public List<LetterResult> getResults() { return List.copyOf(results); }


    public void score(String answer) {
        return; // add scoring logic
    }

    public boolean isValid(int lettersPerWord, Gamemode gamemode, List<Guess> previousGuesses) {
        return true; // placeholder add check for the length  and gamemode stuff.
    }
}



