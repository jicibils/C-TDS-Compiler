/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;

import java.util.List;

/**
 *
 * @author Adrian Tissera
 */
public class FieldDecl{
	private final Type t;
	private final List<IdFieldDecl> lifd;
	
	
	public FieldDecl(Type t, List<IdFieldDecl> lifd){
		this.t = t;
		this.lifd = lifd;
	}
	
}
