package main;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.ast.Program;
import main.java.lexer.Lexer;
import main.java.parser.Parser;
import main.java.visitor.*;

/**
 *
 * @author adrian
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        try {
            BufferedReader input = new BufferedReader(new FileReader(args[0]));
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer);
			Object result = parser.parse().value;
			PrettyPrintVisitor printerVisitor = new PrettyPrintVisitor();
			System.out.println(printerVisitor.visit((Program)result));
			
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + args[0]);
			
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Fatal error: no input file\nUse: ./ctds.sh <filename>");
        } catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

}
