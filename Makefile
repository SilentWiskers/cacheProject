CC=javac 

all: cache

cache: cache.java
	$(CC) cache.java


clean:
	rm *.cmo *.cmi *.class
