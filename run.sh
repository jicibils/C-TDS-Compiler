#!/bin/bash
JAVA_FOLDER=src/main/java
if [ ! -d $JAVA_FOLDER ]; then
	mkdir src/main/java
fi

if [ ! -d target/classes ]; then
	mkdir target
	mkdir target/classes
fi
java -jar lib/jflex-1.6.1.jar -d src/main/java/scanner/ src/main/jflex/lexer.flex
java -jar lib/jflex-1.6.1.jar -d src/main/java/scanner/ src/main/jflex/lexerStandalone.flex
java -jar lib/java-cup-11b.jar -destdir src/main/java/parser/ -parser Parser -symbols Sym src/main/cup/parser.cup

# echo "*******************  COLLECTING DEPENDENCIES  *********************************"
# mvn dependency:copy-dependencies
export CLASSPATH=""
for file in `ls lib`; do export CLASSPATH=$CLASSPATH:lib/$file; done
export CLASSPATH=$CLASSPATH:target/classes
echo "*******************  COMPILING PROGRAM******************************************"

javac -d target/classes/ src/main/java/parser/*.java src/main/java/scanner/*.java src/main/*.java -classpath $CLASSPATH


