// TypeCheckVisitor.java

package main.java.visitor;

import java.util.LinkedList;
import java.util.List;
import main.java.ast.*;

public class TypeCheckVisitor implements ASTVisitor<List<ErrorClass>>{
    private Type returnTypeMethod; //Attribute to save return type of a method
    
    @Override
    public List<ErrorClass> visit(Program p) {
        LinkedList<ErrorClass> errors = new LinkedList<>();
        
        for(ClassDecl cd : p.getClassList()){
            errors.addAll(cd.accept(this));
        }
        
        return errors;
    }

    @Override
    public List<ErrorClass> visit(ClassDecl decl) {
        LinkedList<ErrorClass> errors = new LinkedList<>();
        
        for(FieldDecl fd : decl.getFieldDecl()){
            errors.addAll(fd.accept(this));
        }
        
        for(MethodDecl md : decl.getMethodDecl()){
                errors.addAll(md.accept(this));
        }
        
        return errors;
    }

    @Override
    public List<ErrorClass> visit(FieldDecl field) {
        return new LinkedList<>();
    }
    
    private void setReturnTypeVar(Type t){
        returnTypeMethod = t;
    }
    
    private Type getReturnTypeVar(){
        return returnTypeMethod;
    } 
    
    @Override
    public List<ErrorClass> visit(MethodDecl md) {
        LinkedList<ErrorClass> errors = new LinkedList<>();
        //Guardar en var global tipo ret
        setReturnTypeVar(md.getType());
        
        //Que hacer si es externo? si no es null, hago
        if(md.getBlock() != null){ 
            errors.addAll(md.getBlock().accept(this));
        }
        return errors;
    }

    @Override
    public List<ErrorClass> visit(IdFieldDecl aThis) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(Param aThis) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(BodyClass aThis) {
        return new LinkedList<ErrorClass>();
    }
    
    @Override
    public List<ErrorClass> visit(AssignStmt stmt) {
        LinkedList<ErrorClass> errors = new LinkedList<>();
        errors.addAll(stmt.getLocation().accept(this));
        if(errors.isEmpty()){
            //There is no errors
            errors.addAll(stmt.getExpression().accept(this));
            if(errors.isEmpty()){
                //There is no errors in expression
                
                System.out.println("******** EL TIPO DE LOCATION (izq) ES ******** "+stmt.getLocation().getType());
                System.out.println("######## EL TIPO DE EXPRESSION (der) ES ######## "+stmt.getExpression().getType());
                System.out.println("\n");
                if(!(stmt.getLocation().getType().equals(stmt.getExpression().getType()))){ //check if type loc = type expr
                    String errorAssign = "Error: location type doesn't match with expression type";
                    errors.add(new ErrorClass(stmt.getLineNumber(), stmt.getColumnNumber(), errorAssign));
                }else{
                    Type type = stmt.getLocation().getType(); //Since types matches, use one of them to do the comparison
                    AssignOpType operator = stmt.getOperator();
                    
                    if(!(validOperator(type, operator))){  // The types matched but operator is not valid for the type of operands. Add error to list.
                        String errorNotValidOp = "Error: not valid operator. Operands must be type integer or type float";
                        errors.add(new ErrorClass(stmt.getLineNumber(),stmt.getColumnNumber(), errorNotValidOp));
                    }
                }
            }
        }
        //chequear que tipo de loc = tipo expr.
        //expr ya tendra seteado el tipo en BinOpExpr o UnaryOpExpr
        
        return errors;
    }
    
    //Private method used in AssignStmt visit to check if a operator is valid for those operands
    //For example, int += int or float += are valid but bool += bool is not.
    
    private boolean validOperator(Type t, AssignOpType op){
        switch(t){
            case TBOOL : switch(op){
                case INC :
                    return false;
                case DEC :
                    return false;
                default :
                    return true;
            }
            
            default :
                return true;
        }
    }
    
    /*   
    private boolean validOperator(Type t, AssignOpType op){
        switch (op){
            case INC : switch(t){
                case TINTEGER :
                    return true;
                case TFLOAT :
                    return true;
                default :
                    return false;
            }
            
            case DEC : switch(t){
                case TINTEGER :
                    return true;
                case TFLOAT :
                    return true;
                default :
                    return false;
            }
            
            default :
                return false;
        }
    }
*/
    @Override
    public List<ErrorClass> visit(ReturnStmt stmt) {
        
        LinkedList<ErrorClass> errors = new LinkedList<>();
        
        if(stmt.getExpression() != null){
            errors.addAll(stmt.getExpression().accept(this));
            
            if(!getReturnTypeVar().equals(Type.TVOID)){

                if(!stmt.getExpression().getType().equals(getReturnTypeVar())){
                    String error = "Error: Types doesn't match. Return type should be of type "+getReturnTypeVar()+" but it is of type "+stmt.getExpression().getType()+".";
                    errors.add(new ErrorClass(stmt.getLineNumber(),stmt.getColumnNumber(),error));
                }
            } else {
                String error = "Error: Void methods must not have a return expression.";
                errors.add(new ErrorClass(stmt.getLineNumber(),stmt.getColumnNumber(),error));
            }
        } else {
            
            if(!getReturnTypeVar().equals(Type.TVOID)){
                //String error = "Error: Return type of method is void but the expression is returning a return expression with Type "+getReturnTypeVar()+".";
                String error = "Error: The method must return an expression of type "+getReturnTypeVar()+ " but return expression is empty.";
                errors.add(new ErrorClass(stmt.getLineNumber(),stmt.getColumnNumber(),error));
            }
        }
        
        return errors;
    }

    @Override
    public List<ErrorClass> visit(IfStatement stmt) {
        LinkedList<ErrorClass> errorsIf = new LinkedList<>();
        errorsIf.addAll(stmt.getCondition().accept(this));
        if(stmt.getCondition().getType().equals(Type.TBOOL)){  //Debo usar la clase Type.TBOOL o poner bool?
            errorsIf.addAll(stmt.getIfBlock().accept(this));
            if(stmt.getElseBlock() != null){
                errorsIf.addAll(stmt.getElseBlock().accept(this));
            }
        }else{
            errorsIf.add(new ErrorClass(stmt.getLineNumber(),stmt.getColumnNumber(),"Error: the if condition type must be bool. "));
        }
        return errorsIf;
    }

    @Override
    public List<ErrorClass> visit(ContinueStmt stmt) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(WhileStatement stmt) {
        LinkedList<ErrorClass> errorsWhile = new LinkedList<>();
        
        errorsWhile.addAll(stmt.getExpression().accept(this));
        //Que hacer si al hacer accept de la condition da error?
        //Se decide, por sugerencia, cortar al hallar el primer error
        
        if(errorsWhile.isEmpty()){  //There is no errors in condition expression
            //Check if expression is type bool
            if(stmt.getExpression().getType().equals(Type.TBOOL)){
                //llamo accept de block
                errorsWhile.addAll(stmt.getBlock().accept(this));
            }else{
                errorsWhile.add(new ErrorClass(stmt.getLineNumber(),stmt.getColumnNumber(),"Error: while condition expression must be of type boolean "));
            }
        }
        return errorsWhile;
    }

    @Override
    public List<ErrorClass> visit(BreakStatement stmt) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(SemicolonStmt stmt) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(ForStatement stmt) {
        LinkedList<ErrorClass> listErrors = new LinkedList<>();
        listErrors.addAll(stmt.getAssign().accept(this)); //visit initial assign
        //DECIDIR Que hacer si getAssign da error?
        //Se decide, por sugerencia, cortar al hallar el primer error.
        
        //There is no errors in initial assign. So, proceed to visit 
        if(listErrors.isEmpty()){
            if(stmt.getAssign().getExpression().getType().equals(Type.TINTEGER)){
                //If expr in initial assign is TYPE INTEGER accept condition
                listErrors.addAll(stmt.getCondition().accept(this));
                //if there is no errors, check type of cond
                if(listErrors.isEmpty()){
                    if(stmt.getCondition().getType().equals(Type.TINTEGER)){
                        listErrors.addAll(stmt.getBlock().accept(this)); //accept block
                    } else {
                        listErrors.add(new ErrorClass(stmt.getLineNumber(),stmt.getColumnNumber(),"Error: the type of condition must be integer"));
                    }
                }
                
            }else{
                listErrors.add(new ErrorClass(stmt.getLineNumber(),stmt.getColumnNumber(),"Error: initial and final expression must be type integer"));
            }
            return listErrors;
        }
        return listErrors;
    }

    @Override
    public List<ErrorClass> visit(BinOpExpr expr) {
        LinkedList<ErrorClass> errors = new LinkedList<>();
        //visito operando izquierdo y agrego a list
        errors.addAll(expr.getLeftOperand().accept(this));
        
        if(errors.isEmpty()){  //Se decide, por sugerencia, chequear si hay errores previo a continuar ejecucion
            //accept operando derecho y agrego a lista
            errors.addAll(expr.getRightOperand().accept(this));
        
            //Que pasa si tira error alguno de los operandos? Corto o sigo?
            //Se decide, por sugerencia, cortar si se hallan errores.
            if(errors.isEmpty()){
                
                Type typeLeftOp = expr.getLeftOperand().getType();      //Type left operand
                Type typeRightOp = expr.getRightOperand().getType();    //Type right operand
                BinOpType operator = expr.getOperator();                //Type operator
        
                if(!(typeLeftOp.equals(typeRightOp) && checkCompatibility(typeLeftOp, operator))){ //operator aren't equal or operator and operands aren't comp
                    String errorString = "Error: Type operands aren't equal or operands type aren't compatible with operator." ;
                    errors.add(new ErrorClass(expr.getLineNumber(),expr.getColumnNumber(), errorString));
                    
                }else{
                    if(!checkArithmeticalOp(operator)){
                        expr.setType(Type.TBOOL);   //operator is either boolean or relational.
                    }else{
                        expr.setType(Type.TINTEGER); //if operator is type arithmetical, set expression type as TINTEGER
                    }
                }
            }//close if
        }
        //setear tipo de expr      
        return errors;
    }
    
    private boolean checkArithmeticalOp(BinOpType operator){
        
        switch(operator){
            case MINUS :
                return true;
            case PLUS :
                return true;
            case DIV :
                return true;
            case MULT :
                return true;
            case MOD : 
                return true;
            default :
                return false;
        }
    }
    
    //private method to check if operands are compatible with operator
    private boolean checkCompatibility(Type t, BinOpType operator){
        
        switch (t){
            case TINTEGER : switch (operator){
                case OROR : 
                    return false;
                case ANDAND : 
                    return false;
                default : 
                    return true;
            }
            case TFLOAT : switch (operator){
                case OROR : 
                    return false;
                case ANDAND : 
                    return false;
                case MOD :
                    return false;
                default:
                    return true;
            }
            case TBOOL : switch(operator){
                case EQEQ :
                    return true;
                case NOTEQ :
                    return true;
                case ANDAND :
                    return true;
                case OROR :
                    return true;
                default :
                    return false;
            }
            default :
                return false;
        }

    }
   
    @Override
    public List<ErrorClass> visit(UnaryOpExpr expr) {
        LinkedList<ErrorClass> errors = new LinkedList<>();
        errors.addAll(expr.getOperand().accept(this));
        
        Type operand = expr.getOperand().getType();
        UnaryOpType operator = expr.getOperator();
        
        if(!isValidOperator(operand, operator)){
            errors.add(new ErrorClass (expr.getLineNumber(),expr.getColumnNumber(),"Error: Incompatible operator with operand"));
        }
        else{
            //set type
            if(expr.getOperator().equals(UnaryOpType.MINUS))
                expr.setType(Type.TINTEGER);
            else
                expr.setType(Type.TBOOL);
        }
        //setear tipo expr
        return errors;
    }
    
    
    
    private boolean isValidOperator(Type operand, UnaryOpType operator){
        switch(operand){
            case TFLOAT : switch(operator){
                case MINUS :
                    return true;
                default :
                    return false;
            }
            
            case TINTEGER : switch(operator){
                case MINUS :
                    return true;
                default :
                    return false;
                
            }
            
            case TBOOL : switch(operator){
                case NOT :
                    return true;
                default :
                    return false;
            }
            default :
                return false;
        }
    }
    
    @Override
    public List<ErrorClass> visit(MethodCallStmt stmt) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(IntLiteral lit) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(FloatLiteral lit) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(BoolLiteral lit) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(VarLocation loc) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(VarListLocation loc) {
        return new LinkedList<ErrorClass>();
    }

    @Override
    public List<ErrorClass> visit(Block block) {
        LinkedList<ErrorClass> errorBlock = new LinkedList<>();
        for(FieldDecl fieldDecl : block.getFieldDecl()){
            errorBlock.addAll(fieldDecl.accept(this));
        }
        for(Statement stmt : block.getStatements()){
            errorBlock.addAll(stmt.accept(this));
        }
        return errorBlock;
    }

    @Override
    public List<ErrorClass> visit(MethodCall call) {
        //return new LinkedList<ErrorClass>();

        LinkedList<ErrorClass> errors = new LinkedList<>();
        
        return errors;
    }
    
    @Override
    public List<ErrorClass> visit(Attribute a) {
        return new LinkedList<>();
    }
}
