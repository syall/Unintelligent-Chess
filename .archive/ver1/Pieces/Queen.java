package archive.ver1.Pieces;

import java.util.ArrayList;

public class Queen extends ChessPiece {

    public Queen (Point where, char name, int color){
        super(where, name, color);
    }

    public ArrayList<Point> moves(ChessPiece[][] board, ArrayList<ChessPiece> opp, Point king) {
        ArrayList<Point> list = new ArrayList<>();

        list = horizontal(board, list);
        list = vertical(board, list);
        list = diagonal(board, list);

        return list;
    }

}