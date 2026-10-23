package wordle;

public class GuessFactory {
    public Guess createGuess(String word) {
        return new Guess(word);
    }
}

