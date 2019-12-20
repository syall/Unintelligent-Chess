package pieces;

import java.util.List;
import java.util.function.Function;

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

    @Override
    public List<Function<Point, List<Point>>> getMoveMethods() {
        // TODO Auto-generated method stub
        return null;
    }

}
