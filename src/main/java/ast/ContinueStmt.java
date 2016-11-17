package main.java.ast;

import main.java.visitor.ASTVisitor;

public class ContinueStmt extends Statement{

	public ContinueStmt(int line, int column){
        setLineNumber(line);
        setColumnNumber(column);
	}
    
    @Override
	public String toString(){
		return "continue";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
