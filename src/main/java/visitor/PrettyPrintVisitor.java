/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.visitor;

import main.java.ast.*;

public class PrettyPrintVisitor implements ASTVisitor<String> {

	@Override
	public String visit(Program p) {
		return (String)p.toString();
	}

	@Override
	public String visit(AssignStmt stmt) {
		return (String)"";
	}


	@Override
	public String visit(ReturnStmt stmt) {
		return (String)"";
	}

	@Override
	public String visit(IfStatement stmt) {
		return (String)"";
	}

	@Override
	public String visit(ContinueStmt stmt) {
		return (String)"";
	}

	@Override
	public String visit(WhileStatement stmt) {
		return (String)"";
	}

	@Override
	public String visit(BreakStatement stmt) {
		return (String)"";
	}

	@Override
	public String visit(SemicolonStmt stmt) {
		return (String)"";
	}

	@Override
	public String visit(BinOpExpr expr) {
		return (String)"";
	}

	@Override
	public String visit(UnaryOpExpr expr) {
		return (String)"";
	}

	@Override
	public String visit(IntLiteral lit) {
		return (String)"";
	}

	@Override
	public String visit(FloatLiteral lit) {
		return (String)"";
	}

	@Override
	public String visit(BoolLiteral lit) {
		return (String)"";
	}

	@Override
	public String visit(VarLocation loc) {
		return (String)"";
	}

	@Override
	public String visit(VarListLocation loc) {
		return (String)"";
	}

	@Override
	public String visit(Block aThis) {
		return (String)"";
	}


	@Override
	public String visit(MethodCall call) {
		return (String)"";
	}

	@Override
	public String visit(ClassDecl aThis) {
		return (String)"";
	}

	@Override
	public String visit(BodyClass aThis) {
		return (String)"";
	}

	@Override
	public String visit(FieldDecl aThis) {
		return (String)"";
	}

	@Override
	public String visit(IdFieldDecl aThis) {
		return (String)"";
	}

	@Override
	public String visit(Param aThis) {
		return (String)"";
	}

	@Override
	public String visit(MethodDecl aThis) {
		return (String)"";
	}

    @Override
    public String visit(MethodCallStmt stmt) {
        return (String)"";
    }

    @Override
    public String visit(ForStatement stmt) {
        return (String)"";
    }

    @Override
    public String visit(Attribute a) {
        return (String)"";
    }
	
}
