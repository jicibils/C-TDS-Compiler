package main;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.ast.Program;
import main.java.lexer.Lexer;
import main.java.parser.Parser;
import main.java.visitor.*;


public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        try {
            BufferedReader input = new BufferedReader(new FileReader(args[0]));
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer);
            Program result = (Program)parser.parse().value;
            mainCheck(result);
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

    public static void mainCheck(Program program){
        int result;
        CheckExistMainVisitor mainVisitor = new CheckExistMainVisitor();
        result = mainVisitor.visit(program);
        if (result == 0){
            System.out.println("Fatal error: There isn't method 'Main'");            
        }
        else{
            if(result == -1){
                System.out.println("Fatal error: Method Main contain arguments");
            }
            else{
                if(result > 1){
                    System.out.println("Fatal error: there is more than one method 'Main'");
                }
            }
        }
    }
}
