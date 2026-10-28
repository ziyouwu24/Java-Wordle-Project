package wordle.observers;

public class TestObserver implements Observer{
    private boolean guessesMaxed = false;
    private int invalidGuesses = 0;
    private int validGuesses = 0;

    public void notifyEvent(GameEvent gameEvent) {
        switch (gameEvent) {
            case MAX_GUESSES:
                guessesMaxed = true;
                System.out.println("max guesses kid");
                break;
            case INVALID_GUESS:
                invalidGuesses++;
                System.out.println("invalid kid");
                break;
            case VALID_GUESS:
                validGuesses++;
                System.out.println("valid kid");
                break;
            default:
                break;
        }
    }

    public boolean guessesMaxed() {
        return guessesMaxed;
    }

    public int invalidGuesses() {
        return invalidGuesses;
    }

    public int validGuesses() {
        return validGuesses;
    }
}
