package pieces;

import java.util.List;
import java.util.function.BiFunction;

public class Bishop extends Piece {

    public Bishop(final char color) {
        super(color);
        this.type = 'b';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♝" : "♗";
    }

    @Override
    public List<BiFunction<Point, Piece[][], List<Point>>> getMoveMethods() {
        // TODO Auto-generated method stub
        return null;
    }


}
