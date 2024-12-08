package fr.RyoKaST.Gomoku;

import fr.RyoKaST.IHM.IBoard;

public class Gomoku {
    private IBoard board;

    public void setBoardSize(String boardSize) {
        board = new Board(boardSize);
    }

    public void clearBoard() {
        if (board == null) throw new CommandFailedException("board not initialized");
        board.clearBoard();
    }

    public void play(String player, String pos) {
        if (board == null) throw new CommandFailedException("board not initialized");
        board.play(player, pos);
    }

    public String genMove(String player) {
        if (board == null) throw new CommandFailedException("board not initialized");
        return board.genmove(player);
    }

    public void showBoard() {
        if (board == null) throw new CommandFailedException("board not initialized");
        board.showBoard();
    }
}
