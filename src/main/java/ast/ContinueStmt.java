package main.java.ast;

import main.java.visitor.ASTVisitor;

public class ContinueStmt extends Statement{

	public ContinueStmt(int line, int column){
        this.setLineNumber(line);
        this.setColumnNumber(column);
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
