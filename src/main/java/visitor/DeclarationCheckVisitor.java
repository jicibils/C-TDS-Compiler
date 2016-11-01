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
               System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAa");
        if(count == 0){
            String res = "BREAK is outside of a loop ";
            errorList.add(res);
               System.out.println("11111111111111111111111111111111");
         }
        return errorList;
    }

    public List<String> visit(ContinueStmt stmt){
        List<String> errorList = new LinkedList<String>();
               System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBB");
        if(count == 0){
            String res = "CONTINUE is outside of a loop ";
            errorList.add(res);
               System.out.println("555555555555555555555555555555");
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

    public List<String> visit(VarLocation loc){
        List<String> errorList = new LinkedList<String>();
        Attribute exist = null;
        exist = search(loc.getId());
        if(exist == null){
            errorList.add("Location was not founded");
        }else{
            errorList.addAll(loc.accept(this)); 
        }
        return errorList;
    }


    public List<String> visit(VarListLocation loc){
        List<String> errorList = new LinkedList<String>();
        Attribute exist = null;
        exist = search(loc.getId());
        if(exist == null){
            errorList.add("Location was not founded");
        }else{
            errorList.addAll(loc.getExpression().accept(this)); 
        }
        return errorList;
    }

    // visit method call
    public List<String> visit(MethodCall call){
        List<String> errorList = new LinkedList<String>();
        Attribute exist = null;
        exist = search(call.getId());
        if(exist == null){
            errorList.add("Method declaration was not founded");
        }else{
            for (Expression expr : call.getArgList()) {
                errorList.addAll(expr.accept(this));
            }
        }
        return errorList;
    }

    //method for search declaration in simbolTable
    private Attribute search(String id) {
        Attribute res = null;
        int currentlevel = table.getIndex();
        while((res == null) && (currentlevel >= 0)){
            res = table.searchByName(id,currentlevel);
            currentlevel--;
        }
        // return (res!=null);
        return res;
        //ASI ESTA BIEN O EN EL CASO DE LAS CLASES Y LOS METODOS DONDE TIENEN
        // CAMPOS COMPUESTOS ADENTRO TENDRIA QUE HACER UN TRATAMIENTO ESPECIAL 
        // PARA EXPLORAR ADENTRO DE ESOS ?????? ESTILO UN FOR EACH O ALGO DEL ESTILO??



    }

    public List<String> visit(BodyClass bodyClass){
        List<String> errorList = new LinkedList<String>();
        for (FieldDecl fieldDeclaration: bodyClass.getFieldDeclaration()) {
            errorList.addAll(fieldDeclaration.accept(this));
        }
        for (MethodDecl methodDeclaration : bodyClass.getMethodDeclaration()) {
            errorList.addAll(methodDeclaration.accept(this));            
        }
        return errorList;

        //EN BODYCLASS NO HAGO NADA O VISITO COMO LO HICE ARRIBA?????
        // return new LinkedList<String>();
    }

    public List<String> visit(IdFieldDecl aThis){
        return new LinkedList<String>();
    }
   
    public List<String> visit(IntLiteral lit){
        return new LinkedList<String>();
    }

    public List<String> visit(FloatLiteral lit){
        return new LinkedList<String>();
    }

    public List<String> visit(BoolLiteral lit){
        return new LinkedList<String>();
    }

    public List<String> visit(SemicolonStmt stmt){
        return new LinkedList<String>();
    }

    public List<String> visit(Attribute a) {
        return new LinkedList<String>();
    }

//***************************************************************

    //methodCall buscar si esta declarado el metodo en la tabla de simbolos

    //varLocation y el otro es cuando se da un uso y es buscar si existe en la tabla
    // de simbolos y si esta es asignarle sus tipos y sino esta error



    //terminar el declarationCheck



    // agregar los OFFSET despues de terminar codigo intermedio
    //arreglar scripts 
//***************************************************************

}
