package main.java.ast;

public abstract class Literal extends Expression {
	/*
	 * @return: returns Type of Literal instance
	 */
	@Override
	public abstract Type getType();
}
