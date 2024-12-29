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
}
