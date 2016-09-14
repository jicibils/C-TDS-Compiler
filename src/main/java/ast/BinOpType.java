package main.java.ast;

public enum BinOpType {
	PLUS, // Arithmetic
	MINUS,
	MULT,
	DIV,
	MOD,
	LT, // Relational
	LTEQ,
	GT,
	GTEQ,
	NOTEQ, // Equal
	EQEQ, 
	ANDAND, // Conditional
	OROR;
	
	@Override
	public String toString() {
		switch(this) {
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case MULT:
				return "*";
			case DIV:
				return "/";
			case MOD:
				return "%";
			case LT:
				return "<";
			case LTEQ:
				return "<=";
			case GT:
				return ">";
			case GTEQ:
				return ">=";
			case EQEQ:
				return "==";
			case NOTEQ:
				return "!=";
			case ANDAND:
				return "&&";
			case OROR:
				return "||";
		}
		
		return null;
	}
}
