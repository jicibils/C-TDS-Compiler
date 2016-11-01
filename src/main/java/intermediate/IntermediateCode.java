package main.java.intermediate;

import main.java.ast.*;

public class IntermediateCode{
    private Instruction operator;
    private Label label;     
    
    private AST operand1;
    private AST operand2;
    private AST result;
    
    public IntermediateCode(Instruction operator, AST op1, AST op2, AST res){
        this.operator = operator;
        this.operand1 = op1;
        this.operand2 = op2;
        this.result = res;
    }
    
    public Instruction getOperator(){
        return operator;
    }
    public Label getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label.toString() + ": " + operator.toString();
    }

}
