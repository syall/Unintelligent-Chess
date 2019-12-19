all: play

build: Main.java pieces/ lib/
	javac Main.java -d out/

play: build
	java -classpath out/ Main
	make clean

jar: build lib/ pieces/
	jar cmvf Chess.mf Unintelligent-Chess.jar out/

clean:
	find . -name "*.class" -type f -delete
