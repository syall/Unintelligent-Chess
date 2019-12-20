package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@SuppressWarnings("serial")
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
        return new ArrayList<BiFunction<Point, Piece[][], List<Point>>>() {
            {
                add(diagonals);
            }
        };
    }


}
