package fr.RyoKaST;

public class CommandFailedException extends RuntimeException {
    public CommandFailedException(String message) {
        super(message);
    }
}
