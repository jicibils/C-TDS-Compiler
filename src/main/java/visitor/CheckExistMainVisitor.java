/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.visitor;

import main.java.ast.AssignStmt;
import main.java.ast.Attribute;
import main.java.ast.BinOpExpr;
import main.java.ast.Block;
import main.java.ast.BodyClass;
import main.java.ast.BoolLiteral;
import main.java.ast.BreakStatement;
import main.java.ast.ClassDecl;
import main.java.ast.ContinueStmt;
import main.java.ast.FieldDecl;
import main.java.ast.FloatLiteral;
import main.java.ast.ForStatement;
import main.java.ast.IdFieldDecl;
import main.java.ast.IfStatement;
import main.java.ast.IntLiteral;
import main.java.ast.MethodCall;
import main.java.ast.MethodCallStmt;
import main.java.ast.MethodDecl;
import main.java.ast.Param;
import main.java.ast.Program;
import main.java.ast.ReturnStmt;
import main.java.ast.SemicolonStmt;
import main.java.ast.UnaryOpExpr;
import main.java.ast.VarListLocation;
import main.java.ast.VarLocation;
import main.java.ast.WhileStatement;

public class CheckExistMainVisitor implements ASTVisitor<Integer>{
    
    public CheckExistMainVisitor(){
    
    }
     //visit a program
    public Integer visit(Program p){
        int mainCount = 0;
        
        for(ClassDecl cd : p.getClassList()){
            
            mainCount += cd.accept(this);
            
        }
        return mainCount;
    }
    
    //visit its classes in order to find if exists a method named main
    public Integer visit(ClassDecl cd){
        int mainCounter = 0;
        for(MethodDecl md : cd.getMethodDecl()){
            mainCounter += md.accept(this);
        }
        return mainCounter;
    }
    
    public Integer visit(MethodDecl md){
        
        if(md.getId().equals("main")){
            if(md.getParam().size()==0){  //A main must not contain any arguments!
                return 1;
            }
        }
        return -1;
    }

    @Override
    public Integer visit(AssignStmt stmt) {
        return 0;
    }

    @Override
    public Integer visit(ReturnStmt stmt) {
        return 0;
    }

    @Override
    public Integer visit(IfStatement stmt) {
        return 0;
    }

    @Override
    public Integer visit(ContinueStmt stmt) {
        return 0;
    }

    @Override
    public Integer visit(WhileStatement stmt) {
        return 0;
    }

    @Override
    public Integer visit(BreakStatement stmt) {
        return 0;
    }

    @Override
    public Integer visit(SemicolonStmt stmt) {
        return 0;
    }

    @Override
    public Integer visit(ForStatement stmt) {
        return 0;
    }

    @Override
    public Integer visit(BinOpExpr expr) {
        return 0;
    }

    @Override
    public Integer visit(UnaryOpExpr expr) {
        return 0;
    }

    @Override
    public Integer visit(MethodCallStmt stmt) {
        return 0;
    }

    @Override
    public Integer visit(IntLiteral lit) {
        return 0;
    }

    @Override
    public Integer visit(FloatLiteral lit) {
        return 0;
    }

    @Override
    public Integer visit(BoolLiteral lit) {
        return 0;
    }

    @Override
    public Integer visit(VarLocation loc) {
        return 0;
    }

    @Override
    public Integer visit(VarListLocation loc) {
        return 0;
    }

    @Override
    public Integer visit(Block aThis) {
        return 0;
    }

    @Override
    public Integer visit(MethodCall call) {
        return 0;
    }

    @Override
    public Integer visit(FieldDecl aThis) {
        return 0;
    }

    @Override
    public Integer visit(IdFieldDecl aThis) {
        return 0;
    }

    @Override
    public Integer visit(Param aThis) {
        return 0;
    }

    @Override
    public Integer visit(BodyClass aThis) {
        return 0;
    }

    @Override
    public Integer visit(Attribute a) {
        return 0;
    }
}
