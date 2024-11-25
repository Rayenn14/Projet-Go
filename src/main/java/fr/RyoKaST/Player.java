package fr.RyoKaST;

import java.util.Set;
import java.util.TreeSet;

public class Player {
    Set<Integer> pawnList;
    int pawnListSize;
    int boardSize;
    
    public Player(int boardSize) {
        this.boardSize = boardSize;
        pawnList = new TreeSet<>();
        pawnListSize = 0;
    }
    

    public void play(int pos) {
        pawnList.add(pos);
    }
}
