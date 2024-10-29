package eu.telecomnancy.pcl.serpython.ast;

public class BooleanLitteral extends Expression {
    private boolean value;

    public BooleanLitteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BooleanLitteral other = (BooleanLitteral) obj;
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
