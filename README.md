# Unintelligent Chess

Simple Chess implementation with Object Oriented Programming in mind.

## Objects

### Board

The Board will hold the Players and be responsible for checking with check().

* Turn Count
* Players
* Passant
* static check()

### Player

Players will have their Pieces.

* Color
* HashTable of Piece
* canMove

### Piece

We have type to determine what moves to add into moves as well as a moved boolean.

* Type
* Color
* Passant
* Moved
* HashSet of Points for Possible Moves
* getMoves() and auxiliary functions

## Structure

    Turn Loop (Board)
    {
        Input Loop (Player)
        {
            Move (Piece)
        }
        Reset Player
        Check Condition
    }

## Usage

Fork the Repository at https://github.com/syall/chess.

In a command line,

    $: make

will build, run, and clean from the Makefile

To get a JAR file,

    $: make jar

To run a JAR file,

    $: java -jar UnintelligentChess.jar

## Roadmap

This project was done on a whim, running through what I thought was going to be simple, but turned out to be more complicated than I thought.

Some items I would consider in future versions:

* Tests
* GUI
* AI Opponent
* Removing Player (turns out to almost be useless)
* Optimization on check()
  * Based on Last Move
  * Single Array instead of HashTable
* Networking? (Never did this in Java before)
