package fr.RyoKaST.App;

import fr.RyoKaST.Gomoku.Gomoku;
import fr.RyoKaST.Stable.IJeu;
import fr.RyoKaST.Gomoku.CommandFailedException;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        IJeu game = new Gomoku();

        while (running) {
            String command = scanner.nextLine();
            String[] commandPart = command.split(" ");

            if (commandPart.length < 1) throw new CommandFailedException("Invalid command");
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
                commandID = String.valueOf(tempCommandID) + " ";

            }

            boolean indeReturn = false;
            try {
                switch (commandPart[1].toLowerCase()) {
                    case "boardsize":
                        game.setBoardSize(commandPart[2]);
                        break;
                    case "clear_board":
                        game.clearBoard();
                        break;
                    case "play":
                        if (commandPart.length != 4) throw new CommandFailedException("Invalid command");
                        game.play(commandPart[2], commandPart[3]);
                        break;
                    case "genmove":
                        if (commandPart.length != 3) throw new CommandFailedException("Invalid command");

                        System.out.println("=" + commandID + game.genMove(commandPart[2]));
                        indeReturn = true;
                        break;
                    case "showboard":
                        indeReturn = true;
                        System.out.println("=" + commandID);
                        game.showBoard();
                        break;

                    case "quit":
                        running = false;
                        break;
                }

                if (!indeReturn) System.out.println("=" + commandID + "\n");
            } catch (CommandFailedException e) {
                System.err.println("?" + commandID + e.getMessage() + "\n");
            } catch (NullPointerException e) {
                System.err.println("?" + commandID + " board not initialized" + "\n");
            }
        }
        scanner.close();
    }
}
