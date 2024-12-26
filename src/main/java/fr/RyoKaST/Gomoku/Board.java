package fr.RyoKaST.Gomoku;

import fr.RyoKaST.Player.Player;
import fr.RyoKaST.Stable.IBoard;
import fr.RyoKaST.Stable.IPlayer;
import fr.RyoKaST.Stable.PawnType;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class Board implements IBoard {
    int boardSize;
    Pawn[] board;
    IPlayer white, black;
    Random random;

    private void initialise(int size) {
        if(size <= 2 || size > 15) throw new CommandFailedException("Invalid board size (3-15)");
        this.boardSize = size;
        this.board = new Pawn[size * size];
        white = new Player(boardSize);
        black = new Player(boardSize);
        random = new Random();
    }

    public Board(int boardSize) {
        initialise(boardSize);
    }

    public Board(String boardSize) {
        try {
            initialise(Integer.parseInt(boardSize));
        } catch (NumberFormatException e) {
            throw new CommandFailedException("Invalid board size");
        }
    }

    @Override
    public void clearBoard() {
        initialise(boardSize);
    }

    public boolean isEmpty() {
        for (Pawn pawn : board) {
            if (pawn != null) {
                return false; // Si une case contient un pion, le plateau n'est pas vide
            }
        }
        return true;
    }

    @Override
    public void play(String player, String pos) {
        pos = pos.toUpperCase();
        int x = pos.charAt(0) - 'A'; // lettre vers int table ascii
        int y = pos.charAt(1) - '1'; // char vers int table ascii aussi on considere que les chiffres commencent a 1
        
        if(x < 0 || x >= boardSize || y < 0 || y >= boardSize) throw new CommandFailedException("illegal move");

        int index = x + y * boardSize;
        play(player, index);
        
    }

    private void play(String player, int index) {
        if(canPlay(player, index)) {
            if(player.equalsIgnoreCase("white")) {
                board[index] = new Pawn(PawnType.white);
                black.play(index);
            } else {
                board[index] = new Pawn(PawnType.black);
                white.play(index);
                
                System.out.println( evaluateBoard(PawnType.black));
            }
            
        } else {
            throw new CommandFailedException("illegal move");
        }
    }
    
    private boolean canPlay(String player, int pos) {
        return board[pos] == null;
    }

    @Override
    public String genmove(String player) {
        /*
        int x = random.nextInt(boardSize);
        int y = random.nextInt(boardSize);
        int index = x + y * boardSize;
        int initx = x; 
        while(board[index] != null) {
            if(x == initx) {
                y = y+1%boardSize;
            }
            x = x+1%boardSize;
            index = x + y * boardSize;
        }

        if(player.equalsIgnoreCase("white")) {
            this.play("white", index);
            //System.out.println(evaluateBoard(PawnType.white));
        } else {
            this.play("black", index);
            //System.out.println(evaluateBoard(PawnType.black));
            
        }

        return (char)(x + 'A') + "" + (y + 1);
        */

        
        PawnType playerPawn = player.equals("white") ? PawnType.white : PawnType.black;
        String bestPos = minimax(playerPawn, 2);
        play(player, bestPos);

        return bestPos;
        
        
    }

    @Override
    public void showBoard() {
        StringBuilder letters = new StringBuilder(" ");

        int maxPad = String.valueOf(boardSize + 1).length();

        for (int pad = maxPad; pad > 1; --pad)
            letters.append(" ");

        for (int i = 0; i < boardSize; i++) {
            letters.append(" ").append((char)('A' + i));
        }

        StringBuilder boardStringBuilder = new StringBuilder(letters);

        for (int row = boardSize - 1; row >= 0; --row) { // Inverse les lignes
            String rowLabel = String.valueOf(row + 1);
            boardStringBuilder.append("\n");
            for (int pad = maxPad - rowLabel.length(); pad > 0; --pad)
                boardStringBuilder.append(" ");
            boardStringBuilder.append(rowLabel);

            for (int col = 0; col < boardSize; ++col) {
                int index = row * boardSize + col;
                if (board[index] == null) {
                    boardStringBuilder.append(" .");
                } else {
                    boardStringBuilder.append(" ").append(board[index].getPawType().toString().charAt(0));
                }
            }

            boardStringBuilder.append(" ").append(row + 1);
        }

        boardStringBuilder.append("\n").append(letters).append("\n");
        System.out.println(boardStringBuilder.toString());
    }


    public String getCellValue(String cell) {
        cell = cell.toUpperCase();
        int x = cell.charAt(0) - 'A'; // Convertir la lettre en index de colonne
        int y = cell.charAt(1) - '1'; // Convertir le chiffre en index de ligne

        // Vérifier si la position est hors des limites
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
            throw new CommandFailedException("Invalid cell position");
        }

        int index = x + y * boardSize;
        Pawn pawn = board[index]; // Récupérer la case correspondante

        // Retourner la valeur de la case
        if (pawn == null) {
            return "."; // Case vide
        } else {
            return pawn.getPawType().toString(); // Retourne "white" ou "black"
        }
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
        int index = (col - 'A') + (row - '1') * boardSize;
        board[index] = new Pawn(playerPawn);
        
        // Récursion
        PawnType nextPlayer = (playerPawn == PawnType.white) ? PawnType.black : PawnType.white;
        minimax(nextPlayer, depth - 1);
        
        // apres la recursion, annuler les coups ;)
        board[index] = null;
        
        return moveAtDepthOne;
    }

    private String findBestMove(PawnType playerPawn) {
        int bestScore = Integer.MIN_VALUE;
        String bestPos = null;
    
        // Vérifier les pions déjà présents sur le plateau
        boolean boardIsEmpty = true;
        for (Pawn pawn : board) {
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
    
                if (board[index] == null) {
                    if (!hasAdjacentPawn(row, col)) continue;
    
                    // Simuler le placement du pion
                    board[index] = new Pawn(playerPawn);
    
                    int score = evaluateBoard(playerPawn);
    
                    // Évaluer les coups de l'adversaire
                    int opponentBestScore = evaluateOpponentBestMove(playerPawn);
    
                    score -= opponentBestScore * 0.8; // moins important 
    
                    if (score > bestScore) {
                        bestScore = score;
                        // Convertir les coordonnées en notation de position (ex: A1, B2)
                        bestPos = String.valueOf((char)('A' + col)) + (row + 1);
                    }
    
                    // Annuler le placement du pion
                    board[index] = null;
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
            if (newRow >= 0 && newRow < boardSize && 
                newCol >= 0 && newCol < boardSize) {
                int index = newRow * boardSize + newCol;
                if (board[index] != null) {
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
    
        for (int oppRow = 0; oppRow < boardSize; oppRow++) {
            for (int oppCol = 0; oppCol < boardSize; oppCol++) {
                int oppIndex = oppRow * boardSize + oppCol;
    
                // Trouver un coup possible pour l'adversaire
                if (board[oppIndex] == null) {
                    board[oppIndex] = new Pawn(opponentPawn);
    
                    // Évaluer le score de l'adversaire
                    int opponentScore = evaluateBoard(opponentPawn);
                    opponentBestScore = Math.max(opponentBestScore, opponentScore);
    
                    // Annuler le coup de l'adversaire
                    board[oppIndex] = null;
                }
            }
        }
    
        return opponentBestScore;
    }

    private int evaluateBoard(PawnType playerPawn) {
        int score = 0;
    
        int[][] directions = {{1, 0},   /* horizontal  */{0, 1},   /* vertical  */{1, 1},   /* diagonal descending  */{1, -1}   /* diagonal ascending  */};
    
        // Utiliser un ensemble pour tracker les positions déjà évaluées
        Set<Integer> evaluatedPositions = new HashSet<>();
    
        // Traverse the board
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int index = row * boardSize + col;
                
                if (evaluatedPositions.contains(index)) continue;
                
                if (board[index] == null || board[index].getPawType() != playerPawn) continue;
                
                // Marquer cette position comme évaluée
                evaluatedPositions.add(index);
                
                for (int[] direction : directions) {
                    int alignmentScore = calculateDirectionalScore(row, col, direction[0], direction[1], playerPawn);
                    score += alignmentScore;
                }
            }
        }

        return score;
    }
    
    private int calculateDirectionalScore(int startRow, int startCol, int rowDir, int colDir, PawnType playerPawn) {
        int totalScore = 0;
        int consecutivePawns = 1; // Commencer à 1 pour inclure le pion initial
        int openEnds = 0;
        
        int[][] searchDirections = {{-1, -1}, {1, 1}};
        
        for (int[] searchDir : searchDirections) {
            int checkRow = startRow;
            int checkCol = startCol;
            int stepCount = 0;
            
            while (stepCount < 4) {
                checkRow += searchDir[0] * rowDir;
                checkCol += searchDir[0] * colDir;
                stepCount++;
                
                if (checkRow < 0 || checkRow >= boardSize ||  checkCol < 0 || checkCol >= boardSize)  break;
                
                int index = checkRow * boardSize + checkCol;
                
                // Si la case est vide, c'est un bout ouvert
                if (board[index] == null) {
                    openEnds++;
                    break;
                }
                
                // Si le pion est du même type, on continue
                if (board[index].getPawType() == playerPawn) {
                    consecutivePawns++;
                } else {
                    // Pion adverse bloque la séquence
                    break;
                }
            }
        }
        
        return totalScoreLogSwitch(totalScore, consecutivePawns, openEnds);
    }

    private int totalScoreLogSwitch(int totalScore, int consecutivePawns, int openEnds) {
        switch (consecutivePawns) {
            case 5:  // Victoire
                totalScore += 10000;
                break;
            case 4:  // Presque victoire
                totalScore += (openEnds == 2) ? 5000 : 1000;
                break;
            case 3:  // Menace sérieuse
                totalScore += (openEnds == 2) ? 500 : 100;
                break;
            case 2:  // Début de motif
                totalScore += (openEnds == 2) ? 50 : 10;
                break;
        }
        
        return totalScore;
    }
    
}
