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

    // TODO: Get Legal Moves
    public boolean getMoves() {
        
        return true;
    }

    // TODO: Check for Castle
    public void castle() {

    }

    // TODO: Check for En Passant
    public void passant() {
        
    }
}