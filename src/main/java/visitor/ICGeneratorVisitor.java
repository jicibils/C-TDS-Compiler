/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.visitor;

import java.util.LinkedList;
import main.java.ast.*;
import main.java.intermediate.Instruction;
import main.java.intermediate.IntermediateCode;

/**
 *
 * @author Ezequiel Arangue
 */
public class ICGeneratorVisitor implements ASTVisitor<Location>{
    
    private LinkedList<IntermediateCode> list;
    private int tempCounter;  //variable to store amount of temporal location used
    
    public ICGeneratorVisitor(){
        
        list = new LinkedList<>();
        tempCounter = 1;          
        
    }

    @Override
    public Location visit(AssignStmt stmt) {
        Location loc = stmt.getLocation().accept(this); // Get left part. Operand 1
        Location expr = stmt.getExpression().accept(this); // Get rigth part. Operand 2
        
        switch (stmt.getOperator()){
            case ASSIGN :                       //assign, op1, op2, res
                list.add(new IntermediateCode(Instruction.ASSIGN,loc,expr, stmt.getLocation()));  //Necesito usar un temporal o directamente 
                return stmt.getLocation();                                                       //  lo hago en location izq?
                
            case INC :
                Location temp = new VarLocation("T"+tempCounter,stmt.getLineNumber(),stmt.getColumnNumber());
                
            case DEC :
                //TODO
            
        }
        
        return null;
    }

    @Override
    public Location visit(ReturnStmt stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(IfStatement stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(ContinueStmt stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(WhileStatement stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(BreakStatement stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(SemicolonStmt stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(ForStatement stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(BinOpExpr expr) {
        VarLocation locLeftExpr = (VarLocation)expr.getLeftOperand().accept(this);  //location of leftExpr
        VarLocation locRightExpr = (VarLocation)expr.getRightOperand().accept(this); //location of rightExpr
        
        VarLocation tempLoc = new VarLocation("T"+tempCounter,expr.getLineNumber(),expr.getColumnNumber());  //temporal location to store results
        tempLoc.setType(expr.getType());   //set type to temporal
        
        //I need to obtain the appropiate instruction according to operator and its type
        //since we do not have the binary expression separated by type.
        Instruction instruction = getAppropiateInstruction(expr.getOperator(),expr.getType());  //method to obtain correspondent instruction
        IntermediateCode icode = new IntermediateCode(instruction,locLeftExpr,locRightExpr,tempLoc); //create 3-ways code
        
        list.add(icode); //add to list
        
        tempCounter++;  //increment counter that store amount of temporal location used
        
        return tempLoc;
    }
    
    private Instruction getAppropiateInstruction(BinOpType operator, Type t){
        switch (operator){
            case LT :
                return Instruction.LT;
            case LTEQ :
                return Instruction.LTEQ;
            case GT :
                return Instruction.GT;
            case GTEQ :
                return Instruction.GTEQ;
            case NOTEQ :
                return Instruction.NOTEQ;
            case EQEQ : 
                return Instruction.EQEQ;
            case OROR :
                return Instruction.OROR;
            case ANDAND :
                return Instruction.ANDAND;
            case PLUS :
                if(t.equals(Type.TINTEGER)){
                    return Instruction.ADDINT;
                }else{
                    return Instruction.ADDFLOAT;
                }
            case MINUS :
                if(t.equals(Type.TINTEGER))
                    return Instruction.SUBINT;
                else
                    return Instruction.SUBFLOAT;
            case DIV :
                if(t.equals(Type.TINTEGER))
                    return Instruction.DIVINT;
                else
                    return Instruction.DIVFLOAT;
            case MULT :
                if(t.equals(Type.TINTEGER))
                    return Instruction.MULTINT;
                else
                    return Instruction.MULTFLOAT;
            case MOD :
                return Instruction.MOD;
        }
        return null;
    }

    @Override
    public Location visit(UnaryOpExpr expr) {
        // ! expr
        // - expr
        
        //create temporal location to store result
        VarLocation tempLocation = new VarLocation("T"+tempCounter,expr.getLineNumber(),expr.getColumnNumber());
        
        //retrieve operand expression
        VarLocation locExpr = (VarLocation)expr.getOperand().accept(this);  //Get operand
        
        //IntermediateCode ic = new IntermediateCode();
        
        
        
        tempCounter++;  //increment temporal counter
        return null;
    }

    @Override
    public Location visit(MethodCallStmt stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(IntLiteral lit) {
        return null;
    }

    @Override
    public Location visit(FloatLiteral lit) {
        return null;
    }

    @Override
    public Location visit(BoolLiteral lit) {
        return null;
    }

    @Override
    public Location visit(VarLocation loc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(VarListLocation loc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(Block aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(MethodCall call) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(Program aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(ClassDecl aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(FieldDecl aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(MethodDecl aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(IdFieldDecl aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(Param aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(BodyClass aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(Attribute a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}