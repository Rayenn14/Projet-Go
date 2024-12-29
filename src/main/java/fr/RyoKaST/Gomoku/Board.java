package fr.RyoKaST.Gomoku;

import fr.RyoKaST.Stable.IBoard;

import fr.RyoKaST.Stable.PawnType;

import java.util.Random;


public class Board implements IBoard {
    int boardSize;
    Pawn[] board;
    
    Random random;

    private void initialise(int size) {
        if(size <= 2 || size > 15) throw new CommandFailedException("Invalid board size (3-15)");
        this.boardSize = size;
        this.board = new Pawn[size * size];
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
    public String showBoard() {
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
        return boardStringBuilder.toString();
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
        Pawn pawn = board[index];

        // Retourner la valeur de la case
        if (pawn == null) {
            return "."; // Case vide
        } else {
            return pawn.getPawType().toString(); // Retourne "white" ou "black"
        }
    }
    
    @Override
    public int getBoardSize() {
        return boardSize;
    }

    @Override 
    public Pawn getCase(int pos) {
        return board[pos];
    }

    @Override 
    public void setCase(int pos, Pawn pawn) {
        board[pos] = pawn;
    }

    @Override
    public Pawn[] getBoard() {
        return board;
    }

    @Override
    public void play(PawnType pawnType, int pos) {
        if (board[pos] == null) {
            board[pos] = new Pawn(pawnType);
        } else {
            throw new CommandFailedException("Illegal move");
        }
    }

    public boolean gameFinish() {
        int boardSize = getBoardSize();

        // Définir le nombre minimum de pions alignés requis en fonction de la taille du plateau
        int requiredPawns = (boardSize <= 3) ? 3 : (boardSize == 4 ? 4 : 5);

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (getCase(row * boardSize + col) != null) {
                    if (checkAlignment(row, col, requiredPawns)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkAlignment(int row, int col, int requiredPawns) {
        int boardSize = getBoardSize();
        PawnType currentPawn = getCase(row * boardSize + col).getPawType();
        // Directions : gauche -> droite, haut -> bas, diagonale, anti-diagonale
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        // Vérification dans les 4 directions possibles
        for (int[] dir : directions) {
            int consecutivePawns = 1;  // Comptage du pion actuel
            // Vérification dans les deux sens
            for (int i = -1; i <= 1; i += 2) {
                int checkRow = row, checkCol = col;
                for (int steps = 1; steps < requiredPawns; steps++) {
                    checkRow += dir[0] * i;
                    checkCol += dir[1] * i;
                    // Vérifier les limites du plateau
                    if (checkRow < 0 || checkCol < 0 || checkRow >= boardSize || checkCol >= boardSize) {
                        break;
                    }
                    // Vérifier si le pion est du même type
                    if (getCase(checkRow * boardSize + checkCol) != null
                            && getCase(checkRow * boardSize + checkCol).getPawType() == currentPawn) {
                        consecutivePawns++;
                    } else {
                        break;
                    }
                }
            }
            if (consecutivePawns >= requiredPawns) {
                return true;
            }
        }

        return false;
    }

}
