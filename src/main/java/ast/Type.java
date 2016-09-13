package main.java.ast;

public enum Type {
	TINTEGER,
	TFLOAT,
	TBOOL,
	TVOID,
	UNDEFINED,
	INTARRAY,
	FLOATARRAY,
	BOOLEANARRAY;

	@Override
	public String toString() {
		switch(this) {
			case TINTEGER:
				return "integer";
			case TFLOAT:
				return "float";
			case TBOOL:
				return "bool";
			case TVOID:
				return "void";
			case UNDEFINED:
				return "undefined";
			case INTARRAY:
				return "integer[]";
			case FLOATARRAY:
				return "float[]";
			case BOOLEANARRAY:
				return "bool[]";
		}
		
		return null;
	}
	
	public boolean isArray() {
		if (this == Type.INTARRAY) {
			return true;
		}
		if (this == Type.FLOATARRAY) {
			return true;		
		}
		if (this == Type.BOOLEANARRAY) {
			return true;
		}
		
		return false;
	}
}

