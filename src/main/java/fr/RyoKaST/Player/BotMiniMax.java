package fr.RyoKaST.Player;

import fr.RyoKaST.Gomoku.Board;
import fr.RyoKaST.Gomoku.Pawn;
import fr.RyoKaST.Stable.IPlayer;
import fr.RyoKaST.Stable.PawnType;

public class BotMiniMax implements IPlayer {
    private Board board;
    PawnType playerPawn;
    int depth;

    public BotMiniMax(Board board, PawnType playerPawn, int depth ) {
        this.board = board;
        this.playerPawn = playerPawn;
        this.depth = depth;
    }

    public String getColor(){
        return playerPawn == PawnType.white ? "white" : "black";
    }

    public void play(){
        board.play(getColor(), minimax(playerPawn, depth));
    }


    private String minimax(PawnType playerPawn, int depth) {
        // Cas de base et termine de la récursion
        if (depth == 1) {
            return board.findBestMove(playerPawn);
        }

        String moveAtDepthOne = board.findBestMove(playerPawn);
        if (moveAtDepthOne == null) return null;

        // Convertir et jouer le coup pour simuler
        char col = moveAtDepthOne.charAt(0);
        char row = moveAtDepthOne.charAt(1);
        int index = (col - 'A') + (row - '1') * board.getBoardSize();
        board.getboard()[index] = new Pawn(playerPawn);

        // Récursion
        PawnType nextPlayer = (playerPawn == PawnType.white) ? PawnType.black : PawnType.white;
        minimax(nextPlayer, depth - 1);

        // apres la recursion, annuler les coups ;)
        board.getboard()[index] = null;

        return moveAtDepthOne;
    }







    @Override
    public void play(int pos) {}
}
