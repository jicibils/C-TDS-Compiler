/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.intermediate;

public class Label {
    private int labelNumber; //attribute that represents the autoincremental number of a label, e.g. Label1
    
    public Label(int n){
        this.labelNumber = n;
    }
    
    public void setLabelNumber(int n){
        this.labelNumber = n;
    }
    
    public int getNumber(){
        return labelNumber;
    }
    
    @Override
    public String toString(){
        return "";
    }

}
