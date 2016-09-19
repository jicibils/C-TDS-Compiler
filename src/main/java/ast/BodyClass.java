/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;

import java.util.List;
import main.java.visitor.ASTVisitor;

/**
 *
 * @author Adrian Tissera
 */
public class BodyClass extends AST{
	private final List<FieldDecl> fl;
	private final List<MethodDecl> ml;
	
	public BodyClass(List<FieldDecl> fl, List<MethodDecl> ml, int ln, int cn) {
		this.fl = fl;
		this.ml = ml;
		this.setLineNumber(ln);
		this.setColumnNumber(cn);
	}

	@Override
	public String toString() {
		String result = new String();
		if (fl != null) {
			for (FieldDecl fd : fl) {
				result += fd.toString() + "\n";
			}
		}
		if (ml != null) {
			result += "\n";
			for (MethodDecl md: ml) {
				result += md.toString() + "\n";
			}			
		}
		return result;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
