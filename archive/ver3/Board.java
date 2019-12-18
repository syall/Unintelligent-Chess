package com;

import java.util.Hashtable;

public class Board {

    public int turn = 0;
    public Player white;
    public Player black;
    public boolean passant = false;

    public Board() {
        white = new Player('w');
        black = new Player('b');
    }

    // Show Board
    public String toString() {
        // Row Label
        String output = "  ";
        for (int i = 0; i < 8; i++)
            output += "  " + i + "  ";
        output += "\n";

        // Check Rows
        for (int row = 0; row < 8; row++) {
            // Column Label
            output += row + " ";
            // Check Columns
            for (int col = 0; col < 8; col++) {
                Point temp = new Point(row, col);
                Piece n = null;
                // If white piece is ther
                if ((n = white.pieces.get(temp.hashCode())) != null) {
                    output += "[" + n + "]";
                }
                // If black piece is there
                else if ((n = black.pieces.get(temp.hashCode())) != null) {
                    output += "[" + n + "]";
                }
                // If no piece is there
                else {
                    output += "[---]";
                }
            }
            output += "\n";
        }
        return output;
    }

    public static boolean check(char color, Board board) {

        // Get the King of Player color
        int hash = -1;
        Hashtable<Integer, Piece> playing = board.white.pieces;
        Hashtable<Integer, Piece> waiting = board.black.pieces;
        if (color == 'b') {
            playing = board.black.pieces;
            waiting = board.white.pieces;
        }

        // Get the King
        for (Integer key : playing.keySet()) {
            if (playing.get(key).type == 'k') {
                hash = key;
                break;
            }
        }

        // Get King Position
        int row = hash / 8;
        int col = hash % 8;

        // Cardinals
        // Right
        for (int i = col + 1; i < 8; i++) {
            Piece n = null;
            // White
            if ((n = playing.get(row * 8 + i)) != null) {
                break;
            }
            // Black
            else if ((n = waiting.get(row * 8 + i)) != null) {
                if (n.type == 'r' || n.type == 'q') {
                    return true;
                } else if (n.type == 'k' && i == col + 1) {
                    return true;
                }
            }
        }
        // Left
        for (int i = col - 1; i >= 0; i--) {
            Piece n = null;
            // White
            if ((n = playing.get(row * 8 + i)) != null) {
                break;
            }
            // Black
            else if ((n = waiting.get(row * 8 + i)) != null) {
                if (n.type == 'r' || n.type == 'q') {
                    return true;
                } else if (n.type == 'k' && i == col - 1) {
                    return true;
                }
            }
        }
        // Above
        for (int i = row - 1; i >= 0; i--) {
            Piece n = null;
            // White
            if ((n = playing.get(i * 8 + col)) != null) {
                break;
            }
            // Black
            else if ((n = waiting.get(i * 8 + col)) != null) {
                if (n.type == 'r' || n.type == 'q') {
                    return true;
                } else if (n.type == 'k' && i == row - 1) {
                    return true;
                }
            }
        }
        // Below
        for (int i = row + 1; i < 8; i++) {
            Piece n = null;
            // White
            if ((n = playing.get(i * 8 + col)) != null) {
                break;
            }
            // Black
            else if ((n = waiting.get(i * 8 + col)) != null) {
                if (n.type == 'r' || n.type == 'q') {
                    return true;
                } else if (n.type == 'k' && i == row + 1) {
                    return true;
                }
            }
        }

        // Diagonals
        // Upper Right
        for (int i = 1; row - i >= 0 && col + i < 8; i++) {
            Piece n = null;
            // White
            if (playing.get((row - i) * 8 + col + i) != null) {
                break;
            }
            // Black
            else if ((n = waiting.get((row - i) * 8 + col + i)) != null) {
                if (n.type == 'b' || n.type == 'q') {
                    return true;
                } else if (row - i == row - 1 && col + i == col + 1) {
                    if (n.type == 'k' || n.type == 'p') {
                        return true;
                    }
                }
            }
        }
        // Upper Left
        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            Piece n = null;
            // White
            if (playing.get((row - i) * 8 + col - i) != null) {
                break;
            }
            // Black
            else if ((n = waiting.get((row - i) * 8 + col - i)) != null) {
                if (n.type == 'b' || n.type == 'q') {
                    return true;
                } else if (row - i == row - 1 && col - i == col - 1) {
                    if (n.type == 'k' || n.type == 'p') {
                        return true;
                    }
                }
            }
        }
        // Lower Right
        for (int i = 1; row + i < 8 && col + i < 8; i++) {
            Piece n = null;
            // White
            if (playing.get((row + i) * 8 + col + i) != null) {
                break;
            }
            // Black
            else if ((n = waiting.get((row + i) * 8 + col + i)) != null) {
                if (n.type == 'b' || n.type == 'q') {
                    return true;
                } else if (row + i == row + 1 && col + i == col + 1) {
                    if (n.type == 'k' || n.type == 'p') {
                        return true;
                    }
                }
            }
        }
        // Lower Left
        for (int i = 1; row + i < 8 && col - i >= 0; i++) {
            Piece n = null;
            // White
            if (playing.get((row + i) * 8 + col - i) != null) {
                break;
            }
            // Black
            else if ((n = waiting.get((row + i) * 8 + col - i)) != null) {
                if (n.type == 'b' || n.type == 'q') {
                    return true;
                } else if (row + i == row + 1 && col - i == col - 1) {
                    if (n.type == 'k' || n.type == 'p') {
                        return true;
                    }
                }
            }
        }

        // Knight
        // Above 2
        if (row - 2 >= 0) {
            // Left 1
            if (col - 1 >= 0) {
                if (waiting.get((row - 2) * 8 + col - 1) != null && waiting.get((row - 2) * 8 + col - 1).type == 'n') {
                    return true;
                }
            }
            // Right 1
            else if (col + 1 < 8) {
                if (waiting.get((row - 2) * 8 + col + 1) != null && waiting.get((row - 2) * 8 + col + 1).type == 'n') {
                    return true;
                }
            }
        }
        // Above 1
        if (row - 1 >= 0) {
            // Left 2
            if (col - 2 >= 0) {
                if (waiting.get((row - 1) * 8 + col - 2) != null && waiting.get((row - 1) * 8 + col - 2).type == 'n') {
                    return true;
                }
            }
            // Right 2
            else if (col + 2 < 8) {
                if (waiting.get((row - 1) * 8 + col + 2) != null && waiting.get((row - 1) * 8 + col + 2).type == 'n') {
                    return true;
                }
            }
        }
        // Below 1
        if (row + 1 < 8) {
            // Left 2
            if (col - 2 >= 0) {
                if (waiting.get((row + 1) * 8 + col - 2) != null && waiting.get((row + 1) * 8 + col - 2).type == 'n') {
                    return true;
                }
            }
            // Right 2
            else if (col + 2 < 8) {
                if (waiting.get((row + 1) * 8 + col + 2) != null && waiting.get((row + 1) * 8 + col + 2).type == 'n') {
                    return true;
                }
            }
        }
        // Below 2
        if (row + 2 < 8) {
            // Left 1
            if (col - 1 >= 0) {
                if (waiting.get((row + 2) * 8 + col - 1) != null && waiting.get((row + 2) * 8 + col - 1).type == 'n') {
                    return true;
                }
            }
            // Right 1
            else if (col + 1 < 8) {
                if (waiting.get((row + 2) * 8 + col + 1) != null && waiting.get((row + 2) * 8 + col + 1).type == 'n') {
                    return true;
                }
            }
        }

        // If got past all of the tests, then not in check!
        return false;
    }

}