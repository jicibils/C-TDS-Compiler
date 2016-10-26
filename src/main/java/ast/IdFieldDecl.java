/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;

import main.java.visitor.ASTVisitor;

public class IdFieldDecl extends AST{

	//DONDE HABIA IntLiteral cambie por Integer
	
	private String id;
	private Integer il;
	
	public IdFieldDecl(String id, Integer il){
		this.id = id;
		this.il = il;
	}
	public IdFieldDecl(String id){
		this.id = id;
	}

	public boolean isArray(){
 		return il>0;
  	}

	public String getId() {
		return id;
	}

	public Integer getSize() {
		return il;
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
