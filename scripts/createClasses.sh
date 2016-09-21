#!/bin/bash
echo "*******************  CREATING JAVA CLASSES FOR LEXER AND PARSER  ******************************************"
JAVA_FOLDER=src/main/java
if [ ! -d $JAVA_FOLDER ]; then
	mkdir src/main/java
	mkdir src/main/java/lexer
	mkdir src/main/java/parser
fi

java -jar lib/jflex-1.6.1.jar -d src/main/java/lexer/ src/main/jflex/lexer.flex
java -jar lib/jflex-1.6.1.jar -d src/main/java/lexer/ src/main/jflex/lexerStandalone.flex
java -jar lib/java-cup-11b.jar -destdir src/main/java/parser/ -parser Parser -symbols Sym src/main/cup/parser.cup