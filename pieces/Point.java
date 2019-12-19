package pieces;

public class Point {
    public int row;
    public int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String toString() {
        return "(" + row + "," + col + ")";
    }

    public boolean equals(Point obj) {
        return (row == obj.row) && (col == obj.col);
    }

}
