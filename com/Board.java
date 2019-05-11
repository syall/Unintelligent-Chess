package com;

/**
 * Board
 */
public class Board {

    public int turn = 0;
    public Player white;
    public Player black;
    public boolean passant = false;

    public Board() {
        white = new Player('w');
        black = new Player('b'); 
    }

    // Show Board
    public String toString() {
        String output = "";

        // Check Rows
        for(int row = 0; row < 8; row++) {
            // Check Columns
            for (int col = 0; col < 8; col++) {
                Point temp = new Point(row, col);
                Piece n = null;
                // If white piece is ther
                if((n = white.pieces.get(temp.hashCode())) != null) {
                    output += "[" + n + "]";
                }
                // If black piece is there
                else if((n = black.pieces.get(temp.hashCode())) != null) {
                    output += "[" + n + "]";
                }
                // If no piece is there
                else {
                    output += "[---]";
                }
            }
            output += "\n";
        }
        return output;
    }

    /** 
     * TODO: Check
     * 
     */ 
    public boolean check() {
        
        return false;
    }

}