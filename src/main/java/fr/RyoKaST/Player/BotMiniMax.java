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
            return findBestMove(playerPawn);
        }

        String moveAtDepthOne = findBestMove(playerPawn);
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

    private String findBestMove(PawnType playerPawn) {
        int bestScore = Integer.MIN_VALUE;
        String bestPos = null;

        // Vérifier les pions déjà présents sur le plateau
        boolean boardIsEmpty = true;
        for (Pawn pawn : board.getboard()) {
            if (pawn != null) {
                boardIsEmpty = false;
                break;
            }
        }

        // Si le plateau est vide, jouer au centre plus de possibilité
        if (boardIsEmpty) {
            int centerRow = board.getBoardSize() / 2;
            int centerCol = board.getBoardSize() / 2;
            return String.valueOf((char)('A' + centerCol)) + (centerRow + 1);
        }

        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                int index = row * board.getBoardSize() + col;

                if (board.getboard()[index] == null) {
                    if (!hasAdjacentPawn(row, col)) continue;

                    // Simuler le placement du pion
                    board.getboard()[index] = new Pawn(playerPawn);

                    int score = board.evaluateBoard(playerPawn);

                    // Évaluer les coups de l'adversaire
                    int opponentBestScore = evaluateOpponentBestMove(playerPawn);

                    score -= opponentBestScore * 0.8; // moins important

                    if (score > bestScore) {
                        bestScore = score;
                        // Convertir les coordonnées en notation de position (ex: A1, B2)
                        bestPos = String.valueOf((char)('A' + col)) + (row + 1);
                    }

                    // Annuler le placement du pion
                    board.getboard()[index] = null;
                }
            }
        }

        return bestPos;
    }

    // Méthode pour vérifier s'il y a des pions adjacents
    private boolean hasAdjacentPawn(int row, int col) {
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            // Vérifier les limites du plateau
            if (newRow >= 0 && newRow < board.getBoardSize() &&
                    newCol >= 0 && newCol < board.getBoardSize()) {
                int index = newRow * board.getBoardSize() + newCol;
                if (board.getboard()[index] != null) {
                    return true;
                }
            }
        }

        return false;
    }

    // Méthode pour évaluer le meilleur coup de l'adversaire
    private int evaluateOpponentBestMove(PawnType playerPawn) {
        PawnType opponentPawn = (playerPawn == PawnType.white) ? PawnType.black : PawnType.white;
        int opponentBestScore = Integer.MIN_VALUE;

        for (int oppRow = 0; oppRow < board.getBoardSize(); oppRow++) {
            for (int oppCol = 0; oppCol < board.getBoardSize(); oppCol++) {
                int oppIndex = oppRow * board.getBoardSize() + oppCol;

                // Trouver un coup possible pour l'adversaire
                if (board.getboard()[oppIndex] == null) {
                    board.getboard()[oppIndex] = new Pawn(opponentPawn);

                    // Évaluer le score de l'adversaire
                    int opponentScore = board.evaluateBoard(opponentPawn);
                    opponentBestScore = Math.max(opponentBestScore, opponentScore);

                    // Annuler le coup de l'adversaire
                    board.getboard()[oppIndex] = null;
                }
            }
        }
        return opponentBestScore;
    }

    @Override
    public void play(int pos) {}
}
