package archive.ver2.parts;
import java.util.ArrayList;

public class Combo {
    ArrayList<Piece> our;
    ArrayList<Piece> opp;
    boolean passant;

    public Combo(ArrayList<Piece> our, ArrayList<Piece> opp, boolean passant) {
        this.our = our;
        this.opp = opp;
        this.passant = passant;
    }

    public ArrayList<Piece> get(int i) {
        if (i == 0) {
            return our;
        }
        return opp;
    }

    public boolean getPassant() {
        return passant;
    }
}