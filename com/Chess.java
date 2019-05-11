package com;

public class Chess {
    public static void main(String[] args) {

        // Initialize Board
        Board board = new Board();

        // Turn Loop
        while(true) {
            // Print out Turn
            System.out.println("Turn " + board.turn + " ---------------------------------\n");

            // Set Players based on Turn
            Player playing = null;
            Player waiting = null;
            if(board.turn%2 == 0) {
                playing = board.white;
                waiting = board.black;
            } else {
                playing = board.black;
                waiting = board.white;
            }

            // Print out Board and Pieces for both Players
            System.out.println(board);
            System.out.println(playing);
            System.out.println(waiting);

            // Arbitrary Break Condition
            if(board.turn == 4) {
                break;
            }

            // Increase Turn Count
            board.turn++;
        }
    }
}