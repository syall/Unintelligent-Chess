package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Board {

    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board[row].length; col++)
                board[row][col] = generatePiece(row, col);
    }

    private Piece generatePiece(int row, int col) {
        char color = row < 2 ? 'w' : row > 5 ? 'b' : 'e';
        if (row == 1 || row == 6)
            return new Pawn(color);
        else if (row > 1 && row < 6)
            return new EmptyPiece(color);
        else if (col == 0 || col == 7)
            return new Rook(color);
        else if (col == 1 || col == 6)
            return new Knight(color);
        else if (col == 2 || col == 5)
            return new Bishop(color);
        else if (color == 'w')
            return col == 3 ? new Queen(color) : new King(color);
        else if (color == 'b')
            return col == 3 ? new King(color) : new Queen(color);
        else
            return null;
    }

    public String getPieces() {
        String black = "";
        String white = "";
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board[row].length; col++)
                if (board[row][col].color == 'b')
                    black += board[row][col].toString();
                else if (board[row][col].color == 'w')
                    white += board[row][col].toString();
        return String.format("Pieces:\nBlack: %s\nWhite: %s", black, white);
    }

    public String toString() {
        String header = "   a  b  c  d  e  f  g  h   ";
        String result = "Board:\n" + header + "\n";
        for (int row = 0; row < board.length; row++) {
            result += String.format("%s ", row + 1);
            for (int col = 0; col < board[row].length; col++)
                result += String.format("[%s]", board[row][col].toString());
            result += String.format(" %s\n", row + 1);
        }
        result += header;
        return result;
    }

    private Point generatePoint(String pos) {
        return new Point(Character.getNumericValue(pos.charAt(0)), pos.charAt(1) - 'a');
    }

    public boolean move(String start, String dest) {
        Point sPoint = generatePoint(start);
        Piece sPiece = board[sPoint.row][sPoint.col];
        List<Point> moves = new ArrayList<Point>();
        for (BiFunction<Point, Piece[][], List<Point>> f : sPiece.getMoveMethods())
            moves.addAll(f.apply(sPoint, board));
        Point fPoint = generatePoint(dest);
        boolean validMove = false;
        for (Point p : moves)
            if (fPoint.equals(p)) {
                validMove = true;
                break;
            }
        if (!validMove)
            return false;
        sPiece.moved = true;
        board[fPoint.row][fPoint.col] = sPiece;
        board[sPoint.row][sPoint.col] = new EmptyPiece('e');
        return true;
    }

}
