#!/bin/bash

cd build/classes
export CLASSPATH="."
for file in `ls ../../lib/`; do export CLASSPATH=$CLASSPATH:../../lib/$file; done
for file in `ls main/java/ast/`; do export CLASSPATH=$CLASSPATH:main/java/ast/$file; done
for file in `ls main/java/lexer/`; do export CLASSPATH=$CLASSPATH:main/java/lexer/$file; done
for file in `ls main/java/parser/`; do export CLASSPATH=$CLASSPATH:main/java/parser/$file; done
for file in `ls main/java/visitor/`; do export CLASSPATH=$CLASSPATH:main/java/visitor/$file; done

echo "*******************  EXECUTING PROGRAM  ******************************************"
java -classpath $CLASSPATH main.Main $1
