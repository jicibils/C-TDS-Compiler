#!/bin/bash
rm src/main/java/parser/Sym.java
rm src/main/java/parser/Parser.java
rm src/main/java/lexer/Lexer.java
rm src/main/java/lexer/LexerStandalone.java
echo "*******************  BUILDING PROJECT  ******************************************"
JAVA_FOLDER=src/main/java
if [ ! -d $JAVA_FOLDER ]; then
	mkdir src/main/java
	mkdir src/main/java/lexer
	mkdir src/main/java/parser
fi

if [ ! -d build/ ]; then
	mkdir build
	mkdir build/classes
fi

java -jar lib/jflex-1.6.1.jar -d src/main/java/lexer/ src/main/jflex/lexer.flex
java -jar lib/jflex-1.6.1.jar -d src/main/java/lexer/ src/main/jflex/lexerStandalone.flex
java -jar lib/java-cup-11b.jar -destdir src/main/java/parser/ -parser Parser -symbols Sym src/main/cup/parser.cup

export CLASSPATH=""
for file in `ls lib`; do export CLASSPATH=$CLASSPATH:lib/$file; done
export CLASSPATH=$CLASSPATH:build/classes
echo "Done!"

echo "*******************  COMPILING PROGRAM  ******************************************"
javac -classpath $CLASSPATH -d build/classes/ 	\
		src/main/java/parser/*.java 			\
		src/main/java/lexer/*.java 				\
		src/main/java/ast/*.java 				\
		src/main/java/visitor/*.java 			\
		src/main/*.java 						\
		test/main/java/*.java
echo "Done!"

echo "*******************  TESTING PROGRAM  ******************************************"
# cd build/test/classes/main/java/
# pwd
# export CLASSPATH=""
# for file in `ls ../../../../../lib/`; do export CLASSPATH=$CLASSPATH:../../../../../lib/$file; done
# echo $CLASSPATH
# java -cp $CLASSPATH org.junit.runner.JUnitCore LexerStandaloneTest
# echo "Done!"
