package pieces;

public class Knight extends Piece {

    public Knight(char color) {
        super(color);
        this.type = 'n';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♞" : "♘";
    }

}
