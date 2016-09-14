/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;

/**
 *
 * @author Adrian Tissera
 */
public class IdFieldDecl {
	private final String id;
	private final IntLiteral il;
	
	public IdFieldDecl(String id, IntLiteral il){
		this.id = id;
		this.il = il;
	}
}
