// TypeCheckVisitor.java

package main.java.visitor;

import main.java.ast.*;

// Concrete visitor
public class TypeCheckVisitor implements ASTVisitor<String> {

	public TypeCheckVisitor(){

	}

	// visit statements	


    public String visit(AssignStmt stmt){
    	return "";
    }

    public String visit(ReturnStmt stmt){
    	return "";
    }

    public String visit(IfStatement stmt){
    	return "";
    }

    public String visit(ContinueStmt stmt){
    	return "";
    }

    public String visit(WhileStatement stmt){
    	return "";
    }

    public String visit(BreakStatement stmt){
    	return "";
    }

    public String visit(SemicolonStmt stmt){
    	return "";
    }

    public String visit(ForStatement stmt){
    	return "";
    }

	// visit expressions


	public String visit(BinOpExpr expr){
		return "";
	}
 	public String visit(UnaryOpExpr expr){
 		return "";
 	}
	public String visit(MethodCallStmt stmt){
		return "";
	}

	// visit literals	


    public String visit(IntLiteral lit){
    	return "";
    }
    public String visit(FloatLiteral lit){
    	return "";
    }
    public String visit(BoolLiteral lit){
    	return "";
    }
    
	// visit locations	


    public String visit(VarLocation loc){
    	return "";
    }
	public String visit(VarListLocation loc){
		return "";
	}

    public String visit(Block aThis){
    	return "";
    }

    // visit method calls


    public String visit(MethodCall call){
    	return "";
    }

    //visit program


	public String visit(Program p) {
    	return "";
	}

	//visit declarations 


	public String visit(ClassDecl cDecl){
    	return "";
	}


	public String visit(FieldDecl aThis){
		return "";
	}


	public String visit(MethodDecl decl){
    	return "";
	}


	public String visit(IdFieldDecl aThis){
		return "";
	}
	public String visit(Param aThis){
		return "";
	}

	public String visit(BodyClass aThis){
		return "";
	}

    @Override
    public String visit(Attribute a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
