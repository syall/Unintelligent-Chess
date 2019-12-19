package pieces;

public class King extends Piece {

    public boolean moved = false;

    public King(char color) {
        super(color);
        this.type = 'k';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♚" : "♔";
    }

}
