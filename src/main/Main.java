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
import java.util.List;
import java.util.LinkedList;


public class Main {
    /**
     * @param args the command line arguments
     */
    private static List<String> errorList;

    public static void main(String[] args) {        

        try {

            errorList = new LinkedList<String>();

            BufferedReader input = new BufferedReader(new FileReader(args[0]));
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer);
            Program result = (Program)parser.parse().value;
            mainCheck(result);
            declarationCheck(result);
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
        CheckExistMainVisitor mainVisitor = new CheckExistMainVisitor();
        Integer res = mainVisitor.visit(program);
        if (res == 0){
            System.out.println("Fatal error: There isn't method 'Main'");            
        }
        else{
            if(res == -1){
                System.out.println("Fatal error: Method Main contain arguments");
            }
            else{
                if(res > 1){
                    System.out.println("Fatal error: there is more than one method 'Main'");
                }
            }
        }
    }

    public static void declarationCheck(Program program) {
        DeclarationCheckVisitor decl = new DeclarationCheckVisitor();
        errorList.addAll(decl.visit(program));
    }

}
