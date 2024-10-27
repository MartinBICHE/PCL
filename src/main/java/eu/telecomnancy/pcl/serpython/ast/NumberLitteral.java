package eu.telecomnancy.pcl.serpython.ast;

public class NumberLitteral extends Expression {
    private int value;

    public NumberLitteral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}