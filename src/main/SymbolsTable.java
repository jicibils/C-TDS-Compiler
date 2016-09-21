// SymbolsTable.java
package main.java.visitor;

import main.java.ast.*;
import java.util.LinkedList;
import java.util.ArrayList;


public class SymbolsTable {
	
	private ArrayList<LinkedList<AST>> table;      
	private int index; 					   


	public SymbolsTable() {
		table = new ArrayList<LinkedList<AST>>();
		index = 0;
		table.add(index,new LinkedList<AST>());
	}


	public int getIndex() {
		return index;
	}

	public void openBlock() {
		index++;
		table.add(index,new LinkedList<AST>());
	}

	public void closeBlock() {
		table.get(index).clear();
		index--;
	}


	public boolean isEmpty() {
		return (index == 0);
	}

	//table clean
	public void makeEmpty() {
		table.clear();
	}

	//block clean
	public void makeEmptyBlock() {
		table.get(index).clear();
	}

	// push a symbol in some block
	public boolean pushSymbol(Identifier symbol) {
		if (exists(symbol,index)) {
			return false;
		} else {
			table.get(index).add(symbol);
			return true;
		}
	}


	// Returns the list of symbols of an index
	
	public LinkedList<AST> getListSymbols(int index) {
		return table.get(index);
	}

	//Returns true if an element already exists in the block 
	public boolean exists(Identifier elem,int index) {
		for (AST ast : table.get(index)) {
			Identifier elemTable = (Identifier)ast;
			if (elem.getSym().equals(elemTable.getSym())) {
				return true;
			}
		}
		return false;
	}


	//Search a symbol 
	public Identifier search(String elem,int index) {
		for (AST ast : table.get(index)) {
			Identifier elemTable = (Identifier)ast;
			if (elemTable.getSym().equals(elem)) {
				return elemTable;
			} 
		}
		return null;
	}
}