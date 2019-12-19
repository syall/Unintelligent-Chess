package pieces;

import java.util.HashSet;

public abstract class Piece {

    public char type;
    public char color;
    public HashSet<Point> moves = new HashSet<Point>();

    public Piece(char color) {
        this.color = color;
    }

    abstract public String toString();

}
