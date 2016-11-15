package main.java.ast;

import main.java.visitor.ASTVisitor;

public class IfStatement extends Statement {
	private Expression condition;
	private Block ifBlock;
	private Block elseBlock;
	
	public IfStatement(Expression cond, Block ifBl, int line, int column) {
		this.condition = cond;
		this.ifBlock = ifBl;
		this.elseBlock = null;
		setLineNumber(line);
		setColumnNumber(column);
	}
	
	public IfStatement(Expression cond, Block ifBl, Block elseBl, int line, int column) {
		this.condition = cond;
		this.ifBlock = ifBl;
		this.elseBlock = elseBl;
		setLineNumber(line);
		setColumnNumber(column);
	}

	public Expression getCondition() {
		return condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Block getIfBlock() {
		return ifBlock;
	}

	public void setIfBlock(Block ifBlock) {
		this.ifBlock = ifBlock;
	}

	public Block getElseBlock() {
		return elseBlock;
	}

	public void setElseBlock(Block elseBlock) {
		this.elseBlock = elseBlock;
	}
	
	public Boolean thereIsElseBlock() {
		return elseBlock != null;
	}

	@Override
	public String toString() {
		String rtn = "if " + condition + '\n' + ifBlock.toString();
		
		if (elseBlock != null) {
			rtn += "else \n" + elseBlock;
		}
		
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
