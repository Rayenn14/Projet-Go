package fr.RyoKaST.Stable;

public interface IJeu {
    void setBoardSize(String size);

    void clearBoard();

    void play(String player, String pos);

    void showBoard();

    String genMove(String s);
}
