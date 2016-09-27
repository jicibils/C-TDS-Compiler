package main.java.ast;

import main.java.visitor.ASTVisitor;

public class Attribute extends AST{
    String id;
    Type type;
    AST value;
    
    public Attribute(String id, Type t, AST value){
        this.id = id;
        this.type = t;
        this.value = value;
    }
    
    public Attribute(String id){
        this.id = id;
        this.type = null;
        this.value = null;
    }
    
    public String getId(){
        return id;
    }
    
    public void setId(String i){
        id = i;
    }
    
    public Type getType(){
        return type;
    }

    public void setType(Type t){
        type = t;
    }
    
    public AST getValue(){
        return value;
    }
    
    public void setValue(AST t){
        value = t;
    }
    @Override
    public <T> T accept(ASTVisitor<T> v) {
	return v.visit(this);
    }
    
}