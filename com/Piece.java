package com;

import java.util.HashSet;

/**
 * Piece
 */
public class Piece {

    public char type;
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

    /**
     * TODO: Get Legal Moves
     * - Horizontal
     * - Vertical
     * - Diagonal
     * - Knights
     */ 
    public boolean getMoves() {
        moves.add(new Point(2, 0));
        return true;
    }

}