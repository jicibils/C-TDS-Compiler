package main.java.ast;

import main.java.visitor.ASTVisitor;

public class IntLiteral extends Literal {
	private String rawValue;
	private Integer value;
	
	/*
	 * Constructor for int literal that takes a string as an input
	 * @param: String integer
	 */
	public IntLiteral(String val, int line, int column){
		rawValue = val; // Will convert to int value in semantic check
		value = null;
		setLineNumber(line);
		setColumnNumber(column);
	}

	public IntLiteral(Integer n){
		rawValue = n.toString();
		value = n;
	}

	@Override
	public Type getType() {
		return Type.TINTEGER;
	}

	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	/*
	* Get Integer value
	*/

	public Integer getValue() {
		return value;
	}

	/*
	* Set Integer value
	*/

	public void setValue(Integer value) {
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
