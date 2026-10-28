package wordle.boards;

public class HardBoard extends Board {
    public HardBoard(String correctWord, int totalGuesses, int lettersPerWord) {
        super(correctWord, totalGuesses, lettersPerWord);
    }

    @Override
    protected boolean isValid(String guess) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValid'");
    }
}
