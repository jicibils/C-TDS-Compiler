// AssemblerGenerator.java

package main.java.assembler;

import java.util.List;
import java.util.LinkedList;
import main.java.ast.*;
import main.java.intermediate.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class AssemblerGenerator {

    public AssemblerGenerator(){
    }

 	public static void generateCodeAssembler(List<IntermediateCode> iCodeStmt) {
        System.out.println("ESTOY EN GENERATE CODE ASSEMBLER!!!!!!!!!!");
 		
 		try {

 			// Output file
      		PrintWriter writer = new PrintWriter("assembler.s", "UTF-8");

      		//initialize the file
			writer.println(initialize());
			
			for (IntermediateCode intermediateCode: iCodeStmt) {

				// Generate the assembler for each instruction
				writer.println(generateCodeInstruction(intermediateCode));
			}			

      		//finish the file
			writer.println(finish());

			writer.close();

 		} catch (IOException e) {
 			e.printStackTrace();
 		}

 	}


 	private static String generateCodeInstruction(IntermediateCode iCode) {
        System.out.println("ESTOY EN GENERATE CODE INSTRUCTION!!!!!!!!!!");
 		
 		Instruction instruction = iCode.getOperator();

 		switch (instruction) {
            case ADDFLOAT:
 				return generateCodeFloatOperation(iCode,"addf");
            case ADDINT:
 				return generateCodeIntegerOperation(iCode,"addi");
            case SUBFLOAT:
                return "SUBFLOAT";
            case SUBINT:
                return "SUBINT";
            case MULTFLOAT:
                return "MULTFLOAT";
            case MULTINT:
                return "MULTINT";
            case DIVFLOAT:
                return "DIVFLOAT";
            case DIVINT:
                return "DIVINT";
            case MOD:
                return "MOD";
            case ANDAND:
                return "ANDAND";
            case OROR:
                return "OROR";
            case LT:
                return "LT";
            case LTEQ:
                return "LTEQ";
            case GT:
                return "GT";
            case GTEQ:
                return "GTEQ";
            case EQEQ:
                return "EQEQ";
            case NOTEQ:
                return "NOTEQ";
            case NOT:
                return "NOT";
            case MINUSFLOAT:
                return "MINUSFLOAT";
            case MINUSINT:
                return "MINUSINT";
            case ASSIGNI:
                return "ASSIGNI";
            case ASSIGNF:
                return "ASSIGNF";
            case ASSIGNB:
                return "ASSIGNB";
            case INCI:
                return "INCI";
            case INCF:
                return "INCF";
            case DECI:
                return "DECI";
            case DECF:
                return "DECF";
            case ASSIGNLITFLOAT:
                return "ASSIGNLITFLOAT";
            case ASSIGNLITINT:
                return "ASSIGNLITINT";
            case ASSIGNLITBOOL:
                return "ASSIGNLITBOOL";
            case LABELBEGINCLASS:
			return "LABELBEGINCLASS";
            case LABELBEGINMETHOD:
                return "LABELBEGINMETHOD";
            case LABELENDMETHOD:
                return "LABELENDMETHOD";
            case PUSHID:
                return "PUSHID";
            case PUSHPARAM:
                return "PUSHPARAM";
            case CALL:
                return "CALL";
            case INITINT:
                return "INITINT";
            case INITFLOAT:
                return "INITFLOAT";
            case INITBOOL:;
                return "INITBOOL";
            case INITARRAY:;
                return "INITARRAY";
            case JF:
                return "JF";
            case JMP:
                return "JMP";
            case LABEL:
                return "LABEL";
            case LESS:
                return "LESS";
            case RETURN:
                return "RETURN";
            case RETURNINT:
                return "RETURNINT";
            case RETURNFLOAT:
                return "RETURNFLOAT";
            case RETURNBOOL:
                return "RETURNBOOL";
 			default: return "";
        }
    }


 	private static String initialize() {
 		String initialize = "     .file 	"+"fileName"+"\n";
 			initialize += "     .text \n";
 			initialize += "     .globl 	main \n";
 			initialize += "     .type 	main, @function \n";
 			initialize += "main: \n";
 			initialize += ".LFB0: \n";
 			initialize += "\n";
 			return initialize;
 	}


 	private static String finish() {
 		String finish = " \n";
 			finish += ".LFE0: \n";
 			finish += "		.size 	main, 	.-main \n";
 			finish += "		.ident 	'GCC:  (Ubuntu 4.8.4-2ubuntu1~14.04.3) 4.8.4' \n";
 			finish += "		.section	.note.GNU-stack,'',@progbits \n";
 			finish += " \n";
 			return finish;
 	}

 	private static String generateCodeFloatOperation(IntermediateCode iCode,String nameInstruction) {
 		return nameInstruction;
 	}

 	private static String generateCodeIntegerOperation(IntermediateCode iCode,String nameInstruction) {
 		return nameInstruction;
 	}

}
