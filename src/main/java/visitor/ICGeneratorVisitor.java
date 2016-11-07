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
        tempCounter = 0;          
        labelCounter = 0;          
        stackIn = new Stack<Label>();
        stackOut = new Stack<Label>();
    }

    @Override
    public Location visit(Program program) {
        System.out.println("ESTOY EN PROGRAM!!!!!!!!!!");
        for (ClassDecl classDecl : program.getClassList()){
            classDecl.accept(this);
        }
        return null;
    }

    @Override
    public Location visit(ClassDecl cDecl) {
        System.out.println("ESTOY EN CLASSDECL!!!!!!!!!!");
        //label with the name class
        Label beginClass = genLabel(cDecl.getId());
        list.add(new IntermediateCode(Instruction.LABELBEGINCLASS,null,null,beginClass));
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
        System.out.println("ESTOY EN FIELDDECL!!!!!!!!!!");
        for (IdFieldDecl idFieldDecl : fieldDecl.getListId()) {
            if(idFieldDecl.isArray()){
                list.add(new IntermediateCode(Instruction.INITARRAY,null,null,idFieldDecl));
            }else{
                if (fieldDecl.getType().equals(Type.TINTEGER)) {
                    list.add(new IntermediateCode(Instruction.INITINT,null,null,idFieldDecl));
                }else{
                    if (fieldDecl.getType().equals(Type.TFLOAT)) {
                        list.add(new IntermediateCode(Instruction.INITFLOAT,null,null,idFieldDecl));
                    }else{
                        if (fieldDecl.getType().equals(Type.TBOOL)) {
                            list.add(new IntermediateCode(Instruction.INITBOOL,null,null,idFieldDecl));
                        }
                    }
                }
            }
        } 
        return null;
                // en vez de aceptar ir insertand los objetos (preguntar si es var o array) initVar y initArray
                // discriminar entre los accesos a var y array con instrucciones nuevas (en statement o expression)PREGUNTAR PANCHO
    }

    @Override
    public Location visit(MethodDecl method) {
        System.out.println("ESTOY EN METHODDECL!!!!!!!!!!");

        if(!(method.isExtern())){
            //label with the name method BEGIN METHOD
            Label beginMethod = genLabel(method.getId());
            list.add(new IntermediateCode(Instruction.LABELBEGINMETHOD,null,null,beginMethod));

            method.getBlock().accept(this);

            //label with the name method END METHOD
            Label endMethod = genLabel(method.getId());
            list.add(new IntermediateCode(Instruction.LABELENDMETHOD,null,null,endMethod));
        }

        return null;
    }


    @Override
    public Location visit(Param aThis) {
        System.out.println("ESTOY EN PARAM!!!!!!!!!!");
        return null;
    }

    @Override
    public Location visit(Block block) {
        System.out.println("ESTOY EN BLOCKLITERAL!!!!!!!!!!");
        for(FieldDecl fieldDecl : block.getFieldDecl()){
            fieldDecl.accept(this);
        }

        for(Statement statement : block.getStatements()){
            statement.accept(this);
        }

        return null;
    }

        
    @Override
    public Location visit(AssignStmt stmt) {
        System.out.println("ESTOY EN ASSIGNSTMT!!!!!!!!!!");
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

    @Override
    public Location visit(MethodCallStmt stmt) {
        System.out.println("ESTOY EN METHODCALLSTMT!!!!!!!!!!");
        return stmt.getMethodCall().accept(this);
    }

    @Override
    public Location visit(IfStatement stmt) {
        System.out.println("ESTOY EN IFSTATEMENT!!!!!!!!!!");
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
    public Location visit(ForStatement stmt) {
        System.out.println("ESTOY EN FORSTATEMENT!!!!!!!!!!");
        //Create Label BEGINFOR & ENDFOR
        Label beginFor = genLabel("BEGINFOR");
        Label endFor = genLabel("ENDFOR");
        
        stackIn.push(beginFor);
        stackOut.push(endFor);

        stmt.getAssign().accept(this);                      //ASSIGN 0 _ i - Init i with zero
        Location i = stmt.getAssign().getLocation();        // i 
        Location T0 = stmt.getCondition().accept(this);                   //SUM x y T0. Store this result in temporal
        
        VarLocation T1 = new VarLocation("T"+tempCounter,stmt.getLineNumber(),stmt.getColumnNumber());
        incTempCounter(); //tempCounter++;
        
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
    public Location visit(WhileStatement stmt) {
        System.out.println("ESTOY EN WHILESTATEMENT!!!!!!!!!!");
        
        Label beginWhile  = genLabel("BEGINWHILE");
        Label endWhile    = genLabel("ENDWHILE");
        
        stackIn.push(beginWhile);
        stackOut.push(endWhile);

        list.add(new IntermediateCode(Instruction.LABEL,null,null, beginWhile));
        Location T1 = stmt.getExpression().accept(this);
        list.add(new IntermediateCode(Instruction.JF,T1,null,endWhile));   //If T1 is false jump to end
        stmt.getBlock().accept(this);
        list.add(new IntermediateCode(Instruction.JMP,null,null,beginWhile));
        list.add(new IntermediateCode(Instruction.LABEL,null,null,endWhile));
        
        stackIn.pop();
        stackOut.pop();
        return null;
    }

    @Override
    public Location visit(ReturnStmt stmt) {
        System.out.println("ESTOY EN RETURNSTMT!!!!!!!!!!");
        if(stmt.getExpression() == null){
            list.add(new IntermediateCode(Instruction.RETURN,null,null,null));            
        }else{
            Location temporal = stmt.getExpression().accept(this);
            
            switch(stmt.getExpression().getType()){
                case TINTEGER :
                    list.add(new IntermediateCode(Instruction.RETURNINT,null,null,temporal));
                case TFLOAT :
                    list.add(new IntermediateCode(Instruction.RETURNFLOAT,null,null,temporal));
                case TBOOL : 
                    list.add(new IntermediateCode(Instruction.RETURNBOOL,null,null,temporal));
                case TVOID :
                    list.add(new IntermediateCode(Instruction.RETURN,null,null,null));
                
            }
        }      

        return null;
    }

    @Override
    public Location visit(BreakStatement stmt) {
        System.out.println("ESTOY EN BREAKSTATEMENT!!!!!!!!!!");
        // method peek make pop of stack without remove pop of the stack
        Label breakLabel = stackOut.peek();
        list.add(new IntermediateCode(Instruction.JMP,null,null,breakLabel));
        return null;
    }

    @Override
    public Location visit(ContinueStmt stmt) {
        System.out.println("ESTOY EN CONTINUESTMT!!!!!!!!!!");
        // method peek make pop of stack without remove pop of the stack
        Label continueLabel = stackIn.peek();
        list.add(new IntermediateCode(Instruction.JMP,null,null,continueLabel));
        return null;
    }

    @Override
    public Location visit(SemicolonStmt stmt) {
        System.out.println("ESTOY EN SEMICOLONSTMT!!!!!!!!!!");
        return null;
    }


    @Override
    public Location visit(BinOpExpr expr) {
        System.out.println("ESTOY EN BINOPEXPR!!!!!!!!!!");
        VarLocation locLeftExpr = (VarLocation)expr.getLeftOperand().accept(this);  //location of leftExpr
        VarLocation locRightExpr = (VarLocation)expr.getRightOperand().accept(this); //location of rightExpr
        
        VarLocation tempLoc = new VarLocation("T"+tempCounter,expr.getLineNumber(),expr.getColumnNumber());  //temporal location to store results
        incTempCounter(); //tempCounter++ increment counter that store amount of temporal location used
        tempLoc.setType(expr.getType());   //set type to temporal
        
        //I need to obtain the appropiate instruction according to operator and its type
        //since we do not have the binary expression separated by type.
        Instruction instruction = getProperInstruction(expr.getOperator(),expr.getType());  //method to obtain correspondent instruction
        IntermediateCode icode = new IntermediateCode(instruction,locLeftExpr,locRightExpr,tempLoc); //create 3-ways code
        
        list.add(icode); //add to list
        
        return tempLoc;
    }
    
    private Instruction getProperInstruction(BinOpType operator, Type t){
        System.out.println("ESTOY EN GETPROPERINSTRUCTION BIN!!!!!!!!!!");

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
        System.out.println("ESTOY EN UNARYOPEXPR!!!!!!!!!!");
        
        //create temporal location to store result
        VarLocation tempLoc = new VarLocation("T"+tempCounter,expr.getLineNumber(),expr.getColumnNumber());
        incTempCounter(); //tempCounter++ increment temporal counter
        //set type
        tempLoc.setType(expr.getType());
        
        //retrieve operand expression
        VarLocation locExpr = (VarLocation)expr.getOperand().accept(this);  //Get operand
        Instruction instruction = getProperInstruction(expr.getOperator(), expr.getType());  //Get proper instruction
        IntermediateCode ic = new IntermediateCode(instruction,locExpr,null,tempLoc);  //Create intermediate code
        list.add(ic);
        
        return tempLoc;
    }
    
    private Instruction getProperInstruction(UnaryOpType operator, Type t){
        System.out.println("ESTOY EN GETPROPERINSTRUCTION unary!!!!!!!!!!");
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
    public Location visit(VarLocation loc) {
        System.out.println("ESTOY EN VARLOCATION!!!!!!!!!!");
        return loc;
    }

    @Override
    public Location visit(VarListLocation loc) {
        System.out.println("ESTOY EN VARLISTLOCATION!!!!!!!!!!");
        return loc;
    }

    @Override
    public Location visit(MethodCall methodCall) {
        System.out.println("ESTOY EN METHODCALL!!!!!!!!!!");

        for (Expression expression: methodCall.getArgList()) {
            list.add(new IntermediateCode(Instruction.PUSHPARAM,null,null,expression.accept(this)));
        }

        list.add(new IntermediateCode(Instruction.CALL,null,null,methodCall));

        return null;
    }

    @Override
    public Location visit(IntLiteral lit) {
        System.out.println("ESTOY EN INTLITERAL!!!!!!!!!!");
        Location tempLocation = new VarLocation("T"+tempCounter,lit.getLineNumber(),lit.getColumnNumber());
        incTempCounter();//tempCounter++;
        
        list.add(new IntermediateCode(Instruction.ASSIGNLITINT,lit,null,tempLocation));
        
        return tempLocation;
    }

    @Override
    public Location visit(FloatLiteral lit) {
        System.out.println("ESTOY EN FLOATLITERAL!!!!!!!!!!");
        VarLocation tempLocation = new VarLocation("T"+tempCounter,lit.getLineNumber(),lit.getColumnNumber());
        incTempCounter(); //tempCounter++;
        
        list.add(new IntermediateCode(Instruction.ASSIGNLITFLOAT,lit, null, tempLocation));
        
        return tempLocation;
        //Debo crear un temporal donde guardar la loc del lit y retornarlo.
        //Previamente, crear una instruction para cada literal. i.e. Instruction.assignLitFloat(...);
    }

    @Override
    public Location visit(BoolLiteral lit) {
        
        System.out.println("ESTOY EN BOOLLITERAL!!!!!!!!!!");
        Location tempLocation = new VarLocation("T"+tempCounter,lit.getLineNumber(),lit.getColumnNumber());
        incTempCounter(); //tempCounter++;
        list.add(new IntermediateCode(Instruction.ASSIGNLITBOOL,lit,null,tempLocation));
        
        return tempLocation;
    }


    @Override
    public Location visit(IdFieldDecl aThis) {
        System.out.println("ESTOY EN IDFIELDDECL!!!!!!!!!!");
        return null;
    }


    @Override
    public Location visit(BodyClass bodyClass) {
        System.out.println("ESTOY EN BODYCLASS!!!!!!!!!!");
        for (FieldDecl fieldDeclaration: bodyClass.getFieldDeclaration()) {
            fieldDeclaration.accept(this);
        }
        for (MethodDecl methodDeclaration : bodyClass.getMethodDeclaration()) {
            methodDeclaration.accept(this);
        }
        return null;
    }

    @Override
    public Location visit(Attribute a) {
        System.out.println("ESTOY EN ATTRIBUTE!!!!!!!!!!");
        return null;
    }

    
    private void incTempCounter(){
        tempCounter++;
    }

    private Label genLabel(String name){
        labelCounter++;
        Label label = new Label(name,labelCounter);
        return label;

    }

    public LinkedList<IntermediateCode> getICList(){
        return list;
    }    
    
}
