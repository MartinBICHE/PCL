package eu.telecomnancy.pcl.serpython.ast;

public class StringLitteral extends Expression {
    private String value;

    public StringLitteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }    
}
