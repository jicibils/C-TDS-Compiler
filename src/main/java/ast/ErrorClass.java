/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ast;


public class ErrorClass {
    String desc;
    int line;
    int col;
    
    public ErrorClass(int l, int c, String d){
        this.desc = d;
        this.line = l;
        this.col  = c;
    }
    public String getDesc(){
        return desc;
    }
    
    public void setDesc(String d){
        this.desc = d;
    }
    
    public int getLine(){
        return line;
    }
    
    public void setLine(int l){
        this.line = l;
    }

    public int getColumn(){
        return col;
    }
    
    public void setColumn(int c){
        this.col = c;
    }
    
    public String toString(){
        String res = "";
        res = "Line: "+line+" Col: "+col+"\n"+desc;
        return res;
    }
}
