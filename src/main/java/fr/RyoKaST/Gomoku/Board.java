package fr.RyoKaST.Gomoku;

import fr.RyoKaST.Player.Player;
import fr.RyoKaST.Stable.IBoard;
import fr.RyoKaST.Stable.IPlayer;
import fr.RyoKaST.Stable.PawnType;

import java.util.*;


public class Board implements IBoard {
    int boardSize;
    Pawn[] board;
    IPlayer white, black;
    Random random;

    private void initialise(int size) {
        if(size <= 2 || size > 15) throw new CommandFailedException("Invalid board size (3-15)");
        this.boardSize = size;
        this.board = new Pawn[size * size];
        white = new Player(this, PawnType.white);
        black = new Player(this, PawnType.black);
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

    public int getBoardSize() {
        return boardSize;
    }

    public Pawn[] getboard(){
        return board;
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

    public void play(String player, int index) {
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

    public int evaluateBoard(PawnType playerPawn) {
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

    public int totalScoreLogSwitch(int totalScore, int consecutivePawns, int openEnds) {
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
