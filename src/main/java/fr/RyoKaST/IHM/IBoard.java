package fr.RyoKaST.IHM;

public interface IBoard {
    void clearBoard();

    void play(String player, String pos);

    String genmove(String player);

    void showBoard();
}
