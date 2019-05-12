package com;

import java.util.HashSet;
import java.util.Hashtable;

public class Piece {

    public char type; // p, n, k, r, q, b
    public char color;
    public boolean passant = false;
    public boolean moved = false;
    public HashSet<Point> moves = null;

    public Piece(char type, char color) {
        this.type = type;
        this.color = color;
    }

    public String toString() {
        return type + "-" + color;
    }

    public boolean getMoves(Board board) {

        moves = new HashSet<Point>();

        // Cardinals
        if (type == 'r' || type == 'q') {
            moves.addAll(this.cardinals(board));
        }
        // Diagonals
        if (type == 'b' || type == 'q') {
            moves.addAll(this.diagonals(board));
        }
        // Pawns
        if (type == 'p') {
            moves.addAll(this.pawnMoves(board));
        }
        // Knights
        if (type == 'n') {
            moves.addAll(this.knightMoves(board));
        }
        // King
        if (type == 'k') {
            moves.addAll(this.kingMoves(board));
        }
        
        // If no possible moves
        if (moves.isEmpty()) {
            return false;
        }

        // If possible moves
        return true;
    }

    // TODO: Add check() to Knight, King, Cardinals, Diagonals

    public HashSet<Point> pawnMoves(Board board){

        HashSet<Point> add = new HashSet<>();

        Hashtable<Integer, Piece> playing = board.white.pieces;
        Hashtable<Integer, Piece> waiting = board.black.pieces;
        if (color == 'b') {
            playing = board.black.pieces;
            waiting = board.white.pieces;
        }

        int hash = -1;
        for (Integer key : playing.keySet()) {
            if (playing.get(key).equals(this)) {
                hash = key;
                break;
            }
        }

        int row = hash / 8;
        int col = hash % 8;

        int direction = color == 'w' ? 1 : -1;

        // Forward 1 Space
        if(playing.get((row+direction)*8+col) == null && waiting.get((row+direction)*8+col) == null) {
            // Remove from Original Position
            playing.remove((row)*8 + col);
            // Put in Destination
            playing.put((row+direction)*8 + col, this);
            // Check
            if(!Board.check(color, board)) {
                add.add(new Point(row+direction, col));
            }
            // Remove from Destination
            playing.remove((row+direction)*8 + col);
            // Put in Original Position
            playing.put((row)*8 + col, this);

            // Forward 2 Spaces
            if(!moved && playing.get((row+direction*2)*8+col) == null && waiting.get((row+direction*2)*8+col) == null) {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row+direction*2)*8 + col, this);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row+direction*2, col));
                }
                // Remove from Destination
                playing.remove((row+direction*2)*8 + col);
                // Put in Original Position
                playing.put((row)*8 + col, this);
            }
        }

        // Capture a Piece Left
        if(waiting.get((row+direction)*8+col-1) != null) {
            // Remove from Original Position
            playing.remove((row)*8 + col);
            // Remove from Destination
            Piece replace = waiting.remove((row+direction)*8 + col-1);
            // Put in Destination
            playing.put((row+direction)*8 + col-1, this);
            // Check
            if(!Board.check(color, board)) {
                add.add(new Point(row+direction, col-1));
            }
            // Remove from Destination
            playing.remove((row+direction)*8 + col-1);
            // Put in Destination
            waiting.put((row+direction)*8 + col-1, replace);
            // Put in Original Position
            playing.put((row)*8 + col, this);
        }

        // Capture a Piece Right
        if(waiting.get((row+direction)*8+col+1) != null) {
            // Remove from Original Position
            playing.remove((row)*8 + col);
            // Remove from Destination
            Piece replace = waiting.remove((row+direction)*8 + col+1);
            // Put in Destination
            playing.put((row+direction)*8 + col+1, this);
            // Check
            if(!Board.check(color, board)) {
                add.add(new Point(row+direction, col+1));
            }
            // Remove from Destination
            playing.remove((row+direction)*8 + col+1);
            // Put in Destination
            waiting.put((row+direction)*8 + col+1, replace);
            // Put in Original Position
            playing.put((row)*8 + col, this);
        }

        // Return Legal Moves
        return add;
    }

    public HashSet<Point> knightMoves(Board board) {

        HashSet<Point> add = new HashSet<>();

        Hashtable<Integer, Piece> playing = board.white.pieces;
        Hashtable<Integer, Piece> waiting = board.black.pieces;
        if (color != board.white.color) {
            playing = board.black.pieces;
            waiting = board.white.pieces;
        }

        int hash = -1;
        for (Integer key : playing.keySet()) {
            if (playing.get(key).equals(this)) {
                hash = key;
                break;
            }
        }

        int row = hash / 8;
        int col = hash % 8;

        // Knight
        // Above 2
        if(row-2 >= 0) {
            // Left 1
            if(col-1 >= 0) {
                if(playing.get((row-2)*8 + col-1) == null) {
                    add.add(new Point(row-2, col-1));
                }
            }
            // Right 1
            if(col+1 < 8) {
                if(playing.get((row-2)*8 + col+1) == null) {
                    add.add(new Point(row-2, col+1));
                }
            }
        }
        // Above 1
        if(row-1 >= 0) {
            // Left 2
            if(col-2 >= 0) {
                if(playing.get((row-1)*8 + col-2) == null) {
                    add.add(new Point(row-1, col-2));
                }
            }
            // Right 2
            if(col+2 < 8) {
                if(playing.get((row-1)*8 + col+2) == null) {
                    add.add(new Point(row-1, col+2));
                }
            }
        }
        // Below 1
        if(row+1 < 8) {
            // Left 2
            if(col-2 >= 0) {
                if(playing.get((row+1)*8 + col-2) == null) {
                    add.add(new Point(row+1, col-2));
                }
            }
            // Right 2
            if(col+2 < 8) {
                if(playing.get((row+1)*8 + col+2) == null) {
                    add.add(new Point(row+1, col+2));
                }
            }
        }
        // Below 2
        if(row+2 < 8) {
            // Left 1
            if(col-1 >= 0) {
                if(playing.get((row+2)*8 + col-1) == null) {
                    add.add(new Point(row+2, col-1));
                }
            }
            // Right 1
            if(col+1 < 8) {
                if(playing.get((row+2)*8 + col+1) == null) {
                    add.add(new Point(row+2, col+1));
                }
            }
        }

        return add;
    }

    public HashSet<Point> kingMoves(Board board) {

        HashSet<Point> add = new HashSet<>();

        Hashtable<Integer, Piece> playing = board.white.pieces;
        Hashtable<Integer, Piece> waiting = board.black.pieces;
        if (color != board.white.color) {
            playing = board.black.pieces;
            waiting = board.white.pieces;
        }

        int hash = -1;
        for (Integer key : playing.keySet()) {
            if (playing.get(key).equals(this)) {
                hash = key;
                break;
            }
        }

        int row = hash / 8;
        int col = hash % 8;

        // Above
        if(row-1 >= 0) {
            if(playing.get((row-1)*8+col) == null) {
                add.add(new Point(row-1, col));
            }
            // Upper Left
            if(col-1 >= 0) {
                if(playing.get((row-1)*8+col-1) == null) {
                    add.add(new Point(row-1, col-1));
                }
            }
            // Upper Right
            if(col+1 < 8) {
                if(playing.get((row-1)*8+col+1) == null) {
                    add.add(new Point(row-1, col+1));
                }
            }
        }
        // Left
        if(col-1 >= 0) {
            if(playing.get((row)*8+col-1) == null) {
                add.add(new Point(row, col-1));
            }
        }
        // Right
        if(col+1 <8) {
            if(playing.get((row)*8+col+1) == null) {
                add.add(new Point(row, col+1));
            }
        } 
        // Below
        if(row+1 < 8) {
            if(playing.get((row+1)*8+col) == null) {
                add.add(new Point(row+1, col));
            }
            // Upper Left
            if(col-1 >= 0) {
                if(playing.get((row+1)*8+col-1) == null) {
                    add.add(new Point(row+1, col-1));
                }
            }
            // Upper Right
            if(col+1 <8) {
                if(playing.get((row+1)*8+col+1) == null) {
                    add.add(new Point(row+1, col+1));
                }
            }
        }

        return add;
    }

    public HashSet<Point> cardinals(Board board) {

        HashSet<Point> add = new HashSet<>();

        Hashtable<Integer, Piece> playing = board.white.pieces;
        Hashtable<Integer, Piece> waiting = board.black.pieces;
        if (color != board.white.color) {
            playing = board.black.pieces;
            waiting = board.white.pieces;
        }

        int hash = -1;
        for (Integer key : playing.keySet()) {
            if (playing.get(key).equals(this)) {
                hash = key;
                break;
            }
        }

        int row = hash / 8;
        int col = hash % 8;

        // Cardinals
        // Right
        for (int i = col + 1; i < 8; i++) {
            // White
            if (playing.get(row * 8 + i) != null) {
                break;
            }
            // Black
            else {
                add.add(new Point(row, i));
                if (waiting.get(row * 8 + i) != null) {
                    break;
                }
            }
        }
        // Left
        for (int i = col - 1; i >= 0; i--) {
            // White
            if (playing.get(row * 8 + i) != null) {
                break;
            }
            // Black
            else {
                add.add(new Point(row, i));
                if (waiting.get(row * 8 + i) != null) {
                    break;
                }
            }
        }
        // Above
        for (int i = row - 1; i >= 0; i--) {
            // White
            if (playing.get(i * 8 + col) != null) {
                break;
            }
            // Black
            else {
                add.add(new Point(i, col));
                if (waiting.get(i * 8 + col) != null) {
                    break;
                }
            }
        }
        // Below
        for (int i = row + 1; i < 8; i++) {
            // White
            if (playing.get(i * 8 + col) != null) {
                break;
            }
            // Black
            else {
                add.add(new Point(i, col));
                if (waiting.get(i * 8 + col) != null) {
                    break;
                }
            }
        }

        return add;
    }

    public HashSet<Point> diagonals(Board board) {

        HashSet<Point> add = new HashSet<>();

        Hashtable<Integer, Piece> playing = board.white.pieces;
        Hashtable<Integer, Piece> waiting = board.black.pieces;
        if (color != board.white.color) {
            playing = board.black.pieces;
            waiting = board.white.pieces;
        }

        int hash = -1;
        for (Integer key : playing.keySet()) {
            if (playing.get(key).equals(this)) {
                hash = key;
                break;
            }
        }

        int row = hash / 8;
        int col = hash % 8;

        // Diagonals
        // Upper Right
        for(int i = 1; row-i >= 0 && col+i < 8; i++) {
            // White
            if(playing.get((row-i)*8+col+i) != null) {
                break;
            }
            else {
                // null
                add.add(new Point(row-i, col+i));
                // Black
                if(waiting.get((row-i)*8+col+i) != null) {
                    break;
                }
            }
        }
        
        // Upper Left
        for(int i = 1; row-i >= 0 && col-i >= 0; i++) {
            // White
            if(playing.get((row-i)*8+col-i) != null) {
                break;
            }
            else {
                // null
                add.add(new Point(row-i, col-i));
                // Black
                if(waiting.get((row-i)*8+col-i) != null) {
                    break;
                }
            }
        }
        // Lower Right
        for(int i = 1; row+i < 8 && col+i < 8; i++) {
            // White
            if(playing.get((row+i)*8+col+i) != null) {
                break;
            }
            else {
                // null
                add.add(new Point(row+i, col+i));
                // Black
                if(waiting.get((row+i)*8+col+i) != null) {
                    break;
                }
            }
        }
        // Lower Left
        for(int i = 1; row+i < 8 && col-i >= 0; i++) {
            // White
            if(playing.get((row+i)*8+col-i) != null) {
                break;
            }
            else {
                // null
                add.add(new Point(row+i, col-i));
                // Black
                if(waiting.get((row+i)*8+col-i) != null) {
                    break;
                }
            }
        }

        return add;
    }

}