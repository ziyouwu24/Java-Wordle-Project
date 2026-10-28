package wordle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import wordle.boards.Board;
import wordle.boards.BoardFactory;

public class Game {
    private final Board board;

    private Game(Board board) {
        this.board = board;
    }

    public static Builder getBuilder(BoardFactory boardFactory) {
        return new Builder(boardFactory);
    }

    public void submitGuess(String guess) {
        this.board.addGuess(guess);
    }

    public static class Builder {
        private final String DEFAULT_TARGET_LIST = "/wordlist.txt";
        private final String DEFAULT_GUESS_LIST = "/wordlist.txt";

        private String customWord;
        private int lettersPerWord = Board.DEFAULT_LETTERS_PER_WORD;
        private int totalGuesses = Board.DEFAULT_TOTAL_GUESSES;
        private Gamemode gamemode = Board.DEFAULT_GAMEMODE;

        private String targetList = DEFAULT_TARGET_LIST;
        private String guessList = DEFAULT_GUESS_LIST;

        private final BoardFactory boardFactory;

        private Builder(BoardFactory boardFactory) {
            this.boardFactory = boardFactory;
        }

        public Builder setLettersPerWord(int lettersPerWord) {
            if(lettersPerWord < 1) {

                return this;
            }

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

        public Builder setCustomWord(String customWord) {
            

            this.customWord = customWord;
            return this;
        }

        public Game build() {
            String targetWord = customWord;

            if(targetWord == null) {
                targetWord = selectWord();
            }

            Board board = BoardFactory.createBoard(gamemode);
            return new Game(this);
        }

        private String selectWord() throws IOException {
            InputStream input = getClass()
                    .getResourceAsStream(targetList);

            if (input == null) {
                throw new IOException("Could not find target list");
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(input))) {

                List<String> words = reader.lines()
                        .map(String::trim)
                        .filter(word -> word.matches("[a-zA-Z]{"+ lettersPerWord +"}"))
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());

                if (words.isEmpty()) {
                    throw new IOException("No valid words found");
                }

                return words.get(new Random().nextInt(words.size()));
            }
        }
    }
}

