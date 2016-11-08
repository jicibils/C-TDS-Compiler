package main.java.ast;

import main.java.visitor.ASTVisitor;

public class Attribute extends AST{
    private String id;
    private Type type;
    private AST value;
    private int offset;    

    
    public Attribute(String id, Type t, AST value){
        this.id = id;
        this.type = t;
        this.value = value;
        this.offset = 0;
    }
    
    public Attribute(String id){
        this.id = id;
        this.type = null;
        this.value = null;
        this.offset = 0;
    }
    public Attribute(String id, AST value){
        this.id = id;
        this.type = null;
        this.value = value;
        this.offset = 0;
    }
    public Attribute(Type t, AST value){
        this.type = t;
        this.value = value;
        this.id = "";
        this.offset = 0;
    }
    public Attribute(String id, Type t){
        this.id = id;
        this.type = t;
        this.value = null;
        this.offset = 0;
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
    
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
       this.offset = offset;
    }



    @Override
    public <T> T accept(ASTVisitor<T> v) {
	return v.visit(this);
    }
    
}