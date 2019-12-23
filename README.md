# Unintelligent Chess

Simple Chess implementation with Object Oriented Programming in mind.

Not all of the complexity of the Chess game is implemented (En Passant, Check, Promotion) but overall not a concern as the focus was to demonstrate Object Oriented Principles.

## Classes

### Board

The Board holds the 2D array of all the pieces as well as the location of both kings, acting as a Data Structure except for one method that implements moving the piece.

### Piece

Each piece inherits from the Piece class, and each piece will define the type and the methods to generate moves.

These "move methods" are Java BiFunctions (lambdas) that specify a certain set of Points that a piece can reach:

* horizontals
* verticals
* diagonals
* pawn
* knight
* king

These are added to a List returned by each Piece-implemented version of getMoveMethods, `List<BiFunction<Point, Piece[][], List<Point>>>`.

### Point

The Point class is a simple generic class that holds a row and col field, and is manipulated by the board to validate moves.

## Structure

The structure of the Game Loop is found in Main (named conveniently to run on repl.it) and will only update to the next turn when the turn is complete.

## Usage

In the command line, running `make` will build, run, and clean from the Makefile.

To create a JAR, run `make jar`.

To run a JAR file, run `java -jar Unintelligent-Chess.jar`.

## Personal Notes

This project was done on a whim, as I had never properly used the Object Oriented Paradigm to separate concerns (as opposed to procedural and functional programming).

As a personal project, I found that Java might not have been the good choice, as there was no easy way to iterate or prototype the project due to the constraints of Object Oriented Programming.

Each different version is in the archive folder, as the refactoring was so drastic, it was faster to rewrite all of the classes.

The difficulty of displaying the user interface of the game also was a blocker, as learning how to use a traditional GUI interface such as Java Swing or FX would be too much overhead for a small project I thought of on a whim.
