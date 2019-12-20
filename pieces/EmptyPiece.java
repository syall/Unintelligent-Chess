package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

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
    public List<BiFunction<Point, Piece[][], List<Point>>> getMoveMethods() {
        return new ArrayList<BiFunction<Point, Piece[][], List<Point>>>();
    }

}
