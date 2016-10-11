package main.java.ast;

import main.java.visitor.ASTVisitor;

public abstract class AST {
	private int lineNumber;
	private int colNumber;
        //private String id;
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(int ln) {
		lineNumber = ln;
	}
	
	public int getColumnNumber() {
		return colNumber;
	}
	
	public void setColumnNumber(int cn) {
		colNumber = cn;
	}

	public abstract <T> T accept(ASTVisitor<T> v);
}
