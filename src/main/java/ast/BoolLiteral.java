package main.java.ast;

import main.java.visitor.ASTVisitor;

public class BoolLiteral extends Literal{
	private String rawValue;
	private Boolean value;

	public BoolLiteral(Boolean val){
		this.rawValue = val.toString();
		this.value = val;
	}
	
	@Override
	public Type getType() {
		return Type.TBOOL;
	}


	public Boolean getBoolvalue() {
		return value;
	}


	public void setBoolvalue(Boolean value) {
		this.value = value;
	}
	
	/*
	* Get string value
	*/
	public String getRawValue() {
		return rawValue;
	}

	/*
	* Set string value
	*/
	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}

	@Override
	public String toString() {
		return rawValue;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}