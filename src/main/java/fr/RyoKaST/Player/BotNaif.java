package fr.RyoKaST.Player;

import fr.RyoKaST.Gomoku.Board;
import fr.RyoKaST.Stable.IPlayer;
import fr.RyoKaST.Stable.PawnType;

public class BotNaif implements IPlayer {
    private Board board;
    PawnType playerPawn;

    public BotNaif(Board board, PawnType playerPawn) {
        this.board = board;
        this.playerPawn = playerPawn;
    }

    @Override
    public void play(int pos) {}

    @Override
    public void play() {
        board.play(getColor(), getFirstEmptySpot());
    }

    public String getColor(){
        return playerPawn == PawnType.white ? "white" : "black";
    }

    public String getFirstEmptySpot() {
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                int index = row * board.getBoardSize() + col;
                if (board.getboard()[index] == null) {
                    return String.valueOf((char) ('A' + col)) + (row + 1); // Format: A1, B2, etc.
                }
            }
        }
        return null;
    }

}
