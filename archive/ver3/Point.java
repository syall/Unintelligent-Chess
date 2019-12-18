package archive.ver3;

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

    public int hashCode() {
        return row * 8 + col;
    }

    public static Point unHashCode(Integer hash) {

        return new Point(hash / 8, hash % 8);
    }

    public boolean equals(Point obj) {
        return (row == obj.row) && (col == obj.col);
    }
}
