import java.util.Scanner;

import pieces.Board;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private boolean completed = true;
    private Board board;
    private int turn = 0;

    public Main() {
        board = new Board();
    }

    private void displayHeader() {
        System.out.println("============ Unintelligent Chess ============");
    }

    private void displayFooter() {
        System.out.println("============ Chess Game Finished ============");
    }

    private String getInput() {
        System.out.print("input: ");
        return scanner.nextLine();
    }

    private void displayBoard() {
        System.out.println(board.toString());
    }

    private void displayPieces() {
        System.out.println(board.getPieces());
    }

    private void movePiece() {
        System.out.println("Starting Piece:");
        System.out.print("Row ");
        int sr = 0;
        try {
            sr = Integer.parseInt(getInput());
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }
        System.out.print("Column ");
        String sc = getInput();
        System.out.println("Destination:");
        System.out.print("Row ");
        int fr = 0;
        try {
            fr = Integer.parseInt(getInput());
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }
        System.out.print("Column ");
        String fc = getInput();
        completed = board.move(sr, sc, fr, fc);
        if (completed)
            System.out.println("Move piece " + sr + sc + " to " + fr + fc);
    }

    private void displayHelp() {
        System.out.println("Help:");
        System.out.println("- board = Shows the Chess Board");
        System.out.println("- pieces = Shows both Player's Pieces");
        System.out.println("- move = Move piece from <x, y> to <x, y>");
        System.out.println("- help = Help with Usage");
        System.out.println("- quit = Quit the game");
    }

    private void quit() {
        if (confirmDecision())
            turn = -100;
    }

    private boolean confirmDecision() {
        System.out.println("Are you sure? [Y|_]");
        return getInput().equals("Y");
    }

    private void wrongInput(String input) {
        System.out.println(String.format("'%s' is not a valid input.", input));
        displayHelp();
    }

    private void nextTurn() {
        if (completed)
            turn++;
    }

    private boolean isActive() {
        if (turn < 0)
            return false;
        if (completed) {
            System.out.println(String.format("\nTurn %d >", turn));
            System.out.println(board.toString());
            System.out.println(board.getPieces());
            completed = false;
        }
        return true;
    }

    private void displayEndGame() {
        switch (turn) {
        case -100:
            System.out.println("Game Quit.");
            break;
        case -1:
            System.out.println("White Player Won!");
            break;
        case -2:
            System.out.println("Black Player Won!");
            break;
        case -3:
            System.out.println("Stalemate.");
            break;
        default:
            System.out.println("Impossible :(");
            break;
        }
    }

    private void run() {
        displayHeader();
        displayHelp();
        while (isActive()) {
            String input = getInput();
            switch (input) {
            case "board":
                displayBoard();
                break;
            case "pieces":
                displayPieces();
                break;
            case "move":
                movePiece();
                break;
            case "help":
                displayHelp();
                break;
            case "quit":
                quit();
                break;
            default:
                wrongInput(input);
                break;
            }
            nextTurn();
        }
        displayEndGame();
        displayFooter();
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.run();
    }

}
