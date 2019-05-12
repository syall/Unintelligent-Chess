all: play

build: com/Chess.java
	javac com/Chess.java

play: build
	java com/Chess
	make clean

jar: build com/
	jar cmvf Chess.mf Chess.jar com/

clean:
	find . -name "*.class" -type f -delete
