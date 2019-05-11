package archive.ver1.Pieces;

import java.util.ArrayList;

public class King extends ChessPiece {

    public King(Point where, char name, int color) {
        super(where, name, color);
    }

    public ArrayList<Point> moves(ChessPiece[][] board, ArrayList<ChessPiece> opp, Point king) {
        ArrayList<Point> list = new ArrayList<>();

        // Backwards
        if (x > 0 && (board[x - 1][y] == null || board[x - 1][y].getColor() != color)) {
            list.add(new Point(x - 1, y));
        }
        // Back Left
        if (x > 0 && y > 0 && (board[x - 1][y - 1] == null || board[x - 1][y - 1].getColor() != color)) {
            list.add(new Point(x - 1, y - 1));
        }
        // Back Right
        if (x > 0 && y < 7 && (board[x - 1][y + 1] == null || board[x - 1][y + 1].getColor() != color)) {
            list.add(new Point(x - 1, y + 1));
        }
        // Left
        if (y > 0 && (board[x][y - 1] == null || board[x][y - 1].getColor() != color)) {
            list.add(new Point(x, y - 1));
        }
        // Right
        if (y < 7 && (board[x][y + 1] == null || board[x][y + 1].getColor() != color)) {
            list.add(new Point(x, y + 1));
        }
        // Forward
        if (x < 7 && (board[x + 1][y] == null || board[x + 1][y].getColor() != color)) {
            list.add(new Point(x + 1, y));
        }
        // Forward Left
        if (x < 7 && y > 0 && (board[x + 1][y - 1] == null || board[x + 1][y - 1].getColor() != color)) {
            list.add(new Point(x + 1, y - 1));
        }
        // Forward Right
        if (x < 7 && y < 7 && (board[x + 1][y + 1] == null || board[x + 1][y + 1].getColor() != color)) {
            list.add(new Point(x + 1, y + 1));
        }

        return list;
    }

}