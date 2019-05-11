import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PlayChess {

    public static void main(String[] args) {

        // Start
        System.out.println("start\n");

        // Players
        ArrayList<ChessPiece> black = new ArrayList<>();
        ArrayList<ChessPiece> white = new ArrayList<>();

        // Game Board
        ChessPiece[][] board = initBoard(black, white);

        // Scanner
        Scanner in = new Scanner(System.in);

        // Turn Count
        int turnCount = 0;

        // Win
        int win = -1;

        // Passant
        Point en = null;
        int passant = -1;

        // Current King
        Point cKing = new Point(board[7][4].getX(), board[7][4].getY());

        // Turn
        while ((win = turn(turnCount++, board, black, white, cKing)) == -1) {

            // Variables
            int sx = -1;
            int sy = -1;
            int fx = -1;
            int fy = -1;
            int index = -1;
            ArrayList<Point> movesChecked = new ArrayList<>();
            ArrayList<Point> passing = new ArrayList<>();

            // Castle
            int cR = -2;
            int cL = -2;
            /*
            int castlePosition = 0;
            if (turnCount % 2 == 0) {
                castlePosition = 7;
            }
            if (cR != -3 && cL != -3 && board[castlePosition][4] != null
                    && board[castlePosition][4].getStatus() == -1) {
                for (int i = 5; i <= 6; i++) {
                    if (board[castlePosition][i] != null) {
                        if (castlePosition == 7 && hittable(board, white, new Point(castlePosition, i))) {
                            cR = -1;
                        } else if (castlePosition == 0 && hittable(board, black, new Point(castlePosition, i))) {
                            cR = -1;
                        }
                    }
                }
                if (cR == -2 && board[castlePosition][7] != null && board[castlePosition][7].getStatus() == -1) {
                    cR = 0;
                }
                for (int i = 3; i >= 1; i--) {
                    if (board[castlePosition][i] != null) {
                        if (castlePosition == 7 && hittable(board, white, new Point(castlePosition, i))) {
                            cL = -1;
                        } else if (castlePosition == 0 && hittable(board, black, new Point(castlePosition, i))) {
                            cL = -1;
                        }
                    }
                }
                if (cL == -2 && board[castlePosition][0] != null && board[castlePosition][0].getStatus() == -1) {
                    cL = 0;
                }
            }
            if (cL == 0 || cR == 0) {
                System.out.println("castle <castle>");
            }
            */

            // Passant
            int which = 0;
            if (passant == 0) {
                int row = en.x;
                int col = en.y;
                int dir = -1;
                if (turnCount % 2 == 0) {
                    dir = 1;
                }
                ChessPiece eaten = board[row][col];
                if (col > 0 && col < 7) {
                    ChessPiece temp;
                    temp = board[row][col - 1];
                    if (temp != null && temp.getName() == 'p' && temp.getColor() != eaten.getColor()) {
                        passing.add(new Point(row + dir * 1, col));
                        which += 1;
                    }
                    temp = board[row][col + 1];
                    if (temp != null && temp.getName() == 'p' && temp.getColor() != eaten.getColor()) {
                        passing.add(new Point(row + dir * 1, col));
                        which += 2;
                    }
                }
                if (passing.size() != 0) {
                    System.out.println("en passant <passant>");
                } else {
                    passant = -1;
                    en = null;
                }
            }

            // Turn Actions
            while (true && passant != 1) {
                System.out.print("input: ");
                String input = in.nextLine();
                // Show Pieces
                if (input.equals("pieces")) {
                    showPieces(black, white);
                }
                // Show Board
                else if (input.equals("board")) {
                    showBoard(board);
                }
                // Select Piece
                else if (input.equals("piece")) {
                    showBoard(board);
                    while (true) {
                        while (true) {
                            System.out.print("piece coordinates:\nx: ");
                            try {
                                sx = Integer.parseInt(in.nextLine());
                                break;
                            } catch (NumberFormatException nfe) {
                                System.out.println("not valid. try again");
                            }
                        }
                        while (true) {
                            System.out.print("y: ");
                            try {
                                sy = Integer.parseInt(in.nextLine());
                                break;
                            } catch (NumberFormatException nfe) {
                                System.out.println("not valid. try again");
                            }
                        }
                        if (sx < 0 || sx > 7 || sy < 0 || sy > 7) {
                            System.out.println("out of bounds. try again");
                        } else if (board[sx][sy] != null && turnCount % 2 != board[sx][sy].getColor()) {
                            System.out.println("not your piece. try again");
                        } else if (board[sx][sy] == null) {
                            System.out.println("no piece there. try again\n");
                        } else {
                            System.out.println("piece selected: " + board[sx][sy]);
                            break;
                        }
                    }
                    ArrayList<ChessPiece> team;
                    if(turnCount % 2 == 0) {
                        team = white;
                    }
                    else {
                        team = black;
                    }
                    movesChecked = board[sx][sy].moves(board, team, cKing);
                    if (movesChecked == null || movesChecked.size() == 0) {
                        System.out.println("piece cannot move. choose another piece");
                        sx = -1;
                        sy = -1;
                        continue;
                    }
                    showMoves(movesChecked);
                }
                // Show Moves of Piece Selected
                else if (input.equals("moves")) {
                    if (sx < 0 && sy < 0) {
                        System.out.println("choose a piece first");
                        continue;
                    }
                    showMoves(movesChecked);
                }
                // Choose Move of Piece Selected
                else if (input.equals("move")) {
                    if (sx < 0 && sy < 0) {
                        System.out.println("choose a piece first");
                        continue;
                    }
                    showBoard(board);
                    while (true) {
                        while (true) {
                            showMoves(movesChecked);
                            System.out.print("index of move: ");
                            try {
                                index = Integer.parseInt(in.nextLine());
                                break;
                            } catch (NumberFormatException nfe) {
                                System.out.println("not valid. try again");
                            }
                        }
                        if (index < 0 || index > movesChecked.size() - 1) {
                            System.out.println("out of bounds. try again.");
                        } else {
                            fx = movesChecked.get(index).x;
                            fy = movesChecked.get(index).y;
                            System.out.println("moving to " + "(" + fx + "," + fy + ")");
                            if (board[fx][fy] != null) {
                                if (board[fx][fy].getColor() == 0) {
                                    black.remove(board[fx][fy]);
                                } else {
                                    white.remove(board[fx][fy]);
                                }
                            }
                            // Passant
                            if (board[sx][sy].getName() == 'p' && (fx == sx + 2 || fx == sx - 2)) {
                                passant = 0;
                                en = new Point(fx, fy);
                            } else {
                                passant = -1;
                                en = null;
                            }
                            // Pawn Promotion
                            if (board[sx][sy].getName() == 'p') {
                                if ((fx == 7 || fx == 0) && (board[fx][fy] == null || board[fx][fy].getName() != 'k')) {
                                    System.out.println("promote pawn <q|r|n|b>: ");
                                    String promo;
                                    while (true) {
                                        promo = in.nextLine();
                                        if (!promo.equals("q") && !promo.equals("r") && !promo.equals("n")
                                                && !promo.equals("b")) {
                                            System.out.print("invalid. <q|r|n|b>");
                                        } else {
                                            break;
                                        }
                                    }
                                    if (promo.equals("q")) {
                                        board[fx][fy] = new Queen(new Point(fx, fy), 'q', board[sx][sy].getColor());
                                    } else if (promo.equals("b")) {
                                        board[fx][fy] = new Bishop(new Point(fx, fy), 'b', board[sx][sy].getColor());
                                    } else if (promo.equals("r")) {
                                        board[fx][fy] = new Rook(new Point(fx, fy), 'r', board[sx][sy].getColor());
                                    } else if (promo.equals("n")) {
                                        board[fx][fy] = new Knight(new Point(fx, fy), 'n', board[sx][sy].getColor());
                                    }
                                } else {
                                    board[sx][sy].setX(fx);
                                    board[sx][sy].setY(fy);
                                    board[sx][sy].setStatus();
                                    board[fx][fy] = board[sx][sy];
                                }
                            }
                            // Every other Piece
                            else {
                                board[sx][sy].setX(fx);
                                board[sx][sy].setY(fy);
                                board[sx][sy].setStatus();
                                board[fx][fy] = board[sx][sy];
                            }
                            board[sx][sy] = null;
                            break;
                        }
                    }
                    System.out.println("");
                    break;
                }
                // Passant
                else if (passant == 0 && input.equals("passant")) {
                    int row = en.x;
                    int col = en.y;
                    ChessPiece eaten = board[row][col];
                    System.out.print("en passant? <y|n>: ");
                    showMoves(passing);
                    String pass = null;
                    while (true) {
                        pass = in.nextLine();
                        if (pass.equals("y") || pass.equals("n")) {
                            break;
                        } else {
                            System.out.println("invalid. <y|n>");
                        }
                    }
                    if (pass.equals("y")) {
                        String decision = "";
                        if (which == 3) {
                            showMoves(movesChecked);
                            System.out.print("<left|right>");
                            while (true) {
                                decision = in.nextLine();
                                if (decision.equals("left") || decision.equals("right")) {
                                    break;
                                } else {
                                    System.out.println("invalid. <left|right>");
                                }
                            }
                        }
                        if (which == 1 || decision.equals("left")) {
                            int moveX = passing.get(0).x;
                            int moveY = passing.get(0).y;
                            System.out.println("moving to " + "(" + moveX + "," + moveY + ")");
                            board[moveX][moveY] = board[row][col - 1];
                            board[moveX][moveY].setX(moveX);
                            board[moveX][moveY].setY(moveY);
                            board[row][col - 1] = null;
                        } else if (which == 2 || decision.equals("right")) {
                            int moveX;
                            int moveY;
                            if (which == 3) {
                                moveX = passing.get(1).x;
                                moveY = passing.get(1).y;
                            } else {
                                moveX = movesChecked.get(0).x;
                                moveY = movesChecked.get(0).y;
                            }
                            System.out.println("moving to " + "(" + moveX + "," + moveY + ")");
                            board[moveX][moveY] = board[row][col + 1];
                            board[moveX][moveY].setX(moveX);
                            board[moveX][moveY].setY(moveY);
                            board[row][col + 1] = null;
                        }
                        if (eaten.getColor() == 0) {
                            black.remove(eaten);
                        } else {
                            white.remove(eaten);
                        }
                        board[row][col] = null;
                        en = null;
                        passant = 1;
                        continue;
                    }
                }
                // Castle
                else if ((cL == 0 || cR == 0) && input.equals("castle")) {
                    String decision = "";
                    if (cL == 0 && cR == 0) {
                        System.out.println("<left|right>");
                        while (true) {
                            decision = in.nextLine();
                            if (decision.equals("left") || decision.equals("right")) {
                                break;
                            } else {
                                System.out.println("invalid. <left|right>");
                            }
                        }
                    } else if (cL == 0) {
                        decision = "left";
                    } else {
                        decision = "right";
                    }

                    if (decision.equals("left")) {

                    } else if (decision.equals("right")) {

                    }

                    cL = -3;
                    cR = -3;
                    continue;
                }
                // Invalid Command
                else {
                    System.out.println("Invalid Input.\n<pieces|board|piece|moves|move>");
                }
                System.out.println();
            }
            // Passant
            if (passant == 1) {
                passant = -1;
            }
        }

        in.close();

        // Win Conditions
        if (win == 0)

        {
            System.out.println("w wins");
        } else if (win == 1) {
            System.out.println("b wins");
        } else {
            System.out.println("stalemate");
        }

        // End
        System.out.println("\nend");
    }

    // Initialize Board
    public static ChessPiece[][] initBoard(ArrayList<ChessPiece> black, ArrayList<ChessPiece> white) {

        // Game Board
        ChessPiece[][] board = new ChessPiece[8][8];

        // Color of Piece
        int color = 0;

        // Piece
        char piece;

        // Initialize Game Board
        for (int i = 0; i < 8; i++) {
            // Switch to White
            if (i == 6) {
                color = 1;
            }
            for (int j = 0; j < 8; j++) {
                // Spaces with no Pieces on it
                if (i > 1 && i < 6) {
                    board[i][j] = null;
                }
                // (p)awn
                else if (i == 1 || i == 6) {
                    piece = 'p';
                    board[i][j] = new Pawn(new Point(i, j), piece, color);
                }
                // (r)ook
                else if (j == 0 || j == 7) {
                    piece = 'r';
                    board[i][j] = new Rook(new Point(i, j), piece, color);
                }
                // k(n)ight
                else if (j == 1 || j == 6) {
                    piece = 'n';
                    board[i][j] = new Knight(new Point(i, j), piece, color);
                }
                // (b)ishop
                else if (j == 2 || j == 5) {
                    piece = 'b';
                    board[i][j] = new Bishop(new Point(i, j), piece, color);
                }
                // (q)ueen
                else if (j == 4) {
                    piece = 'k';
                    board[i][j] = new King(new Point(i, j), piece, color);
                }
                // (k)ing
                else if (j == 3) {
                    piece = 'q';
                    board[i][j] = new Queen(new Point(i, j), piece, color);
                }
                if (board[i][j] != null) {
                    if ((board[i][j].getColor()) == 0) {
                        black.add(board[i][j]);
                    } else {
                        white.add(board[i][j]);
                    }
                }
            }
        }
        Collections.reverse(black);
        // Return Game Board
        return board;
    }

    // Turn
    public static int turn(int turnCount, ChessPiece[][] board, ArrayList<ChessPiece> black,
            ArrayList<ChessPiece> white, Point king) {
        int win = winCheck(board, black, white, king);
        if (win != -1) {
            return win;
        }

        System.out.print("t" + turnCount);
        for (int i = 0; i < 6; i++) {
            System.out.print("------");
        }
        System.out.println("--\n");

        showBoard(board);
        showPieces(black, white);

        return -1;
    }

    // Show Pieces
    public static void showPieces(ArrayList<ChessPiece> black, ArrayList<ChessPiece> white) {
        System.out.print("b:");
        for (int i = 0; i < black.size(); i++) {
            System.out.print(black.get(i).getName());
        }
        System.out.println();

        System.out.print("w:");
        for (int i = 0; i < white.size(); i++) {
            System.out.print(white.get(i).getName());
        }
        System.out.println();
        System.out.println();

    }

    // Show Board
    public static void showBoard(ChessPiece[][] board) {
        System.out.print("  ");
        for (int i = 0; i < 8; i++) {
            System.out.print("  " + i + "  ");
        }
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null)
                    System.out.print("[---]");
                else
                    System.out.print("[" + board[i][j] + "]");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Win Check
    public static int winCheck(ChessPiece[][] board, ArrayList<ChessPiece> our, ArrayList<ChessPiece> opp, Point king) {
        /*
         * int check = -1; int checkMate = -1; int staleMate = -1;
         * 
         * if(hittable(board, our, king)) { check = 0; if(checkmate(board, our, king)) {
         * checkMate = 0; } } else if(stalemate(board, opp, king){ staleMate = 0; }
         * 
         * if(checkMate == 0) { return 0; } else if(check == 0) { return 1; } else
         * if(stalemate == 0) { return 2; } else { return -1; }
         */
        int win = -1;
        for (ChessPiece i : our) {
            if (i.getName() == 'k') {
                win = 0;
                break;
            }
        }
        if (win == -1) {
            return 0;
        }
        win = -1;
        for (ChessPiece i : opp) {
            if (i.getName() == 'k') {
                win = 0;
                break;
            }
        }
        if (win == -1) {
            return 1;
        }
        return -1;
    }

    // Show Moves
    public static void showMoves(ArrayList<Point> movesChecked) {
        for (int i = 0; i < movesChecked.size(); i++) {
            Point n = movesChecked.get(i);
            System.out.print("[" + i + ":(" + n.x + "," + n.y + ")]");
            if ((i + 1) % 3 == 0 && i + 1 != movesChecked.size()) {
                System.out.println();
            }
        }
        System.out.println();
    }

    // Check
    public static boolean hittable(ChessPiece[][] board, ArrayList<ChessPiece> opp, Point king) {
        /*
         * // Horizontal return true; // Vertical return true; // Diagonal return true;
         * // Knights return true; // Pawns return true; // King return true;
         */
        return false;
    }

    public static boolean checkmate(ChessPiece[][] board, ArrayList<ChessPiece> our, Point king) {
        /*
         * all piece moves() { // dummy move if(!hittable(board, our, king)) { return
         * false; } } return true;
         */
        return false;
    }

    public static boolean stalemate(ChessPiece[][] board, ArrayList<ChessPiece> our, ArrayList<ChessPiece> opp,
            Point king) {
        for (ChessPiece n : opp) {
            if (n.moves(board, our, king).size() != 0) {
                return false;
            }
        }
        return true;
    }
}