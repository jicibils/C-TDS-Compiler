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
public class MethodDecl extends Declaration{
	private final Type t;
	private final String id;
	private final List<Param> pl;
	private final Block b;	
	
	public MethodDecl(Type t, String id, List<Param> pl, Block b, int ln, int cl) {
		this.t = t;
		this.id = id;
		this.pl = pl;
		this.b = b;
		this.setLineNumber(ln);
		this.setColumnNumber(cl);
	}
	
	@Override
	public String toString() {
		String result = this.t + " " + this.id + " (";
		if (this.pl != null) {
			int n = this.pl.size();
			for (Param p : this.pl) {
				result += p.toString();
				if (n > 1) {
					result += ", ";
				}
				n--;
			}
		}
		result += ")\n{\n";
		if (b != null) {
			result += this.b.toString();
		}
		result += "}";
		return result;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}
