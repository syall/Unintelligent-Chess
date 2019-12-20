package pieces;

import java.util.List;
import java.util.function.Function;

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

    @Override
    public List<Function<Point, List<Point>>> getMoveMethods() {
        // TODO Auto-generated method stub
        return null;
    }

}
