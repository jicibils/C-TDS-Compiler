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

