package com;

import java.util.Scanner;

public class Chess {
    public static void main(String[] args) {

        // Initialize Scanner 
        Scanner in = new Scanner(System.in);

        // Initialize Board
        Board board = new Board();

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
            System.out.println(
                board + "\n" + 
                playing + "\n" + 
                waiting
            );

            // Input Loop for Player playing
            int plays = 0;
            String input = null;
            while(plays == 0) {
                // Scan input
                System.out.print("input: "); input = in.nextLine();

                // Show Board
                if(input.equals("board")) {
                    System.out.println(board);
                }
                // Show Pieces
                else if(input.equals("pieces")) {
                    System.out.println(
                        playing + "\n" +
                        waiting
                    );
                }
                // Move a Piece
                else if(input.equals("move")) {
                    /** 
                     * TODO: Move
                     * - Choose a Space with Playing's Piece
                     * - Choose a Space to move with Playing's Piece
                     */
                    
                    plays = 1;
                }
                // Help
                else if(input.equals("help")) {
                    System.out.println(
                        "- board = Shows the Chess Board\n" +
                        "- pieces = Shows both Player's Pieces\n" +
                        "- move = Move a Piece from one Point to another Point\n" +
                        "- help = Help with Usage\n" +
                        "- quit = Quit the game\n"
                    );
                }
                // Quit
                else if(input.equals("quit")) {
                    // Prompt
                    System.out.print("Are you sure? "); 
                    int confirm = 0;
                    do {
                        input = in.nextLine();
                        // yes
                        if(input.equals("yes")) {
                            System.exit(0);
                        }
                        // no
                        else if(!input.equals("no")) {
                            System.out.print("Choose yes or no: ");
                        }
                        // Invalid
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
            for(Integer key : playing.pieces.keySet()){
                playing.canMove = playing.canMove || playing.pieces.get(key).getMoves();
            }

            // Recalculate Legal Moves and Check for Player waiting
            waiting.canMove = false;
            for(Integer key : waiting.pieces.keySet()){
                waiting.canMove = waiting.canMove || waiting.pieces.get(key).getMoves();
            }

            // Checkmate
            if (!waiting.canMove || board.turn == 4) {
                loop = 1;
                // Stalemate
                if(!playing.canMove) {
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
    }
}