package eu.telecomnancy.pcl.serpython.ast;

public class BooleanLitteral extends Expression {
    private boolean value;

    public BooleanLitteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
