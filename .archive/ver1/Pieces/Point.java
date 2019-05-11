package archive.ver1.Pieces;
public class Point {

    public int x = 0;
    public int y = 0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return x + ", " + y;
    }
}