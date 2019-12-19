package pieces;

public class Pawn extends Piece {

    public boolean passant = false;
    public boolean moved = false;

    public Pawn(char color) {
        super(color);
        this.type = 'p';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♟" : "♙";
    }

}
