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

    public BiFunction<Point, Piece[][], List<Point>> verticals = (p, board) -> {
        ArrayList<Point> moves = new ArrayList<Point>();
        Piece piece = board[p.row][p.col];
        for (int i = p.row - 1; i >= 0; i--) {
            if (board[i][p.col].color == piece.color)
                break;
            moves.add(new Point(i, p.col));
            if (board[i][p.col].color != 'e')
                break;
        }
        for (int i = p.row + 1; i < board.length; i++) {
            if (board[i][p.col].color == piece.color)
                break;
            moves.add(new Point(i, p.col));
            if (board[i][p.col].color != 'e')
                break;
        }
        return moves;
    };

    public BiFunction<Point, Piece[][], List<Point>> diagonals = (p, board) -> {
        ArrayList<Point> moves = new ArrayList<Point>();
        Piece piece = board[p.row][p.col];
        // Upper Left
        for (int i = 1; p.row - i >= 0 && p.col - i >= 0; i++) {
            if (board[p.row - i][p.col - i].color == piece.color)
                break;
            moves.add(new Point(p.row - i, p.col - i));
            if (board[p.row - i][p.col - i].color != 'e')
                break;
        }
        // Upper Right
        for (int i = 1; p.row - i >= 0 && p.col + i < 8; i++) {
            if (board[p.row - i][p.col + i].color == piece.color)
                break;
            moves.add(new Point(p.row - i, p.col + i));
            if (board[p.row - i][p.col + i].color != 'e')
                break;
        }
        // Lower Left
        for (int i = 1; p.row + i < 8 && p.col - i >= 0; i++) {
            if (board[p.row + i][p.col - i].color == piece.color)
                break;
            moves.add(new Point(p.row + i, p.col - i));
            if (board[p.row + i][p.col - i].color != 'e')
                break;
        }
        // Lower Right
        for (int i = 1; p.row + i < 8 && p.col + i < 8; i++) {
            if (board[p.row + i][p.col + i].color == piece.color)
                break;
            moves.add(new Point(p.row + i, p.col + i));
            if (board[p.row + i][p.col + i].color != 'e')
                break;
        }
        return moves;
    };

}
