package main.java.ast;

import java.util.List;
import main.java.visitor.ASTVisitor;


public class MethodDecl extends Declaration{
	private Type t;
	private String id;
	private List<Param> pl;
	private Block b;	
	private Boolean isExtern;
	private int maxOffset;

	
	public MethodDecl(Type t, String id, List<Param> pl, Block b, int ln, int cl) {
		this.t = t;
		this.id = id;
		this.pl = pl;
		if(b==null)
			isExtern = true;
		else{
			isExtern = false;
			this.b = b;
		}
		this.setLineNumber(ln);
		this.setColumnNumber(cl);
	}
	
	public Type getType() {
		return t;
	}

	public String getId() {
		return id;
	}

	public List<Param> getParam() {
		return pl;
	}

	public Block getBlock() {
		return b;
	}

	public boolean isExtern() {
		return isExtern;
	}

    public int getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(int maxOffset) {
        this.maxOffset = maxOffset;
    }


	public boolean thereIsReturn() {
		if (!isExtern) {
			return searchReturn(b);
		} 
		return false;
	}

	// Returns true if the block has a return in the first list of statements 
	private boolean blockHasReturn(Block block) {
		for (Statement stmt : block.getStatements()) {
			if (stmt instanceof ReturnStmt) {
				return true;
			}
		}
		return false;
	}

	
	// Returns true if the  Block has a return in the list of statements
	private boolean searchReturn(Block block) {
		if (blockHasReturn(block)) {
			return true;
		} else {
			List<Statement> listStmt = block.getStatements();
			boolean exist = false;
			for (int i = 0; i < listStmt.size() && !exist; i++) {
				Statement stmt = listStmt.get(i);
				if (stmt instanceof IfStatement) {
					IfStatement ifStmt = (IfStatement) stmt;
					if (ifStmt.thereIsElseBlock()) {
						exist = searchReturn(ifStmt.getIfBlock()) && searchReturn(ifStmt.getElseBlock());	
					}
				} else if (stmt instanceof Block) {
					exist = searchReturn((Block)stmt);
				}
			}
			return exist;
		}
		
	}


	@Override
	public String toString() {
		String result = this.t + " " + this.id + " (";
		if (this.pl != null) {
			int n = this.pl.size();
			for (Param p : this.pl) {
				result += p.toString();
				if (n > 1) {
					result += ", ";
				}
				n--;
			}
		}
		result += ")\n{\n";
		if (b != null) {
			result += this.b.toString();
		}
		result += "}";
		return result;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}






