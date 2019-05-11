package archive.ver1.Pieces;

import java.util.ArrayList;

public class Knight extends ChessPiece {

    public Knight(Point where, char name, int color) {
        super(where, name, color);
    }

    public ArrayList<Point> moves(ChessPiece[][] board, ArrayList<ChessPiece> opp, Point king) {
        ArrayList<Point> list = new ArrayList<>();

        // Forward Left
        if (x < 6 && y > 0 && (board[x + 2][y - 1] == null || board[x + 2][y - 1].getColor() != color)) {
            list.add(new Point(x + 2, y - 1));
        }
        // Forward Right
        if (x < 6 && y < 7 && (board[x + 2][y + 1] == null || board[x + 2][y + 1].getColor() != color)) {
            list.add(new Point(x + 2, y + 1));
        }
        // Left Back
        if (x > 0 && y > 1 && (board[x - 1][y - 2] == null || board[x - 1][y - 2].getColor() != color)) {
            list.add(new Point(x - 1, y - 2));
        }
        // Left Forward
        if (x < 7 && y > 1 && (board[x + 1][y - 2] == null || board[x + 1][y - 2].getColor() != color)) {
            list.add(new Point(x + 1, y - 2));
        }
        // Back Left
        if(x > 1 && y > 0 && (board[x-2][y-1] == null || board[x-2][y-1].getColor() != color)) {
            list.add(new Point(x-2, y - 1));
        }
        // Back Right
        if(x > 1 && y < 7 && (board[x-2][y+1] == null || board[x-2][y+1].getColor() != color)) {
            list.add(new Point(x-2, y + 1));
        }
        // Right Back
        if(x > 0 && y < 6 && (board[x-1][y+2] == null || board[x-1][y+2].getColor() != color)) {
            list.add(new Point(x-1, y+2));
        }
        // Right Forward
        if(x < 7 && y < 6 && (board[x+1][y+2] == null || board[x+1][y+2].getColor() != color)) {
            list.add(new Point(x+1, y+2));
        }

        return list;
    }

}