package eu.telecomnancy.pcl.serpython.ast;

import eu.telecomnancy.pcl.serpython.errors.AlreadyExistError;
import eu.telecomnancy.pcl.serpython.errors.LookupError;
import eu.telecomnancy.pcl.serpython.types.*;
import java.util.HashMap;

public class SymbolTable {
    private abstract static class Entry {
        private final String name;
        private Type type;

        public Entry(String name, Type type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public Type getType() {
            return type;
        }
    }

    public static class Variable extends Entry {
        public Variable(String name, Type type) {
            super(name, type);
        }

        public Variable(String name) {
            this(name, null);
        }
    }

    public static class Function extends Entry {
        public Function(String name, Type type) {
            super(name, type);
        }

        public Function(String name) {
            this(name, null);
        }
    }

    private HashMap<String, SymbolTable.Entry> table;
    private SymbolTable parent;
    private int scopeLevel;

    public SymbolTable(SymbolTable parent, int scopeLevel) {
        this.table = new HashMap<String, SymbolTable.Entry>();
        this.parent = parent;
        this.scopeLevel = scopeLevel;
    }

    public SymbolTable() {
        this(null, 0);
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    public void setScopeLevel(int scopeLevel) {
        this.scopeLevel = scopeLevel;
    }

    /**
     * Set the parent scope of the symbol table.
     * @param parent
     */
    public void setParent(SymbolTable parent) {
        this.parent = parent;
    }

    private void addSymbol(String name, Entry entry) throws AlreadyExistError {
        if(existLocally(name)) {
            throw new AlreadyExistError("Symbol '" + name + "' already exist.");
        }
        this.table.put(name, entry);
    }

    /**
     * Add a new variable to the symbol table, with the specified type.
     * @param name
     * @param type
     */
    public void addVariable(String name, Type type) throws AlreadyExistError {
        Entry entry = new Variable(name, type);
        this.addSymbol(name, entry);
    }

    /**
     * Add a new function to the symbol table, with the specified type.
     * @param name
     * @param type
     * @throws AlreadyExistError
     */
    public void addFunction(String name, Type type) throws AlreadyExistError {
        Entry entry = new Function(name, type);
        this.addSymbol(name, entry);
    }

    /**
     * Declare a new variable in the symbol table. The symbol's type is not known yet.
     * @param name
     */
    public void declareVariable(String name) throws AlreadyExistError {
        this.addVariable(name, null);
    }

    /**
     * Declare a new function in the symbol table. The full function type is not known yet.
     * @param name
     * @throws AlreadyExistError
     */
    public void declareFunction(String name) throws AlreadyExistError {
        this.addFunction(name, null);
    }

    /**
     * Check if the given symbol is declared locally.
     * @param name
     * @return
     */
    public boolean existLocally(String name) {
        return this.table.getOrDefault(name, null) != null;
    }

    /**
     * Perform a local lookup for the given symbol.
     * If the symbol is not found, there is NO recursive lookup.
     * @param name
     * @return
     * @throws LookupError
     */
    public SymbolTable.Entry localLookup(String name) throws LookupError {
        Entry result = this.table.getOrDefault(name, null);
        if(result == null) {
            throw new LookupError("Symbol '" + name + "' not found.");
        }
        return result;
    }

    /**
     * Look reccursively for the given symbol in the symbol table chain.
     * If the symbol is not found in any scope, an error is thrown.
     * @param name
     * @return the information about the looked-up symbol
     * @throws LookupError
     */
    public SymbolTable.Entry lookup(String name) throws LookupError {
        Entry result = this.table.getOrDefault(name, null);
        if(result == null) {
            if(this.parent != null) {
                return this.parent.lookup(name);
            }
            // if not found in any scope, throw an exception
            throw new LookupError("Symbol '" + name + "' not found.");
        }
        return result;
    }
}
