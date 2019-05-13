package com;

import java.util.HashSet;
import java.util.Hashtable;

public class Piece {

    public char type;
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
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row-2)*8 + col-1, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row-2)*8 + col-1);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row-2, col-1));
                    }
                    // Remove from Destination
                    playing.remove((row-2)*8 + col-1);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row-2)*8 + col-1, temp);
                }
            }
            // Right 1
            if(col+1 < 8) {
                if(playing.get((row-2)*8 + col+1) == null) {
                     // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row-2)*8 + col+1, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row-2)*8 + col+1);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row-2, col+1));
                    }
                    // Remove from Destination
                    playing.remove((row-2)*8 + col+1);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row-2)*8 + col+1, temp);
                }
            }
        }
        // Above 1
        if(row-1 >= 0) {
            // Left 2
            if(col-2 >= 0) {
                if(playing.get((row-1)*8 + col-2) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row-1)*8 + col-2, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row-1)*8 + col-2);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row-1, col-2));
                    }
                    // Remove from Destination
                    playing.remove((row-1)*8 + col-2);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row-1)*8 + col-2, temp);
                }
            }
            // Right 2
            if(col+2 < 8) {
                if(playing.get((row-1)*8 + col+2) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row-1)*8 + col+2, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row-1)*8 + col+2);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row-1, col+2));
                    }
                    // Remove from Destination
                    playing.remove((row-1)*8 + col+2);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row-1)*8 + col+2, temp);
                }
            }
        }
        // Below 1
        if(row+1 < 8) {
            // Left 2
            if(col-2 >= 0) {
                if(playing.get((row+1)*8 + col-2) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row+1)*8 + col-2, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row+1)*8 + col-2);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row+1, col-2));
                    }
                    // Remove from Destination
                    playing.remove((row+1)*8 + col-2);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row+1)*8 + col-2, temp);
                }
            }
            // Right 2
            if(col+2 < 8) {
                if(playing.get((row+1)*8 + col+2) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row+1)*8 + col+2, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row+1)*8 + col+2);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row+1, col+2));
                    }
                    // Remove from Destination
                    playing.remove((row+1)*8 + col+2);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row+1)*8 + col+2, temp);
                }
            }
        }
        // Below 2
        if(row+2 < 8) {
            // Left 1
            if(col-1 >= 0) {
                if(playing.get((row+2)*8 + col-1) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row+2)*8 + col-1, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row+2)*8 + col-1);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row+2, col-1));
                    }
                    // Remove from Destination
                    playing.remove((row+2)*8 + col-1);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row+2)*8 + col-1, temp);
                }
            }
            // Right 1
            if(col+1 < 8) {
                if(playing.get((row+2)*8 + col+1) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row+2)*8 + col+1, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row+2)*8 + col+1);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row+2, col+1));
                    }
                    // Remove from Destination
                    playing.remove((row+2)*8 + col+1);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row+2)*8 + col+1, temp);
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
            // Top
            if(playing.get((row-1)*8 + col) == null) {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row-1)*8 + col, this);
                // Remove from Destination
                Piece temp = waiting.remove((row-1)*8 + col);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row-1, col));
                }
                // Remove from Destination
                playing.remove((row-1)*8 + col);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null)
                    waiting.put((row-1)*8 + col, temp);
            }
            // Upper Left
            if(col-1 >= 0) {
                if(playing.get((row-1)*8 + col-1) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row-1)*8 + col-1, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row-1)*8 + col-1);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row-1, col-1));
                    }
                    // Remove from Destination
                    playing.remove((row-1)*8 + col-1);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row-1)*8 + col-1, temp);
                }
            }
            // Upper Right
            if(col+1 < 8) {
                if(playing.get((row-1)*8 + col+1) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row-1)*8 + col+1, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row-1)*8 + col+1);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row-1, col+1));
                    }
                    // Remove from Destination
                    playing.remove((row-1)*8 + col+1);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row-1)*8 + col+1, temp);
                }
            }
        }
        // Left
        if(col-1 >= 0) {
            if(playing.get((row)*8 + col-1) == null) {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row)*8 + col-1, this);
                // Remove from Destination
                Piece temp = waiting.remove((row)*8 + col-1);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row, col-1));
                }
                // Remove from Destination
                playing.remove((row)*8 + col-1);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null)
                    waiting.put((row)*8 + col-1, temp);
            }
        }
        // Right
        if(col+1 <8) {
            if(playing.get((row)*8 + col+1) == null) {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row)*8 + col+1, this);
                // Remove from Destination
                Piece temp = waiting.remove((row)*8 + col+1);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row, col+1));
                }
                // Remove from Destination
                playing.remove((row)*8 + col+1);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null)
                    waiting.put((row)*8 + col+1, temp);
            }
        } 
        // Below
        if(row+1 < 8) {
            // Bottom
            if(playing.get((row+1)*8 + col) == null) {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row+1)*8 + col, this);
                // Remove from Destination
                Piece temp = waiting.remove((row+1)*8 + col);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row+1, col));
                }
                // Remove from Destination
                playing.remove((row+1)*8 + col);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null)
                    waiting.put((row+1)*8 + col, temp);
            }
            // Upper Left
            if(col-1 >= 0) {
                if(playing.get((row+1)*8 + col-1) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row+1)*8 + col-1, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row+1)*8 + col-1);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row+1, col-1));
                    }
                    // Remove from Destination
                    playing.remove((row+1)*8 + col-1);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row+1)*8 + col-1, temp);
                }
            }
            // Upper Right
            if(col+1 <8) {
                if(playing.get((row+1)*8 + col+1) == null) {
                    // Remove from Original Position
                    playing.remove((row)*8 + col);
                    // Put in Destination
                    playing.put((row+1)*8 + col+1, this);
                    // Remove from Destination
                    Piece temp = waiting.remove((row+1)*8 + col+1);
                    // Check
                    if(!Board.check(color, board)) {
                        add.add(new Point(row+1, col+1));
                    }
                    // Remove from Destination
                    playing.remove((row+1)*8 + col+1);
                    // Put in Original Position
                    playing.put((row)*8 + col, this);
                    // Put in Destination
                    if(temp != null)
                        waiting.put((row+1)*8 + col+1, temp);
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
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row)*8 + i, this);
                // Remove from Destination
                Piece temp = waiting.remove((row)*8 + i);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row, i));
                }
                // Remove from Destination
                playing.remove((row)*8 + i);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null) {
                    waiting.put((row)*8 + i, temp);
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
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row)*8 + i, this);
                // Remove from Destination
                Piece temp = waiting.remove((row)*8 + i);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row, i));
                }
                // Remove from Destination
                playing.remove((row)*8 + i);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null) {
                    waiting.put((row)*8 + i, temp);
                    break;
                }
            }
        }
        // Above
        for (int i = row - 1; i >= 0; i--) {
            // White
            if (playing.get((i)*8 + col) != null) {
                break;
            }
            // Black
            else {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((i)*8 + col, this);
                // Remove from Destination
                Piece temp = waiting.remove((i)*8 + col);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(i, col));
                }
                // Remove from Destination
                playing.remove((i)*8 + col);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null) {
                    waiting.put((i)*8 + col, temp);
                    break;
                }
            }
        }
        // Below
        for (int i = row + 1; i < 8; i++) {
            // White
            if (playing.get((i)*8 + col) != null) {
                break;
            }
            // Black
            else {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((i)*8 + col, this);
                // Remove from Destination
                Piece temp = waiting.remove((i)*8 + col);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(i, col));
                }
                // Remove from Destination
                playing.remove((i)*8 + col);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null) {
                    waiting.put((i)*8 + col, temp);
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
            if(playing.get((row-i)*8 + col+i) != null) {
                break;
            }
            else {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row-i)*8 + col+i, this);
                // Remove from Destination
                Piece temp = waiting.remove((row-i)*8 + col+i);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row-i, col+i));
                }
                // Remove from Destination
                playing.remove((row-i)*8 + col+i);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null) {
                    waiting.put((row-i)*8 + col+i, temp);
                    break;
                }
            }
        }
        // Upper Left
        for(int i = 1; row-i >= 0 && col-i >= 0; i++) {
            // White
            if(playing.get((row-i)*8 + col-i) != null) {
                break;
            }
            else {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row-i)*8 + col-i, this);
                // Remove from Destination
                Piece temp = waiting.remove((row-i)*8 + col-i);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row-i, col-i));
                }
                // Remove from Destination
                playing.remove((row-i)*8 + col-i);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null) {
                    waiting.put((row-i)*8 + col-i, temp);
                    break;
                }
            }
        }
        // Lower Right
        for(int i = 1; row+i < 8 && col+i < 8; i++) {
            // White
            if(playing.get((row+i)*8 + col+i) != null) {
                break;
            }
            else {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row+i)*8 + col+i, this);
                // Remove from Destination
                Piece temp = waiting.remove((row+i)*8 + col+i);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row+i, col+i));
                }
                // Remove from Destination
                playing.remove((row+i)*8 + col+i);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null) {
                    waiting.put((row+i)*8 + col+i, temp);
                    break;
                }
            }
        }
        // Lower Left
        for(int i = 1; row+i < 8 && col-i >= 0; i++) {
            // White
            if(playing.get((row+i)*8 + col-i) != null) {
                break;
            }
            else {
                // Remove from Original Position
                playing.remove((row)*8 + col);
                // Put in Destination
                playing.put((row+i)*8 + col-i, this);
                // Remove from Destination
                Piece temp = waiting.remove((row+i)*8 + col-i);
                // Check
                if(!Board.check(color, board)) {
                    add.add(new Point(row+i, col-i));
                }
                // Remove from Destination
                playing.remove((row+i)*8 + col-i);
                // Put in Original Position
                playing.put((row)*8 + col, this);
                // Put in Destination
                if(temp != null) {
                    waiting.put((row+i)*8 + col-i, temp);
                    break;
                }
            }
        }

        return add;
    }

}