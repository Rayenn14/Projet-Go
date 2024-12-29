package fr.RyoKaST.Player;

import fr.RyoKaST.Gomoku.Pawn;
import fr.RyoKaST.Stable.PawnType;

public class BotMiniMax extends Abot{
    
    public BotMiniMax() {
        super();
    }

    @Override
    protected int genmove(PawnType playerPawn, int depth) {
        // ne marche plus si paser en chiffre a cause du return Null qui n'existe pas en int
        // peut etre en Integer ca marche mais pas sure et pas le temps on y a passer plus de 3h
         if (depth == 1) {
            String posString = findBestMove(playerPawn);
            int x = posString.charAt(0) - 'A';
            int y = posString.charAt(1) - '1';
            int index = x + y * board.getBoardSize();
            return index;
        }


        String posString = minimax(playerPawn, depth);
        int x = posString.charAt(0) - 'A';
        int y = posString.charAt(1) - '1';
        int index = x + y * board.getBoardSize();
        return index;
        
    }

    private String minimax(PawnType playerPawn, int depth) {
        // Cas de base et termine de la récursion
        if (depth == 1) {
            return findBestMove(playerPawn);
        }
        
        int boardSize = board.getBoardSize();

        String moveAtDepthOne = findBestMove(playerPawn);
        if (moveAtDepthOne == null) return null;
        
        // Convertir et jouer le coup pour simuler
        char col = moveAtDepthOne.charAt(0);
        char row = moveAtDepthOne.charAt(1);
        int index = (col - 'A') + (row - '1') * boardSize;
        board.setCase(index, new Pawn(playerPawn));
        // board[index] = new Pawn(playerPawn);
        
        // Récursion
        PawnType nextPlayer = (playerPawn == PawnType.white) ? PawnType.black : PawnType.white;
        minimax(nextPlayer, depth - 1);
        
        // apres la recursion, annuler les coups ;)
        board.setCase(index, null);
        // board[index] = null;
        
        return moveAtDepthOne;
    }
    private String findBestMove(PawnType playerPawn) {
        int boardSize = board.getBoardSize();
        int bestScore = Integer.MIN_VALUE;
        String bestPos = null;
    
        // Vérifier les pions déjà présents sur le plateau
        boolean boardIsEmpty = true;
        for (Pawn pawn : board.getBoard()) {
            if (pawn != null) {
                boardIsEmpty = false;
                break;
            }
        }
    
        // Si le plateau est vide, jouer au centre plus de possibilité
        if (boardIsEmpty) {
            int centerRow = boardSize / 2;
            int centerCol = boardSize / 2;
            return String.valueOf((char)('A' + centerCol)) + (centerRow + 1);
        }
    
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int index = row * boardSize + col;
    
                if ( board.getCase(index) == null
                    // board[index] == null
                    ) {
                    if (!hasAdjacentPawn(row, col)) continue;
    
                    // Simuler le placement du pion
                    board.setCase(index, new Pawn(playerPawn));
                    // board[index] = new Pawn(playerPawn);
    
                    int score = jeu.evaluateBoard(playerPawn);
    
                    // Évaluer les coups de l'adversaire
                    int opponentBestScore = evaluateOpponentBestMove(playerPawn);
    
                    score -= opponentBestScore * 0.8; // moins important 
    
                    if (score > bestScore) {
                        bestScore = score;
                        // Convertir les coordonnées en notation de position (ex: A1, B2)
                        bestPos = String.valueOf((char)('A' + col)) + (row + 1);
                    }
    
                    // Annuler le placement du pion
                    board.setCase(index, null);
                    // board[index] = null;
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
        
        int boardSize = board.getBoardSize();

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            // Vérifier les limites du plateau
            if (newRow >= 0 && newRow < boardSize && 
                newCol >= 0 && newCol < boardSize) {
                int index = newRow * boardSize + newCol;
                if (board.getCase(index) != null) {
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
        int boardSize = board.getBoardSize();

        for (int oppRow = 0; oppRow < boardSize; oppRow++) {
            for (int oppCol = 0; oppCol < boardSize; oppCol++) {
                int oppIndex = oppRow * boardSize + oppCol;
    
                // Trouver un coup possible pour l'adversaire
                if (board.getCase(oppIndex) == null) {
                    board.setCase(oppIndex, new Pawn(opponentPawn));
    
                    // Évaluer le score de l'adversaire
                    int opponentScore = jeu.evaluateBoard(opponentPawn);
                    opponentBestScore = Math.max(opponentBestScore, opponentScore);
    
                    // Annuler le coup de l'adversaire
                    board.setCase(oppIndex, null);
                }
            }
        }
    
        return opponentBestScore;
    }
}
