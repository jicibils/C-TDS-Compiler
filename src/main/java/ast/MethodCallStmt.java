/*
 *
 */
package main.java.ast;

import main.java.visitor.ASTVisitor;

public class MethodCallStmt extends Statement{
    
    private MethodCall methodCall;
    
    public MethodCallStmt(MethodCall mc, int line, int column){
        methodCall = mc;
        this.setLineNumber(line);
        this.setColumnNumber(column);
    }
    
    public MethodCall getMethodCall(){
        return methodCall;
    }
    
    public void setMethodCall(MethodCall m){
        this.methodCall = m;
    }
    
    @Override
    public String toString(){
        return methodCall.toString() + ";";
    }
    @Override
    public <T> T accept(ASTVisitor<T> v) {
    	return v.visit(this);
    }
    
}
