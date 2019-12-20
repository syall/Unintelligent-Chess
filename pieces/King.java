package pieces;

import java.util.List;
import java.util.function.Function;

public class King extends Piece {

    public boolean moved = false;

    public King(char color) {
        super(color);
        this.type = 'k';
    }

    @Override
    public String toString() {
        return color == 'b' ? "♚" : "♔";
    }

    @Override
    public List<Function<Point, List<Point>>> getMoveMethods() {
        // TODO Auto-generated method stub
        return null;
    }

}
