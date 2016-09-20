package main.java.ast;

import main.java.visitor.ASTVisitor;

public class ReturnStmt extends Statement {
	private Expression expression; // the return expression
	
	public ReturnStmt(Expression e, int line, int column) {
		this.expression = e;
		this.setLineNumber(line);
		this.setColumnNumber(column);
	}
	
	public ReturnStmt(int line, int column) {
		this.expression = null;
		this.setLineNumber(line);
		this.setColumnNumber(column);
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		if (expression == null) {
			return "return";
		}
		else {
			return "return " + expression;
		}
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
