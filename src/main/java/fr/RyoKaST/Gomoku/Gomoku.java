package fr.RyoKaST.Gomoku;

import java.util.HashSet;
import java.util.Set;

import fr.RyoKaST.Stable.Ibot;
import fr.RyoKaST.Player.BotMiniMax;
import fr.RyoKaST.Player.Player;
import fr.RyoKaST.Stable.IBoard;
import fr.RyoKaST.Stable.IJeu;
import fr.RyoKaST.Stable.IPlayer;
import fr.RyoKaST.Stable.PawnType;

public class Gomoku implements IJeu {
    private IBoard board;
    private IPlayer white, black;
    private Ibot bot;

    @Override
    public void setBoardSize(String boardSizeInput) {
        board = new Board(boardSizeInput);

        white = new Player(board.getBoardSize(), PawnType.white);
        black = new Player(board.getBoardSize(), PawnType.black);
        bot = new BotMiniMax();
    }

    @Override
    public void clearBoard() {
        if (board == null) throw new CommandFailedException("board not initialized");
        board.clearBoard();
    }

    @Override
    public String genMove(String player) {
        if (board == null) throw new CommandFailedException("board not initialized");
        PawnType playerPawn = player.equals("white") ? PawnType.white : PawnType.black;
        int bestPos = bot.genmove(this, playerPawn, 2);
        play(player, bestPos);

        int boardSize = board.getBoardSize();

        int col = bestPos % boardSize;
        int row = bestPos / boardSize;
        String chosenPosition = (char) ('A' + col) + String.valueOf(row + 1);

        return chosenPosition;
    }

    @Override
    public void showBoard() {
        if (board == null) throw new CommandFailedException("board not initialized");
        board.showBoard();
    }


    @Override
    public void play(String player, String pos) {
        if (board == null) throw new CommandFailedException("board not initialized");
        pos = pos.toUpperCase();
        int x = pos.charAt(0) - 'A'; // lettre vers int table ascii
        int y = pos.charAt(1) - '1'; // char vers int table ascii aussi on considere que les chiffres commencent a 1
        
        int boardSize = board.getBoardSize();

        if(x < 0 || x >= boardSize || y < 0 || y >= boardSize) throw new CommandFailedException("illegal move");

        int index = x + y * boardSize;
        play(player, index);
        
    }

    private void play(String player, int index) {
        if(canPlay(player, index)) {
            if(player.equalsIgnoreCase("white")) {
                black.play(this, index);
            } else {
                white.play(this, index);
            }
            
        } else {
            throw new CommandFailedException("illegal move");
        }
    }
    
    private boolean canPlay(String player, int pos) {
        return board.getCase(pos) == null;
    }

    @Override
    public IBoard getBoard() {
        return board;
    }

    @Override
    public int evaluateBoard(PawnType playerPawn) {
        int score = 0;
        int boardSize = board.getBoardSize();

        int[][] directions = {{1, 0},   /* horizontal  */{0, 1},   /* vertical  */{1, 1},   /* diagonal descending  */{1, -1}   /* diagonal ascending  */};

        // Utiliser un ensemble pour tracker les positions déjà évaluées
        Set<Integer> evaluatedPositions = new HashSet<>();

        // Traverse the board
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int index = row * boardSize + col;
                
                if (evaluatedPositions.contains(index)) continue;
                
                if (board.getCase(index) == null || board.getCase(index).getPawType() != playerPawn) continue;
                
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
        int boardSize = board.getBoardSize();

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
                if (board.getCase(index) == null) {
                    openEnds++;
                    break;
                }
                
                // Si le pion est du même type, on continue
                if (board.getCase(index).getPawType() == playerPawn) {
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
