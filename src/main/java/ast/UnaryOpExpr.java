package main.java.ast;

import main.java.visitor.ASTVisitor;

public class UnaryOpExpr extends Expression {
	
	private UnaryOpType operator; 	//expr = operator expr
	private Expression operand; 	//expression
	
	
	//Constructor
	
	public UnaryOpExpr(UnaryOpType op, Expression e,int line,int column){
		operator = op;
		operand = e;
		this.setLineNumber(line);
		this.setColumnNumber(column);
	}
	
	public UnaryOpType getOperator() {
		return operator;
	}

	public void setOperator(UnaryOpType operator) {
		this.operator = operator;
	}

	public Expression getOperand() {
		return operand;
	}

	public void setOperand(Expression operand) {
		this.operand = operand;
	}


	@Override
	public String toString() {
		return operator.toString() + operand.toString();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}