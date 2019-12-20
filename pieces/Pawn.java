package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@SuppressWarnings("serial")
public class Pawn extends Piece {

    public boolean passant = false;
    public boolean moved = false;

    public Pawn(char color) {
        super(color);
        this.type = 'p';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♟" : "♙";
    }

    @Override
    public List<BiFunction<Point, Piece[][], List<Point>>> getMoveMethods() {
        return new ArrayList<BiFunction<Point, Piece[][], List<Point>>>() {
            {
                add(pawn);
            }
        };
    }

}
