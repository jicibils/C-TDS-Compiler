// DeclarationCheckVisitor.java

package main.java.visitor;

import main.java.ast.*;
import main.java.ast.ErrorClass;
import java.util.List;
import java.util.LinkedList;

// Concrete visitor
public class DeclarationCheckVisitor implements ASTVisitor<List<ErrorClass>> {

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

    public List<ErrorClass> visit(Program program) {
        System.out.println("ESTOY EN PROGRAM!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        // table.pushNewLevel();// cuando construis el objeto ahi se crea el nivel necesario para program(class)
        for(ClassDecl classdecl : program.getClassList()){
            Attribute attribute = new Attribute(classdecl.getId(),classdecl);
            if(table.insertSymbol(attribute)){
                errorList.addAll(classdecl.accept(this));
            }else{
                String errorAssign = "Error class: already exists a class with name: " + classdecl.getId() +" ->  Line: "+classdecl.getLineNumber()+" Column: "+classdecl.getColumnNumber();
                errorList.add(new ErrorClass(classdecl.getLineNumber(), classdecl.getColumnNumber(), errorAssign));
            }
        }
        table.popLevel();
        return errorList;
    }

    //visit declarations 


    public List<ErrorClass> visit(ClassDecl cDecl){
        System.out.println("ESTOY EN CLASS DECLARATION!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
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
                String errorAssign = "Error method: already exists a method with name: " + methodDecl.getId() +" ->  Line: "+methodDecl.getLineNumber()+" Column: "+methodDecl.getColumnNumber();
                errorList.add(new ErrorClass(methodDecl.getLineNumber(), methodDecl.getColumnNumber(), errorAssign));
            }
        }
        table.popLevel();
        return errorList;
    }

    public List<ErrorClass> visit(FieldDecl fieldDecl){
        System.out.println("ESTOY EN FIELD DECLARATION!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        List<IdFieldDecl> list = new LinkedList<IdFieldDecl>();
        Type type;
        String id;
        int i = 0; 
        type = fieldDecl.getType();
        list = fieldDecl.getListId();
        while (i<list.size()){
            id = list.get(i).getId();

            //OFFSET
            Attribute attribute = new Attribute(id,type,fieldDecl);
            attribute.setOffset(genOffset());


            if (!(table.insertSymbol(attribute))){
                String errorAssign = "Error fieldDecl: already exists a field with name: " + id +" ->  Line: "+fieldDecl.getLineNumber()+" Column: "+fieldDecl.getColumnNumber();
                errorList.add(new ErrorClass(fieldDecl.getLineNumber(), fieldDecl.getColumnNumber(), errorAssign));
            }
            i++;
        }
        return errorList;
    }


    public List<ErrorClass> visit(MethodDecl method){
        System.out.println("ESTOY EN METHOD DECLARATION!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        if (!(method.isExtern())){
            
            if(!method.thereIsReturn()){
                String errorAssign = "Error method: there isn't return statement in the method: " + method.getId() +" ->  Line: "+method.getLineNumber()+" Column: "+method.getColumnNumber();
                errorList.add(new ErrorClass(method.getLineNumber(), method.getColumnNumber(), errorAssign));
            }
            
            //open level for parameters
            table.pushNewLevel();    

            //SET MAXOFFSET 
            // EMPEZAR SIEMPRE ACA LOS MAXOFFSET CON -4
            offset = -4;

            for(Param param : method.getParam()){
                errorList.addAll(param.accept(this));
            }

            Block block = method.getBlock();

            errorList.addAll(block.accept(this));

            method.setMaxOffset(offset);
            System.out.println("MAX OFFSET declaration !!!!!!!!!!");
            System.out.println(method.getMaxOffset());
        }

        //close level to parameters
        table.popLevel();
        return errorList;
    }        
    



    public List<ErrorClass> visit(Param param){
        System.out.println("ESTOY EN PARAM!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        Attribute attribute = new Attribute(param.getId(),param.getType(),param);
        if(!(table.insertSymbol(attribute))){
                String errorAssign = "Error parameters: already exists a parameter with name: " + param.getId() +" ->  Line: "+param.getLineNumber()+" Column: "+param.getColumnNumber();
                errorList.add(new ErrorClass(param.getLineNumber(), param.getColumnNumber(), errorAssign));
        }
        return errorList;
    }

    
    // visit locations  

    public List<ErrorClass> visit(Block block){
        System.out.println("ESTOY EN BLOCK!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
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

    public List<ErrorClass> visit(AssignStmt stmt){
        System.out.println("ESTOY EN ASSIGN STATEMENT!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        errorList.addAll(stmt.getLocation().accept(this));
        errorList.addAll(stmt.getExpression().accept(this));
        return errorList;
    }

    public List<ErrorClass> visit(MethodCallStmt stmt){
        System.out.println("ESTOY EN METHOD CALL STATEMENT!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<ErrorClass>();
        errorList.addAll(stmt.getMethodCall().accept(this));
        return errorList;
    }

    public List<ErrorClass> visit(IfStatement stmt){
        System.out.println("ESTOY EN IF STATEMENT!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        errorList.addAll(stmt.getCondition().accept(this));
        errorList.addAll(stmt.getIfBlock().accept(this));
        if (stmt.thereIsElseBlock()){
            errorList.addAll(stmt.getElseBlock().accept(this));
        }
        return errorList;
    }

    public List<ErrorClass> visit(ForStatement stmt){
        System.out.println("ESTOY EN FOR STATEMENT!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        count++;
        errorList.addAll(stmt.getAssign().accept(this));
        errorList.addAll(stmt.getCondition().accept(this));
        errorList.addAll(stmt.getBlock().accept(this));
        count--;
        return errorList;
    }

    public List<ErrorClass> visit(WhileStatement stmt){
        System.out.println("ESTOY EN WHILE STATEMENT!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        count++;
        errorList.addAll(stmt.getExpression().accept(this));
        errorList.addAll(stmt.getBlock().accept(this));
        count--;
        return errorList;
    }

    public List<ErrorClass> visit(ReturnStmt stmt){
        System.out.println("ESTOY EN RETURN STATEMENT!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        if(stmt.getExpression() != null){
            errorList.addAll(stmt.getExpression().accept(this));
        }
        return errorList;
    }

    public List<ErrorClass> visit(BreakStatement stmt){
        System.out.println("ESTOY EN BREAK STATEMENT!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        if(count == 0){
            String errorAssign = "Error BreakStatement: BREAK is outside of a loop:  ->  Line: "+stmt.getLineNumber()+" Column: "+stmt.getColumnNumber();
            errorList.add(new ErrorClass(stmt.getLineNumber(), stmt.getColumnNumber(), errorAssign));
         }
        return errorList;
    }

    public List<ErrorClass> visit(ContinueStmt stmt){
        System.out.println("ESTOY EN CONTINUE STATEMENT!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        if(count == 0){
            String errorAssign = "Error ContinueStatement: CONTINUE is outside of a loop:  ->  Line: "+stmt.getLineNumber()+" Column: "+stmt.getColumnNumber();
            errorList.add(new ErrorClass(stmt.getLineNumber(), stmt.getColumnNumber(), errorAssign));
         }
        return errorList;
    }


    public List<ErrorClass> visit(BinOpExpr expr){
        System.out.println("ESTOY EN BIN OP EXPRESSION!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        errorList.addAll(expr.getLeftOperand().accept(this));
        errorList.addAll(expr.getRightOperand().accept(this));
        return errorList;
    }
    public List<ErrorClass> visit(UnaryOpExpr expr){
        System.out.println("ESTOY EN UNARY OP EXPRESSION!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        errorList.addAll(expr.getOperand().accept(this));
        return errorList;
    }

    public List<ErrorClass> visit(VarLocation loc){
        System.out.println("ESTOY EN VAR LOCATION!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        boolean flagForError = false;
        Attribute declaration;
        //if list of ids is empty is a simple location. form ID
        if(loc.getArrayId().isEmpty()){
            declaration = search(loc.getId());
            if (declaration != null) {
                // add the location 

                //set Offset
                loc.setDeclaration(declaration);
                //METERLE EL OFFSET QUE YA TRAJO!! NO UNO NUEVO
                loc.setOffset(declaration.getOffset());

            } else {
                // The location not founded
                flagForError = true;
            }
            if (flagForError) {
                String errorAssign = "Error VarLocation: LOCATION doesn't exist:  ->  Line: "+loc.getLineNumber()+" Column: "+loc.getColumnNumber();
                errorList.add(new ErrorClass(loc.getLineNumber(), loc.getColumnNumber(), errorAssign));
            }
        }else{

            //if list of ids isn't empty isn't a simple location
            // The location is of the form ID.ID
    
            declaration = search(loc.getId());
            if (declaration!=null) {
                // add the location 
                //set Offset
                loc.setDeclaration(declaration);
                loc.setOffset(declaration.getOffset());


            } else {
                // The location not founded
                flagForError = true;
            }
            if (flagForError) {
                String errorAssign = "Error VarLocation: LOCATION doesn't exist:  ->  Line: "+loc.getLineNumber()+" Column: "+loc.getColumnNumber();
                errorList.add(new ErrorClass(loc.getLineNumber(), loc.getColumnNumber(), errorAssign));
            }
        }
        return errorList;
    }


    public List<ErrorClass> visit(VarListLocation loc){
        System.out.println("ESTOY EN VAR LIST LOCATION!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        boolean flagForError = false;
        Attribute declaration;
        //if list of ids is empty is a simple location. form ID[expr]
        if(loc.getArrayId().isEmpty()){
            declaration = search(loc.getId());
            if (declaration != null) {
                // add the location 
                //set Offset
                loc.setDeclaration(declaration);
                loc.setOffset(declaration.getOffset());

                errorList.addAll(loc.getExpression().accept(this)); 
            } else {
                // The location not founded
                flagForError = true;
            }
            if (flagForError) {
                String errorAssign = "Error VarListLocation: LOCATION doesn't exist:  ->  Line: "+loc.getLineNumber()+" Column: "+loc.getColumnNumber();
                errorList.add(new ErrorClass(loc.getLineNumber(), loc.getColumnNumber(), errorAssign));
            }
        }else{

            //if list of ids isn't empty isn't a simple location
            // The location is of the form ID.ID[expr]
    
            declaration = search(loc.getId());
            if (declaration!=null) {
                // add the location 
                //set Offset
                loc.setDeclaration(declaration);
                loc.setOffset(declaration.getOffset());

                errorList.addAll(loc.getExpression().accept(this)); 
            } else {
                // The location not founded
                flagForError = true;
            }
            if (flagForError) {
                String errorAssign = "Error VarListLocation: LOCATION doesn't exist:  ->  Line: "+loc.getLineNumber()+" Column: "+loc.getColumnNumber();
                errorList.add(new ErrorClass(loc.getLineNumber(), loc.getColumnNumber(), errorAssign));
            }
        }
        return errorList;
    }



    // visit method call
    public List<ErrorClass> visit(MethodCall call){
        System.out.println("ESTOY EN METHOD CALL!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
        Attribute exist = null;
        exist = search(call.getId());
        
        if(exist == null){
            String errorAssign = "Error MethodCall: method declaration was not founded :  ->  Line: "+call.getLineNumber()+" Column: "+call.getColumnNumber();
            errorList.add(new ErrorClass(call.getLineNumber(), call.getColumnNumber(), errorAssign));
        }else{
            call.setType(exist.getType());
            for (Expression expr : call.getArgList()) {
                errorList.addAll(expr.accept(this));
            }
        }
        return errorList;
    }

    //method for search declaration in simbolTable
    private Attribute search(String id) {
        System.out.println("ESTOY EN SEARCH!!!!!!!!!!");
        List<ErrorClass> errorList = new LinkedList<>();
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
                        if (res == null) {
                            String errorAssign = "Error Search: The search is not successful";
                            errorList.add(new ErrorClass(0,0, errorAssign));
                        }
                    }    
                }
            }
        }
        return res;
    }

    public List<ErrorClass> visit(BodyClass bodyClass){
        System.out.println("ESTOY EN BODY CLASS!!!!!!!!!!");
        // List<ErrorClass> errorList = new LinkedList<ErrorClass>();
        // for (FieldDecl fieldDeclaration: bodyClass.getFieldDeclaration()) {
        //     errorList.addAll(fieldDeclaration.accept(this));
        // }
        // for (MethodDecl methodDeclaration : bodyClass.getMethodDeclaration()) {
        //     errorList.addAll(methodDeclaration.accept(this));            
        // }
        // return errorList;

        //EN BODYCLASS NO HAGO NADA O VISITO COMO LO HICE ARRIBA?????
        return new LinkedList<ErrorClass>();
    }

    public List<ErrorClass> visit(IdFieldDecl aThis){
        System.out.println("ESTOY EN ID FIELD DECLARATION!!!!!!!!!!");
        return new LinkedList<ErrorClass>();
    }
   
    public List<ErrorClass> visit(IntLiteral lit){
        System.out.println("ESTOY EN INT LITERAL!!!!!!!!!!");
        return new LinkedList<ErrorClass>();
    }

    public List<ErrorClass> visit(FloatLiteral lit){
        System.out.println("ESTOY EN FLOAT LITERAL!!!!!!!!!!");
        return new LinkedList<ErrorClass>();
    }

    public List<ErrorClass> visit(BoolLiteral lit){
        System.out.println("ESTOY EN BOOL LITERAL!!!!!!!!!!");
        return new LinkedList<ErrorClass>();
    }

    public List<ErrorClass> visit(SemicolonStmt stmt){
        System.out.println("ESTOY EN SEMI COLON STATEMENT!!!!!!!!!!");
        return new LinkedList<ErrorClass>();
    }

    public List<ErrorClass> visit(Attribute a) {
        System.out.println("ESTOY EN ATTRIBUTE!!!!!!!!!!");
        return new LinkedList<ErrorClass>();
    }

    private int genOffset() {
        System.out.println("ESTOY EN GEN OFFSET!!!!!!!!!!");
        System.out.println(offset);
        offset -= 4;
        System.out.println(offset);
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
