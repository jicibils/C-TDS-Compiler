/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;

import main.java.visitor.ASTVisitor;

public class IdFieldDecl extends AST{
	private final String id;
	private final IntLiteral il;
	
	public IdFieldDecl(String id, IntLiteral il){
		this.id = id;
		this.il = il;
	}

	@Override
	public String toString() {
		String result = id;
		return result;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
