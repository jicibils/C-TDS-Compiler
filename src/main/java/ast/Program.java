/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;

import java.util.List;
import main.java.visitor.ASTVisitor;

public class Program extends AST{
	private final List<ClassDecl> classList;
	
	public Program (List<ClassDecl> classList, int ln, int cn) {
		this.classList = classList;
		this.setLineNumber(ln);
		this.setColumnNumber(cn);
		
	}

	public List<ClassDecl> getClassList() {
		return classList;
	}
	
	public void addClassDecl (ClassDecl classDecl) {
		this.classList.add(classDecl);
	}
	
	@Override
	public String toString(){
		String result = new String();
		for (ClassDecl cd : this.classList) {
			result += cd.toString() + "\n";
		}
		return result;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}
