package eu.telecomnancy.pcl.serpython.ast;
import java.util.ArrayList;

public class Block {
    ArrayList<Statement> Statements;

    public Block(ArrayList<Statement> Statements) {
        this.Statements = Statements;
    }

    public ArrayList<Statement> get() {
        return Statements;
    }
}
