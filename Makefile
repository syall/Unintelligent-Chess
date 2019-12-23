all: play

build: Main.java pieces/
	javac Main.java -d out/

play: build
	java -classpath out/ Main
	make clean

jar: build pieces/
	jar cvfm Unintelligent-Chess.jar Chess.mf -C out/ .

clean:
	find . -name "*.class" -type f -delete
