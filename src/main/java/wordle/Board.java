package wordle;

import java.util.ArrayList;
import java.util.List;


public class Board {
    private final List<Guess> guesses = new ArrayList<>();

    public void addGuess(Guess guess) { guesses.add(guess); }
    public List<Guess> getGuesses() { return List.copyOf(guesses); }

    public void scoreGuesses(String answer) {

        for (Guess guess : guesses) {
            guess.score(answer);
        }
    }

    public String display() {

        throw new UnsupportedOperationException("Display is not implemented yet.");
    }
}


