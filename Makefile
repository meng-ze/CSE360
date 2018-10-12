#!/bin/bash
JFLAGS = -d .
JC = javac
JVM = java

All: CSE360TeamProject/*.java
	$(JC) $(JFLAGS) $^

run: ANETA.class
	$(JVM) ANETA

@PHONY:
clean:
	rm -rf *.class

