package fr.RyoKaST.Player;

import fr.RyoKaST.Gomoku.Board;
import fr.RyoKaST.Stable.IPlayer;
import fr.RyoKaST.Stable.PawnType;

public class Player implements IPlayer {
    private Board board;
    PawnType playerPawn;


    public Player(Board b, PawnType p) {
        this.board = b;
        this.playerPawn = p;
    }

    public String getColor(){
        return playerPawn == PawnType.white ? "white" : "black";
    }

    @Override
    public void play(int pos) {
        board.play(getColor(), pos);
    }

    @Override
    public void play() {

    }
}