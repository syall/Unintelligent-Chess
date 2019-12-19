package pieces;

public class EmptyPiece extends Piece {

    public EmptyPiece(char color) {
        super(color);
    }

    @Override
    public String toString() {
        return "⠀";
    }

}
