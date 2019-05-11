package archive.ver2.parts;

import java.util.ArrayList;

public class Piece {
    char name;
    boolean moved = false;
    char color;
    int x;
    int y;

    public Piece(int x, int y, char name, char color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    // TO DO: Moves
    public ArrayList<Point> moves(Piece ourKing, ArrayList<Piece> opp, boolean passant)
    // = Positions in the 2 Lists and !check()
    {
        // Check = Chess.check(ourKing, opp)

        ArrayList<Point> list = new ArrayList<>();
        // Pawn
        if (name == 'p') {
            if (passant == true) {
                // Add En Passant Position(s)
            }
            // Add Regular Pawn Movements/Check
            // Add Pawn Capture/Check
        }
        // Rook
        else if (name == 'r') {
            // Check for Castle Left
            // Check for Castle Right
            // Add Horizontals/Check
            // Add Verticals/Check
        }
        // King
        else if (name == 'k') {
            // Check for Castle Left
            // Check for Castle Right
            // Add Regular King Movements/Check
        }
        // Queen
        else if (name == 'q') {
            // Add Diagonals/Check
            // Add Horizontals/Check
            // Add Verticals/Check
        }
        // Knight
        else if (name == 'n') {
            // Add Regular Knight Movements/Check
        }
        // Bishop
        else if (name == 'b') {
            // Add Diagonals/Check
        }
        return list;
    }

    // Move, setting new piece lists and passant
    public Combo move(int index, ArrayList<Point> list, ArrayList<Piece> our, ArrayList<Piece> opp) {
        // Select move where from list at index
        Point where = list.get(index);
        int x = where.getX();
        int y = where.getY();

        // Move will allow opponent to do Passant Potentially
        boolean passant = false;
        if (this.name == 'p' && moved == false) {
            if (x == this.x + 2 || x == this.x - 2)
                passant = true;
        }
        moved = true;

        // See if the move ate an Enemy Piece
        int i;
        if ((i = Chess.position(opp, where)) != -1) {
            opp.remove(i);
        }

        // New Coordinates for Piece
        this.x = x;
        this.y = y;

        // Return Results
        return new Combo(our, opp, passant);
    }

    public char getColor() {
        return color;
    }

    public ArrayList<Point> horizontal(Piece ourKing, ArrayList<Piece> our, ArrayList<Piece> opp) {
        // List of Possible Moves
        ArrayList<Point> list = new ArrayList<>();

        // Right of the Piece
        for (int i = y + 1; i <= 7; i++) {
            Point tempPoint = new Point(x, i);
            int index;
            // If own piece, stop
            if (Chess.position(our, tempPoint) != -1) {
                break;
            }
            // If not own piece, check
            else if ((index = Chess.position(opp, tempPoint)) != -1) {
                ArrayList<Piece> tempArray1 = our;
                ArrayList<Piece> tempArray2 = opp;
                for (Piece n : tempArray1) {
                    if (n.equals(this)) {
                        n.setX(x);
                        n.setY(i);
                        break;
                    }
                }
                for (int j = 0; j < tempArray2.size(); j++) {
                    if (tempArray2.get(j).equals(opp.get(index))) {
                        tempArray2.remove(j);
                        break;
                    }
                }
                if (Chess.check(ourKing, tempArray1, tempArray2) == false) {
                    list.add(tempPoint);
                    break;
                }
            }
            // If it is null
            else {
                list.add(tempPoint);
            }
        }
        // Left of the Piece
        for (int i = y - 1; i >= 0; i--) {
            Point tempPoint = new Point(x, i);
            int index;
            // If own piece, stop
            if (Chess.position(our, tempPoint) != -1) {
                break;
            }
            // If not own piece, check
            else if ((index = Chess.position(opp, tempPoint)) != -1) {
                ArrayList<Piece> tempArray1 = our;
                ArrayList<Piece> tempArray2 = opp;
                for (Piece n : tempArray1) {
                    if (n.equals(this)) {
                        n.setX(x);
                        n.setY(i);
                        break;
                    }
                }
                for (int j = 0; j < tempArray2.size(); j++) {
                    if (tempArray2.get(j).equals(opp.get(index))) {
                        tempArray2.remove(j);
                        break;
                    }
                }
                if (Chess.check(ourKing, tempArray1, tempArray2) == false) {
                    list.add(tempPoint);
                    break;
                }
            }
            // If it is null
            else {
                list.add(tempPoint);
            }
        }

        return list;
    }

    public boolean horizontalCheck(ArrayList<Piece> our, ArrayList<Piece> opp) {
        // Right of the Piece
        
        for (int i = y + 1; i <= 7; i++) {
            Point tempPoint = new Point(x, i);
            int index;
            // If own piece, stop
            if (Chess.position(our, tempPoint) != -1) {
                return false;
            }
            // If not own piece, check
            else if ((index = Chess.position(opp, tempPoint)) != -1) {
                if(opp.get(index).getName() == 'k' && i == y+1) {
                    return true;
                }
                else if(opp.get(index).getName() == 'q' || opp.get(index).getName() == 'r') {
                    return true;
                }
            }
        }
        return false;
    }
    
    public ArrayList<Point> vertical(Piece ourKing, ArrayList<Piece> our, ArrayList<Piece> opp) {
        // List of Possible Moves
        ArrayList<Point> list = new ArrayList<>();

        // Below the Piece
        for (int i = x + 1; i <= 7; i++) {
            Point tempPoint = new Point(i, y);
            int index;
            // If own piece, stop
            if (Chess.position(our, tempPoint) != -1) {
                break;
            }
            // If not own piece, check
            else if ((index = Chess.position(opp, tempPoint)) != -1) {
                ArrayList<Piece> tempArray1 = our;
                ArrayList<Piece> tempArray2 = opp;
                for (Piece n : tempArray1) {
                    if (n.equals(this)) {
                        n.setX(i);
                        n.setY(y);
                        break;
                    }
                }
                for (int j = 0; j < tempArray2.size(); j++) {
                    if (tempArray2.get(j).equals(opp.get(index))) {
                        tempArray2.remove(j);
                        break;
                    }
                }
                if (Chess.check(ourKing, tempArray1, tempArray2) == false) {
                    list.add(tempPoint);
                    break;
                }
            }
            // If it is null
            else {
                list.add(tempPoint);
            }
        }
        // Left of the Piece
        for (int i = x - 1; i >= 0; i--) {
            Point tempPoint = new Point(i, y);
            int index;
            // If own piece, stop
            if (Chess.position(our, tempPoint) != -1) {
                break;
            }
            // If not own piece, check
            else if ((index = Chess.position(opp, tempPoint)) != -1) {
                ArrayList<Piece> tempArray1 = our;
                ArrayList<Piece> tempArray2 = opp;
                for (Piece n : tempArray1) {
                    if (n.equals(this)) {
                        n.setX(i);
                        n.setY(y);
                        break;
                    }
                }
                for (int j = 0; j < tempArray2.size(); j++) {
                    if (tempArray2.get(j).equals(opp.get(index))) {
                        tempArray2.remove(j);
                        break;
                    }
                }
                if (Chess.check(ourKing, tempArray1, tempArray2) == false) {
                    list.add(tempPoint);
                    break;
                }
            }
            // If it is null
            else {
                list.add(tempPoint);
            }
        }

        return list;
    }

    public boolean verticalCheck(ArrayList<Piece> our, ArrayList<Piece> opp) {
        // Right of the Piece
        for (int i = x + 1; i <= 7; i++) {
            Point tempPoint = new Point(i, y);
            int index;
            // If own piece, stop
            if (Chess.position(our, tempPoint) != -1) {
                return false;
            }
            // If not own piece, check
            else if ((index = Chess.position(opp, tempPoint)) != -1) {
                if(opp.get(index).getName() == 'k' && i == y+1) {
                    return true;
                }
                else if(opp.get(index).getName() == 'q' || opp.get(index).getName() == 'r') {
                    return true;
                }
            }
        }
        return false;
    }

    // TO DO: Diagonal
    public ArrayList<Point> diagonal(Piece ourKing, ArrayList<Piece> our, ArrayList<Piece> opp) {
        ArrayList<Point> list = new ArrayList<>();

        return list;
    }

    // TO DO: Diagonal Check
    public boolean diagonalCheck(ArrayList<Piece> our, ArrayList<Piece> opp) {

        return false;
    }

    // TO DO: Knight
    public ArrayList<Point> knight(Piece ourKing, ArrayList<Piece> our, ArrayList<Piece> opp) {
        ArrayList<Point> list = new ArrayList<>();

        return list;
    }

    // TO DO: Knight Check
    public boolean knightCheck(ArrayList<Piece> our, ArrayList<Piece> opp) {
    
        return false;
    }

    // TO DO: Pawn
    public ArrayList<Point> pawn(Piece ourKing, ArrayList<Piece> our, ArrayList<Piece> opp) {
        ArrayList<Point> list = new ArrayList<>();

        return list;
    }

    // TO DO: Pawn Check
    public boolean pawnCheck(ArrayList<Piece> our, ArrayList<Piece> opp) {
    
        return false;
    }

    // TO DO: Passant
    public ArrayList<Point> passant(Piece ourKing, ArrayList<Piece> our, ArrayList<Piece> opp) {
    ArrayList<Point> list = new ArrayList<>();

        return list;
    }

    // TO DO: Castle
    public ArrayList<Point> castle(Piece ourKing, ArrayList<Piece> our, ArrayList<Piece> opp) {
        ArrayList<Point> list = new ArrayList<>();
    
        return list;
    } 

}