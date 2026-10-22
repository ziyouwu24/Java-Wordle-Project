package wordle;

public class Game {
    private Board board;

    private Game(Board board) {

    }

    public Builder getBuilder(BoardFactory boardFactory) {
        return new Builder(boardFactory);
    }

    public static class Builder {
        private int lettersPerWord = Board.DEFAULT_LETTERS_PER_WORD;
        private int totalGuesses = Board.DEFAULT_TOTAL_GUESSES;
        private Gamemode gamemode = Board.DEFAULT_GAMEMODE;

        private BoardFactory boardFactory;

        private Builder(BoardFactory boardFactory) {
            this.boardFactory = boardFactory;
        }

        public Builder setLettersPetWord(int lettersPerWord) {
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
            Board newBoard = boardFactory.createBoard(lettersPerWord, totalGuesses, gamemode);

            return new Game(newBoard);
        }
    }
}
