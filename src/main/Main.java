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
import main.java.intermediate.*;
import main.java.ast.ErrorClass;

/*
public class Main {
    /**
     * @param args the command line arguments
     */
/*
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
*/
    
public class Main {

    private static List<String> errorList;
    private static List<ErrorClass> errorListType;
    private static List<IntermediateCode> iCList;

    static public void main(String argv[]) {

        try {

            errorList = new LinkedList<String>();
            errorListType = new LinkedList<ErrorClass>();

            iCList = new LinkedList<IntermediateCode>();
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));
            Program result = (Program)p.parse().value;      


            // CheckExistMainVisitor
            mainCheck(result);                  //Call to static method

            // DeclarationCheckVisitor
            declarationCheck(result);   //Call to static method

            if (errorList.size()==0) {
                System.out.println("DeclarationCheckVisitor OK!!!!!!");
                System.out.print("\n   ");
            }else{
                System.out.println("Fatal Error in DeclarationCheckVisitor");
                System.out.print("\n   ");
                int index = errorList.size()-1;
                while(index>0){
                    String res = errorList.get(index);
                    System.out.println(res);
                    index--;
                }

            }

            // TypeCheckVisitor
            if (errorList.size()==0) {
                typeCheck(result);
                System.out.println("TypeCheckVisitor OK!!!!!!");
                System.out.print("\n   ");
            }else{
                System.out.println("Fatal Error in TypeCheckVisitor");
                System.out.print("\n   ");
                int index = errorListType.size()-1;
                while(index>0){
                    ErrorClass res = errorListType.get(index);
                    System.out.println(res.getDesc());
                    index--;
                }

            }

            // ICGeneratorVisitor
            if (errorList.size()==0) {
                iCGenerator(result);
                for (IntermediateCode iC : iCList) {
                    System.out.println(iC.toString());
                }
                // int index = iCList.size()-1;
                // while(index>0){
                //     IntermediateCode res = iCList.get(index);
                //     System.out.println(res.toString());
                //     index--;
                // }
                System.out.println("intermediate code OK!!!!!!");
                System.out.print("\n   ");
            }else{
                System.out.println("Fatal Error in intermediate code");
                System.out.print("\n   ");
            }


        } catch (Exception e) {
            System.out.println("Mensaje de error:\n"+e.getMessage());
            e.printStackTrace();
            
        }
    }
    
    private static void mainCheck(Program program){
        CheckExistMainVisitor mainVisitor = new CheckExistMainVisitor();
        Integer res = mainVisitor.visit(program);
        if((res > 1) || (res == 0))
            System.out.println("Fatal Error: Program must contain just one main method without arguments.");
    }

    private static void declarationCheck(Program program) {
        DeclarationCheckVisitor decl = new DeclarationCheckVisitor();
        errorList.addAll(decl.visit(program));
    }

    private static void typeCheck(Program program) {
        TypeCheckVisitor tCheck = new TypeCheckVisitor();
        errorListType.addAll(tCheck.visit(program));
    }

    public static void iCGenerator(Program program) {
        ICGeneratorVisitor iCGeneratorVisitor = new ICGeneratorVisitor();
        iCGeneratorVisitor.visit(program);
        iCList = iCGeneratorVisitor.getICList();
    }
}



