package archive.ver2.parts;

import java.util.ArrayList;
import java.util.Scanner;

public class Chess {
    public static void main(String[] args) {

        // Scanner
        Scanner in = new Scanner(System.in);

        // Initialize "Board"
        Combo start = initBoard();

        // 2-Space Pawn Move Last Turn
        // Since En Passant needs move from previous turn, it needs to be outside of the loops
        boolean passant = false;

        // Lists
        // Alternating based on Previous Turn
        ArrayList<Piece> our = start.get(1);
        ArrayList<Piece> opp = start.get(0);
        ArrayList<Piece> temp = null;

        // Turn
        int turnCount = 0;
        while(true) {
            // Turn Count
            System.out.println("Turn " + turnCount + " ---------------------------------");

            // Set Kings
            Piece ourKing = null;
            Piece oppKing = null;

            // Kings name based on Turn Count
            if(turnCount%2 == 0) {
                ourKing = getKing(our);
                oppKing = getKing(opp);
            }
            else {
                ourKing = getKing(opp);
                oppKing = getKing(our);
            }

            // Switch our and opp
            temp = our;
            our = opp;
            opp = temp;

            // Default Information
            showBoard(our, opp);
            showPieces(our, opp);

            // Pair of new lists after move
            Combo pair = null;

            // List of moves for piece chosen
            ArrayList<Point> list = null;

            Piece n = null;

            // Input Loop until Piece is Moved
            while(true) {
                System.out.print("input: ");
                String input = in.nextLine();
                /*
                Not Mandatory
                    showBoard()
                    showPieces()
                    showMoves() after piece is chosen
                */
                if(input.equals("board")) {
                    showBoard(our, opp);
                }
                else if(input.equals("pieces")) {
                    showPieces(our, opp);
                }
                else if(input.equals("moves")) {
                    if(list == null) {
                        System.out.println("Choose a piece first");
                    }
                    else {
                        showMoves(list);
                    }
                }
                /*
                Mandatory
                    Piece = piece(index)
                    pair = move() -> break;
                */
                else if(input.equals("piece")) {
                    int x = 0;
                    int y = 0;
                    // Input Loop to get Piece
                    while (true) {
                        System.out.print("piece coordinates:\nx: ");
                        try {
                            x = Integer.parseInt(in.nextLine());
                            break;
                        } catch (NumberFormatException nfe) {
                            System.out.println("not valid. try again");
                        }
                    }
                    while (true) {
                        System.out.print("y: ");
                        try {
                            y = Integer.parseInt(in.nextLine());
                            break;
                        } catch (NumberFormatException nfe) {
                            System.out.println("not valid. try again");
                        }
                    }
                    n = piece(new Point(x, y), ourKing, opp, our, passant);
                    if(n != null) {
                        list = n.moves(ourKing, opp, passant);
                        showMoves(list);
                    }
                }
                else if(input.equals("move")) {
                    showMoves(list);
                    int index = 0;
                    // Input Loop to get Index
                    while (true) {
                        showMoves(list);
                        System.out.print("index of move: ");
                        try {
                            index = Integer.parseInt(in.nextLine());
                            break;
                        } catch (NumberFormatException nfe) {
                            System.out.println("not valid. try again");
                        }
                    }
                    pair = n.move(index, list, our, opp);
                    break;
                }
                else {
                    System.out.println("Invalid Command");
                }
            }
            
            // Update passant
            passant = pair.getPassant();

            // Update lists
            our = pair.get(0);
            opp = pair.get(1);

            // Check
            if(check(oppKing, our, opp)) {
                // Checkmate
                if(!movable(oppKing, our, opp, passant)) {
                    // White Wins!
                    if(turnCount%2 == 0) {
                        System.out.println("White Wins!");
                    }
                    // Black Wins!
                    else {
                        System.out.println("Black Wins!");
                    }
                    break;
                }
                //opponent in check
                if(turnCount%2 == 0) {
                    System.out.println("Black is in Check");
                }
                // Black Wins!
                else {
                    System.out.println("White is in Check");
                }
            }
            // Stalemate
            if(!movable(oppKing, our, opp, passant)) {
                //stalemate
                System.out.println("Stalemate");
                break;
            }

            // Increase Turn Count
            turnCount++;
        }

        // Close Scanner
        in.close();
    }

    // Select a Piece
    public static Piece piece(Point where, Piece ourKing, ArrayList<Piece> opp, ArrayList<Piece> our, boolean passant) {
        // Select Piece n from our by where
        int index = position(our, where);
        if(index != -1) {
            Piece n = our.get(index);
            // If Piece is Yours
            if(n.getColor() == ourKing.getColor()) {
                System.out.println("Piece Selected");
                return n;
            }
            else {
                // If Piece is not Yours
                System.out.println("Piece is not yours");
                return null;
            }
        }

        System.out.println("No Piece Selected");
        return null;
    }

    // TO DO: Check
    public static boolean check(Piece king, ArrayList<Piece> our, ArrayList<Piece> opp)
    // opposite side's king search outward 
    {
        // Point where = new Point(king.getX(), king.getY());

        /*
        Diagonals until first piece found can reach the square
            Pawn, King if immediate
            Bishop, Queen
        */
        
        /*
        Horizontals
            King if immediate
            Rook, Queen
        */

        /*
        Verticals
            King if immediate
            Rook, Queen
        */

        /*
        Knights Hard Code
        */

        return false;
    }

    // Checks if a Piece is at a Position
    public static int position(ArrayList<Piece> list, Point where) {
        int x = where.x;
        int y = where.y;

        for(int i = 0; i < list.size(); i++) {
            Piece n = list.get(i);
            if(n.getX() == x && n.getY() == y) {
                return i;
            }
        }

        return -1;
    }

    // Displays the Board
    public static void showBoard(ArrayList<Piece> our, ArrayList<Piece> opp) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int index;
                if ((index = position(our, new Point(i, j))) != -1) {
                    System.out.print("[" + our.get(index).getName() + "-" + our.get(index).getColor() + "]");

                } else if ((index = position(opp, new Point(i, j))) != -1) {
                    System.out.print("[" + opp.get(index).getName() + "-" + opp.get(index).getColor() + "]");
                } else {
                    System.out.print("[---]");
                }
            }
            System.out.println();
        }
    }

    // Displays all Pieces
    public static void showPieces(ArrayList<Piece> our, ArrayList<Piece> opp) {
        char ourColor = 'w';
        char oppColor = 'b';
        if(our.get(0).getColor() == 'b') {
            ourColor = 'b';
            oppColor = 'w';
        }
        System.out.print(ourColor + ":");
        for (Piece n : our) {
            System.out.print(n.getName());
        }
        System.out.println();
        System.out.print(oppColor + ":");
        for (Piece n : opp) {
            System.out.print(n.getName());
        }
        System.out.println();
    }

    // Displays all Moves for Selected Piece
    public static void showMoves(ArrayList<Point> list) {
        int i;
        for (i = 0; i < list.size(); i++) {
            Point n = list.get(i);
            System.out.print("[" + i + ":(" + n.getX() + "," + n.getY() + ")]");
            if ((i + 1) % 4 == 0) {

            }
        }
        if ((i + 1) % 4 != 0) {
            System.out.println();
        }
    }

    // Tests if a opponent has legal moves
    public static boolean movable(Piece oppKing, ArrayList<Piece> our, ArrayList<Piece> opp, boolean passant) {
        for (Piece n : opp) {
            if (n.moves(oppKing, our, passant).size() != 0) {
                return true;
            }
        }
        return false;
    }

    // For marking out kings for each turn
    public static Piece getKing(ArrayList<Piece> list) {
        for (Piece n : list) {
            if (n.getName() == 'k') {
                return n;
            }
        }
        return null;
    }

    // Initializes Board
    public static Combo initBoard() {
        // Lists to return
        ArrayList<Piece> our = new ArrayList<>();
        ArrayList<Piece> opp = new ArrayList<>();

        int row = 0;
        // 16 Pieces For each Side
        for (int i = 0; i < 8; i++) {
            if (i <= 7) {
                if (row == 1) {
                    our.add(new Piece(row, i, 'p', 'w'));
                    opp.add(new Piece(7 - row, i, 'p', 'b'));
                } else if (row == 0) {
                    if (i == 0 || i == 7) {
                        our.add(new Piece(0, i, 'r', 'w'));
                        opp.add(new Piece(7 - row, i, 'r', 'b'));
                    } else if (i == 1 || i == 6) {
                        our.add(new Piece(0, i, 'n', 'w'));
                        opp.add(new Piece(7 - row, i, 'n', 'b'));
                    } else if (i == 2 || i == 5) {
                        our.add(new Piece(0, i, 'b', 'w'));
                        opp.add(new Piece(7 - row, i, 'b', 'b'));
                    } else if (i == 3) {
                        our.add(new Piece(0, i, 'q', 'w'));
                        opp.add(new Piece(7 - row, i, 'q', 'b'));
                    } else if (i == 4) {
                        our.add(new Piece(0, i, 'k', 'w'));
                        opp.add(new Piece(7 - row, i, 'k', 'b'));
                    }
                }
            }
            if (i == 7 && row == 0) {
                i = -1;
                row = 1;
            }
        }
        return new Combo(our, opp, false);
    }
}
