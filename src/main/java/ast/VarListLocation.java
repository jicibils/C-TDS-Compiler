package main.java.ast;

import main.java.visitor.ASTVisitor;
import java.util.List;
import java.util.LinkedList;

public class VarListLocation extends Location {
	private int blockId;
	private List<String> arrayId;		
	private Expression expr;			

	public VarListLocation(String id,Expression expr,int line,int column) {
		this.blockId = -1;
		this.id = id;
		this.expr = expr;
		arrayId = new LinkedList<String>();
		this.setLineNumber(line);
		this.setColumnNumber(column);
	}

	public VarListLocation(String id,Expression expr,List<String> l,int line,int column) {
		this.id = id;
		this.expr = expr;
		this.blockId = -1;
		arrayId = l;
		this.setLineNumber(line);
		this.setColumnNumber(column);
	}


	public int getBlockId() {
		return blockId;
	}


	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}


	public List<String> getArrayId() {
		return arrayId;
	}

	public void setArrayId(List<String> l) {
		arrayId = l;
	}


	public Expression getExpression() {
		return expr;
	}

	public void setExpression(Expression e) {
		expr = e;
	}

	@Override
	public String toString() {
		String locString = id;
		for (String arr : arrayId) {
			locString += "." + arr;
		}
		locString += "[" + expr.toString() + "]";
		return locString;
	}


	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}