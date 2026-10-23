package wordle;


public class Game {
    public static final int DEFAULT_LETTERS_PER_WORD = 5;
    public static final int DEFAULT_TOTAL_GUESSES = 6;
    public static final Gamemode DEFAULT_GAMEMODE = Gamemode.NORMAL;

    private final Board board;
    private final GuessFactory guessFactory;
    private final int lettersPerWord;
    private final int totalGuesses;
    private final Gamemode gamemode;

    private Game(Builder builder) {
        board = builder.boardFactory.createBoard();
        guessFactory = builder.guessFactory;
        lettersPerWord = builder.lettersPerWord;
        totalGuesses = builder.totalGuesses;
        gamemode = builder.gamemode;
    }

    public static Builder getBuilder(BoardFactory boardFactory, GuessFactory guessFactory) {
        return new Builder(boardFactory, guessFactory);
    }

    public Board getBoard() { return board; }
    public int getLettersPerWord() { return lettersPerWord; }
    public int getTotalGuesses() { return totalGuesses; }
    public Gamemode getGamemode() { return gamemode; }

    public void submitGuess(String word) {


        throw new UnsupportedOperationException("Turns are not implemented yet.");
    }

    public static class Builder {
        private int lettersPerWord = DEFAULT_LETTERS_PER_WORD;
        private int totalGuesses = DEFAULT_TOTAL_GUESSES;
        private Gamemode gamemode = DEFAULT_GAMEMODE;
        private final BoardFactory boardFactory;
        private final GuessFactory guessFactory;

        private Builder(BoardFactory boardFactory, GuessFactory guessFactory) {
            this.boardFactory = boardFactory;
            this.guessFactory = guessFactory;
        }

        public Builder setLettersPerWord(int lettersPerWord) {
            this.lettersPerWord = lettersPerWord;
            return this;
        }

        public Builder setTotalGuesses(int totalGuesses) {
            this.totalGuesses = totalGuesses;
            return this;
        }

        public Builder setGamemode(Gamemode gamemode) {
            this.gamemode = gamemode;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}

