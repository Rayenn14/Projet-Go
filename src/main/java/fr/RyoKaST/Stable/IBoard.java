package fr.RyoKaST.Stable;

public interface IBoard {
    void clearBoard();

    void play(String player, String pos);

    void play(String player, int pos);

    void showBoard();

    String genmove(String player);
}
