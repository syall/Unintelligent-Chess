package com;

import java.util.Scanner;

public class Chess {
    public static void main(String[] args) throws CloneNotSupportedException {

        // Initialize Scanner
        Scanner in = new Scanner(System.in);

        // Initialize Board
        Board board = new Board();

        // Set Legal Moves for all Pieces in white
        for (int key = 0; key < 16; key++) {
            board.white.pieces.get(key).getMoves(board);
        }

        // Set Legal Moves for all Pieces in black
        for (int key = 48; key < 64; key++) {
            board.black.pieces.get(key).getMoves(board);
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
                playing = board.black;
                waiting = board.white;
            }

            // Print out Board and Pieces for both Players
            System.out.println(board + "\n" + playing);

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
                    System.out.print("X-Coordinate: ");
                    do {
                        try {
                            sx = Integer.parseInt(in.nextLine());
                            if (sx < 0 || sx > 7)
                                System.out.print("[0-7]: ");
                        } catch (NumberFormatException nfe) {
                            System.out.print("[0-7]: ");
                        }
                    } while (sx < 0 || sx > 7);
                    // Y-Coordinate of Starting Point
                    int sy = -1;
                    System.out.print("Y-Coordinate: ");
                    do {
                        try {
                            sy = Integer.parseInt(in.nextLine());
                            if (sy < 0 || sy > 7)
                                System.out.print("[0-7]: ");
                        } catch (NumberFormatException nfe) {
                            System.out.print("[0-7]: ");
                        }
                    } while (sy < 0 || sy > 7);

                    Piece n;
                    Integer hash = new Point(sx, sy).hashCode();
                    // If Piece chosen in Invalid
                    if ((n = playing.pieces.get(hash)) == null) {
                        System.out.println("Invalid Starting Point.");
                        continue;
                    }

                    // If Piece belongs to Player playing

                    // Action to Do!
                    int confirm = -1;
                    int fx = -1;
                    int fy = -1;

                    // En Passant
                    int pRow = n.color == 'w' ? 4 : 3;
                    if (n.type == 'p' && board.passant && pRow == sx) {
                        // Options
                        int moves = 0;

                        // Reset passant
                        board.passant = false;

                        // Direction of Pawn
                        int direction = n.color == 'w' ? 1 : -1;

                        // Check Left
                        Piece nPass = null;
                        if ((nPass = waiting.pieces.get(pRow * 8 + sy - 1)) != null && nPass.passant) {
                            nPass.passant = false;
                            moves = 1;
                            fx = pRow + direction;
                            fy = sy - 1;
                        }
                        // Check Right
                        else if ((nPass = waiting.pieces.get(pRow * 8 + sy + 1)) != null && nPass.passant) {
                            nPass.passant = false;
                            moves = 2;
                            fx = pRow + direction;
                            fy = sy + 1;
                        }

                        // Check
                        // Remove Original Piece
                        Piece origin = playing.pieces.remove(sx * 8 + sy);
                        // Remove Passant Piece
                        Piece sad = null;
                        if (moves == 1) {
                            sad = waiting.pieces.remove(pRow * 8 + sy - 1);
                        } else if (moves == 2) {
                            sad = waiting.pieces.remove(pRow * 8 + sy + 1);
                        }
                        // En Passant
                        playing.pieces.put(fx * 8 + fy, origin);
                        // Check
                        if (Board.check(origin.color, board)) {
                            moves = 0;
                            confirm = -1;
                        }
                        // Remove En Passant
                        playing.pieces.remove(fx * 8 + fy);
                        // Put in Original Piece
                        playing.pieces.put(sx * 8 + sy, origin);
                        // Put in Passant Piece
                        if (moves == 1) {
                            waiting.pieces.put(pRow * 8 + sy - 1, sad);
                        } else if (moves == 2) {
                            waiting.pieces.put(pRow * 8 + sy + 1, sad);
                        }

                        // If Passant is valid
                        if (moves == 1 || moves == 2) {
                            System.out.print("En Passant? ");
                            do {
                                input = in.nextLine();
                                // yes
                                if (input.equals("yes")) {
                                    confirm = moves;
                                    break;
                                }
                                // Invalid
                                else if (!input.equals("no")) {
                                    System.out.print("Choose yes or no: ");
                                }
                                // no
                                else {
                                    confirm = -1;
                                    break;
                                }
                            } while (true);
                        }
                    }

                    // Castle
                    if (n.type == 'k' && !n.moved && !Board.check(n.color, board)) {
                        // Left
                        boolean left = true;
                        // Left 1
                        if (playing.pieces.get(sx * 8 + 3) == null && waiting.pieces.get(sx * 8 + 3) == null) {
                            // Remove from Original Position
                            playing.pieces.remove((sx) * 8 + sy);
                            // Put in Destination
                            playing.pieces.put((sx) * 8 + 3, n);
                            // Check
                            if (Board.check(n.color, board)) {
                                left = false;
                            }
                            // Remove from Destination
                            playing.pieces.remove((sx) * 8 + 3);
                            // Put in Original Position
                            playing.pieces.put((sx) * 8 + sy, n);
                        } else {
                            left = false;
                        }
                        // Left 2
                        if (playing.pieces.get(sx * 8 + 2) == null && waiting.pieces.get(sx * 8 + 2) == null) {
                            // Remove from Original Position
                            playing.pieces.remove((sx) * 8 + sy);
                            // Put in Destination
                            playing.pieces.put((sx) * 8 + 2, n);
                            // Check
                            if (Board.check(n.color, board)) {
                                left = false;
                            }
                            // Remove from Destination
                            playing.pieces.remove((sx) * 8 + 2);
                            // Put in Original Position
                            playing.pieces.put((sx) * 8 + sy, n);
                        } else {
                            left = false;
                        }
                        // Left 3
                        if (!(playing.pieces.get(sx * 8 + 1) == null && waiting.pieces.get(sx * 8 + 1) == null)) {
                            left = false;
                        }
                        // Left 4 (Rook)
                        if (playing.pieces.get(sx * 8 + 0) == null || playing.pieces.get(sx * 8 + 0).moved) {
                            left = false;
                        }
                        // Left Possible!
                        if (left) {
                            confirm += 3;
                        }

                        // Right
                        boolean right = true;
                        // Right 1
                        if (playing.pieces.get(sx * 8 + 5) == null && waiting.pieces.get(sx * 8 + 5) == null) {
                            // Remove from Original Position
                            playing.pieces.remove((sx) * 8 + sy);
                            // Put in Destination
                            playing.pieces.put((sx) * 8 + 5, n);
                            // Check
                            if (Board.check(n.color, board)) {
                                right = false;
                            }
                            // Remove from Destination
                            playing.pieces.remove((sx) * 8 + 5);
                            // Put in Original Position
                            playing.pieces.put((sx) * 8 + sy, n);
                        } else {
                            right = false;
                        }
                        // Right 2
                        if (playing.pieces.get(sx * 8 + 6) == null && waiting.pieces.get(sx * 8 + 6) == null) {
                            // Remove from Original Position
                            playing.pieces.remove((sx) * 8 + sy);
                            // Put in Destination
                            playing.pieces.put((sx) * 8 + 6, n);
                            // Check
                            if (Board.check(n.color, board)) {
                                right = false;
                            }
                            // Remove from Destination
                            playing.pieces.remove((sx) * 8 + 6);
                            // Put in Original Position
                            playing.pieces.put((sx) * 8 + sy, n);
                        } else {
                            right = false;
                        }
                        // Right 4 (Rook)
                        if (playing.pieces.get(sx * 8 + 7) == null || playing.pieces.get(sx * 8 + 7).moved) {
                            right = false;
                        }
                        // Right Possible!
                        if (right) {
                            confirm += 4;
                        }

                        // Prompt Castle if Possible
                        // Both Castles Possible
                        if (right && left) {
                            System.out.print("Castle <left, right, no>? ");
                            do {
                                input = in.nextLine();
                                // Left
                                if (input.equals("left")) {
                                    confirm = 3;
                                    break;
                                }
                                // Right
                                else if (input.equals("right")) {
                                    confirm = 4;
                                    break;
                                }
                                // No
                                else if (input.equals("no")) {
                                    confirm = -1;
                                    break;
                                }
                                // Invalid
                                else {
                                    System.out.print("Choose <left, right, no>: ");
                                }
                            } while (true);
                        }
                        // Left Possible
                        else if (left) {
                            System.out.print("Castle Left? ");
                            do {
                                input = in.nextLine();
                                // yes
                                if (input.equals("yes")) {
                                    confirm = 3;
                                    break;
                                }
                                // Invalid
                                else if (!input.equals("no")) {
                                    System.out.print("Choose yes or no: ");
                                }
                                // no
                                else {
                                    confirm = -1;
                                    break;
                                }
                            } while (true);
                        }
                        // Right Possible
                        else if (right) {
                            System.out.print("Castle Right? ");
                            do {
                                input = in.nextLine();
                                // yes
                                if (input.equals("yes")) {
                                    confirm = 4;
                                    break;
                                }
                                // Invalid
                                else if (!input.equals("no")) {
                                    System.out.print("Choose yes or no: ");
                                }
                                // no
                                else {
                                    confirm = -1;
                                    break;
                                }
                            } while (true);
                        }

                        // Set Final
                        if (confirm != -1) {
                            fx = sx;
                            // Left
                            if (confirm == 3) {
                                fy = 2;
                            }
                            // Right
                            if (confirm == 4) {
                                fy = 6;
                            }
                        }
                    }

                    // If not a Special Case
                    if (confirm == -1) {
                        System.out.println("Choose a Destination:");

                        // X-Coordinate of Destination

                        System.out.print("X-Coordinate: ");
                        do {
                            try {
                                fx = Integer.parseInt(in.nextLine());
                                if (fx < 0 || fx > 7)
                                    System.out.print("[0-7]: ");
                            } catch (NumberFormatException nfe) {
                                System.out.print("[0-7]: ");
                            }
                        } while (fx < 0 || fx > 7);
                        // Y-Coordinate of Destination
                        System.out.print("Y-Coordinate: ");
                        do {
                            try {
                                fy = Integer.parseInt(in.nextLine());
                                if (fy < 0 || fy > 7) {
                                    System.out.print("[0-7]: ");
                                }
                            } catch (NumberFormatException nfe) {
                                System.out.print("[0-7]: ");
                            }
                        } while (fy < 0 || fy > 7);

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
                    }

                    // If Player waiting has a piece at Destination
                    Integer oppHash = new Point(fx, fy).hashCode();
                    if (waiting.pieces.containsKey(oppHash)) {
                        waiting.pieces.remove(oppHash);
                    }

                    // Special Conditions
                    if (confirm != -1) {
                        System.out.println(waiting);
                        // En Passant Right
                        if (confirm == 1) {
                            waiting.pieces.remove(pRow * 8 + fy);
                        }
                        // En Passant Left
                        else if (confirm == 2) {
                            waiting.pieces.remove(pRow * 8 + fy);
                        }
                        // Castle Left
                        else if (confirm == 3) {
                            playing.pieces.put(sx * 8 + 3, playing.pieces.remove(sx * 8 + 0));
                            playing.pieces.get(sx * 8 + 3).moved = true;
                        }
                        // Castle Right
                        else if (confirm == 4) {
                            playing.pieces.put(sx * 8 + 5, playing.pieces.remove(sx * 8 + 7));
                            playing.pieces.get(sx * 8 + 5).moved = true;
                        }
                    }

                    // Remap Player playing's piece
                    playing.pieces.put(oppHash, playing.pieces.remove(hash));

                    // Set moved of the piece moved to true
                    playing.pieces.get(oppHash).moved = true;

                    // Pawn Special Cases after Moved
                    if (playing.pieces.get(oppHash).type == 'p') {
                        // Pawn Promotion
                        if ((playing.pieces.get(oppHash).color == 'w' && fx == 7)
                                || (playing.pieces.get(oppHash).color == 'b' && fx == 0)) {
                            int promo = 0;
                            System.out.print("Promote Pawn:\n" + "- q = queen\n" + "- r = rook\n" + "- b = bishop\n"
                                    + "- n = knight\nPromote: ");
                            do {
                                input = in.nextLine();
                                if (input.equals("q") || input.equals("r") || input.equals("b") || input.equals("n")) {
                                    promo = 1;
                                } else {
                                    System.out.print("Invalid Promotion. Try Again: ");
                                }
                            } while (promo == 0);
                            playing.pieces.get(oppHash).type = input.toCharArray()[0];
                        }
                        // Moved 2 Spaces
                        else if (sx + 2 == fx || sx - 2 == fx) {
                            board.passant = true;
                            playing.pieces.get(oppHash).passant = true;
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

            // Set Legal Moves for all Pieces
            for (int key = 0; key < 64; key++) {
                if (playing.pieces.get(key) != null) {
                    playing.canMove = playing.pieces.get(key).getMoves(board) || playing.canMove;
                    // Reset passant if not Passant this turn
                    if (!board.passant) {
                        playing.pieces.get(key).passant = false;
                    }
                } else if (waiting.pieces.get(key) != null) {
                    waiting.canMove = waiting.pieces.get(key).getMoves(board) || waiting.canMove;
                    // Reset passant if not Passant this turn
                    if (!board.passant) {
                        waiting.pieces.get(key).passant = false;
                    }
                }
            }

            // Check
            if (Board.check(waiting.color, board)) {
                // Checkmate
                if (!waiting.canMove) {
                    loop = 1;
                } else {
                    String name = waiting.color == 'w' ? "White" : "Black";
                    System.out.println(name + " is in Check");
                }
                // Stalemate
                if (!playing.canMove) {
                    loop = 2;
                }
            }

            // Increase Turn Count
            board.turn++;
        }

        // Close Scanner
        in.close();

        // Winner
        board.turn--;
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