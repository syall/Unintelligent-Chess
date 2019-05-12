package com;

import java.util.HashSet;
import java.util.Hashtable;

/**
 * Piece
 */
public class Piece {

    public char type; // p, r, n, b, k, q
    public char color;
    public boolean moved = false;
    public HashSet<Point> moves = new HashSet<>();

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

    /** TODO: Add check() to methods below
     * For each add
     * - Make copied Board temp
     * - Simulate move in temp
     * - if(!check(color, temp)) { add.add(new Point(row, col));}
     */

    public HashSet<Point> pawnMoves(Board board) {

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

        int direction = 1;
        if(color == 'b') {
            direction = -1;
        }

        // Forward 1 Space
        if(playing.get(row+direction+col) == null && waiting.get(row+direction+col) == null) {
            add.add(new Point(row+direction, col));
            // Forward 2 Spaces
            if(playing.get(row+direction*2+col) == null && waiting.get(row+direction*2+col) == null) {
                add.add(new Point(row+direction*2, col));
            }
        }

        // Capture a Piece Left
        if(waiting.get(row+direction+col-1) != null) {
            add.add(new Point(row+direction, col-1));
        }

        // Capture a Piece Right
        if(waiting.get(row+direction+col+1) != null) {
            add.add(new Point(row+direction, col+1));
        }

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
            else if(col+1 < 8) {
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
            else if(col+2 < 8) {
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
            else if(col+2 < 8) {
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
            else if(col+1 < 8) {
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
            if(col+1 <8) {
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
        for(int i = row-1; i >= 0; i--) {
            for(int j = col+1; j < 8; j++) {
                // White
                if(playing.get(i*8+j) != null) {
                    break;
                }
                // Black
                else {
                    add.add(new Point(i, j));
                    if(waiting.get(i*8+j) != null) {
                        break;
                    }
                }
            }
        }
        // Upper Left
        for(int i = row-1; i >= 0; i--) {
            for(int j = col-1; j >= 0; j--) {
                // White
                if(playing.get(i*8+j) != null) {
                    break;
                }
                // Black
                else {
                    add.add(new Point(i, j));
                    if(waiting.get(i*8+j) != null) {
                        break;
                    }
                }
            }
        }
        // Lower Right
        for(int i = row+1; i < 8; i++) {
            for(int j = col+1; j < 8; j++) {
                // White
                if(playing.get(i*8+j) != null) {
                    break;
                }
                // Black
                else {
                    add.add(new Point(i, j));
                    if(waiting.get(i*8+j) != null) {
                        break;
                    }
                }
            }
        }
        // Lower Left
        for(int i = row+1; i < 8; i++) {
            for(int j = col-1; j >= 0; j--) {
                // White
                if(playing.get(i*8+j) != null) {
                    break;
                }
                // Black
                else {
                    add.add(new Point(i, j));
                    if(waiting.get(i*8+j) != null) {
                        break;
                    }
                }
            }
        }

        return add;
    }
}