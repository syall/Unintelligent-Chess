package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@SuppressWarnings("serial")
public class Knight extends Piece {

    public Knight(char color) {
        super(color);
        this.type = 'n';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♞" : "♘";
    }

    @Override
    public List<BiFunction<Point, Piece[][], List<Point>>> getMoveMethods() {
        return new ArrayList<BiFunction<Point, Piece[][], List<Point>>>() {
            {

            }
        };
    }

}
