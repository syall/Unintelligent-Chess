package pieces;

public class Rook extends Piece {

    public boolean moved = false;

    public Rook(char color) {
        super(color);
        this.type = 'r';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♜" : "♖";
    }

}
