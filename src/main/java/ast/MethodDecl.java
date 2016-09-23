package main.java.ast;

import java.util.List;
import main.java.visitor.ASTVisitor;


public class MethodDecl extends Declaration{
	private Type t;
	private String id;
	private List<Param> pl;
	private Block b;	
	private Boolean isExtern;

	
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
