package main.java.intermediate;

import main.java.ast.*;

/**
 *
 * @author Ezequiel Arangue
 */
public class IntermediateCode{
    private String operator;
    
    private AST operand1;
    private AST operand2;
    private AST result;
    
    public IntermediateCode(String operator, AST op1, AST op2, AST res){
        this.operator = operator;
        this.operand1 = op1;
        this.operand2 = op2;
        this.result = res;
    }
    
    public String getOperator(){
        return operator;
    }
}
