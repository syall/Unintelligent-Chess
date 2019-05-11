
import java.util.ArrayList;

public abstract class ChessPiece {
    
    char name;
    int color;
    int x;
    int y;
    int moved = -1;

    public ChessPiece(Point set, char name, int color) {
        this.name = name;
        this.color = color;
        this.x = set.x;
        this.y = set.y;
    }

    public abstract ArrayList<Point> moves(ChessPiece[][] board, ArrayList<ChessPiece> opp, Point king);

    public char getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setStatus() {
        this.moved = 0;
    }

    public int getStatus() {
        return moved;
    }

    public String toString() {
        char col = 'b';
        if(color == 1)
            col = 'w';
        return name + "-" + col;
    }

    public ArrayList<Point> horizontal(ChessPiece[][] board, ArrayList<Point> list) {
        for(int i = y - 1; i >= 0; i--) {
            if(board[x][i] == null) {
                list.add(new Point(x, i));
            }
            else {
                if(board[x][i].getColor() != color) {
                    list.add(new Point(x, i));
                }
                break;
            }
        }
        for(int i = y + 1; i <= 7; i++) {
            if(board[x][i] == null) {
                list.add(new Point(x, i));
            }
            else {
                if(board[x][i].getColor() != color) {
                    list.add(new Point(x, i));
                }
                break;
            }
        }
        return list;
    }

    public ArrayList<Point> vertical(ChessPiece[][] board, ArrayList<Point> list) {
        for(int i = x - 1; i >= 0; i--) {
            if(board[i][y] == null) {
                list.add(new Point(i, y));
            }
            else {
                if(board[i][y].getColor() != color) {
                    list.add(new Point(i, y));
                }
                break;
            }
        }
        for(int i = x + 1; i <= 7; i++) {
            if(board[i][y] == null) {
                list.add(new Point(i, y));
            }
            else {
                if(board[i][y].getColor() != color) {
                    list.add(new Point(i, y));
                }
                break;
            }
        }
        return list;
    }

    public ArrayList<Point> diagonal(ChessPiece[][] board, ArrayList<Point> list) {
        for(int i = 1; x-i >= 0 && y-i >= 0; i++) {
            if(board[x-i][y-i] == null) {
                list.add(new Point(x-i, y-i));
            }
            else {
                if(board[x-i][y-i].getColor() != color) {
                    list.add(new Point(x-i, y-i));
                }
                break;
            }
        }
        for(int i = 1; x-i >= 0 && y+i <= 7; i++) {
            if(board[x-i][y+i] == null) {
                list.add(new Point(x-i, y+i));
            }
            else {
                if(board[x-i][y+i].getColor() != color) {
                    list.add(new Point(x-i, y+i));
                }
                break;
            }
        }
        for(int i = 1; x+i <= 7 && y-i >=0; i++) {
            if(board[x+i][y-i] == null) {
                list.add(new Point(x+i, y-i));
            }
            else {
                if(board[x+i][y-i].getColor() != color) {
                    list.add(new Point(x+i, y-i));
                }
                break;
            }
        }
        for(int i = 1; x+i <= 7 && y+i <= 7; i++) {
            if(board[x+i][y+i] == null) {
                
                list.add(new Point(x+i, y+i));
            }
            else {
                if(board[x+i][y+i].getColor() != color) {
                    list.add(new Point(x+i, y+i));
                }
                break;
            }
        }

        return list;
    }

}