package main;


import java.io.FileReader;
import main.java.ast.Program;
import main.java.lexer.Lexer;
import main.java.parser.Parser;
import main.java.visitor.*;
import java.util.List;
import java.util.LinkedList;
import main.java.ast.ErrorClass;

public class MainForDeclarationTest {

    private static List<ErrorClass> errorList;
    
    static public void main(String argv[]) {

        try {

            errorList = new LinkedList<ErrorClass>();
            
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));
            Program result = (Program)p.parse().value;      


            // CheckExistMainVisitor
            mainCheck(result);                  //Call to static method

            // DeclarationCheckVisitor
            declarationCheck(result);   //Call to static method

            if (errorList.size()==0) {
                System.out.println("No declaration errors found.\n");
            } else {
                
                System.out.println("There are one or more declaration errors. The errors are the following: ");
                
                int index = errorList.size()-1;
                while(index>=0){
                    ErrorClass res = errorList.get(index);
                    System.out.println(res.getDesc());
                    index--;
                }
            }

        } catch (Exception e) {
            System.out.println("Wrong input.");
        }
    }
    
    private static void mainCheck(Program program){
        CheckExistMainVisitor mainVisitor = new CheckExistMainVisitor();
        Integer res = mainVisitor.visit(program);
        if((res > 1) || (res == 0)){
            System.out.println("Fatal Error: Program must contain just one main method without arguments.");
            System.out.print("\n   ");
        }else{
            System.out.println("Main Check OK.");            
            System.out.print("\n   ");
        }
    }

    private static void declarationCheck(Program program) {
        DeclarationCheckVisitor decl = new DeclarationCheckVisitor();
        errorList.addAll(decl.visit(program));
    }
}