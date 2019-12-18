all: play

build: Main.java
	javac Main.java -d out/

play: build
	java -classpath out/ Main
	make clean

jar: build lib/
	jar cmvf Chess.mf Unintelligent-Chess.jar lib/

clean:
	find . -name "*.class" -type f -delete
