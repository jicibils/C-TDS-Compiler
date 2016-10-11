package main.java.ast;

import main.java.visitor.ASTVisitor;

public class WhileStatement extends Statement{
	private Expression expr;
	private Block block;

	public WhileStatement(Expression e, Block b, int line, int column){
		this.expr = e;
		this.block = b;
        this.setLineNumber(line);
        this.setColumnNumber(column);
                
	}

	public Expression getExpression(){
		return expr;
	}

	public void setExpression(Expression e){
		this.expr = e;
	}

	public Block getBlock(){
		return block;
	}

	public void setBlock(Block b){
		this.block = b;
	}

	@Override
	public String toString(){
		return "while" + expr.toString() + ", \n" + block.toString();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}