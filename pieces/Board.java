package pieces;

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

}
