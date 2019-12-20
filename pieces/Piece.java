package pieces;

import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;

public abstract class Piece {

    public char type;
    public char color;
    public HashSet<Point> moves = new HashSet<Point>();
    public boolean moved;

    public Piece(char color) {
        this.color = color;
    }

    public abstract String toString();

    public abstract List<BiFunction<Point, Piece[][], List<Point>>> getMoveMethods();

}
