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
	private static int contLabelMethod = -1; //index for initialize methods
    private static PrintWriter writer;
    private static int cantMethods = 0;
	private static int index = 1; //index for finish methods
    private static LinkedList<String> listIdMethod; //list ids methods


    public AssemblerGenerator(){
	}

 	public static void generateCodeAssembler(List<IntermediateCode> iCodeStmt) {
        System.out.println("ESTOY EN GENERATE CODE ASSEMBLER!!!!!!!!!!");
 		
 		try {

 			// Output file
      		writer = new PrintWriter("assembler.s", "UTF-8");
      		IntermediateCode ultObj = new IntermediateCode(null,null,null,null);

      		//make the list with method's ids
      		contMethods(iCodeStmt);
			

			for (IntermediateCode intermediateCode: iCodeStmt) {

				// Generate the assembler for each instruction
				writer.println(generateCodeInstruction(intermediateCode));
				ultObj = intermediateCode;
			}			

			// recovery name method
			Label label = (Label)ultObj.getResult();
      		//finish the file
			writer.println(finish(label.getLabelId()));

			writer.close();

 		} catch (IOException e) {
 			e.printStackTrace();
 		}

 	}

 	//method that return the number of methods and make list with method's ids
 	private static void contMethods(List<IntermediateCode> iCodeStmt) {
        System.out.println("ESTOY EN CONT METHODS!!!!!!!!!!");
        listIdMethod = new LinkedList<String>();        
		for (IntermediateCode obj: iCodeStmt) {
			Instruction instruction = obj.getOperator();
			String res = instruction.toString();
	        if(res.equals("LABELBEGINMETHOD")){
			Label label = (Label)obj.getResult();
	        	listIdMethod.add(label.getLabelId());	        	
	        	cantMethods++;
	        }
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
		 		if(contLabelMethod == -1){
					Label label = (Label)iCode.getResult();
			  		//initialize the file
					writer.println(initialize(label.getLabelId()));
				}
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

    //method for generate the number of methods' labels in assembler file
 	private static int contLabelMethod() {
        System.out.println("ESTOY EN CONT LABEL METHOD()!!!!!!!!!!");
 		return contLabelMethod = contLabelMethod + 1;
 	}


	//initialize assembler's file 
 	private static String initialize(String id) {
        System.out.println("ESTOY EN INITIALIZE!!!!!!!!!!");
        String initialize = "     .file     "+"\""+"assembler.s"+"\""+"\n";
 			initialize += "     .section 		.rodata \n";
 			initialize += ".LC0: \n";
 			initialize += "     .string "+"\"%d\" \n";
 			initialize += "     .text \n";
 			initialize += "     .globl 	"+id+" \n";
 			initialize += "     .type 	"+id+", @function \n";
			//FILENAME ES EL NOMBRE DEL PRIMER METODO QUE HAYAs 			
 			initialize += "\n";
 			return initialize;
 	}

	//initialize methods in assembler's file 
 	private static String initializeMethod(String id,int maxOffset) {
        System.out.println("ESTOY EN INITIALIZE METHOD!!!!!!!!!!");
 		String initializeMethod = id+": \n";
 			initializeMethod += " .LFB"+contLabelMethod()+": \n";
			initializeMethod += "\t pushq	%rbp\n";
			initializeMethod += "\t movq	%rsp, %rbp\n";
			initializeMethod += "\t subq	$"+(maxOffset * (-1))+", %rsp\n";
 			initializeMethod += "\n";
 			return initializeMethod;
 	}

	//finish methods in assembler's file, common for all
 	private static String finishMethodCommon(String id) {
        System.out.println("ESTOY EN FINISH METHOD COMMON!!!!!!!!!!");
 		String	finishMethodCommon = " \n";
			finishMethodCommon +="\t leave \n";
  			finishMethodCommon += "\t ret \n";
 			finishMethodCommon += ".LFE"+contLabelMethod+": \n";
 			finishMethodCommon += " \n";
 			return finishMethodCommon;

 	}

 	//recovery id to next method to current method
 	private static String nextMethod(){
 		return listIdMethod.get(contLabelMethod + 1);
 	}

	//finish methods in assembler's file, if the method isn't the last method
 	private static String finishMethod(String id) {
        System.out.println("ESTOY EN FINISH METHOD!!!!!!!!!!");
 		String	finishMethod = " \n";
 			finishMethod += "		.size 	"+id+", 	.-"+id+" \n";
 			finishMethod += "		.globl	"+nextMethod()+" \n";
 			finishMethod += "		.type	"+nextMethod()+", @function \n";
 			finishMethod += " \n";
 			return finishMethod;
 		}


	//finish methods in assembler's file, if the method is the last method
 	private static String finish(String id) {
        System.out.println("ESTOY EN FINISH!!!!!!!!!!");
 		String finish = " \n";
 			//FILENAME ES EL NOMBRE DEL ULTIMO METODO QUE HAYA
 			finish += "		.size 	"+id+", 	.-"+id+" \n";
 			// finish += "		.ident 	\"GCC:  (Ubuntu 4.8.4-2ubuntu1~14.04.3) 4.8.4\" \n";
 			// finish += "		.section	.note.GNU-stack,'',@progbits \n";
 			finish += " \n";
 			return finish;
 	}

 	private static String generateCodeInitVar(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE INIT VAR!!!!!!!!!!");
 		if(nameInstruction.equals("initint")){
	        System.out.println("ESTOY EN GENERATE CODE INIT INT!!!!!!!!!!");
 			return "";
 		}else{
	 		if(nameInstruction.equals("initfloat")){
    		    System.out.println("ESTOY EN GENERATE CODE INIT FLOAT!!!!!!!!!!");
	 			return "";
	 		}else{
		 		if(nameInstruction.equals("initbool")){
        			System.out.println("ESTOY EN GENERATE CODE INIT BOOL!!!!!!!!!!");
	 				return "";
 				}else{
			 		if(nameInstruction.equals("initarray")){
        				System.out.println("ESTOY EN GENERATE CODE INIT ARRAY!!!!!!!!!!");
		 				return "";
		 			}			 				
	 			}
 			}
		}
		return "";
 	}

 	private static String generateCodeAssignStmt(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE ASSIGN STMT!!!!!!!!!!");


 		if(nameInstruction.equals("assigni")){
 	        System.out.println("ESTOY EN GENERATE CODE ASSIGN INT!!!!!!!!!!");

	        VarLocation varLocation = (VarLocation)iCode.getResult();
	        Location loc = (Location)iCode.getOp2();
	        AssignStmt assignStmt  = (AssignStmt)loc.getDeclaration().getValue();
	        Expression expression = assignStmt.getExpression();
			int offset = varLocation.getOffset();

	        //make the assembler
 			String result = "\t movl	$"+expression+", "+offset+"(%rbp) \n";
 				result += "\t movl	"+offset+"(%rbp)"+", %eax \n";	

 			return result;
 		}else{
	 		if(nameInstruction.equals("assignf")){
    		    System.out.println("ESTOY EN GENERATE CODE ASSIGN FLOAT!!!!!!!!!!");
	 			return "";
	 		}else{
		 		if(nameInstruction.equals("assignb")){
        			System.out.println("ESTOY EN GENERATE CODE ASSIGN BOOL!!!!!!!!!!");
	 				return "";
 				}else{
			 		if(nameInstruction.equals("inci")){
        				System.out.println("ESTOY EN GENERATE CODE ASSIGN INC INT!!!!!!!!!!");

				        VarLocation varLocation = (VarLocation)iCode.getResult();
        				int offset = varLocation.getOffset();
				        //make the assembler
			 			String result = "\t addl	$1, "+offset+"(%rbp) \n";
			 				result += "\t movl	"+offset+"(%rbp)"+", %eax \n";	

			 			return result;
 					}else{
	 					if(nameInstruction.equals("incf")){
        					System.out.println("ESTOY EN GENERATE CODE ASSIGN INC FLOAT!!!!!!!!!!");
	 						return "";
	 					}else{
		 					if(nameInstruction.equals("deci")){
        						System.out.println("ESTOY EN GENERATE CODE ASSIGN DEC INT!!!!!!!!!!");

						        VarLocation varLocation = (VarLocation)iCode.getResult();
		        				int offset = varLocation.getOffset();
						        //make the assembler
					 			String result = "\t subl	$1, "+offset+"(%rbp) \n";
					 				result += "\t movl	"+offset+"(%rbp)"+", %eax \n";	

					 			return result;

				 			}else{
			 					if(nameInstruction.equals("decf")){
        							System.out.println("ESTOY EN GENERATE CODE ASSIGN DEC FLOAT!!!!!!!!!!");
					 				return "";
					 			}
				 			}
				 		}
				 	}
 				}
 			}
 		}
		return "";
 	}


 	private static String generateCodeFloatOperation(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE FLOAT OPERATION!!!!!!!!!!");
 		if(nameInstruction.equals("addfloat")){
 			return "";
 		}else{
	 		if(nameInstruction.equals("subfloat")){
	 			return "";
	 		}else{
		 		if(nameInstruction.equals("multfloat")){
	 				return "";
 				}else{
			 		if(nameInstruction.equals("divfloat")){
		 				return "";
		 			}else{
				 		if(nameInstruction.equals("minusfloat")){
			 				return "";
			 			}
		 			}			 				
	 			}
 			}
		}
		return "";
 	}

 	private static String generateCodeIntegerOperation(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE INTEGER OPERATION!!!!!!!!!!");
 		if(nameInstruction.equals("addint")){
 			return "";
 		}else{
	 		if(nameInstruction.equals("subint")){
	 			return "";
	 		}else{
		 		if(nameInstruction.equals("multint")){
	 				return "";
 				}else{
			 		if(nameInstruction.equals("divint")){
		 				return "";
		 			}else{
				 		if(nameInstruction.equals("minusint")){
			 				return "";
			 			}
		 			}			 				
	 			}
 			}
		}
		return "";
 	}

 	// return is implemented how the sentence "printf" in C
 	private static String generateCodeReturnStmt(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE RETURN STMT!!!!!!!!!!");
 		if(nameInstruction.equals("return")){
        	System.out.println("ESTOY EN GENERATE CODE RETURN !!!!!!!!!!");
 			return "";
 		}else{
	 		if(nameInstruction.equals("returnint")){
		        System.out.println("ESTOY EN GENERATE CODE RETURN INTEGER!!!!!!!!!!");
	 			String	result = "\t movl	%eax, %esi \n";	
	 				result += "\t movl	$.LC0, %edi \n";	
	 				result += "\t movl	$0, %eax \n";	
	 				result += "\t call	printf \n";	
	 				result += "\t movl	$0, %eax \n";

	 			return result;	
	 		}else{
		 		if(nameInstruction.equals("returnfloat")){
			        System.out.println("ESTOY EN GENERATE CODE RETURN FLOAT!!!!!!!!!!");
	 				return "";
 				}else{
			 		if(nameInstruction.equals("returnbool")){
				        System.out.println("ESTOY EN GENERATE CODE RETURN BOOL!!!!!!!!!!");
		 				return "";
		 			}
 				}
 			}
 		}
		return "";
 	}


 	private static String generateCodeLiteralStmt(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE LITERAL STMT!!!!!!!!!!");
 		if(nameInstruction.equals("assignlitint")){
	        System.out.println("ESTOY EN GENERATE CODE LITERAL INT!!!!!!!!!!");
 			return "";
 		}else{
	 		if(nameInstruction.equals("assignlitfloat")){
    		    System.out.println("ESTOY EN GENERATE CODE LITERAL FLOAT!!!!!!!!!!");
	 			return "";
	 		}else{
		 		if(nameInstruction.equals("assignlitbool")){
        			System.out.println("ESTOY EN GENERATE CODE LITERAL BOOL!!!!!!!!!!");
	 				return "";
 				}
 			}
 		}
		return "";
 	}

 	private static String generateCodeOperatorConditional(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR CONDITIONAL!!!!!!!!!!");
 		if(nameInstruction.equals("andand")){
 			return "";
 		}else{
	 		if(nameInstruction.equals("oror")){
	 			return "";
 			}
		}
		return "";
 	}


 	private static String generateCodeOperatorRelational(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR RELATIONAL!!!!!!!!!!");
 		if(nameInstruction.equals("lt")){
 			return "";
 		}else{
	 		if(nameInstruction.equals("lteq")){
	 			return "";
	 		}else{
		 		if(nameInstruction.equals("gt")){
	 				return "";
 				}else{
			 		if(nameInstruction.equals("gteq")){
		 				return "";
		 			}			 				
	 			}
 			}
		}
		return "";
 	}

 	private static String generateCodeOperatorEqual(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR EQUAL!!!!!!!!!!");
 		if(nameInstruction.equals("eqeq")){
 			return "";
 		}else{
	 		if(nameInstruction.equals("noteq")){
	 			return "";
 			}
		}
		return "";
 	}

 	private static String generateCodeOperatorPush(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR PUSH!!!!!!!!!!");
 		if(nameInstruction.equals("pushid")){
 			return "";
 		}else{
	 		if(nameInstruction.equals("pushparam")){
	 			return "";
 			}
		}
		return "";
 	}

 	private static String generateCodeOperatorCall(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR CALL!!!!!!!!!!");
 		if(nameInstruction.equals("call")){
 			return "";
		}
		return "";
 	}

 	private static String generateCodeOperatorJump(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR JUMP!!!!!!!!!!");
 		if(nameInstruction.equals("jf")){
 			return "";
 		}else{
	 		if(nameInstruction.equals("jmp")){
	 			return "";
 			}
		}
		return "";
 	}


 	private static String generateCodeLabels(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE LABELS!!!!!!!!!!");
 		if(nameInstruction.equals("label")){
 			return "";
 		}else{
	 		if(nameInstruction.equals("labelbeginclass")){
	 			return "";
	 		}else{
		 		if(nameInstruction.equals("labelbeginmethod")){
			        Label label = (Label)iCode.getResult();
		 			String res = initializeMethod(label.getLabelId(),label.getMaxOffset());
	 				return res;
 				}else{
			 		if(nameInstruction.equals("labelendmethod")){
				        Label label = (Label)iCode.getResult();
			 			String result = finishMethodCommon(label.getLabelId());
			 			if(index < cantMethods){
			 				index++;
				 			String res = finishMethod(label.getLabelId());
			 				return result + res;
			 			}else{
			 				return result;
			 			}
		 			}			 				
	 			}
 			}
		}
		return "";
 	}

 	private static String generateCodeOperatorNot(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR NOT!!!!!!!!!!");
 		if(nameInstruction.equals("not")){
 			return "";
		}
		return "";
 	}

 	 	private static String generateCodeOperatorMod(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR MOD!!!!!!!!!!");
 		if(nameInstruction.equals("mod")){
 			return "";
		}
		return "";
 	}

 	 	private static String generateCodeOperatorLess(IntermediateCode iCode,String nameInstruction) {
        System.out.println("ESTOY EN GENERATE CODE OPERATOR LESS!!!!!!!!!!");
 		if(nameInstruction.equals("less")){
 			return "";
		}
		return "";
 	}

}
