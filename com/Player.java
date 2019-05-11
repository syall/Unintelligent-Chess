package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * Player
 */
public class Player {

    public char color;
    public Hashtable<Integer, Piece> pieces = new Hashtable<>();

    // Initialize Player Color and Pieces
    public Player(char color) {

        // Set Player
        this.color = color;

        // Set Row for Pawns
        int row = 1;
        if(color == 'b') {
            row = 6;
        }
        
        // Pawns
        for(int i = 0; i < 8; i++) {
            pieces.put(new Point(row, i).hashCode(), new Piece('p', color));
        }

        // Set Row for Other Pieces
        row = row == 1 ? 0 : 7;

        // Rooks
        pieces.put(new Point(row, 0).hashCode(), new Piece('r', color));
        pieces.put(new Point(row, 7).hashCode(), new Piece('r', color));

        // Knights
        pieces.put(new Point(row, 1).hashCode(), new Piece('n', color));
        pieces.put(new Point(row, 6).hashCode(), new Piece('n', color));

        // Bishops
        pieces.put(new Point(row, 2).hashCode(), new Piece('b', color));
        pieces.put(new Point(row, 5).hashCode(), new Piece('b', color));

        // Queen
        pieces.put(new Point(row, 3).hashCode(), new Piece('q', color));

        // King
        pieces.put(new Point(row, 4).hashCode(), new Piece('k', color));

    }

    public String toString() {
        String output =  color + ":\n";
        // Get Points and Sort them
        ArrayList<Integer> keys = new ArrayList<Integer>(pieces.keySet());
        Collections.sort(keys);

        // Unhash and Print Pairings
        int line = 1;
        for(Integer key : keys) {
            output += "[" + pieces.get(key);
            output += Point.unHashCode(key) + "]";
            if(line%4 == 0) {
                output += "\n";
            }
            line++;
        }

        return output;
    }
    
}