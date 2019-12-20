package pieces;

import java.util.List;
import java.util.function.Function;

public class Queen extends Piece {

    public Queen(char color) {
        super(color);
        this.type = 'q';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♛" : "♕";
    }

    @Override
    public List<Function<Point, List<Point>>> getMoveMethods() {
        // TODO Auto-generated method stub
        return null;
    }

}
