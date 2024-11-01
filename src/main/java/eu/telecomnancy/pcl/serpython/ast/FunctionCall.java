package eu.telecomnancy.pcl.serpython.ast;

public class FunctionCall extends Expression {
    private String name;
    private Expression[] arguments;

    /**
     * Constructs a FunctionCall object with the given name and arguments.
     *
     * @param name      the name of the function
     * @param arguments the array of Expression objects representing the arguments of the function
     */
    public FunctionCall(String name, Expression[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    /**
     * Returns the name of the function.
     *
     * @return the name of the function
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the arguments of the function.
     *
     * @return the array of Expression objects representing the arguments of the function
     */
    public Expression[] getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("(");
        for (var i = 0; i < arguments.length; i++) {
            sb.append(arguments[i].toString());
            if (i < arguments.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        for (Expression argument : arguments) {
            hash = 57 * hash + argument.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        final FunctionCall other = (FunctionCall) object;
        if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.arguments.length != other.arguments.length) {
            return false;
        }
        for (int i = 0; i < arguments.length; i++) {
            if (!this.arguments[i].equals(other.arguments[i])) {
                return false;
            }
        }
        return true;
    }
}
