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
 				return generateCodeFloatOperation(iCode,"addfloat");
            case ADDINT:
 				return generateCodeIntegerOperation(iCode,"addint");
            case SUBFLOAT:
 				return generateCodeFloatOperation(iCode,"subfloat");
            case SUBINT:
 				return generateCodeIntegerOperation(iCode,"subint");
            case MULTFLOAT:
 				return generateCodeFloatOperation(iCode,"multfloat");
            case MULTINT:
 				return generateCodeIntegerOperation(iCode,"multint");
            case DIVFLOAT:
 				return generateCodeFloatOperation(iCode,"divfloat");
            case DIVINT:
 				return generateCodeIntegerOperation(iCode,"divint");
            case MOD:
    			return generateCodeOperatorMod(iCode,"mod");
            case ANDAND:
 				return generateCodeOperatorConditional(iCode,"andand");
            case OROR:
 				return generateCodeOperatorConditional(iCode,"oror");
            case LT:
    			return generateCodeOperatorRelational(iCode,"lt");
            case LTEQ:
    			return generateCodeOperatorRelational(iCode,"lteq");
            case GT:
    			return generateCodeOperatorRelational(iCode,"gt");
            case GTEQ:
    			return generateCodeOperatorRelational(iCode,"gteq");
            case EQEQ:
    			return generateCodeOperatorEqual(iCode,"eqeq");
            case NOTEQ:
    			return generateCodeOperatorEqual(iCode,"noteq");
            case NOT:
    			return generateCodeOperatorNot(iCode,"not");
            case MINUSFLOAT:
 				return generateCodeFloatOperation(iCode,"minusfloat");
            case MINUSINT:
 				return generateCodeIntegerOperation(iCode,"minusint");
            case ASSIGNI:
 				return generateCodeAssignStmt(iCode,"assigni");
            case ASSIGNF:
 				return generateCodeAssignStmt(iCode,"assignf");
            case ASSIGNB:
     			return generateCodeAssignStmt(iCode,"assignb");
            case INCI:
 				return generateCodeAssignStmt(iCode,"inci");
            case INCF:
 				return generateCodeAssignStmt(iCode,"incf");
            case DECI:
 				return generateCodeAssignStmt(iCode,"deci");
            case DECF:
 				return generateCodeAssignStmt(iCode,"decf");
            case ASSIGNLITFLOAT:
 				return generateCodeLiteralStmt(iCode,"assignlitfloat");
            case ASSIGNLITINT:
 				return generateCodeLiteralStmt(iCode,"assignlitint");
            case ASSIGNLITBOOL:
     			return generateCodeLiteralStmt(iCode,"assignlitbool");
            case LABELBEGINCLASS:
     			return generateCodeLabels(iCode,"labelbeginclass");
            case LABELBEGINMETHOD:
     			return generateCodeLabels(iCode,"labelbeginmethod");
            case LABELENDMETHOD:
     			return generateCodeLabels(iCode,"labelendmethod");
            case PUSHID:
    			return generateCodeOperatorPush(iCode,"pushid");
            case PUSHPARAM:
    			return generateCodeOperatorPush(iCode,"pushparam");
            case CALL:
    			return generateCodeOperatorCall(iCode,"call");
            case INITINT:
 				return generateCodeInitVar(iCode,"initint");
            case INITFLOAT:
 				return generateCodeInitVar(iCode,"initfloat");
            case INITBOOL:;
     			return generateCodeInitVar(iCode,"initbool");
            case INITARRAY:;
     			return generateCodeInitVar(iCode,"initarray");
            case JF:
    			return generateCodeOperatorJump(iCode,"jf");
            case JMP:
    			return generateCodeOperatorJump(iCode,"jmp");
            case LABEL:
     			return generateCodeLabels(iCode,"label");
            case LESS:
    			return generateCodeOperatorLess(iCode,"less");
            case RETURN:
 				return generateCodeReturnStmt(iCode,"return");
            case RETURNINT:
 				return generateCodeReturnStmt(iCode,"returnint");
            case RETURNFLOAT:
 				return generateCodeReturnStmt(iCode,"returnfloat");
            case RETURNBOOL:
 				return generateCodeReturnStmt(iCode,"returnbool");
 			default: return "";
        }
    }


 	private static String initialize() {
        System.out.println("ESTOY EN INITIALIZE!!!!!!!!!!");
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
        System.out.println("ESTOY EN FINISH!!!!!!!!!!");
 		String finish = " \n";
 			finish += ".LFE0: \n";
 			finish += "		.size 	main, 	.-main \n";
 			finish += "		.ident 	'GCC:  (Ubuntu 4.8.4-2ubuntu1~14.04.3) 4.8.4' \n";
 			finish += "		.section	.note.GNU-stack,'',@progbits \n";
 			finish += " \n";
 			return finish;
 	}

 	private static String generateCodeFloatOperation(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE FLOAT OPERATION!!!!!!!!!!");
 		if(nameInstruction.equals("addfloat")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("subfloat")){
	 			return nameInstruction;
	 		}else{
		 		if(nameInstruction.equals("multfloat")){
	 				return nameInstruction;
 				}else{
			 		if(nameInstruction.equals("divfloat")){
		 				return nameInstruction;
		 			}else{
				 		if(nameInstruction.equals("minusfloat")){
			 				return nameInstruction;
			 			}
		 			}			 				
	 			}
 			}
		}
		return nameInstruction;
 	}

 	private static String generateCodeIntegerOperation(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE INTEGER OPERATION!!!!!!!!!!");
 		if(nameInstruction.equals("addint")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("subint")){
	 			return nameInstruction;
	 		}else{
		 		if(nameInstruction.equals("multint")){
	 				return nameInstruction;
 				}else{
			 		if(nameInstruction.equals("divint")){
		 				return nameInstruction;
		 			}else{
				 		if(nameInstruction.equals("minusint")){
			 				return nameInstruction;
			 			}
		 			}			 				
	 			}
 			}
		}
		return nameInstruction;
 	}


 	private static String generateCodeReturnStmt(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE RETURN STMT!!!!!!!!!!");
 		if(nameInstruction.equals("return")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("returnint")){
	 			return nameInstruction;
	 		}else{
		 		if(nameInstruction.equals("returnfloat")){
	 				return nameInstruction;
 				}else{
			 		if(nameInstruction.equals("returnbool")){
		 				return nameInstruction;
		 			}
 				}
 			}
 		}
		return nameInstruction;
 	}

 	private static String generateCodeAssignStmt(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE ASSIGN STMT!!!!!!!!!!");
 		if(nameInstruction.equals("assigni")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("assignf")){
	 			return nameInstruction;
	 		}else{
		 		if(nameInstruction.equals("assignb")){
	 				return nameInstruction;
 				}else{
			 		if(nameInstruction.equals("inci")){
 						return nameInstruction;
 					}else{
	 					if(nameInstruction.equals("incf")){
	 						return nameInstruction;
	 					}else{
		 					if(nameInstruction.equals("deci")){
				 				return nameInstruction;
				 			}else{
			 					if(nameInstruction.equals("decf")){
					 				return nameInstruction;
					 			}
				 			}
				 		}
				 	}
 				}
 			}
 		}
		return nameInstruction;
 	}

 	private static String generateCodeLiteralStmt(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE LITERAL STMT!!!!!!!!!!");
 		if(nameInstruction.equals("assignlitint")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("assignlitfloat")){
	 			return nameInstruction;
	 		}else{
		 		if(nameInstruction.equals("assignlitbool")){
	 				return nameInstruction;
 				}
 			}
 		}
		return nameInstruction;
 	}

 	private static String generateCodeOperatorConditional(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR CONDITIONAL!!!!!!!!!!");
 		if(nameInstruction.equals("andand")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("oror")){
	 			return nameInstruction;
 			}
		}
		return nameInstruction;
 	}


 	private static String generateCodeOperatorRelational(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR RELATIONAL!!!!!!!!!!");
 		if(nameInstruction.equals("lt")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("lteq")){
	 			return nameInstruction;
	 		}else{
		 		if(nameInstruction.equals("gt")){
	 				return nameInstruction;
 				}else{
			 		if(nameInstruction.equals("gteq")){
		 				return nameInstruction;
		 			}			 				
	 			}
 			}
		}
		return nameInstruction;
 	}

 	private static String generateCodeOperatorEqual(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR EQUAL!!!!!!!!!!");
 		if(nameInstruction.equals("eqeq")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("noteq")){
	 			return nameInstruction;
 			}
		}
		return nameInstruction;
 	}

 	private static String generateCodeOperatorPush(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR PUSH!!!!!!!!!!");
 		if(nameInstruction.equals("pushid")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("pushparam")){
	 			return nameInstruction;
 			}
		}
		return nameInstruction;
 	}

 	private static String generateCodeOperatorCall(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR CALL!!!!!!!!!!");
 		if(nameInstruction.equals("call")){
 			return nameInstruction;
		}
		return nameInstruction;
 	}

 	private static String generateCodeOperatorJump(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR JUMP!!!!!!!!!!");
 		if(nameInstruction.equals("jf")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("jmp")){
	 			return nameInstruction;
 			}
		}
		return nameInstruction;
 	}

 	private static String generateCodeInitVar(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE INIT VAR!!!!!!!!!!");
 		if(nameInstruction.equals("initint")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("initfloat")){
	 			return nameInstruction;
	 		}else{
		 		if(nameInstruction.equals("initbool")){
	 				return nameInstruction;
 				}else{
			 		if(nameInstruction.equals("initarray")){
		 				return nameInstruction;
		 			}			 				
	 			}
 			}
		}
		return nameInstruction;
 	}

 	private static String generateCodeLabels(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE LABELS!!!!!!!!!!");
 		if(nameInstruction.equals("label")){
 			return nameInstruction;
 		}else{
	 		if(nameInstruction.equals("labelbeginclass")){
	 			return nameInstruction;
	 		}else{
		 		if(nameInstruction.equals("labelbeginmethod")){
	 				return nameInstruction;
 				}else{
			 		if(nameInstruction.equals("labelendmethod")){
		 				return nameInstruction;
		 			}			 				
	 			}
 			}
		}
		return nameInstruction;
 	}

 	private static String generateCodeOperatorNot(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR NOT!!!!!!!!!!");
 		if(nameInstruction.equals("not")){
 			return nameInstruction;
		}
		return nameInstruction;
 	}

 	 	private static String generateCodeOperatorMod(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR MOD!!!!!!!!!!");
 		if(nameInstruction.equals("mod")){
 			return nameInstruction;
		}
		return nameInstruction;
 	}

 	 	private static String generateCodeOperatorLess(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR LESS!!!!!!!!!!");
 		if(nameInstruction.equals("less")){
 			return nameInstruction;
		}
		return nameInstruction;
 	}

}
