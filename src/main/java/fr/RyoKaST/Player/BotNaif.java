package fr.RyoKaST.Player;

import fr.RyoKaST.Stable.PawnType;

public class BotNaif extends Abot {

    public BotNaif() {
        super();
    }

    @Override
    protected int genmove(PawnType playerPawn, int depth) {
        return getFirstEmptySpot();
    }

    private int getFirstEmptySpot() {
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                int index = row * board.getBoardSize() + col;
                if (board.getCase(index) == null) {
                    return index;
                }
            }
        }
        return -1;
    }

}
