package eu.telecomnancy.pcl.serpython.ast;

public class NumberLitteral extends Expression {
    private int value;

    public NumberLitteral(int value) {
        this.value = value;
    }

    public int getValue() {
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
        final NumberLitteral other = (NumberLitteral) obj;
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}