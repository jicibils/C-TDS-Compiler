/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;

import main.java.visitor.ASTVisitor;

public class Param extends AST{

	private Type t;
	private String id;
			
	public Param(Type t, String id, int ln, int cn) {
		this.t = t;
		this.id = id;
		this.setLineNumber(ln);
		this.setColumnNumber(cn);
	}
	
	public Type getType() {
		return t;
	}

	public String getId() {
		return id;
	}
	@Override
	public String toString() {
		return this.t.toString() + " " +this.id;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}
