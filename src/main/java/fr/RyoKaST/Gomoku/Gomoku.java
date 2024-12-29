package fr.RyoKaST.Gomoku;

import fr.RyoKaST.Stable.IBoard;
import fr.RyoKaST.Stable.IJeu;
import fr.RyoKaST.Stable.IPlayer;

public class Gomoku implements IJeu {
    private IBoard board;

    @Override
    public void setBoardSize(String boardSize) {
        board = new Board(boardSize);
    }

    @Override
    public void clearBoard() {
        if (board == null) throw new CommandFailedException("board not initialized");
        board.clearBoard();
    }


    @Override
    public void play(String player, String pos) {
        if (board == null) throw new CommandFailedException("board not initialized");
        board.play(player, pos);
    }

    @Override
    public void showBoard() {
        if (board == null) throw new CommandFailedException("board not initialized");
        board.showBoard();
    }
}
