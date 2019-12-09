JC=javac 

all: cache

cache: cache.java
	$(JC) cache.java


clean:
	rm *.class
