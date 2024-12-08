package fr.RyoKaST.Stable;

public interface IBoard {
    void clearBoard();

    void play(String player, String pos);

    String genmove(String player);

    void showBoard();
}
