package pieces;

public class Queen extends Piece {

    public Queen(char color) {
        super(color);
        this.type = 'q';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♛" : "♕";
    }

}
