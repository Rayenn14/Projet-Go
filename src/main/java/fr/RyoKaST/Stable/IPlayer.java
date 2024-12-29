package fr.RyoKaST.Stable;

public interface IPlayer {
    void play(IJeu jeu,int pos);
    public String getColor();
    public PawnType getPawnType();
}