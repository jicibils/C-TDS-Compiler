package main.java.ast;

public abstract class Location extends Expression {
	protected String id;
	protected Attribute declaration; 	// Declaration associated for search.DeclarationCheckVisitor
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public Attribute getDeclaration() {
		return declaration;
	}

	public int getOffset() {
		return declaration.getOffset();
	}

	public void setOffset(int offset) {
		 declaration.setOffset(offset);
	}



	public void setDeclaration(Attribute declaration) {
		this.declaration = declaration;
		setType(declaration.getType());
	}

}
