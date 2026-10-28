package wordle.boards;

import wordle.Gamemode;

public class BoardFactory {
    public Board createBoard(Gamemode gamemode, String targetWord, int totalGuesses, int lettersPerWord) {
        if(gamemode == Gamemode.NORMAL) {
            return new NormalBoard(targetWord, totalGuesses, lettersPerWord);
        }else {
            return new HardBoard(targetWord, totalGuesses, lettersPerWord);
        }
    }
}

