package archive.ver1.Pieces;

import java.util.ArrayList;

public class Bishop extends ChessPiece {

    public Bishop (Point where, char name, int color){
        super(where, name, color);
    }

    public ArrayList<Point> moves(ChessPiece[][] board, ArrayList<ChessPiece> opp, Point king){
        ArrayList<Point> list = new ArrayList<>();

        diagonal(board, list);
        
        return list;
    }

}