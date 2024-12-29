package fr.RyoKaST.Stable;

import fr.RyoKaST.Gomoku.Pawn;

public interface IBoard {
    void clearBoard();

    void play(PawnType pawnType, int pos);

    String showBoard();

    int getBoardSize();

    Pawn getCase(int pos);

    void setCase(int pos, Pawn pawn);

    Pawn[] getBoard();
}
