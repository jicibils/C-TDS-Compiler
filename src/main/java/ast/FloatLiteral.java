package main.java.ast;

import main.java.visitor.ASTVisitor;

public class FloatLiteral extends Literal {
	private String rawValue;
	private Float value;
	
	/*
	 * Constructor for float literal that takes a string as an input
	 */
	public FloatLiteral(String val){
		rawValue = val; // Will convert to int value in semantic check
		value = null;
	}

	public FloatLiteral(Float n){
		rawValue = n.toString();
		value = n;
	}

	@Override
	public Type getType() {
		return Type.TFLOAT;
	}

	/////////////////////////////////////////////
	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	////////////////////////////////////////////


	public Float getFloatvalue() {
		return value;
	}


	public void setFloatvalue(Float value) {
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
