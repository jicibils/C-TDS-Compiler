// DeclarationCheckVisitor.java

package main.java.visitor;

import main.java.ast.*;
import java.util.List;
import java.util.LinkedList;

// Concrete visitor
public class DeclarationCheckVisitor implements ASTVisitor<List<String>> {

    private SymbolTable table;

    public DeclarationCheckVisitor(){
        table = new SymbolTable();
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
            Attribute attribute = new Attribute(methodDecl.getId(),methodDecl.getType());
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
            table.pushNewLevel();    
            Attribute attribute = new Attribute(method.getId(),method.getType(),method);
            if(table.insertSymbol(attribute)){
                //open level for parameters
                table.pushNewLevel();    
                for(Param param : method.getParam()){
                    errorList.addAll(param.accept(this));
                }

                Block block = method.getBlock();
                errorList.addAll(block.accept(this));

            }//ELSE {ERROR?????????????????????????????????????????}
            //close level to parameters
            table.popLevel();
            //close level to method
            table.popLevel();
        }        
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

    public List<String> visit(VarLocation loc){
        return new LinkedList<String>();
    }
    public List<String> visit(VarListLocation loc){
        return new LinkedList<String>();
    }
    // visit statements 


    public List<String> visit(AssignStmt stmt){
        return new LinkedList<String>();
    }

    public List<String> visit(ReturnStmt stmt){
        return new LinkedList<String>();
    }

    public List<String> visit(IfStatement stmt){
        return new LinkedList<String>();
    }

    public List<String> visit(ContinueStmt stmt){
        return new LinkedList<String>();
    }

    public List<String> visit(WhileStatement stmt){
        return new LinkedList<String>();
    }

    public List<String> visit(BreakStatement stmt){
        return new LinkedList<String>();
    }

    public List<String> visit(SemicolonStmt stmt){
        return new LinkedList<String>();
    }

    public List<String> visit(ForStatement stmt){
        return new LinkedList<String>();
    }

    // visit expressions


    public List<String> visit(BinOpExpr expr){
        return new LinkedList<String>();
    }
    public List<String> visit(UnaryOpExpr expr){
        return new LinkedList<String>();
    }
    public List<String> visit(MethodCallStmt stmt){
        return new LinkedList<String>();
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
    


    // visit method calls


    public List<String> visit(MethodCall call){
        return new LinkedList<String>();
    }




}
