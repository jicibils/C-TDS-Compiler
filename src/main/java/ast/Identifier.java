// Identifier.java

// class that represents the ast nodes

package main.java.ast;

import main.java.visitor.ASTVisitor;

public abstract class Identifier extends AST {

	private String sym;	// Identifier

	public String getSym() {
		return sym;
	}

	public void setSym(String sym) {
		this.sym = sym;
	}
	

}