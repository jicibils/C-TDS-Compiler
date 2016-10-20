// DeclarationCheckVisitor.java

package main.java.visitor;

import main.java.ast.*;
import java.util.List;
import java.util.LinkedList;

// Concrete visitor
public class DeclarationCheckVisitor implements ASTVisitor<List<String>> {

    private SymbolTable table;
    private int count; 

    public DeclarationCheckVisitor(){
        table = new SymbolTable();
        count = 0;
    }

    //visit program

    public List<String> visit(Program program) {
        List<String> errorList = new LinkedList<String>();
        table.pushNewLevel();
        for(ClassDecl classdecl : program.getClassList()){
            Attribute attribute = new Attribute(classdecl.getId(),classdecl);
            if(table.insertSymbol(attribute)){
                errorList.addAll(classdecl.accept(this));
            }
        }
        table.popLevel();
        return errorList;
    }

    //visit declarations 


    public List<String> visit(ClassDecl cDecl){
        List<String> errorList = new LinkedList<String>();
        table.pushNewLevel();    
        for(FieldDecl fieldDecl : cDecl.getFieldDecl()){
            errorList.addAll(fieldDecl.accept(this));
        }

        for(MethodDecl methodDecl : cDecl.getMethodDecl()){
            //add the profile of method
            Attribute attribute = new Attribute(methodDecl.getId(),methodDecl.getType(),methodDecl);
            if(table.insertSymbol(attribute)){
                errorList.addAll(methodDecl.accept(this));
            }
            
        }
        table.popLevel();
        return errorList;
    }

    public List<String> visit(FieldDecl fieldDecl){
        List<String> errorList = new LinkedList<String>();
        List<IdFieldDecl> list = new LinkedList<IdFieldDecl>();
        Type type;
        String id;
        int i = 0; 
        type = fieldDecl.getType();
        list = fieldDecl.getListId();
        while (i<list.size()){
            id = list.get(i).getId();
            Attribute attribute = new Attribute(id,type,fieldDecl);
            if (!(table.insertSymbol(attribute))){
                errorList.add("Error fieldDecl: Line: "+fieldDecl.getLineNumber()+" Column: "+fieldDecl.getColumnNumber());
            }
            i++;
        }
        return errorList;
    }


    public List<String> visit(MethodDecl method){
        List<String> errorList = new LinkedList<String>();
        if (!(method.isExtern())){
            //open level for parameters
            table.pushNewLevel();    
            for(Param param : method.getParam()){
                errorList.addAll(param.accept(this));
            }

            Block block = method.getBlock();
            errorList.addAll(block.accept(this));
        }
        //close level to parameters
        table.popLevel();
        return errorList;
    }        
    


    public List<String> visit(Param param){
        List<String> errorList = new LinkedList<String>();
        Attribute attribute = new Attribute(param.getId(),param.getType(),param);
        if(!(table.insertSymbol(attribute))){
            errorList.add("Error insert parameter: Line: "+param.getLineNumber()+" Column: "+param.getColumnNumber());
        }
        return errorList;
    }

    public List<String> visit(BodyClass aThis){
        return new LinkedList<String>();
    }

    public List<String> visit(IdFieldDecl aThis){
        return new LinkedList<String>();
    }
   
    public List<String> visit(Attribute a) {
        return new LinkedList<String>();
    }
    
    // visit locations  

    public List<String> visit(Block block){
        List<String> errorList = new LinkedList<String>();
        table.pushNewLevel();    
        for(FieldDecl fieldDecl : block.getFieldDecl()){
            errorList.addAll(fieldDecl.accept(this));
        }

        for(Statement statement : block.getStatements()){
            errorList.addAll(statement.accept(this));            
        }

        table.popLevel();
        return errorList;
    }

    // visit statements 

    public List<String> visit(AssignStmt stmt){
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(stmt.getLocation().accept(this));
        errorList.addAll(stmt.getExpression().accept(this));
        return errorList;
    }

    public List<String> visit(MethodCallStmt stmt){
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(stmt.getMethodCall().accept(this));
        return errorList;
    }

    public List<String> visit(IfStatement stmt){
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(stmt.getCondition().accept(this));
        errorList.addAll(stmt.getIfBlock().accept(this));
        if (stmt.thereIsElseBlock()){
            errorList.addAll(stmt.getElseBlock().accept(this));
        }
        return errorList;
    }

    public List<String> visit(ForStatement stmt){
        List<String> errorList = new LinkedList<String>();
        count++;
        errorList.addAll(stmt.getAssign().accept(this));
        errorList.addAll(stmt.getCondition().accept(this));
        errorList.addAll(stmt.getBlock().accept(this));
        count--;
        return errorList;
    }

    public List<String> visit(WhileStatement stmt){
        List<String> errorList = new LinkedList<String>();
        count++;
        errorList.addAll(stmt.getExpression().accept(this));
        errorList.addAll(stmt.getBlock().accept(this));
        count--;
        return errorList;
    }

    public List<String> visit(ReturnStmt stmt){
        List<String> errorList = new LinkedList<String>();
        if(stmt.getExpression() != null){
            errorList.addAll(stmt.getExpression().accept(this));
        }
        return errorList;
    }

    public List<String> visit(BreakStatement stmt){
        List<String> errorList = new LinkedList<String>();
        if(count == 0){
            errorList.add("BREAK is outside of a loop ");
        }
        return errorList;
    }

    public List<String> visit(ContinueStmt stmt){
        List<String> errorList = new LinkedList<String>();
        if(count == 0){
            errorList.add("CONTINUE is outside of a loop ");
        }
        return errorList;
    }

    public List<String> visit(SemicolonStmt stmt){
        return new LinkedList<String>();
    }




    // visit expressions

    public List<String> visit(VarLocation loc){
        List<String> errorList = new LinkedList<String>();
        Boolean exist = false;
        exist = search(loc.getId());
        if(exist == false){
            errorList.add("Location was not founded");
        }else{
            //SI LO ENCUENTRO QUE HAGO????
        }
        return errorList;
    }


    public List<String> visit(VarListLocation loc){
        List<String> errorList = new LinkedList<String>();
        Boolean exist = false;
        exist = search(loc.getId());
        if(exist == false){
            errorList.add("Location was not founded");
        }else{
            //SI LO ENCUENTRO QUE HAGO???? APARTE DE VISITAR LA EXPRESION??
            errorList.addAll(loc.getExpression().accept(this)); 
        }
        return errorList;
    }

    public List<String> visit(BinOpExpr expr){
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(expr.getLeftOperand().accept(this));
        errorList.addAll(expr.getRightOperand().accept(this));
        return errorList;
    }
    public List<String> visit(UnaryOpExpr expr){
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(expr.getOperand().accept(this));
        return errorList;
    }

    // visit literals   


    public List<String> visit(IntLiteral lit){
        return new LinkedList<String>();
    }
    public List<String> visit(FloatLiteral lit){
        return new LinkedList<String>();
    }
    public List<String> visit(BoolLiteral lit){
        return new LinkedList<String>();
    }
    

    // visit method call
    public List<String> visit(MethodCall call){
        List<String> errorList = new LinkedList<String>();
        Boolean exist = false;
        exist = search(call.getId());
        // COMO DIFERENCIO EL CASO EN DONDE EL METODO TIENE UN ID O UNA LISTA DE ID
        if(exist == false){
            errorList.add("Method declaration was not founded");
        }else{
            //declaration was founded
            //TENDRIA QUE INSERTAR EN LA TABLA DE SIMBOLOS O SOLO VISITO LOS ARGUMENTOS
            for (Expression expr : call.getArgList()) {
                errorList.addAll(expr.accept(this));
            }
        }
        return errorList;
    }

    //method for search declaration in simbolTable
    private Boolean search(String id) {
        Attribute res = null;
        int currentlevel = table.getIndex();
        while((res == null) && (currentlevel > 0)){
            res = table.searchByName(id,currentlevel);
            currentlevel--;
        }
        return (res!=null);
    }
    //EL SEARCH ESTA BIEN O HAY QUE DISCRIMINAR DISTINTOS NIVELES SABIENDO QUE NO TODOS LOS NIVELES
    // TIENEN LO MISMO !! ASI COMO ESTA HECHO SI EXISTE LA DECLARACION LA ENCUENTRA???





// DUDAS:
    // me falta el methodCall y el VarLocation y VarListLocation
    // hacer un search que busque declaraciones


//***************************************************************

    //*************************************LISTO*****************
    // arrglar errores de casteo ! (IntLiteral y LinkedList en fielDecl)
    //arreglar lo errores en tiempo de ejecucion (CUP)
    //*************************************LISTO*****************



    //*************************************LISTO*****************
    //HACER CON LOS BREAK Y CONTINUE (contador que sume cuando este en el ciclo y reste cuando 
    // salga y despues en el break y en el continue preguntas por el contador )
    //*************************************LISTO*****************


    //methodCall buscar si esta declarado el metodo en la tabla de simbolos

    //varLocation y el otro es cuando se da un uso y es buscar si existe en la tabla
    // de simbolos y si esta es asignarle sus tipos y sino esta error





    //revisar el mainCheck (el pull borro todo)



    //terminar el declarationCheck



    // agregar los OFFSET despues de terminar codigo intermedio
    //codigoIntermedio
    //arreglar scripts 
//***************************************************************

}
