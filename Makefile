all: play

Chess: parts/Chess.java
	javac parts/Chess.java

play: Chess
	java parts/Chess
	make clean

jar: Chess parts/
	jar cmf parts/Chess.mf Chess.jar parts/

clean:
	find . -name "*.class" -type f -delete
