/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;

import main.java.visitor.ASTVisitor;
import java.util.List;
import java.util.LinkedList;

public class ClassDecl extends AST {
	private String id;
	private BodyClass bc;
	
	public ClassDecl(String id, BodyClass bc, int lineNumber, int colNumber) {
		this.id = id;
		this.bc = bc;
		this.setLineNumber(lineNumber);
		this.setColumnNumber(colNumber);
	}

	public List<MethodDecl> getMethodDecl() {
		return bc.getMethodDeclaration();
	}


	public List<FieldDecl> getFieldDecl() {
		return bc.getFieldDeclaration();
	}
        
        public String getId(){
            return id;
        }
        
        public void setId(String s){
            this.id = s;
        }
        
        public BodyClass getBody(){
            return bc;
        }
        
        public void setBodyClass(BodyClass bc){
            this.bc = bc;
        }
        
	@Override
	public String toString(){
		String result = "Class "+ id + " {\n";
		result += bc.toString();
		result += "}\n";
		return result;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
