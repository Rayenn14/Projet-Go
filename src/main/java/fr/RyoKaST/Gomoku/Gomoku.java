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
    private static final int GENMOVE_DEPTH = 2; 

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
        int bestPos = bot.genmove(this, playerPawn, GENMOVE_DEPTH);
        play(player, bestPos);
        int boardSize = board.getBoardSize();
        int col = bestPos % boardSize;
        int row = bestPos / boardSize;
        String chosenPosition = (char) ('A' + col) + String.valueOf(row + 1);
        return chosenPosition;
    }

    @Override
    public String showBoard() {
        if (board == null) throw new CommandFailedException("board not initialized");
        return board.showBoard();
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
                white.play(this, index);
            } else {
                black.play(this, index);
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
        int score = 0, boardSize = board.getBoardSize();
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        Set<Integer> evaluated = new HashSet<>();

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int index = row * boardSize + col;

                if (!evaluated.add(index) || board.getCase(index) == null
                        || board.getCase(index).getPawType() != playerPawn) continue;

                for (int[] dir : directions)
                    score += calculateDirectionalScore(row, col, dir[0], dir[1], playerPawn);
            }
        }
        return score;
    }

    private int calculateDirectionalScore(int startRow, int startCol, int rowDir, int colDir, PawnType playerPawn) {
        int consecutivePawns = 1, openEnds = 0, boardSize = board.getBoardSize();
        for (int dir = -1; dir <= 1; dir += 2) { // Parcourt les deux directions
            int checkRow = startRow, checkCol = startCol, steps = 0;
            while (++steps < 4) {
                checkRow += dir * rowDir;
                checkCol += dir * colDir;

                if (checkRow < 0 || checkCol < 0 || checkRow >= boardSize || checkCol >= boardSize) break;

                int index = checkRow * boardSize + checkCol;
                if (board.getCase(index) == null) {
                    openEnds++;
                    break;
                } else if (board.getCase(index).getPawType() == playerPawn) {
                    consecutivePawns++;
                } else break;
            }
        }

        return totalScoreLogSwitch(0, consecutivePawns, openEnds);
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
    public boolean gameFinish() {
        return board.gameFinish();
    }



}
