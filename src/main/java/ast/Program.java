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
public class Program extends AST{
	private final List<ClassDecl> class_list;
	
	public Program (List<ClassDecl> class_list) {
		this.class_list = class_list;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
