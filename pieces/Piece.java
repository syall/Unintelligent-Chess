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
        for (int i = 1; p.row - i >= 0 && p.col - i >= 0; i++) {
            if (board[p.row - i][p.col - i].color == piece.color)
                break;
            moves.add(new Point(p.row - i, p.col - i));
            if (board[p.row - i][p.col - i].color != 'e')
                break;
        }
        for (int i = 1; p.row - i >= 0 && p.col + i < 8; i++) {
            if (board[p.row - i][p.col + i].color == piece.color)
                break;
            moves.add(new Point(p.row - i, p.col + i));
            if (board[p.row - i][p.col + i].color != 'e')
                break;
        }
        for (int i = 1; p.row + i < 8 && p.col - i >= 0; i++) {
            if (board[p.row + i][p.col - i].color == piece.color)
                break;
            moves.add(new Point(p.row + i, p.col - i));
            if (board[p.row + i][p.col - i].color != 'e')
                break;
        }
        for (int i = 1; p.row + i < 8 && p.col + i < 8; i++) {
            if (board[p.row + i][p.col + i].color == piece.color)
                break;
            moves.add(new Point(p.row + i, p.col + i));
            if (board[p.row + i][p.col + i].color != 'e')
                break;
        }
        return moves;
    };

    public BiFunction<Point, Piece[][], List<Point>> pawn = (p, board) -> {
        ArrayList<Point> moves = new ArrayList<Point>();
        Piece piece = board[p.row][p.col];
        int d = piece.color == 'b' ? -1 : 1;
        char opp = piece.color == 'b' ? 'w' : 'b';
        if (p.row + d >= 0 && p.row + d < 8 && board[p.row + d][p.col].color == 'e') {
            moves.add(new Point(p.row + d, p.col));
            if (p.row + 2 * d >= 0 && p.row + 2 * d < 8 && board[p.row + 2 * d][p.col].color == 'e' && !piece.moved)
                moves.add(new Point(p.row + 2 * d, p.col));
        }
        if (p.row + d >= 0 && p.row + d < 8 && p.col - 1 >= 0 && board[p.row + d][p.col - 1].color == opp)
            moves.add(new Point(p.row + d, p.col - 1));
        if (p.row + d >= 0 && p.row + d < 8 && p.col + 1 < 8 && board[p.row + d][p.col + 1].color == opp)
            moves.add(new Point(p.row + d, p.col + 1));
        return moves;
    };

    public BiFunction<Point, Piece[][], List<Point>> king = (p, board) -> {
        ArrayList<Point> moves = new ArrayList<Point>();
        Piece piece = board[p.row][p.col];
        if (p.row - 1 >= 0 && p.col - 1 >= 0 && board[p.row - 1][p.col - 1].color != piece.color)
            moves.add(new Point(p.row - 1, p.col - 1));
        if (p.row - 1 >= 0 && p.col + 1 < 8 && board[p.row - 1][p.col + 1].color != piece.color)
            moves.add(new Point(p.row - 1, p.col + 1));
        if (p.row + 1 < 8 && p.col - 1 >= 0 && board[p.row + 1][p.col - 1].color != piece.color)
            moves.add(new Point(p.row + 1, p.col - 1));
        if (p.row + 1 >= 0 && p.col + 1 >= 0 && board[p.row + 1][p.col + 1].color != piece.color)
            moves.add(new Point(p.row + 1, p.col + 1));
        if (p.row - 1 >= 0 && board[p.row - 1][p.col].color != piece.color)
            moves.add(new Point(p.row - 1, p.col));
        if (p.row + 1 < 8 && board[p.row + 1][p.col - 1].color != piece.color)
            moves.add(new Point(p.row + 1, p.col));
        if (p.col - 1 >= 0 && board[p.row][p.col - 1].color != piece.color)
            moves.add(new Point(p.row, p.col - 1));
        if (p.col + 1 < 8 && board[p.row - 1][p.col + 1].color != piece.color)
            moves.add(new Point(p.row, p.col + 1));
        return moves;
    };

    public BiFunction<Point, Piece[][], List<Point>> knight = (p, board) -> {
        ArrayList<Point> moves = new ArrayList<Point>();
        Piece piece = board[p.row][p.col];
        if (p.row - 2 >= 0 && p.col - 1 >= 0 && board[p.row - 2][p.col - 1].color != piece.color)
            moves.add(new Point(p.row - 2, p.col - 1));
        if (p.row - 2 >= 0 && p.col + 1 < 8 && board[p.row - 2][p.col + 1].color != piece.color)
            moves.add(new Point(p.row - 2, p.col + 1));
        if (p.row - 1 >= 0 && p.col - 2 >= 0 && board[p.row - 1][p.col - 2].color != piece.color)
            moves.add(new Point(p.row - 1, p.col - 2));
        if (p.row - 1 >= 0 && p.col + 2 < 8 && board[p.row - 1][p.col + 2].color != piece.color)
            moves.add(new Point(p.row - 1, p.col + 2));
        if (p.row + 2 < 8 && p.col - 1 >= 0 && board[p.row + 2][p.col - 1].color != piece.color)
            moves.add(new Point(p.row + 2, p.col - 1));
        if (p.row + 2 < 8 && p.col + 1 < 8 && board[p.row + 2][p.col + 1].color != piece.color)
            moves.add(new Point(p.row + 2, p.col + 1));
        if (p.row + 1 < 8 && p.col - 2 >= 0 && board[p.row + 1][p.col - 2].color != piece.color)
            moves.add(new Point(p.row + 1, p.col - 2));
        if (p.row + 1 < 8 && p.col + 2 < 8 && board[p.row + 1][p.col + 2].color != piece.color)
            moves.add(new Point(p.row + 1, p.col + 2));
        return moves;
    };

}
