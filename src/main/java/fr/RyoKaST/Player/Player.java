package fr.RyoKaST.Player;

import fr.RyoKaST.Stable.IJeu;
import fr.RyoKaST.Stable.IPlayer;
import fr.RyoKaST.Stable.PawnType;

import java.util.Set;
import java.util.TreeSet;

public class Player implements IPlayer {
    Set<Integer> pawnList;
    int pawnListSize;
    int boardSize;
    PawnType pawnType;
    
    public Player(int boardSize, PawnType pawnType) {
        this.boardSize = boardSize;
        pawnList = new TreeSet<>();
        this.pawnType = pawnType;
        pawnListSize = 0;
    }

    @Override
    public void play(IJeu jeu, int pos) {
        jeu.getBoard().play(getPawnType(), pos);
        pawnList.add(pos);
    }

    @Override
    public PawnType getPawnType() {
        return pawnType;
    }


    @Override
    public String getColor() {
        return pawnType == PawnType.white ? "white" : "black";
    }
}
