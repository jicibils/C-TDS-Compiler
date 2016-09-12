package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
public class Main {
	
	public static void generateLexer(){
		//File lexer = new File("src/main/jflex/Lexer.flex");
		String[] args1 = {"-d", "src/main/java/lexer", "src/main/jflex/lexer.flex"};
		jflex.Main.main(args1);
		String[] args2 = {"-d", "src/main/java/lexer", "src/main/jflex/lexerStandalone.flex"};
		jflex.Main.main(args2);
	}
	
	public static void generateParser(){
		//File lexer = new File("src/main/jflex/Lexer.flex");
		String[] args = {"-destdir", "src/main/java/parser", "-parser", "Parser", "-symbols", "Sym", "src/main/cup/parser.cup"};
		try {
			java_cup.Main.main(args);
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		generateLexer();
		generateParser();
    }
    
}
