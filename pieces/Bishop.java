package pieces;

public class Bishop extends Piece {

    public Bishop(char color) {
        super(color);
        this.type = 'b';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♝" : "♗";
    }
    
}
