package pieces;

public class EmptyPiece extends Piece {

    public char type = 'e';

    public EmptyPiece(char color) {
        super(color);
    }

    @Override
    public String toString() {
        return "⠀";
    }

}
