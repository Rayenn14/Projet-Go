package fr.RyoKaST.Stable;

public interface IJeu {
    void setBoardSize(String size);

    void clearBoard();

    void play(String player, String position);

    String genMove(String player);

    String showBoard();

    IBoard getBoard();

    int evaluateBoard(PawnType playerPawn);
}
