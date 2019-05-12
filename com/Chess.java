package com;

import java.util.Scanner;

public class Chess {
    public static void main(String[] args) {

        // Initialize Scanner
        Scanner in = new Scanner(System.in);

        // Initialize Board
        Board board = new Board();

        // Set Legal Moves for all Pieces in white
        for (Integer key : board.white.pieces.keySet()) {
            board.white.canMove = board.white.pieces.get(key).getMoves(board) || board.white.canMove;
        }

        // Set Legal Moves for all Pieces in black
        for (Integer key : board.black.pieces.keySet()) {
            board.black.canMove = board.black.pieces.get(key).getMoves(board) || board.black.canMove;
        }

        // Turn Loop
        int loop = 0;
        while (loop == 0) {
            // Print out Turn
            System.out.println("Turn " + board.turn + " ---------------------------------\n");

            // Set Players based on Turn
            Player playing = board.white;
            Player waiting = board.black;
            if (board.turn % 2 != 0) {
                playing = board.white;
                waiting = board.black;
            }

            // Print out Board and Pieces for both Players
            System.out.println(board + "\n" + playing + "\n" + waiting);

            // Input Loop for Player playing
            int plays = 0;
            String input = null;
            while (plays == 0) {
                // Scan input
                System.out.print("input: ");
                input = in.nextLine();

                // Show Board
                if (input.equals("board")) {
                    System.out.println(board);
                }
                // Show Pieces
                else if (input.equals("pieces")) {
                    System.out.println(playing + "\n" + waiting);
                }
                // Move a Piece
                else if (input.equals("move")) {

                    System.out.println("Choose a Piece:");

                    // X-Coordinate of Starting Point
                    int sx = -1;
                    System.out.print("X-Coordinate [0-7]: ");
                    do {
                        try {
                            sx = Integer.parseInt(in.nextLine());

                        } catch (NumberFormatException nfe) {
                            System.out.print("Not valid. Try again: ");
                        }
                    } while (!(sx >= 0 && sx <= 7));
                    // Y-Coordinate of Starting Point
                    int sy = -1;
                    System.out.print("Y-Coordinate [0-7]: ");
                    do {
                        try {
                            sy = Integer.parseInt(in.nextLine());

                        } catch (NumberFormatException nfe) {
                            System.out.print("Not valid. Try again: ");
                        }
                    } while (!(sy >= 0 && sy <= 7));

                    Piece n;
                    Integer hash = new Point(sx, sy).hashCode();
                    // If Piece chosen in Invalid
                    if ((n = playing.pieces.get(hash)) == null) {
                        System.out.println("Invalid Starting Point.");
                        continue;
                    }
                    // If Piece belongs to Player playing
                    System.out
                            .println("Are you sure you want to move this piece?: " + n.moves + "(" + sx + "," + sy + ")");
                    int confirm = 0;
                    do {
                        input = in.nextLine();
                        // yes
                        if (input.equals("no")) {
                            continue;
                        }
                        // Invalid
                        else if (!input.equals("yes")) {
                            System.out.print("Choose yes or no: ");
                        }
                        // yes
                        else {
                            confirm = 1;
                        }
                    } while (confirm == 0);

                    // Pawn Special Cases
                    if (n.type == 'p') {
                        /** TODO: En Passant
                         * If passant is true, check for possible En Passant
                         * If passant is possible, confirm if they want to En Passant
                         * List all possible En Passant for Piece n
                         */
                        if (board.passant) {
                            board.passant = false;
                        }
                    }

                    /** TODO: Castle
                     * Check for possible Castle
                     * If Castle is possible, confirm if they want to En Passant
                     * List all possible Castles for Piece n
                     */
                    if (n.type == 'k') {

                    }

                    System.out.println("Choose a Destination:");

                    // X-Coordinate of Destination
                    int fx = -1;
                    System.out.print("X-Coordinate [0-7]: ");
                    do {
                        try {
                            fx = Integer.parseInt(in.nextLine());

                        } catch (NumberFormatException nfe) {
                            System.out.print("Not valid. Try again: ");
                            fx = -1;
                        }
                    } while (!(fx >= 0 && fx <= 7));
                    // Y-Coordinate of Destination
                    int fy = -1;
                    System.out.print("Y-Coordinate [0-7]: ");
                    do {
                        try {
                            fy = Integer.parseInt(in.nextLine());

                        } catch (NumberFormatException nfe) {
                            System.out.println("Not valid. Try again: ");
                            fy = -1;
                        }
                    } while (!(fy >= 0 && fy <= 7));

                    // If Destination is Invalid
                    boolean contain = false;
                    for (Point p : n.moves) {
                        if (p.row == fx && p.col == fy) {
                            contain = true;
                            break;
                        }
                    }

                    if (!contain) {
                        System.out.println("Invalid Destination.");
                        continue;
                    }

                    // If Player waiting has a piece at Destination
                    Integer oppHash = new Point(fx, fy).hashCode();
                    if (waiting.pieces.containsKey(oppHash)) {
                        waiting.pieces.remove(oppHash);
                    }

                    // Remap Player playing's piece
                    playing.pieces.put(oppHash, playing.pieces.remove(hash));

                    // Set moved of the piece moved to true
                    playing.pieces.get(oppHash).moved = true;
                    
                    // Pawn Special Cases
                    if (playing.pieces.get(oppHash).type == 'p') {
                        // Pawn Promotion
                        if ((playing.pieces.get(oppHash).color == 'w' && fx == 7)
                                || (playing.pieces.get(oppHash).color == 'b' && fx == 0)) {
                            int promo = 0;
                            System.out.println(
                                "Promote Pawn:\n" +
                                "- q = queen\n" +
                                "- r = rook\n" +
                                "- b = bishop\n" +
                                "- n = knight\n"
                            );
                            do {
                                input = in.nextLine();
                                if(input.equals("q") || input.equals("r") || input.equals("b") || input.equals("n")) {
                                    promo = 1;
                                }
                                else {
                                    System.out.print("Invalid Promotion. Try Again: ");
                                }
                            } while (promo == 0);
                            playing.pieces.get(oppHash).type = input.toCharArray()[0];
                        }
                        // Moved 2 Spaces
                        else if(sx+2 == fx || sx-2 == fx) {
                            board.passant = true;
                        }
                    }

                    // Move Completed!
                    System.out.println();
                    plays = 1;
                }
                // Help
                else if (input.equals("help")) {
                    System.out.println("- board = Shows the Chess Board\n" + "- pieces = Shows both Player's Pieces\n"
                            + "- move = Move a Piece from one Point to another Point\n" + "- help = Help with Usage\n"
                            + "- quit = Quit the game\n");
                }
                // Quit
                else if (input.equals("quit")) {
                    // Prompt
                    System.out.print("Are you sure? ");
                    int confirm = 0;
                    do {
                        input = in.nextLine();
                        // yes
                        if (input.equals("yes")) {
                            System.exit(0);
                        }
                        // Invalid
                        else if (!input.equals("no")) {
                            System.out.print("Choose yes or no: ");
                        }
                        // no
                        else {
                            confirm = 1;
                        }
                    } while (confirm == 0);
                    System.out.println();
                }
                // Invalid Command
                else {
                    System.out.println("Invalid Command. Type help for details.\n");
                }
            }

            // Recalculate Legal Moves and Check for Player playing
            playing.canMove = false;
            for (Integer key : playing.pieces.keySet()) {
                playing.canMove = playing.pieces.get(key).getMoves(board) || playing.canMove;
            }

            // Recalculate Legal Moves and Check for Player waiting
            waiting.canMove = false;
            for (Integer key : waiting.pieces.keySet()) {
                waiting.canMove = waiting.pieces.get(key).getMoves(board) || waiting.canMove;
            }

            // Checkmate
            if (!waiting.canMove || board.turn == 4) {
                loop = 1;
                // Stalemate
                if (!playing.canMove) {
                    loop = 2;
                }
                board.turn--;
            }

            // Increase Turn Count
            board.turn++;
        }

        // Close Scanner
        in.close();

        // Winner
        if (loop == 1) {
            // White Wins
            if (board.turn % 2 == 0) {
                System.out.print("White ");
            }
            // Black Wins
            else {
                System.out.print("Black ");
            }
            System.out.println("Wins!");
        }
        // Stalemate
        else if (loop == 2) {
            System.out.println("Stalemate...");
        }
        // Impossible
        else {
            System.out.println("Impossible Situation");
            System.exit(1);
        }

        // Exit
        System.exit(0);
    }
}