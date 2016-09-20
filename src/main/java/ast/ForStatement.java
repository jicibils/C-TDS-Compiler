package main.java.ast;

import main.java.visitor.ASTVisitor;

public class ForStatement extends Statement{
	private AssignStmt assign; // initialization
	private Expression condition;
	private Block block;
						/* i      =        1       ,  i   <     10    */
	public ForStatement(String id, Expression init, Expression cond, Block bl, int line, int column){
		assign = new AssignStmt(new VarLocation(id,line,column),AssignOpType.ASSIGN,init); //i.e. "i = 1"
        condition = cond;
        block = bl;
        this.setLineNumber(line);
        this.setColumnNumber(column);                        
	}
	
	public void setCondition(Expression c){
		condition = c;
	}

	public Expression getCond(){
		return condition;
	}

	public AssignStmt getAssign(){
		return assign;
	}

	public void setAssign(AssignStmt a){
		assign = a;
	}

	public Block getBlock(){
		return block;
	}

	public void setActions(Block b){
		block = b;
	}

	@Override
	public String toString() {
		return "for " + assign.toString() + "," + condition.toString() + '\n' + "{" + block.toString() + " }";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}