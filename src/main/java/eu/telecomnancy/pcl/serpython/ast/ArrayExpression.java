package eu.telecomnancy.pcl.serpython.ast;

public class ArrayExpression extends Expression {
    private Expression[] elements;

    /**
     * Constructs an ArrayExpression object with the given elements.
     *
     * @param elements the array of Expression objects representing the elements of the array
     */
    public ArrayExpression(Expression[] elements) {
        this.elements = elements;
    }

    /**
     * Returns the elements of the array.
     *
     * @return the array of Expression objects representing the elements of the array
     */
    public Expression[] getElements() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i].toString());
            if (i < elements.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        for (Expression element : elements) {
            hash = 31 * hash + element.hashCode();
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
        final ArrayExpression other = (ArrayExpression) object;
        if (this.elements.length != other.elements.length) {
            return false;
        }
        for (int i = 0; i < elements.length; i++) {
            if (!this.elements[i].equals(other.elements[i])) {
                return false;
            }
        }
        return true;
    }
}
