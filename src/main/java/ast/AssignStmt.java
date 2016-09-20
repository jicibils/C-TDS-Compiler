package main.java.ast;

import main.java.visitor.ASTVisitor;

public class AssignStmt extends Statement {
	private Location location;
	private Expression expr;
	private AssignOpType operator;

	public AssignStmt(Location loc, AssignOpType op, Expression e,int line, int column) {
		this.location = loc;
		this.expr = e;
		this.operator = op;
		setLineNumber(line);
		setColumnNumber(column);
	}

	public AssignStmt(Location loc, AssignOpType op, Expression e) {
		location = loc;
		expr = e;
		operator = op;
	}
	
	public void setLocation(Location loc) {
		this.location = loc;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setExpression(Expression e) {
		this.expr = e;
	}
	
	public Expression getExpression() {
		return this.expr;
	}
	
	public AssignOpType getOperator() {
		return operator;
	}

	public void setOperator(AssignOpType operator) {
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		return location + " " + operator + " " + expr;
		
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
