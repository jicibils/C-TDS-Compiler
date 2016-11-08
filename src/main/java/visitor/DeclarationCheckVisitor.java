// DeclarationCheckVisitor.java

package main.java.visitor;

import main.java.ast.*;
import java.util.List;
import java.util.LinkedList;

// Concrete visitor
public class DeclarationCheckVisitor implements ASTVisitor<List<String>> {

    private SymbolTable table;
    private int count; 
    public int offset;
    public int maxOffset;

    // atributes that we use in method search for 
    // recognize diferent levels in simbolTable
    private static int levelClass = 0;
    private static int levelFieldAndMethod = 1;
    private static int levelParamsMethod = 2;
    private static int levelBlock = 3;

    public DeclarationCheckVisitor(){
        table = new SymbolTable();
        count = 0;
        offset = -4;
        maxOffset = offset;
    }

    //visit program

    public List<String> visit(Program program) {
        System.out.println("ESTOY EN PROGRAM!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        // table.pushNewLevel();// cuando construis el objeto ahi se crea el nivel necesario para program(class)
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
        System.out.println("ESTOY EN CLASS DECLARATION!!!!!!!!!!");
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
            }else{

            }
            
        }
        table.popLevel();
        return errorList;
    }

    public List<String> visit(FieldDecl fieldDecl){
        System.out.println("ESTOY EN FIELD DECLARATION!!!!!!!!!!");
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
        System.out.println("ESTOY EN METHOD DECLARATION!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        if (!(method.isExtern())){
            //open level for parameters
            table.pushNewLevel();    

            //SET MAXOFFSET
            method.setMaxOffset(maxOffset);

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
        System.out.println("ESTOY EN PARAM!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        Attribute attribute = new Attribute(param.getId(),param.getType(),param);
        if(!(table.insertSymbol(attribute))){
            errorList.add("Error insert parameter: Line: "+param.getLineNumber()+" Column: "+param.getColumnNumber());
        }
        return errorList;
    }

    
    // visit locations  

    public List<String> visit(Block block){
        System.out.println("ESTOY EN BLOCK!!!!!!!!!!");
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
        System.out.println("ESTOY EN ASSIGN STATEMENT!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(stmt.getLocation().accept(this));
        errorList.addAll(stmt.getExpression().accept(this));
        return errorList;
    }

    public List<String> visit(MethodCallStmt stmt){
        System.out.println("ESTOY EN METHOD CALL STATEMENT!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(stmt.getMethodCall().accept(this));
        return errorList;
    }

    public List<String> visit(IfStatement stmt){
        System.out.println("ESTOY EN IF STATEMENT!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(stmt.getCondition().accept(this));
        errorList.addAll(stmt.getIfBlock().accept(this));
        if (stmt.thereIsElseBlock()){
            errorList.addAll(stmt.getElseBlock().accept(this));
        }
        return errorList;
    }

    public List<String> visit(ForStatement stmt){
        System.out.println("ESTOY EN FOR STATEMENT!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        count++;
        errorList.addAll(stmt.getAssign().accept(this));
        errorList.addAll(stmt.getCondition().accept(this));
        errorList.addAll(stmt.getBlock().accept(this));
        count--;
        return errorList;
    }

    public List<String> visit(WhileStatement stmt){
        System.out.println("ESTOY EN WHILE STATEMENT!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        count++;
        errorList.addAll(stmt.getExpression().accept(this));
        errorList.addAll(stmt.getBlock().accept(this));
        count--;
        return errorList;
    }

    public List<String> visit(ReturnStmt stmt){
        System.out.println("ESTOY EN RETURN STATEMENT!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        if(stmt.getExpression() != null){
            errorList.addAll(stmt.getExpression().accept(this));
        }
        return errorList;
    }

    public List<String> visit(BreakStatement stmt){
        System.out.println("ESTOY EN BREAK STATEMENT!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        if(count == 0){
            String res = "BREAK is outside of a loop ";
            errorList.add(res);
         }
        return errorList;
    }

    public List<String> visit(ContinueStmt stmt){
        System.out.println("ESTOY EN CONTINUE STATEMENT!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        if(count == 0){
            String res = "CONTINUE is outside of a loop ";
            errorList.add(res);
         }
        return errorList;
    }


    public List<String> visit(BinOpExpr expr){
        System.out.println("ESTOY EN BIN OP EXPRESSION!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(expr.getLeftOperand().accept(this));
        errorList.addAll(expr.getRightOperand().accept(this));
        return errorList;
    }
    public List<String> visit(UnaryOpExpr expr){
        System.out.println("ESTOY EN UNARY OP EXPRESSION!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        errorList.addAll(expr.getOperand().accept(this));
        return errorList;
    }

    public List<String> visit(VarLocation loc){
        System.out.println("ESTOY EN VAR LOCATION!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        boolean flagForError = false;
        Attribute declaration;
        //if list of ids is empty is a simple location. form ID
        if(loc.getArrayId().isEmpty()){
            declaration = search(loc.getId());
            if (declaration != null) {
                // add the location 
                //set Offset
                loc.setDeclaration(declaration);
                loc.setOffset(genOffset());

            } else {
                // The location not founded
                flagForError = true;
            }
            if (flagForError) {
                String res = "VarLocation doesn't exist";
                errorList.add(res);
            }
        }else{

            //if list of ids isn't empty isn't a simple location
            // The location is of the form ID.ID
    
            declaration = search(loc.getId());
            if (declaration!=null) {
                // add the location 
                //set Offset
                loc.setDeclaration(declaration);
                loc.setOffset(genOffset());

            } else {
                // The location not founded
                flagForError = true;
            }
            if (flagForError) {
                String res = "VarLocation doesn't exist";
                errorList.add(res);
            }
        }
        return errorList;
    }


    public List<String> visit(VarListLocation loc){
        System.out.println("ESTOY EN VAR LIST LOCATION!!!!!!!!!!");
        List<String> errorList = new LinkedList<String>();
        boolean flagForError = false;
        Attribute declaration;
        //if list of ids is empty is a simple location. form ID[expr]
        if(loc.getArrayId().isEmpty()){
            declaration = search(loc.getId());
            if (declaration != null) {
                // add the location 
                //set Offset
                loc.setDeclaration(declaration);
                loc.setOffset(genOffset());

                errorList.addAll(loc.getExpression().accept(this)); 
            } else {
                // The location not founded
                flagForError = true;
            }
            if (flagForError) {
                String res = "VarListLocation doesn't exist";
                errorList.add(res);
            }
        }else{

            //if list of ids isn't empty isn't a simple location
            // The location is of the form ID.ID[expr]
    
            declaration = search(loc.getId());
            if (declaration!=null) {
                // add the location 
                //set Offset
                loc.setDeclaration(declaration);
                loc.setOffset(genOffset());

                errorList.addAll(loc.getExpression().accept(this)); 
            } else {
                // The location not founded
                flagForError = true;
            }
            if (flagForError) {
                String res = "VarLocation doesn't exist";
                errorList.add(res);
            }
        }
        return errorList;
    }



    // visit method call
    public List<String> visit(MethodCall call){
        System.out.println("ESTOY EN METHOD CALL!!!!!!!!!!");
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
        System.out.println("ESTOY EN SEARCH!!!!!!!!!!");
        Attribute res = null;
        int index = table.getIndex();
        if(index>levelBlock) {
            // search in level of blocks
            while ((res == null) && index > levelBlock) {
                res = table.searchByName(id,index);
                index--;
            }
        }
        if (res == null) {
            //search in level 3 (en escala de 0 a 3)
            res = table.searchByName(id,levelBlock);
            if (res == null) {
                //search in level 1 (en escala de 0 a 3)
                res = table.searchByName(id,levelFieldAndMethod);
                if (res == null) {
                    // Search inside of method
                    //search in level 2 (en escala de 0 a 3)
                    res = table.searchByName(id,levelParamsMethod);
                    if (res == null) {
                        // search in level 0 (en escala de 0 a 3) inside of classDecl
                        res = table.searchByName(id,levelClass);
                    // }else{
                    //     errorList.add("Search is not successful");

                    }    
                }
            }
        }
        return res;
    }

    public List<String> visit(BodyClass bodyClass){
        System.out.println("ESTOY EN BODY CLASS!!!!!!!!!!");
        // List<String> errorList = new LinkedList<String>();
        // for (FieldDecl fieldDeclaration: bodyClass.getFieldDeclaration()) {
        //     errorList.addAll(fieldDeclaration.accept(this));
        // }
        // for (MethodDecl methodDeclaration : bodyClass.getMethodDeclaration()) {
        //     errorList.addAll(methodDeclaration.accept(this));            
        // }
        // return errorList;

        //EN BODYCLASS NO HAGO NADA O VISITO COMO LO HICE ARRIBA?????
        return new LinkedList<String>();
    }

    public List<String> visit(IdFieldDecl aThis){
        System.out.println("ESTOY EN ID FIELD DECLARATION!!!!!!!!!!");
        return new LinkedList<String>();
    }
   
    public List<String> visit(IntLiteral lit){
        System.out.println("ESTOY EN INT LITERAL!!!!!!!!!!");
        return new LinkedList<String>();
    }

    public List<String> visit(FloatLiteral lit){
        System.out.println("ESTOY EN FLOAT LITERAL!!!!!!!!!!");
        return new LinkedList<String>();
    }

    public List<String> visit(BoolLiteral lit){
        System.out.println("ESTOY EN BOOL LITERAL!!!!!!!!!!");
        return new LinkedList<String>();
    }

    public List<String> visit(SemicolonStmt stmt){
        System.out.println("ESTOY EN SEMI COLON STATEMENT!!!!!!!!!!");
        return new LinkedList<String>();
    }

    public List<String> visit(Attribute a) {
        System.out.println("ESTOY EN ATTRIBUTE!!!!!!!!!!");
        return new LinkedList<String>();
    }

    private int genOffset() {
        System.out.println("ESTOY EN GEN OFFSET!!!!!!!!!!");
        offset -= 4;
        maxOffset = offset;
        return offset;
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
