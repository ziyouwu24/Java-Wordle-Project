package wordle.boards;

import java.util.List;

public class NormalBoard extends Board {
    public NormalBoard(String targetWord, List<String> validWords, int totalGuesses, int lettersPerWord) {
        super(targetWord, validWords, totalGuesses, lettersPerWord);
    }

    @Override
    protected boolean isValid(String guess) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValid'");
    }
}
