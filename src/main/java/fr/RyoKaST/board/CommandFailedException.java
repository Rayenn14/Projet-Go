package fr.RyoKaST.board;

public class CommandFailedException extends RuntimeException {
    public CommandFailedException(String message) {
        super(message);
    }
}
