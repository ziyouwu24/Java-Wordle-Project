package wordle.boards;

public class NormalBoard extends Board {
    public NormalBoard(String correctWord, int totalGuesses, int lettersPerWord) {
        super(correctWord, totalGuesses, lettersPerWord);
    }

    @Override
    protected boolean isValid(String guess) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValid'");
    }
}
