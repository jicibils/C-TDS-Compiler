// MainCheckVisitor.java

package main.java.visitor;

import main.java.ast.*;

// Concrete visitor
//check the amount of main methods.
public class MainCheckVisitor implements ASTVisitor<Integer> {

	public MainCheckVisitor(){

	}

	// visit statements	


    public Integer visit(AssignStmt stmt){
    	return 0;
    }

    public Integer visit(ReturnStmt stmt){
    	return 0;
    }

    public Integer visit(IfStatement stmt){
    	return 0;
    }

    public Integer visit(ContinueStmt stmt){
    	return 0;
    }

    public Integer visit(WhileStatement stmt){
    	return 0;
    }

    public Integer visit(BreakStatement stmt){
    	return 0;
    }

    public Integer visit(SemicolonStmt stmt){
    	return 0;
    }

    public Integer visit(ForStatement stmt){
    	return 0;
    }

	// visit expressions


	public Integer visit(BinOpExpr expr){
		return 0;
	}
 	public Integer visit(UnaryOpExpr expr){
 		return 0;
 	}
	public Integer visit(MethodCallStmt stmt){
		return 0;
	}

	// visit literals	


    public Integer visit(IntLiteral lit){
    	return 0;
    }
    public Integer visit(FloatLiteral lit){
    	return 0;
    }
    public Integer visit(BoolLiteral lit){
    	return 0;
    }
    
	// visit locations	


    public Integer visit(VarLocation loc){
    	return 0;
    }
	public Integer visit(VarListLocation loc){
		return 0;
	}

    public Integer visit(Block aThis){
    	return 0;
    }

    // visit method calls


    public Integer visit(MethodCall call){
    	return 0;
    }

    //visit program


	public Integer visit(Program p) {
		Integer mains = 0;
		for (ClassDecl classDecl : p.getClassList()) {
			mains = (mains + classDecl.accept(this));
		}
		return mains;
	}

	//visit declarations 


	public Integer visit(ClassDecl cDecl){
		Integer mains = 0;
		for (MethodDecl methodDecl : cDecl.getMethodDecl()) {
			mains = (mains + methodDecl.accept(this));
		}
		return mains;
	}


	public Integer visit(FieldDecl aThis){
		return 0;
	}


	public Integer visit(MethodDecl decl){
		if (decl.getId().equals("main")) {
			if (decl.getParam().size()==0){
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}


	public Integer visit(IdFieldDecl aThis){
		return 0;
	}
	public Integer visit(Param aThis){
		return 0;
	}

	public Integer visit(BodyClass aThis){
		return 0;
	}


}
