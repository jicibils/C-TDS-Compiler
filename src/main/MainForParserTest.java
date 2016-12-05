package main;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.ast.Program;
import main.java.lexer.Lexer;
import main.java.parser.Parser;

public class MainForParserTest {
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {        

        try {

            BufferedReader input = new BufferedReader(new FileReader(args[0]));
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer);
            Program result = (Program)parser.parse().value;
         	System.out.println("No syntax error found in input file.");
        } catch (Exception ex) {
            System.out.println("\nWrong input.");
            
        }
    }
}