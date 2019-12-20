package pieces;

import java.util.List;
import java.util.function.BiFunction;

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
        // TODO Auto-generated method stub
        return null;
    }


}
