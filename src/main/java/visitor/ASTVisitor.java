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

	// visit expressions
	T visit(BinOpExpr expr);;
	T visit(UnaryOpExpr expr);
	T visit(MethodCallStmt stmt);

	// visit literals	
    T visit(IntLiteral lit);
    T visit(FloatLiteral lit);
    T visit(BoolLiteral lit);
    
	// visit locations	
    T visit(VarLocation loc);

    T visit(Block aThis);

    // visit method calls
    T visit(MethodCall call);

	public <T> T visit(Program aThis);

	public <T> T visit(ClassDecl aThis);

	public <T> T visit(BodyClass aThis);

	public <T> T visit(FieldDecl aThis);

	public <T> T visit(IdFieldDecl aThis);

	public <T> T visit(Param aThis);

	public <T> T visit(MethodDecl aThis);


}
