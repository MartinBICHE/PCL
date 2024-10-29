package eu.telecomnancy.pcl.serpython.ast;

public class StringLitteral extends Expression {
    private String value;

    public StringLitteral(String value) {
        this.value = value;
    }

    public String getValue() {
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
        final StringLitteral other = (StringLitteral) obj;
        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
