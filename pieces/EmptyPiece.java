package pieces;

import java.util.List;
import java.util.function.Function;

public class EmptyPiece extends Piece {

    public char type = 'e';

    public EmptyPiece(char color) {
        super(color);
    }

    @Override
    public String toString() {
        return "⠀";
    }

    @Override
    public List<Function<Point, List<Point>>> getMoveMethods() {
        // TODO Auto-generated method stub
        return null;
    }

}
