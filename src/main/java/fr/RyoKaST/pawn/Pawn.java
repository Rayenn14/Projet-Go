package fr.RyoKaST.pawn;

public class Pawn {
    
    PawnType pawType;

    public Pawn(PawnType pawType) {
        this.pawType = pawType;
    }

    public PawnType getPawType() {
        return pawType;
    }
    public void setPawType(PawnType pawType) {
        this.pawType = pawType;
    }
}
