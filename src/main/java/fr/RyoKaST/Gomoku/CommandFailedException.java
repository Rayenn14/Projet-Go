package fr.RyoKaST.Gomoku;

public class CommandFailedException extends RuntimeException {
    public CommandFailedException(String message) {
        super(message);
    }
}
