package archive.ver1.Pieces;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

    public Pawn(Point where, char name, int color) {
        super(where, name, color);
    }

    public ArrayList<Point> moves(ChessPiece[][] board, ArrayList<ChessPiece> opp, Point king) {
        ArrayList<Point> list = new ArrayList<>();
        if (color == 0) {
            if (y != 0 && board[x + 1][y - 1] != null && board[x + 1][y - 1].getColor() != color) {
                list.add(new Point(x + 1, y - 1));
            }
            if (y != 7 && board[x + 1][y + 1] != null && board[x + 1][y + 1].getColor() != color) {
                list.add(new Point(x + 1, y + 1));
            }
            if (x < 7 && board[x + 1][y] == null) {
                list.add(new Point(x + 1, y));
            }
            if (moved == -1 && x < 6 && board[x + 1][y] == null && board[x + 2][y] == null) {
                list.add(new Point(x + 2, y));
            }
        } else if (color == 1) {
            if (y != 0 && board[x - 1][y - 1] != null && board[x - 1][y - 1].getColor() != color) {
                list.add(new Point(x - 1, y - 1));
            }
            if (y != 7 && board[x - 1][y + 1] != null && board[x - 1][y + 1].getColor() != color) {
                list.add(new Point(x - 1, y + 1));
            }
            if (x > 0 && board[x - 1][y] == null) {
                list.add(new Point(x - 1, y));
            }
            if (moved == -1 && x > 1 && board[x - 1][y] == null && board[x - 2][y] == null) {
                list.add(new Point(x - 2, y));
            }
        }

        return list;
    }

}