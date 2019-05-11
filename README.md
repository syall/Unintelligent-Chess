# Java Chess

Simple Chess implementation with Object Oriented Programming in mind.

Design is especially considered with the black box concept.

## Objects

### Board

The Board will hold all the game logic of valid moves and check/checkmate/stalemate.

A turn in the board will persist until a player makes a valid move when possible.

Uses Clone for Checks.

Checks will reset all moves in Players.

* Turn Count
* Players / Checks Loop

### Player

Players will have their Pieces.

* HashTable< Piece > pieces
* Decisions in an Input Loop

### Piece

We assume the Piece is dumb and the moves does not account for checks.

We have type to determine what moves to add into moves as well as a moved boolean.

* Fields
  * char type;
  * boolean moved = false;;
  * HashSet< Point > moves = null;
* Methods
  * getMoves
  * castle
  * passant

## Structure

The structure of the project has black boxes inside one another.

The main method wholly depends on communicating with the Board Object.

Once the Board Object declares Checkmate or Stalemate, end the game.

    Board Loop until Player's Move is Valid{
        Player Loop until Choose a Move{
            Piece gets Possible Moves for a Piece {
                Get Moves for a Piece
            }
            Choose Move
        }
        Validate Move
        Break if {
            Checkmate
            Stalemate
        }
    }