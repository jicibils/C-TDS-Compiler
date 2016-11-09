/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.intermediate;

import main.java.ast.AST;
import main.java.visitor.ASTVisitor;

public class Label extends AST{
    private String id;
    private int labelNumber; //attribute that represents the autoincremental number of a label, e.g. Label1
    private int maxOffset; //maxOffset used for recovery max offset to DeclarationCheckVisitor
    
    
    public Label(int n){
        this.id = "Label";
        this.labelNumber = n;
        this.maxOffset = 0;
    }
    
    public Label(String id, int n){
        this.id = id;
        this.labelNumber = n;
        this.maxOffset = 0;
    }
    
    public void setLabelNumber(int n){
        this.labelNumber = n;
    }
    
    public int getLabelNumber(){
        return labelNumber;
    }
    
    public String getLabelId(){
        return id;
    }
    
    public void setLabelId(String name){
        id = name;
    }

    public int getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(int offset) {
         this.maxOffset = offset;
    }

    
    @Override
    public String toString(){
        return id + labelNumber;
    }

    @Override
    public <T> T accept(ASTVisitor<T> v) {
        return null;
    }

}
