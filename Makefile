all: play

build: com/Chess.java
	javac com/Chess.java

play: build
	java com/Chess
	make clean

jar: build com/
	jar cmvf com/Chess.mf Chess.jar com/

clean:
	find . -name "*.class" -type f -delete
	find . -perm +100 -type f -delete
