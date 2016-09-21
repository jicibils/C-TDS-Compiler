package main.java.ast;

import java.util.ArrayList;
import java.util.List;
import main.java.visitor.ASTVisitor;

public class Block extends Statement {
    private List<FieldDecl> fieldDecl;
	private List<Statement> statements;
	private int blockId;
	
	public Block() {
		statements = new ArrayList<Statement>();
        fieldDecl  = new ArrayList<FieldDecl>();
		blockId    = -1;
	}
	
	public Block(List<FieldDecl> f, List<Statement> s) {
		blockId = -1;
		statements = s;
        fieldDecl = f;
	}
	
	public void addStatement(Statement s) {
		this.statements.add(s);
	}
		
	public List<Statement> getStatements() {
		return this.statements;
	}
        
    public void addFieldDecl(FieldDecl fd){
        this.fieldDecl.add(fd);
    }
        
    public List<FieldDecl> getFieldDecl(){
        return this.fieldDecl;
    }
		
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	@Override
	public String toString() {
        String result = "{\n";

		if (fieldDecl != null) {
			for(FieldDecl f : fieldDecl){
				result += " " + f.toString() + "\n";
			}	
		}
        
		if (statements != null) {
			for (Statement s: statements) {
				result += " " + s.toString() + "\n";
			}	
		}
		result += "}"; 
		return result; 
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}
