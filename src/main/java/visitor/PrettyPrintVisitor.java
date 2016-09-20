/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.visitor;

import main.java.ast.AssignStmt;
import main.java.ast.BinOpExpr;
import main.java.ast.Block;
import main.java.ast.BodyClass;
import main.java.ast.BoolLiteral;
import main.java.ast.BreakStatement;
import main.java.ast.ClassDecl;
import main.java.ast.ContinueStmt;
import main.java.ast.FieldDecl;
import main.java.ast.FloatLiteral;
import main.java.ast.IdFieldDecl;
import main.java.ast.IfStatement;
import main.java.ast.IntLiteral;
import main.java.ast.MethodCall;
import main.java.ast.MethodCallStmt;
import main.java.ast.MethodDecl;
import main.java.ast.Param;
import main.java.ast.Program;
import main.java.ast.ReturnStmt;
import main.java.ast.SemicolonStmt;
import main.java.ast.UnaryOpExpr;
import main.java.ast.VarLocation;
import main.java.ast.VarListLocation;
import main.java.ast.WhileStatement;

/**
 *
 * @author Adrian Tissera
 */
public class PrettyPrintVisitor implements ASTVisitor<String> {

	@Override
	public String visit(Program p) {
		return p.toString();
	}

	@Override
	public String visit(AssignStmt stmt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(ReturnStmt stmt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(IfStatement stmt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(ContinueStmt stmt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(WhileStatement stmt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(BreakStatement stmt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(SemicolonStmt stmt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(BinOpExpr expr) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(UnaryOpExpr expr) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(IntLiteral lit) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(FloatLiteral lit) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(BoolLiteral lit) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(VarLocation loc) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(VarListLocation loc) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(Block aThis) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String visit(MethodCall call) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T visit(ClassDecl aThis) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T visit(BodyClass aThis) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T visit(FieldDecl aThis) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T visit(IdFieldDecl aThis) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T visit(Param aThis) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T visit(MethodDecl aThis) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

        @Override
        public String visit(MethodCallStmt stmt) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
	
}
