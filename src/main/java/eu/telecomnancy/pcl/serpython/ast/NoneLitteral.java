package eu.telecomnancy.pcl.serpython.ast;

public class NoneLitteral extends Expression {
    public NoneLitteral() {
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "None";
    }
}
