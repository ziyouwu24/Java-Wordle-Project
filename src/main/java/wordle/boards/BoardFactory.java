package wordle.boards;

import java.util.List;

import wordle.Gamemode;

public class BoardFactory {
    public Board createBoard(Gamemode gamemode, String targetWord, List<String> validWords, int totalGuesses, int lettersPerWord) {
        if(gamemode == Gamemode.NORMAL) {
            return new NormalBoard(targetWord, validWords, totalGuesses, lettersPerWord);
        }else {
            return new HardBoard(targetWord, validWords, totalGuesses, lettersPerWord);
        }
    }
}

