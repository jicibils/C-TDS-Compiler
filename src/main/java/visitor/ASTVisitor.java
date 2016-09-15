package main.java.visitor;

import main.java.ast.*;

// Abstract visitor
public interface ASTVisitor<T> {
	// visit statements

    T visit(AssignStmt stmt);

    T visit(ReturnStmt stmt);

    T visit(IfStmt stmt);

// visit expressions
	T visit(BinOpExpr expr);;
	T visit(UnaryOpExpr expr);
	
	// visit literals	
    T visit(IntLiteral lit);
    T visit(FloatLiteral lit);
    T visit(BoolLiteral lit);
    
	// visit locations	
    T visit(VarLocation loc);

    T visit(Block aThis);

    // visit method calls
    T visit(MethodCall call);

}
