package pieces;

import java.util.List;
import java.util.function.Function;

public class Bishop extends Piece {

    public Bishop(char color) {
        super(color);
        this.type = 'b';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♝" : "♗";
    }

    @Override
    public List<Function<Point, List<Point>>> getMoveMethods() {
        // TODO Auto-generated method stub
        return null;
    }

}
