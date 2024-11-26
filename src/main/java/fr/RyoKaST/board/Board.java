package fr.RyoKaST.board;

import fr.RyoKaST.player.Player;
import fr.RyoKaST.pawn.Pawn;
import fr.RyoKaST.pawn.PawnType;
import java.util.Random;


public class Board implements IBoard {
    int boardSize;
    Pawn[] board;
    Player white, black;
    Random random;

    private void initialise(int size) {
        if(size <= 6 || size > 15) throw new CommandFailedException("Invalid board size (7-15)");
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
        } else {
            this.play("black", index);
        }

        return (char)(x + 'A') + "" + (y + 1);
    }

    @Override
    public void showBoard() {
        StringBuilder letters = new StringBuilder(" ");

        int maxPad = String.valueOf(boardSize+1).length();
        
        for(int pad = maxPad; pad > 1; --pad)
            letters.append(" ");

        for(int i = 0; i < boardSize; i++) {
            letters.append(" ").append((char)('A' + i) );
        }

        StringBuilder boardStringBuilder = new StringBuilder(letters);
        for(int i = 0; i < board.length; ++i) {
            if(i % boardSize == 0) {
                String nbline = String.valueOf(i/boardSize + 1);
                boardStringBuilder.append("\n");
                for(int pad = maxPad - nbline.length(); pad > 0; --pad)
                    boardStringBuilder.append(" ");
                boardStringBuilder.append(nbline);
            }

            if(board[i] == null) {
                boardStringBuilder.append(" ").append(".");
            } else {
                boardStringBuilder.append(" ").append(board[i].getPawType().toString().charAt(0));
            }

            if(i % boardSize == boardSize -1) {
                boardStringBuilder.append(" ").append(i/boardSize + 1);
            }
            
        }
        boardStringBuilder.append("\n").append(letters).append("\n");
        System.out.println(boardStringBuilder.toString());   
    }
}
