echo "-------------------------------------------------"
echo "----------------- RUNNING TESTS -----------------"
echo "-------------------------------------------------"
echo ""
echo "-----------------------------------------------"
echo "----------------- JFLEX TESTS -----------------"
echo "-----------------------------------------------"
cd ..
cd build/classes
echo ""
echo "----------------- Keywords Tests -----------------"
echo ""

java  main.java.lexer.LexerStandalone ../../test/resource/lexer/keywords.ctds
echo ""
echo "----------------- Delimiters Tests -----------------"
echo ""
java  main.java.lexer.LexerStandalone ../../test/resource/lexer/delimiters.ctds
echo ""
echo "----------------- Identifiers Tests -----------------"
echo ""
java  main.java.lexer.LexerStandalone ../../test/resource/lexer/identifiers.ctds
echo ""
echo "----------------- Literals Tests -----------------"
echo ""
java  main.java.lexer.LexerStandalone ../../test/resource/lexer/literals.ctds
echo ""
echo "----------------- Operators Tests -----------------"
echo ""
java  main.java.lexer.LexerStandalone ../../test/resource/lexer/operators.ctds
echo ""
echo "--------------------------------------------------"
echo "--------------------------------------------------"
echo "--------------------------------------------------"

