package fr.RyoKaST.app;

import fr.RyoKaST.board.CommandFailedException;
import fr.RyoKaST.board.Board;

import java.util.Scanner;


public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        Board board = null;
        
        while (running) {
            String command = scanner.nextLine();
            String[] commandPart = command.split(" ");
            
            if(commandPart.length < 1) throw new CommandFailedException("Invalid command");
            String commandID = "0";
            int tempCommandID = 0;
            try {
                tempCommandID = Integer.parseInt(commandPart[0]);
            } catch (Exception e) {
                tempCommandID = -1;
            }

            if (tempCommandID < 0) {
                commandID = "";
                commandPart = ("0 " + command).split(" ");

            } else {
                commandID = String.valueOf(tempCommandID);
            }
            
            boolean indeReturn = false; 
            try {
                switch (commandPart[1].toLowerCase()) {
                    case "boardsize":
                        board = new Board(commandPart[2]);
                        break;
                    case "clear_board":
                        board.clearBoard();
                        break;
                    case "play":
                        if(commandPart.length != 4) throw new CommandFailedException("Invalid command");
                        board.play(commandPart[2], commandPart[3]);
                        break;
                    case "genmove":
                        if(commandPart.length != 3) throw new CommandFailedException("Invalid command");
                        
                        System.out.println("=" + commandID + " " + board.genmove(commandPart[2]));
                        indeReturn = true;
                        break;
                    case "showboard":
                        if(board == null) throw new CommandFailedException("board not initialized");
                        indeReturn = true;
                        System.out.println("=" + commandID);
                        board.showBoard();
                        break;
                    
                    case "quit":
                        running = false;
                        break;
                }

                if(!indeReturn) System.out.println("=" + commandID + "\n");
            } catch (CommandFailedException e) {
                System.err.println("?"+ commandID + " " + e.getMessage() + "\n");
            } catch (NullPointerException  e) {
                System.err.println("?"+ commandID + " board not initialized" + "\n");
            }
        }
        scanner.close();
    }
}
