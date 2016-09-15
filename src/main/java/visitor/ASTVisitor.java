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
    
// visit locations	
    T visit(VarLocation loc);

// Visit method calls
	T visit(MethodCall call);


    public <T> T visit(Block aThis);
}
