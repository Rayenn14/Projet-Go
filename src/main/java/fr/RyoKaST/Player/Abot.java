package fr.RyoKaST.Player;

import fr.RyoKaST.Stable.IBoard;
import fr.RyoKaST.Stable.IJeu;
import fr.RyoKaST.Stable.Ibot;
import fr.RyoKaST.Stable.PawnType;

public abstract class Abot implements Ibot {
    protected IJeu jeu;
    protected IBoard board;

    public Abot() {
    }

    @Override
    public int genmove(IJeu jeu, PawnType playerPawn, int depth) {
        this.jeu = jeu;
        this.board = jeu.getBoard();
        return genmove(playerPawn, depth);
    }

    protected abstract int genmove(PawnType playerPawn, int depth);
}
