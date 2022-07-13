all: clean build run

.PHONY: clean build zip

clean:
	rm -f *.class

build:
	javac -g Main.java

run:
	java Main
