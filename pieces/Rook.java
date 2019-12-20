package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@SuppressWarnings("serial")
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
    public List<BiFunction<Point, Piece[][], List<Point>>> getMoveMethods() {
        return new ArrayList<BiFunction<Point, Piece[][], List<Point>>>() {
            {
                add(horizontals);
                add(verticals);
            }
        };
    }

}
