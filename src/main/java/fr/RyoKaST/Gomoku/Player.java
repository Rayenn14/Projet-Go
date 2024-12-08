package fr.RyoKaST.Gomoku;

import fr.RyoKaST.Stable.IPlayer;

import java.util.Set;
import java.util.TreeSet;

public class Player implements IPlayer {
    Set<Integer> pawnList;
    int pawnListSize;
    int boardSize;
    
    public Player(int boardSize) {
        this.boardSize = boardSize;
        pawnList = new TreeSet<>();
        pawnListSize = 0;
    }
    

    @Override
    public void play(int pos) {
        pawnList.add(pos);
    }
}
