package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@SuppressWarnings("serial")
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
    public List<BiFunction<Point, Piece[][], List<Point>>> getMoveMethods() {
        return new ArrayList<BiFunction<Point, Piece[][], List<Point>>>() {
            {
                add(diagonals);
                add(horizontals);
                add(verticals);
            }
        };
    }


}
