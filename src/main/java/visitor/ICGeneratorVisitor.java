/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.visitor;

import java.util.LinkedList;
import main.java.ast.*;
import java.util.Stack;

import main.java.intermediate.*;


public class ICGeneratorVisitor implements ASTVisitor<Location>{
    
    private LinkedList<IntermediateCode> list;
    private int tempCounter;  //variable to store amount of temporal location used
    private int labelCounter;  //variable to store amount of labels
    private Stack<Label> stackIn;
    private Stack<Label> stackOut;

    
    public ICGeneratorVisitor(){
        
        list = new LinkedList<>();
        tempCounter = 1;          
        labelCounter = 0;          
        stackIn = new Stack<Label>();
        stackOut = new Stack<Label>();
    }
        
    @Override
    public Location visit(AssignStmt stmt) {
        Location loc = stmt.getLocation().accept(this); // Get left part. Operand 1
        Location expr = stmt.getExpression().accept(this); // Get rigth part. Operand 2
        
        switch (stmt.getOperator()){
            case ASSIGN :                       //assign, op1, op2, res
                if (stmt.getLocation().getType().equals(Type.TINTEGER)) {
                    list.add(new IntermediateCode(Instruction.ASSIGNI,loc,expr, stmt.getLocation()));
                    return stmt.getLocation();
                }else{
                    if (stmt.getLocation().getType().equals(Type.TFLOAT)) {
                        list.add(new IntermediateCode(Instruction.ASSIGNF,loc,expr, stmt.getLocation()));
                        return stmt.getLocation();
                    }else{
                        if (stmt.getLocation().getType().equals(Type.TBOOL)) {
                            list.add(new IntermediateCode(Instruction.ASSIGNB,loc,expr, stmt.getLocation()));
                            return stmt.getLocation();
                        }
                    }
                }
            case INC :
                if (stmt.getLocation().getType().equals(Type.TINTEGER)) {
                    list.add(new IntermediateCode(Instruction.INCI,loc,expr, stmt.getLocation()));
                    return stmt.getLocation();
                }else{
                    if (stmt.getLocation().getType().equals(Type.TFLOAT)) {
                        list.add(new IntermediateCode(Instruction.INCF,loc,expr, stmt.getLocation()));
                        return stmt.getLocation();
                    }
                }
            case DEC :
                if (stmt.getLocation().getType().equals(Type.TINTEGER)) {
                    list.add(new IntermediateCode(Instruction.INCI,loc,expr, stmt.getLocation()));
                    return stmt.getLocation();
                }else{
                    if (stmt.getLocation().getType().equals(Type.TFLOAT)) {
                        list.add(new IntermediateCode(Instruction.INCF,loc,expr, stmt.getLocation()));
                        return stmt.getLocation();
                    }
                }
        }        
        return null;
    }

    
    private int incTempCounter(){
        return ++tempCounter;
    }

    private Label genLabel(String name){
        labelCounter++;
        Label label = new Label(name,labelCounter);
        return label;


    }

    @Override
    public Location visit(IfStatement stmt) {
        Label jumpElse = genLabel("JUMPELSE");
        Label jumpEnd = genLabel("JUMPEND");

        // VarLocation temp = new VarLocation("T"+tempCounter,stmt.getLineNumber(),stmt.getColumnNumber());
        // tempCounter++;

        Location tempLoc = stmt.getCondition().accept(this);

        if( stmt.thereIsElseBlock()) {
            //If false jump to JUMP ELSE
            list.add(new IntermediateCode(Instruction.JF,tempLoc,null,jumpElse));
            // Accept the if block
            stmt.getIfBlock().accept(this);
            //jump to JUMP END    
            list.add(new IntermediateCode(Instruction.JMP,null,null,jumpEnd));
            //LABEL JUMP ELSE
            list.add(new IntermediateCode(Instruction.LABEL,null,null,jumpElse));
            // Accept the else block
            stmt.getElseBlock().accept(this);
            //LABEL JUMP END
            list.add(new IntermediateCode(Instruction.LABEL,null,null,jumpEnd));

        }else{
            //If false jump to JUMPEND because there isn't elseBlock
            list.add(new IntermediateCode(Instruction.JF,tempLoc,null,jumpEnd));
            // Accept the if block
            stmt.getIfBlock().accept(this);
            //LABEL JUMP END
            list.add(new IntermediateCode(Instruction.LABEL,null,null,jumpEnd));

        }
        return null;
    }


    @Override
    public Location visit(WhileStatement stmt) {
        
        Label beginWhile  = genLabel("beginWhile");  //OR Label beginWhile = newLabel("BEGINFOR",labelCounter++)?
        Label endWhile    = genLabel("endWhile");
        
        stackIn.push(beginWhile);
        list.add(new IntermediateCode(Instruction.LABEL,null,null, beginWhile));
        Location T1 = stmt.getExpression().accept(this);
        list.add(new IntermediateCode(Instruction.JF,T1,null,endWhile));   //If T1 is false jump to end
        stackOut.push(endWhile);
        stmt.getBlock().accept(this);
        list.add(new IntermediateCode(Instruction.JMP,null,null,beginWhile));
        list.add(new IntermediateCode(Instruction.LABEL,null,null,endWhile));
        
        stackIn.pop();
        stackOut.pop();
        return null;
    }



    @Override
    public Location visit(ForStatement stmt) {
        //Create Label BEGINFOR & ENDFOR
        Label beginFor = new Label("BEGINFOR",labelCounter);
        labelCounter++;
        Label endFor = new Label("ENDFOR",labelCounter);
        labelCounter++;
        
        stackIn.push(beginFor);
        stmt.getAssign().accept(this);                      //ASSIGN 0 _ i - Init i with zero
        Location i = stmt.getAssign().getLocation();        // i 
        Location T0 = stmt.getCondition().accept(this);                   //SUM x y T0. Store this result in temporal
        
        VarLocation T1 = new VarLocation("T"+tempCounter,stmt.getLineNumber(),stmt.getColumnNumber());
        tempCounter++;
        
        stackOut.push(endFor);
        list.add(new IntermediateCode(Instruction.LABEL,null,null, beginFor));       //LABEL BEGIN FOR
        list.add(new IntermediateCode(Instruction.LESS,i, T0, T1));          //compare if i < cota. Save result in T1
        
        list.add(new IntermediateCode(Instruction.JF,T1,null,endFor));            //If false jump to endFor
        
        stmt.getBlock().accept(this);
        
        list.add(new IntermediateCode(Instruction.INCI,i,new IntLiteral(1),i)); //INC i - i++
        list.add(new IntermediateCode(Instruction.JMP,null,null,endFor));       //JMP BEGIN FOR
        list.add(new IntermediateCode(Instruction.LABEL,null,null,endFor));     //LABEL ENDFOR
        
        stackIn.pop();
        stackOut.pop();
        return null;
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
        Instruction instruction = getAppropiateInstruction(expr.getOperator(), expr.getType());
        //create temporal location to store result
        VarLocation tempLoc = new VarLocation("T"+tempCounter,expr.getLineNumber(),expr.getColumnNumber());
        //set type
        tempLoc.setType(expr.getType());
        
        //retrieve operand expression
        VarLocation locExpr = (VarLocation)expr.getOperand().accept(this);  //Get operand
        
        IntermediateCode ic = new IntermediateCode(instruction,locExpr,null,tempLoc);
        
        tempCounter++;  //increment temporal counter
        return tempLoc;
    }
    
    private Instruction getAppropiateInstruction(UnaryOpType operator, Type t){
        switch(operator){
            case MINUS :
                if(t.equals(Type.TINTEGER))
                    return Instruction.MINUSINT;
                else
                    return Instruction.MINUSFLOAT;
            case NOT :
                return Instruction.NOT;
        }
        
        return null;
    }


    @Override
    public Location visit(IntLiteral lit) {
        Location tempLocation = new VarLocation("T"+tempCounter,lit.getLineNumber(),lit.getColumnNumber());
        tempCounter++;
        
        list.add(new IntermediateCode(Instruction.ASSIGNLITINT,lit,null,tempLocation));
        
        return tempLocation;
    }

    @Override
    public Location visit(FloatLiteral lit) {
        VarLocation tempLocation = new VarLocation("T"+tempCounter,lit.getLineNumber(),lit.getColumnNumber());
        tempCounter++;
        
        list.add(new IntermediateCode(Instruction.ASSIGNLITFLOAT,lit, null, tempLocation));
        
        return tempLocation;
        //Debo crear un temporal donde guardar la loc del lit y retornarlo.
        //Previamente, crear una instruction para cada literal. i.e. Instruction.assignLitFloat(...);
    }

    @Override
    public Location visit(BoolLiteral lit) {
        
        Location tempLocation = new VarLocation("T"+tempCounter,lit.getLineNumber(),lit.getColumnNumber());
        tempCounter++;
        list.add(new IntermediateCode(Instruction.ASSIGNLITBOOL,lit,null,tempLocation));
        
        return tempLocation;
    }


    @Override
    public Location visit(Block block) {
        for(FieldDecl fieldDecl : block.getFieldDecl()){
            fieldDecl.accept(this);
        }

        for(Statement statement : block.getStatements()){
            statement.accept(this);
        }

        return null;
    }

    @Override
    public Location visit(Program program) {
        for (ClassDecl classDecl : program.getClassList()){
            classDecl.accept(this);
        }
        return null;
    }

    @Override
    public Location visit(ClassDecl cDecl) {
        //label with the name class
        Label activeClass = genLabel(cDecl.getId());
        list.add(new IntermediateCode(Instruction.LABEL,null,null,activeClass));
        for (FieldDecl fieldDecl: cDecl.getFieldDecl()) {
            fieldDecl.accept(this);
        }
        for (MethodDecl methodDecl : cDecl.getMethodDecl()) {
            methodDecl.accept(this);
        }
        return null;
    }

    @Override
    public Location visit(FieldDecl fieldDecl) {
        for (IdFieldDecl idFieldDecl : fieldDecl.getListId()) {
                idFieldDecl.accept(this);                                                              
        } 
        return null;
    }

    @Override
    public Location visit(SemicolonStmt stmt) {
        return null;
    }

    @Override
    public Location visit(ContinueStmt stmt) {
        Label continueLabel = stackIn.peek();
        list.add(new IntermediateCode(Instruction.JMP,null,null,continueLabel));
        return null;
    }
    @Override
    public Location visit(BreakStatement stmt) {
        Label breakLabel = stackOut.peek();
        list.add(new IntermediateCode(Instruction.JMP,null,null,breakLabel));
        return null;
    }

    @Override
    public Location visit(MethodDecl method) {
        //label with the name method
        Label activeMethod = genLabel(method.getId());
        list.add(new IntermediateCode(Instruction.LABEL,null,null,activeMethod));

        //FALTA COMPLETAR!!!!!!!!!!!!!!!!

        return null;
    }

    @Override
    public Location visit(MethodCallStmt stmt) {
        return stmt.getMethodCall().accept(this);
    }

    @Override
    public Location visit(MethodCall call) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Se puede generar un label que me informe que "estoy dentro de la clase"
    @Override
    public Location visit(VarLocation loc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location visit(VarListLocation loc) {
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

    @Override
    public Location visit(ReturnStmt stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
