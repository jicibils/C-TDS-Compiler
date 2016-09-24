package main;

import java.util.LinkedList;
import main.java.ast.AST;
import main.java.ast.Attribute;

/**
 *
 * @author
 */
public class SymbolTable{

	private LinkedList<LinkedList<AST>> stack;          //stack: LinkedList of LinkedList that represent a stack of AST nodes
	private int index;			   //index: used to storage the current level

	//Constructor
	public SymbolTable(){
		stack = new LinkedList<LinkedList<AST>>();
		index = 0;
                stack.add(new LinkedList<AST>());
	}
        
	//Insert new block
	public void pushNewLevel(){
                index++;
		LinkedList newBlock = new LinkedList<AST>();
		stack.push(newBlock);
	}

	//Delete block
	public void popLevel(){
		stack.remove(index); //
		index--;
	}

	//Get index
	public int getIndex(){
		return index;
	}

	//Get the symbols presents in a level given as argument
	public LinkedList<AST> getSymbolsAtIndex(int index){
		return stack.get(index);
	}

	//Insert a symbol in present level. If the symbol is inserted returns true, otherwise, returns false
	public boolean insertSymbol(Attribute symbol){
		if(searchByName(symbol.getId(),index)==null){  //this branch means symbols is not inserted yet
			stack.get(index).add(symbol);  //insert in top of stack
			return true;
		}
		return false;		//returns false if symbol is already inserted.
	}

	//Search Symbol by its id. In case it exists, it's returned. Otherwise, it returns null.
        public Attribute searchByName(String id, int index){
            
            //LinkedList<AST> tope = stack.get(index); //get linkedList on Top
            for(AST t : stack.get(index)){
                    Attribute a = (Attribute) t;
                    if(a.getId().equals(id));
                        return a;
            }
            return null;
        }
}