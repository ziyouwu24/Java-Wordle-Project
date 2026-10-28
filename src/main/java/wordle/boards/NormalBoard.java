package wordle.boards;

import java.util.List;

public class NormalBoard extends Board {
    public NormalBoard(String targetWord, List<String> validWords, int totalGuesses, int lettersPerWord) {
        super(targetWord, validWords, totalGuesses, lettersPerWord);
    }

    @Override
    protected boolean isValid(String guess) {
        if(!lettersPerWordMatch(guess)) {
            return false;
        }

        if(!onlyAlphabet(guess)) {
            return false;
        }

        return true;
    }
}
