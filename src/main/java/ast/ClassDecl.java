/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;

import main.java.visitor.ASTVisitor;

/**
 *
 * @author Adrian Tissera
 */
public class ClassDecl extends AST {
	private String id;
	private BodyClass bc;
	
	public ClassDecl(String id, BodyClass bc) {
		this.id = id;
		this.bc = bc;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
