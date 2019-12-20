package pieces;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public abstract class Piece {

    public char type;
    public char color;
    public HashSet<Point> moves = new HashSet<Point>();

    public Piece(char color) {
        this.color = color;
    }

    public abstract String toString();

    public abstract List<Function<Point, List<Point>>> getMoveMethods();

}
