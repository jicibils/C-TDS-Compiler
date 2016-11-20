package main.java.ast;

public abstract class Expression extends AST {
	protected Expression expr;
	protected Type type;
	protected Literal value;				
	
	public Type getType() {
		return this.type;
	}
	
	public void setType(Type t) {
		this.type = t;
	}

	public Literal getValue() {
		return value;
	}

	public void setValue(Literal value) {
		this.value = value;
	}

}
