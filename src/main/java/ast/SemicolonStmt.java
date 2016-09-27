package main.java.ast;

import main.java.visitor.ASTVisitor;


public class SemicolonStmt extends Statement{
    
    public SemicolonStmt(int line, int column){
        this.setLineNumber(line);
        this.setColumnNumber(column);
    }
	@Override
	public String toString(){
		return ";\n";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v){
		return v.visit(this);
	}
}