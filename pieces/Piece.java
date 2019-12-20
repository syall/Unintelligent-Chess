package pieces;

import java.util.ArrayList;
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

    public BiFunction<Point, Piece[][], List<Point>> horizontals = (p, board) -> {
        ArrayList<Point> moves = new ArrayList<Point>();
        Piece piece = board[p.row][p.col];
        for (int i = p.col - 1; i >= 0; i--) {
            if (board[p.row][i].color == piece.color)
                break;
            moves.add(new Point(p.row, i));
            if (board[p.row][i].color != 'e')
                break;
        }
        for (int i = p.col + 1; i < board.length; i++) {
            if (board[p.row][i].color == piece.color)
                break;
            moves.add(new Point(p.row, i));
            if (board[p.row][i].color != 'e')
                break;
        }
        return moves;
    };

}
