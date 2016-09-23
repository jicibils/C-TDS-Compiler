package main.java.visitor;

import main.java.ast.*;

// Abstract visitor
public interface ASTVisitor<T> {

	// visit statements	
    T visit(AssignStmt stmt);

    T visit(ReturnStmt stmt);

    T visit(IfStatement stmt);

    T visit(ContinueStmt stmt);

    T visit(WhileStatement stmt);

    T visit(BreakStatement stmt);

    T visit(SemicolonStmt stmt);

    T visit(ForStatement stmt);

	// visit expressions
	T visit(BinOpExpr expr);
	T visit(UnaryOpExpr expr);
	T visit(MethodCallStmt stmt);

	// visit literals	
    T visit(IntLiteral lit);
    T visit(FloatLiteral lit);
    T visit(BoolLiteral lit);
    
	// visit locations	
    T visit(VarLocation loc);
	T visit(VarListLocation loc);

    T visit(Block aThis);

    // visit method calls
    T visit(MethodCall call);

    //visit program
	T visit(Program aThis);

	//visit declarations 
	T visit(ClassDecl aThis);
	T visit(FieldDecl aThis);
	T visit(MethodDecl aThis);
	T visit(IdFieldDecl aThis);
	T visit(Param aThis);

	T visit(BodyClass aThis);









}
