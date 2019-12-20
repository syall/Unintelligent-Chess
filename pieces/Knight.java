package pieces;

import java.util.List;
import java.util.function.BiFunction;

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
        // TODO Auto-generated method stub
        return null;
    }

}
