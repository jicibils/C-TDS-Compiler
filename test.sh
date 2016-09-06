echo "-------------------------------------------------"
echo "----------------- RUNNING TESTS -----------------"
echo "-------------------------------------------------"
echo ""
echo "-----------------------------------------------"
echo "----------------- JFLEX TESTS -----------------"
echo "-----------------------------------------------"
echo ""
echo "----------------- Keywords Tests -----------------"
echo ""
java -cp target/classes/ LexerStandalone src/test/jflex/keywords.ctds
echo ""
echo "----------------- Delimiters Tests -----------------"
echo ""
java -cp target/classes/ LexerStandalone src/test/jflex/delimiters.ctds
echo ""
echo "----------------- Identifiers Tests -----------------"
echo ""
java -cp target/classes/ LexerStandalone src/test/jflex/identifiers.ctds
echo ""
echo "----------------- Literals Tests -----------------"
echo ""
java -cp target/classes/ LexerStandalone src/test/jflex/literals.ctds
echo ""
echo "----------------- Operators Tests -----------------"
echo ""
java -cp target/classes/ LexerStandalone src/test/jflex/operators.ctds
echo ""
echo "--------------------------------------------------"
echo "--------------------------------------------------"
echo "--------------------------------------------------"
echo ""
echo "-----------------------------------------------"
echo "----------------- CUP TESTS -----------------"
echo "-----------------------------------------------"
echo ""

export CLASSPATH=""
for file in `ls lib`; do export CLASSPATH=$CLASSPATH:lib/$file; done
export CLASSPATH=$CLASSPATH:target/classes
echo "----------------- Ejemplo_pdf Test -----------------"
echo ""
java -cp $CLASSPATH Main src/test/cup/ejemplo_pdf.ctds
echo ""
echo "-----------------  classDeclError Test -----------------"
echo ""
java -cp $CLASSPATH Main src/test/cup/classDeclError.ctds
echo ""
echo "----------------- classDeclOK Test -----------------"
echo ""
java -cp $CLASSPATH Main src/test/cup/classDeclOK.ctds
echo ""
echo "----------------- fieldDeclError Test -----------------"
echo ""
java -cp $CLASSPATH Main src/test/cup/fieldDeclError.ctds
echo ""
echo "----------------- fieldDeclOK Test -----------------"
echo ""
java -cp $CLASSPATH Main src/test/cup/fieldDeclOK.ctds
echo ""

